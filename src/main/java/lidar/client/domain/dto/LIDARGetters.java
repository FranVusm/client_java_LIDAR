package lidar.client.domain.dto;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import lidar.infrastructure.OpcUaConnector;
import java.util.Arrays;

public class LIDARGetters {

    // ==========================================
    // CONVERSION HELPERS
    // ==========================================

    private static Integer convertToInteger(Object value) {
        if (value == null)
            return null;
        if (value instanceof Integer)
            return (Integer) value;
        if (value instanceof Long)
            return ((Long) value).intValue();
        if (value instanceof Number)
            return ((Number) value).intValue();
        return null;
    }

    // Specific helper for Float (some variables explicitly ask for float)
    private static Float convertToFloat(Object value) {
        if (value == null)
            return null;
        if (value instanceof Float)
            return (Float) value;
        if (value instanceof Double)
            return ((Double) value).floatValue();
        if (value instanceof Number)
            return ((Number) value).floatValue();
        return null;
    }

    private static Boolean convertToBoolean(Object value) {
        if (value == null)
            return null;
        if (value instanceof Boolean)
            return (Boolean) value;
        if (value instanceof String) {
            String str = ((String) value).trim().toLowerCase();
            if (str.equals("true") || str.equals("1") || str.equals("yes"))
                return true;
            if (str.equals("false") || str.equals("0") || str.equals("no"))
                return false;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }
        return null;
    }

    // Helper for String
    private static String convertToString(Object value) {
        return value != null ? value.toString() : null;
    }

    // Helper for String Array
    private static String[] convertToStringArray(Object value) {
        if (value == null)
            return new String[0];
        if (value instanceof String[])
            return (String[]) value;
        if (value instanceof Object[]) {
            return Arrays.stream((Object[]) value)
                    .map(Object::toString)
                    .toArray(String[]::new);
        }
        return new String[0];
    }

    public static Integer getLidarState(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_state");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarStatus(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_status");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static String getLidarAppName(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_serverApplicationName");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarOpcuaPort(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_opcuaPort");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarWebPort(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_webPort");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static String getLidarAppStartTime(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_startTime");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getLidarSerialNumber(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_serialNumber");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getLidarTableFileName(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_fileNameTable");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getLidarProsysSdkVersion(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_sdkversion");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarCurrentSessionNumber(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_currentsessionnumber");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static String[] getLidarSessionsName(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_sessionsname");
        return dv.getValue().isNotNull() ? convertToStringArray(dv.getValue().getValue()) : new String[0];
    }

    public static Integer getLidarRandomGeneratorCode(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_randomgeneratorcode");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static Boolean getLidarVerboseStatus(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_verbosestatus");
        return dv.getValue().isNotNull() ? convertToBoolean(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarUpdateTime(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_updatetime");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static String getLidarIsoTstamp(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_isotstamp");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarErrorNumber(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_error_number");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static String getLidarErrorInformation(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_error_information");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getLidarErrorRecovering(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_error_recovering");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarErrorNumberRecovered(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_error_number_recovered");
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static Boolean getLidarErrorNumberOutOfRange(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_error_number_outofrange");
        return dv.getValue().isNotNull() ? convertToBoolean(dv.getValue().getValue()) : null;
    }

    // --- LIDAR Floats / Doubles ---

    public static Float getElasticChannel355Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ElasticChannel355Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getElasticChannel532Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ElasticChannel532Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getElasticChannel1064Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ElasticChannel1064Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getRamanChannelN2387Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_RamanChannelN2387Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getRamanChannelH2o(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_RamanChannelH2o");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getRamanRangeSignalCounts(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_RamanRangeSignalCounts");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getStatisticalErrorPerBin(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_StatisticalErrorPerBin");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getIntegrationTime(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_IntegrationTime");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getCoPolar355Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CoPolar355Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getCrossPolar355Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CrossPolar355Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getCoPolar532Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CoPolar532Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getCrossPolar532Nm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CrossPolar532Nm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getDepolarisationRatioProfile(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_DepolarisationRatioProfile");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getBackscatterCoefficientBetaZ(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_BackscatterCoefficientBetaZ");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getExtinctionCoefficientAlphaZ(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ExtinctionCoefficientAlphaZ");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getAerosolOpticalDepth(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AerosolOpticalDepth");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getLidarRatioSZ(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_LidarRatioSZ");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getHumidityProfileH2o(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_HumidityProfileH2o");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPblHeight(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PblHeight");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getCloudBaseHeight(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CloudBaseHeight");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getSnrPerBin(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_SnrPerBin");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getTimestampUtc(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_Timestamp_Utc");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Integer getLidarHeartbeat(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=heartbeat"); // Nota: Mismo nodo que SI3 heartbeat
        return dv.getValue().isNotNull() ? convertToInteger(dv.getValue().getValue()) : null;
    }

    public static Float getIntegrationAccumulationTime(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_IntegrationAccumulationTime");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getNumberOfAccumulatedPulses(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_NumberOfAccumulatedPulses");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getVerticalResolutionBinSize(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_VerticalResolutionBinSize");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getTemporalResolution(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_TemporalResolution");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getGlobalSnr(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_GlobalSnr");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getQualityFlags(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_QualityFlags");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getInternalTemperatures(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_InternalTemperatures");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getLaserReadingsEnergyVoltagePrf(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_LaserReadingsEnergyVoltagePrf");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getAodTimeSeries(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AodTimeSeries");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getAveragedIntervalProfiles(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AveragedIntervalProfiles");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getNetcdfAsciiGridFiles(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_NetcdfAsciiGridFiles");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getRangeTimeImages(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_RangeTimeImages");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getAshCloudAutomaticDetection(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AshCloudAutomaticDetection");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getMotorised2AxisMount(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_Motorised2AxisMount");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getThreeDScanningCapability(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ThreeDScanningCapability");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getAzimuthRange0360Deg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AzimuthRange0360Deg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getElevationRangeMinus590Deg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ElevationRange_590Deg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPointingAccuracy(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingAccuracy");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getAngularSpeedConfigurable(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AngularSpeedConfigurable");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getModeStareFixed(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ModeStareFixed");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getModeRasterScan(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ModeRasterScan");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getModeConeScan(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ModeConeScan");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getModeVolumeScan(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ModeVolumeScan");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getAngularStepPerBin(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_AngularStepPerBin");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getIntegrationTimePerPosition(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_IntegrationTimePerPosition");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getEthernetApiGuiControl(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_EthernetApiGuiControl");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getCmdSetAz(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CmdSetAz");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getCmdSetEl(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CmdSetEl");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getCmdHome(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CmdHome");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getCmdPark(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CmdPark");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getCmdStartScan(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CmdStartScan");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getTelemetryStatusPositionEncoder(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_TelemetryStatusPositionEncoder");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getCommandLatency(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CommandLatency");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getEncoderPositionConfirmation(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_EncoderPositionConfirmation");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getDirectPointingCommands(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_DirectPointingCommands");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getPointingTolerance(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingTolerance");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getPointingVerification(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingVerification");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getMeasurementStrategyByPointing(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_MeasurementStrategyByPointing");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getPositionQualityFlags(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PositionQualityFlags");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getSafetyInterlocks(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_SafetyInterlocks");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getNoGoZones(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_NoGoZones");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getHumanPresenceLockout(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_HumanPresenceLockout");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getDayNightModes(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_DayNightModes");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getMeasurementTimeUtc(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_MeasurementTimeUtc");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getIntegrationSeconds(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_IntegrationSeconds");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getLaserWavelengthNm(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_LaserWavelengthNm");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getChannelId(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ChannelId");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getRangeM(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_RangeM");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getSignalCounts(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_SignalCounts");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getSignalError(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_SignalError");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getBackscatterCoefMSr(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_BackscatterCoefMSr");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getExtinctionCoefKm1(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ExtinctionCoefKm1");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getDepolarizationRatio(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_DepolarizationRatio");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getWaterVapourMixingRatioGPerKg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_WaterVapourMixingRatioGPerKg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getCloudBaseHeightM(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_CloudBaseHeightM");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPblHeightM(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PblHeightM");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPointingAzDeg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingAzDeg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPointingElDeg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingElDeg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPointingTargetAzDeg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingTargetAzDeg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static Float getPointingTargetElDeg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingTargetElDeg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getPointingStatus(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingStatus");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static Float getPointingAccuracyDeg(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_PointingAccuracyDeg");
        return dv.getValue().isNotNull() ? convertToFloat(dv.getValue().getValue()) : null;
    }

    public static String getScanMode(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_ScanMode");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

    public static String getDeviceStatus(OpcUaConnector connector) throws Exception {
        DataValue dv = connector.readNode("ns=2;s=lidar_get_DeviceStatus");
        return dv.getValue().isNotNull() ? convertToString(dv.getValue().getValue()) : null;
    }

}
