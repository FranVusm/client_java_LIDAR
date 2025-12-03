package lidar.infrastructure;

import org.eclipse.milo.opcua.sdk.client.DiscoveryClient;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.identity.AnonymousProvider;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.*;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture; // Still necessary for CompletableFuture<?> in disconnect
import java.util.function.BiConsumer;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

public class OpcUaConnector {
    private static final Logger logger = LoggerFactory.getLogger(OpcUaConnector.class);

    private String endpointUrl;
    private boolean isSecure;
    private String certPath;
    private String keyPath;

    private OpcUaClient client;
    private Object subscription; // We will use Object for now until finding the correct type
    private final List<Object> monitoredItems = new ArrayList<>();

    public OpcUaConnector(String endpointUrl, boolean isSecure, String certPath, String keyPath) {
        this.endpointUrl = endpointUrl;
        this.isSecure = isSecure;
        this.certPath = certPath;
        this.keyPath = keyPath;
    }

    public void setConnection(String endpointUrl, boolean isSecure, String certPath, String keyPath) {
        this.endpointUrl = endpointUrl;
        this.isSecure = isSecure;
        this.certPath = certPath;
        this.keyPath = keyPath;
    }

    public void restartConnection() throws Exception {
        disconnect();
        connect();
    }

    public OpcUaClient getClient() {
        return client;
    }

    public void connect() throws Exception {
        if (isSecure) {
            connectSecure();
        } else {
            connectInsecure();
        }
    }

    private void connectInsecure() throws Exception {
        // Método original simplificado
        client = OpcUaClient.create(endpointUrl);
        client.connect();
        logger.info("Connected to OPC UA server (Insecure): {}", endpointUrl);
    }

    private void connectSecure() throws Exception {
        logger.info("Initiating SECURE connection to {}...", endpointUrl);
        logger.info("Loading Cert: {}", certPath);
        logger.info("Loading Key: {}", keyPath);

        // 1. Load Certificate and Private Key from files
        X509Certificate certificate = loadCertificate(certPath);
        PrivateKey privateKey = loadPrivateKey(keyPath);
        KeyPair keyPair = new KeyPair(certificate.getPublicKey(), privateKey);

        // 2. Discover endpoints available in the server
        List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointUrl).get();

        // 3. Filter to find Basic256Sha256 + SignAndEncrypt
        EndpointDescription endpoint = endpoints.stream()
                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.Basic256Sha256.getUri()))
                .filter(e -> e.getSecurityMode() == MessageSecurityMode.SignAndEncrypt)
                .findFirst()
                .orElseThrow(() -> new Exception("No secure endpoint found matching Basic256Sha256 & SignAndEncrypt"));

        logger.info("Found Secure Endpoint: {} [{}]", endpoint.getSecurityPolicyUri(), endpoint.getSecurityMode());

        // 4. Configure the Client
        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setApplicationName(LocalizedText.english("SI3 Java Client"))
                .setApplicationUri("urn:si3:client:java")
                .setEndpoint(endpoint)
                // Milo expects a "Certificate Chain" even if it's just one self-signed cert.
                .setCertificate(certificate) // Sets the leaf certificate
                .setCertificateChain(new X509Certificate[] { certificate }) // Sets the chain explicitly
                .setKeyPair(keyPair)
                .setIdentityProvider(new AnonymousProvider())
                .setRequestTimeout(UInteger.valueOf(5000))
                .build();

        // 5. Create and connect
        client = OpcUaClient.create(config);
        client.connect();
        logger.info("Connected SECURELY to OPC UA server: {}", endpointUrl);
    }

    private X509Certificate loadCertificate(String path) throws Exception {
        try (FileInputStream fis = new FileInputStream(path)) {
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            return (X509Certificate) fact.generateCertificate(fis);
        }
    }

    private PrivateKey loadPrivateKey(String path) throws Exception {
        // We assume PKCS#8 format (standard for Java)
        // If your key is pure PEM (begins with -----BEGIN PRIVATE KEY-----), we need to
        // clean it
        String keyContent = new String(Files.readAllBytes(Paths.get(path)));

        // Clean PEM headers if they exist
        String privateKeyPEM = keyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    public void disconnect() throws Exception {
        if (subscription != null) {
            // Attempt to remove subscription if it has delete method
            try {
                if (subscription instanceof CompletableFuture) {
                    ((CompletableFuture<?>) subscription).thenAccept(s -> {
                        try {
                            // Attempt delete if exists
                            subscription.getClass().getMethod("delete").invoke(s);
                        } catch (Exception e) {
                            // Ignore
                        }
                    });
                }
            } catch (Exception e) {
                // Ignore
            }
            subscription = null;
            monitoredItems.clear();
        }
        if (client != null) {
            client.disconnect();
            client = null;
        }
        logger.info("Disconnected from OPC UA server");
    }

    public DataValue readNode(String nodeId) throws Exception {
        NodeId node = NodeId.parse(nodeId);

        return client.readValue(0.0, TimestampsToReturn.Both, node);

    }

    public List<DataValue> readNodes(List<String> nodeIds) throws Exception {
        List<NodeId> nodes = new ArrayList<>();
        for (String nodeId : nodeIds) {
            nodes.add(NodeId.parse(nodeId));
        }
        ReadValueId[] readValueIds = new ReadValueId[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            readValueIds[i] = new ReadValueId(nodes.get(i), AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);
        }

        ReadResponse response = client.read(0.0, TimestampsToReturn.Both, Arrays.asList(readValueIds));

        List<DataValue> values = new ArrayList<>();
        for (DataValue dv : response.getResults()) {
            values.add(dv);
        }
        return values;
    }

    public void writeNode(String nodeId, Object value) throws Exception {
        NodeId node = NodeId.parse(nodeId);
        Variant variant = new Variant(value);
        DataValue dataValue = new DataValue(variant, StatusCode.GOOD, null);

        WriteValue writeValue = new WriteValue(node, AttributeId.Value.uid(), null, dataValue);

        WriteResponse response = client.write(Arrays.asList(writeValue));

        StatusCode code = response.getResults()[0];
        if (!code.isGood()) {
            throw new RuntimeException("Write failed: " + code);
        }
    }

    public void createSubscription(double publishingInterval) throws Exception {
        subscription = publishingInterval;
        logger.info("Subscription configured with publishing interval: {} ms", publishingInterval);
    }

    public void subscribeToNodes(
            List<String> nodeIds,
            BiConsumer<Object, DataValue> onValueChange) throws Exception {

        if (subscription == null) {
            throw new IllegalStateException("Subscription not created. Call createSubscription first.");
        }

        logger.info("Subscribed to {} nodes (using polling mode)", nodeIds.size());
        monitoredItems.addAll(nodeIds);
    }

    public Variant[] callMethod(int namespaceIndex, String methodName, Variant... inputArguments) throws Exception {
        NodeId objectsNodeId = Identifiers.ObjectsFolder;
        NodeId si3NodeId = findObjectNode(objectsNodeId, namespaceIndex, "SI3");
        NodeId methodNodeId = findMethodNode(si3NodeId, methodName);

        CallMethodRequest request = new CallMethodRequest(
                si3NodeId,
                methodNodeId,
                inputArguments);

        CallResponse response = client.call(Arrays.asList(request));

        if (response.getResults() != null && response.getResults().length > 0) {
            CallMethodResult result = response.getResults()[0];
            if (result.getStatusCode().isGood()) {
                return result.getOutputArguments();
            } else {
                throw new RuntimeException("Method call failed: " + result.getStatusCode());
            }
        } else {
            throw new RuntimeException("No results from method call");
        }
    }

    private NodeId findObjectNode(NodeId parentNodeId, int namespaceIndex, String objectName) throws Exception {
        BrowseDescription browseDesc = new BrowseDescription(
                parentNodeId,
                BrowseDirection.Forward,
                Identifiers.Organizes,
                true,
                UInteger.valueOf(0),
                UInteger.valueOf(0));

        BrowseResult browseResult = client.browse(browseDesc);
        ReferenceDescription[] refs = browseResult.getReferences();

        for (ReferenceDescription ref : refs) {
            if (ref.getNodeClass() == NodeClass.Object || ref.getNodeClass() == NodeClass.ObjectType) {
                String foundName = ref.getBrowseName().getName();
                if (foundName.equalsIgnoreCase(objectName)) {
                    return (NodeId) ref.getNodeId().toNodeId(client.getNamespaceTable()).get();
                }
            }
        }

        throw new RuntimeException("Object not found: " + objectName + " in namespace " + namespaceIndex);
    }

    private NodeId findMethodNode(NodeId parentNodeId, String methodName) throws Exception {
        BrowseDescription browseDesc = new BrowseDescription(
                parentNodeId,
                BrowseDirection.Forward,
                Identifiers.HasComponent,
                true,
                UInteger.valueOf(0),
                UInteger.valueOf(0));

        BrowseResult browseResult = client.browse(browseDesc);

        ReferenceDescription[] refs = browseResult.getReferences();
        List<String> availableMethods = new ArrayList<>();

        for (ReferenceDescription ref : refs) {
            if (ref.getNodeClass() == NodeClass.Method) {
                String methodNameFound = ref.getBrowseName().getName();
                availableMethods.add(methodNameFound);
                if (methodNameFound.equalsIgnoreCase(methodName)) {
                    return (NodeId) ref.getNodeId().toNodeId(client.getNamespaceTable()).get();
                }
            }
        }

        String availableList = availableMethods.isEmpty() ? "(ninguno)" : String.join(", ", availableMethods);
        throw new RuntimeException(String.format(
                "Method not found: '%s'. Métodos disponibles en SI3: %s",
                methodName, availableList));
    }
}