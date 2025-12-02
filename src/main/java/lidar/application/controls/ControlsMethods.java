package lidar.application.controls;

import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import lidar.infrastructure.OpcUaConnector;

public class ControlsMethods {
    private static final int NS_IDX = 2;

    public static boolean servFixed(OpcUaConnector connector) throws Exception {
        Variant[] result = connector.callMethod(NS_IDX, "ServFixed");
        return result != null && result.length > 0 && (Boolean) result[0].getValue();
    }

    public static boolean servRandom(OpcUaConnector connector) throws Exception {
        Variant[] result = connector.callMethod(NS_IDX, "ServRandom");
        return result != null && result.length > 0 && (Boolean) result[0].getValue();
    }

    public static boolean servOutOfRange(OpcUaConnector connector) throws Exception {
        Variant[] result = connector.callMethod(NS_IDX, "ServOutOfRange");
        return result != null && result.length > 0 && (Boolean) result[0].getValue();
    }

    public static Variant[] methodCmd(OpcUaConnector connector, String methodName) throws Exception {
        return connector.callMethod(NS_IDX, methodName);
    }

    public static boolean changeFixVal(OpcUaConnector connector, String nodeName, Object value) throws Exception {
        Variant[] result = connector.callMethod(NS_IDX, "change_fix_val",
                new Variant(nodeName), new Variant(value));
        return result != null && result.length > 0 && (Boolean) result[0].getValue();
    }

    public static boolean updateTime(OpcUaConnector connector, int heartbeatValue) throws Exception {
        Variant[] result = connector.callMethod(NS_IDX, "update_time", new Variant(heartbeatValue));
        return result != null && result.length > 0 && (Boolean) result[0].getValue();
    }
}