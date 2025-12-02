package lidar.client.domain.dto;

import lidar.infrastructure.OpcUaConnector;

public class LIDARSetters {

    // ============================================================================
    // Setter functions for OPC UA connection (LIDAR)
    // ============================================================================

    // ------------------------------------------------------------------------
    // SET_LIDAR_*  → valores de configuración
    // ------------------------------------------------------------------------

    public static void setLaserEnable(OpcUaConnector connector, Boolean value) throws Exception {
        // SET_LIDAR_LASER_ENABLE (boolean)
        connector.writeNode("ns=2;s=lidar_set_laser_enable", value);
    }

    public static void setHvEnable(OpcUaConnector connector, Boolean value) throws Exception {
        // SET_LIDAR_HV_ENABLE (boolean)
        connector.writeNode("ns=2;s=lidar_set_hv_enable", value);
    }

    public static void setLaserPrf(OpcUaConnector connector, Integer value) throws Exception {
        // SET_LIDAR_LASER_PRF (int32)
        connector.writeNode("ns=2;s=lidar_set_laser_prf", value);
    }

    public static void setTargetAz(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_TARGET_AZ (double)
        connector.writeNode("ns=2;s=lidar_set_target_az", value);
    }

    public static void setTargetEl(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_TARGET_EL (double)
        connector.writeNode("ns=2;s=lidar_set_target_el", value);
    }

    public static void setScanSpeedAz(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_SCAN_SPEED_AZ (double)
        connector.writeNode("ns=2;s=lidar_set_scan_speed_az", value);
    }

    public static void setScanSpeedEl(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_SCAN_SPEED_EL (double)
        connector.writeNode("ns=2;s=lidar_set_scan_speed_el", value);
    }

    public static void setScanModeSelect(OpcUaConnector connector, Integer value) throws Exception {
        // SET_LIDAR_SCAN_MODE_SELECT (int32)
        connector.writeNode("ns=2;s=lidar_set_scan_mode_select", value);
    }

    public static void setBinWidth(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_BIN_WIDTH (double)
        connector.writeNode("ns=2;s=lidar_set_bin_width", value);
    }

    public static void setAccumulationPulses(OpcUaConnector connector, Integer value) throws Exception {
        // SET_LIDAR_ACCUMULATION_PULSES (int32)
        connector.writeNode("ns=2;s=lidar_set_accumulation_pulses", value);
    }

    public static void setRasterWidth(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_RASTER_WIDTH (double)
        connector.writeNode("ns=2;s=lidar_set_raster_width", value);
    }

    public static void setRasterHeight(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_RASTER_HEIGHT (double)
        connector.writeNode("ns=2;s=lidar_set_raster_height", value);
    }

    public static void setConeAngle(OpcUaConnector connector, Double value) throws Exception {
        // SET_LIDAR_CONE_ANGLE (double)
        connector.writeNode("ns=2;s=lidar_set_cone_angle", value);
    }

    public static void setCmdHome(OpcUaConnector connector, Boolean value) throws Exception {
        // SET_LIDAR_CMD_HOME (boolean)
        connector.writeNode("ns=2;s=lidar_set_cmd_home", value);
    }

    public static void setCmdPark(OpcUaConnector connector, Boolean value) throws Exception {
        // SET_LIDAR_CMD_PARK (boolean)
        connector.writeNode("ns=2;s=lidar_set_cmd_park", value);
    }

    public static void setStartAcquisition(OpcUaConnector connector, Boolean value) throws Exception {
        // SET_LIDAR_START_ACQUISITION (boolean)
        connector.writeNode("ns=2;s=lidar_set_start_acquisition", value);
    }

    // ------------------------------------------------------------------------
    // CMD_LIDAR_*  → comandos booleanos
    // ------------------------------------------------------------------------

    public static void cmdUpdateStop(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_UPDATESTOP (boolean)
        connector.writeNode("ns=2;s=lidar_updatestop", value);
    }

    public static void cmdUpdateResume(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_UPDATERESUME (boolean)
        connector.writeNode("ns=2;s=lidar_updateresume", value);
    }

    public static void cmdSimulOn(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_SIMUL_ON (boolean)
        connector.writeNode("ns=2;s=lidar_simul_on", value);
    }

    public static void cmdSimulOff(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_SIMUL_OFF (boolean)
        connector.writeNode("ns=2;s=lidar_simul_off", value);
    }

    public static void cmdServerShutdown(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_SERVERSHUTDOWN (boolean)
        connector.writeNode("ns=2;s=lidar_servershutdown", value);
    }

    public static void cmdErrorInfo(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_ERROR_INFO (boolean)
        connector.writeNode("ns=2;s=lidar_error_info", value);
    }

    public static void cmdErrorReset(OpcUaConnector connector, Boolean value) throws Exception {
        // CMD_LIDAR_ERROR_RESET (boolean)
        connector.writeNode("ns=2;s=lidar_error_reset", value);
    }

    // ------------------------------------------------------------------------
    // MODE_LIDAR_GO_*  → cambios de modo
    // ------------------------------------------------------------------------

    public static void modeGoLoaded(OpcUaConnector connector, Boolean value) throws Exception {
        // MODE_LIDAR_GO_LOADED (boolean)
        connector.writeNode("ns=2;s=lidar_go_loaded", value);
    }

    public static void modeGoStandby(OpcUaConnector connector, Boolean value) throws Exception {
        // MODE_LIDAR_GO_STANDBY (boolean)
        connector.writeNode("ns=2;s=lidar_go_standby", value);
    }

    public static void modeGoOnline(OpcUaConnector connector, Boolean value) throws Exception {
        // MODE_LIDAR_GO_ONLINE (boolean)
        connector.writeNode("ns=2;s=lidar_go_online", value);
    }

    public static void modeGoMaintenance(OpcUaConnector connector, Boolean value) throws Exception {
        // MODE_LIDAR_GO_MAINTENANCE (boolean)
        // Ojo: en el Excel viene escrito "lidar_go_maintenace" (con typo), lo respeto tal cual.
        connector.writeNode("ns=2;s=lidar_go_maintenace", value);
    }
}
