package lidar.infrastructure;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OpcUaConnectorTest {

    private OpcUaConnector connector;

    private MockedStatic<OpcUaClient> mockedStaticClient;

    @Mock
    private OpcUaClient mockClientInstance1;

    @Mock
    private OpcUaClient mockClientInstance2;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        connector = new OpcUaConnector("opc.tcp://localhost:4840", false, null, null);
        mockedStaticClient = mockStatic(OpcUaClient.class);

        when(mockClientInstance1.connect()).thenReturn(mockClientInstance1);
        when(mockClientInstance2.connect()).thenReturn(mockClientInstance2);
        when(mockClientInstance1.disconnect()).thenReturn(mockClientInstance1);
    }

    @AfterEach
    void tearDown() {
        mockedStaticClient.close();
    }

    @Test
    void testSetConnection_UpdatesEndpointUrl() throws Exception {
        String initialUrl = "opc.tcp://initial:4840";
        String newUrl = "opc.tcp://updated:4840";

        mockedStaticClient.when(() -> OpcUaClient.create(anyString()))
                .thenReturn(mockClientInstance1);

        connector.setConnection(initialUrl, false, null, null);
        connector.setConnection(newUrl, false, "cert", "key");

        connector.connect();

        mockedStaticClient.verify(() -> OpcUaClient.create(newUrl), times(1));

        verify(mockClientInstance1, times(1)).connect();
    }

    @Test
    void testRestartConnection_PerformsDisconnectAndReconnect() throws Exception {

        mockedStaticClient.when(() -> OpcUaClient.create(anyString()))
                .thenReturn(mockClientInstance1)
                .thenReturn(mockClientInstance2);

        connector.connect();

        assertSame(mockClientInstance1, connector.getClient());

        connector.restartConnection();

        verify(mockClientInstance1, times(1)).disconnect();

        mockedStaticClient.verify(() -> OpcUaClient.create(anyString()), times(2));

        verify(mockClientInstance2, times(1)).connect();

        assertSame(mockClientInstance2, connector.getClient());
    }
}