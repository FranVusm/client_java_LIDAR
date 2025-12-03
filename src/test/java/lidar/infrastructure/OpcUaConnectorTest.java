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

    // Mockeamos la clase estática OpcUaClient
    private MockedStatic<OpcUaClient> mockedStaticClient;

    @Mock
    private OpcUaClient mockClientInstance1; // Simula el primer cliente

    @Mock
    private OpcUaClient mockClientInstance2; // Simula el segundo cliente (tras el restart)

    @BeforeEach
    void setUp() throws Exception { // <--- AÑADIR "throws Exception" AQUÍ
        MockitoAnnotations.openMocks(this);

        connector = new OpcUaConnector("opc.tcp://localhost:4840", false, null, null);
        mockedStaticClient = mockStatic(OpcUaClient.class);

        // Ahora el compilador ya no se quejará de la UaException aquí dentro
        when(mockClientInstance1.connect()).thenReturn(mockClientInstance1);
        when(mockClientInstance2.connect()).thenReturn(mockClientInstance2);
        when(mockClientInstance1.disconnect()).thenReturn(mockClientInstance1);
    }

    @AfterEach
    void tearDown() {
        // ES CRÍTICO cerrar el mock estático después de cada test para no afectar a
        // otros
        mockedStaticClient.close();
    }

    @Test
    void testSetConnection_UpdatesEndpointUrl() throws Exception {
        // 1. Configuración Inicial
        String initialUrl = "opc.tcp://initial:4840";
        String newUrl = "opc.tcp://updated:4840";

        // Simulamos que OpcUaClient.create devuelve nuestro mock
        mockedStaticClient.when(() -> OpcUaClient.create(anyString()))
                .thenReturn(mockClientInstance1);

        connector.setConnection(initialUrl, false, null, null);

        // 2. Ejecutar setConnection con la NUEVA url
        connector.setConnection(newUrl, false, "cert", "key");

        // 3. Ejecutar connect() para verificar que usa los nuevos datos
        connector.connect();

        // 4. Verificación
        // Verificamos que OpcUaClient.create se llamó con la "newUrl", no con la
        // inicial
        mockedStaticClient.verify(() -> OpcUaClient.create(newUrl), times(1));

        // Verificamos que se llamó a connect() en la instancia
        verify(mockClientInstance1, times(1)).connect();
    }

    @Test
    void testRestartConnection_PerformsDisconnectAndReconnect() throws Exception {
        // Escenario: El conector ya está conectado y queremos reiniciarlo.

        // 1. Preparamos el mock estático para devolver dos clientes distintos
        // secuencialmente
        mockedStaticClient.when(() -> OpcUaClient.create(anyString()))
                .thenReturn(mockClientInstance1) // Primera llamada (conexión inicial)
                .thenReturn(mockClientInstance2); // Segunda llamada (tras restart)

        // Hacemos la conexión inicial
        connector.connect();

        // Verificamos que estamos usando el cliente 1
        assertSame(mockClientInstance1, connector.getClient());

        // 2. EJECUTAR restartConnection
        connector.restartConnection();

        // 3. VERIFICACIONES

        // A) Verificar que el cliente 1 se desconectó
        verify(mockClientInstance1, times(1)).disconnect();

        // B) Verificar que se creó un nuevo cliente (OpcUaClient.create se llamó 2
        // veces en total)
        mockedStaticClient.verify(() -> OpcUaClient.create(anyString()), times(2));

        // C) Verificar que el cliente 2 se conectó
        verify(mockClientInstance2, times(1)).connect();

        // D) Verificar que el conector ahora tiene la referencia al cliente 2
        assertSame(mockClientInstance2, connector.getClient());
    }
}