package lidar.client.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LIDAR {

    // ==========================================
    // General System Information
    // ==========================================
    private Integer state;
    private Integer status;
    private String appName;
    private Integer opcuaPort;
    private Integer webPort;
    private String appStartTime;
    private String serialNumber;
    private String tableFileName;
    private String prosysSdkVersion;
    private Integer currentSessionNumber;
    private String sessionsName; // Defined as String[100] in table, usually maps to String in Java
    private Integer randomGeneratorCode;
    private Boolean verboseStatus;
    private Integer updateTime;
    private String isotstamp;
    private Integer heartbeat;

    // ==========================================
    // Errors
    // ==========================================
    private Integer errorNumber;
    private String errorInformation;
    private String errorRecovering; // Changed to String as per table
    private Integer errorNumberRecovered;
    private Boolean errorNumberOutofrange;

    // ==========================================
    // LIDAR Measurements & Channels
    // ==========================================
    private Double elasticChannel355Nm;
    private Double elasticChannel532Nm;
    private Double elasticChannel1064Nm;
    private Double ramanChannelN2387Nm;
    private Double ramanChannelH2o;
    private Double ramanRangeSignalCounts;
    private Double statisticalErrorPerBin;
    private Double integrationTime;
    private Double coPolar355Nm;
    private Double crossPolar355Nm;
    private Double coPolar532Nm;
    private Double crossPolar532Nm;
    private Double depolarisationRatioProfile;
    private Double backscatterCoefficientBetaZ;
    private Double extinctionCoefficientAlphaZ;
    private Double aerosolOpticalDepth;
    private Double lidarRatioSZ;
    private Double humidityProfileH2o;
    private Double pblHeight;
    private Double cloudBaseHeight;
    private Double snrPerBin;

    // ==========================================
    // Data Processing & Status
    // ==========================================
    private String timestampUtc;
    private Double integrationAccumulationTime;
    private Double numberOfAccumulatedPulses;
    private Double verticalResolutionBinSize;
    private Double temporalResolution;
    private Double globalSnr;
    private String qualityFlags;
    private Double internalTemperatures;
    private Double laserReadingsEnergyVoltagePrf;
    private Double aodTimeSeries;
    private Double averagedIntervalProfiles;
    private String netcdfAsciiGridFiles;
    private String rangeTimeImages;
    private String ashCloudAutomaticDetection;

    // ==========================================
    // Motion & Control
    // ==========================================
    private String motorised2AxisMount;
    private String threeDScanningCapability;
    private Double azimuthRange0360Deg;
    private Double elevationRangeMinus590Deg;
    private Double pointingAccuracy;
    private Double angularSpeedConfigurable;

    // Modes
    private String modeStareFixed;
    private String modeRasterScan;
    private String modeConeScan;
    private String modeVolumeScan;

    private Double angularStepPerBin;
    private Double integrationTimePerPosition;
    private String ethernetApiGuiControl;

    // Commands (Cmd)
    private String cmdSetAz;
    private String cmdSetEl;
    private String cmdHome;
    private String cmdPark;
    private String cmdStartScan;

    // ==========================================
    // Telemetry & Positioning
    // ==========================================
    private String telemetryStatusPositionEncoder;
    private Double commandLatency;
    private String encoderPositionConfirmation;
    private String directPointingCommands;
    private Double pointingTolerance;
    private String pointingVerification;
    private String measurementStrategyByPointing;

    // ==========================================
    // Safety & Flags
    // ==========================================
    private String positionQualityFlags;
    private String safetyInterlocks;
    private String noGoZones;
    private String humanPresenceLockout;
    private String dayNightModes;

    // ==========================================
    // Detailed Measurements
    // ==========================================
    private String measurementTimeUtc;
    private Double integrationSeconds;
    private Double laserWavelengthNm;
    private String channelId;
    private Double rangeM;
    private Double signalCounts;
    private Double signalError;
    private Double backscatterCoefMSr;
    private Double extinctionCoefKm1;
    private Double depolarizationRatio;
    private Double waterVapourMixingRatioGPerKg;
    private Double cloudBaseHeightM;
    private Double pblHeightM;

    // ==========================================
    // Pointing Status
    // ==========================================
    private Double pointingAzDeg;
    private Double pointingElDeg;
    private Double pointingTargetAzDeg;
    private Double pointingTargetElDeg;
    private String pointingStatus;
    private Double pointingAccuracyDeg;

    // ==========================================
    // Device Info
    // ==========================================
    private String scanMode;
    private String deviceStatus;
    private String fileFormatVersion;

    // ==========================================
    // HELPERS
    // ==========================================
    private static Boolean convertToBoolean(Object value) {
        if (value == null)
            return null;
        if (value instanceof Boolean)
            return (Boolean) value;
        if (value instanceof String) {
            String str = ((String) value).trim().toLowerCase();
            return str.equals("true") || str.equals("1") || str.equals("yes");
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }
        return null;
    }

    private Integer convertToInteger(Object value) {
        if (value == null)
            return null;
        if (value instanceof Integer)
            return (Integer) value;
        if (value instanceof Number)
            return ((Number) value).intValue();
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private Double convertToDouble(Object value) {
        if (value == null)
            return null;
        if (value instanceof Double)
            return (Double) value;
        if (value instanceof Float)
            return ((Float) value).doubleValue();
        if (value instanceof Number)
            return ((Number) value).doubleValue();
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private String convertToString(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    // ==========================================
    // SET ATTR (Generic Setter)
    // ==========================================
    public void setAttr(String name, Object value) {
        if (value == null)
            return;

        switch (name) {
            // General
            case "state":
                state = convertToInteger(value);
                break;
            case "status":
                status = convertToInteger(value);
                break;
            case "app_name":
                appName = convertToString(value);
                break;
            case "opcua_port":
                opcuaPort = convertToInteger(value);
                break;
            case "web_port":
                webPort = convertToInteger(value);
                break;
            case "app_start_time":
                appStartTime = convertToString(value);
                break;
            case "serial_number":
                serialNumber = convertToString(value);
                break;
            case "table_file_name":
                tableFileName = convertToString(value);
                break;
            case "prosys_sdk_version":
                prosysSdkVersion = convertToString(value);
                break;
            case "current_session_number":
                currentSessionNumber = convertToInteger(value);
                break;
            case "sessions_name":
                sessionsName = convertToString(value);
                break;
            case "random_generator_code":
                randomGeneratorCode = convertToInteger(value);
                break;
            case "verbose_status":
                verboseStatus = convertToBoolean(value);
                break;
            case "update_time":
                updateTime = convertToInteger(value);
                break;
            case "isotstamp":
                isotstamp = convertToString(value);
                break;
            case "heartbeat":
                heartbeat = convertToInteger(value);
                break;

            // Errors
            case "error_number":
                errorNumber = convertToInteger(value);
                break;
            case "error_information":
                errorInformation = convertToString(value);
                break;
            case "error_recovering":
                errorRecovering = convertToString(value);
                break;
            case "error_number_recovered":
                errorNumberRecovered = convertToInteger(value);
                break;
            case "error_number_outofrange":
                errorNumberOutofrange = convertToBoolean(value);
                break;

            // Lidar Channels & Measures
            case "elastic_channel_355_nm":
                elasticChannel355Nm = convertToDouble(value);
                break;
            case "elastic_channel_532_nm":
                elasticChannel532Nm = convertToDouble(value);
                break;
            case "elastic_channel_1064_nm":
                elasticChannel1064Nm = convertToDouble(value);
                break;
            case "raman_channel_n2_387_nm":
                ramanChannelN2387Nm = convertToDouble(value);
                break;
            case "raman_channel_h2o":
                ramanChannelH2o = convertToDouble(value);
                break;
            case "raman_range_signal_counts":
                ramanRangeSignalCounts = convertToDouble(value);
                break;
            case "statistical_error_per_bin":
                statisticalErrorPerBin = convertToDouble(value);
                break;
            case "integration_time":
                integrationTime = convertToDouble(value);
                break;
            case "co_polar_355_nm":
                coPolar355Nm = convertToDouble(value);
                break;
            case "cross_polar_355_nm":
                crossPolar355Nm = convertToDouble(value);
                break;
            case "co_polar_532_nm":
                coPolar532Nm = convertToDouble(value);
                break;
            case "cross_polar_532_nm":
                crossPolar532Nm = convertToDouble(value);
                break;
            case "depolarisation_ratio_profile":
                depolarisationRatioProfile = convertToDouble(value);
                break;
            case "backscatter_coefficient_beta_z":
                backscatterCoefficientBetaZ = convertToDouble(value);
                break;
            case "extinction_coefficient_alpha_z":
                extinctionCoefficientAlphaZ = convertToDouble(value);
                break;
            case "aerosol_optical_depth":
                aerosolOpticalDepth = convertToDouble(value);
                break;
            case "lidar_ratio_s_z":
                lidarRatioSZ = convertToDouble(value);
                break;
            case "humidity_profile_h2o":
                humidityProfileH2o = convertToDouble(value);
                break;
            case "pbl_height":
                pblHeight = convertToDouble(value);
                break;
            case "cloud_base_height":
                cloudBaseHeight = convertToDouble(value);
                break;
            case "snr_per_bin":
                snrPerBin = convertToDouble(value);
                break;

            // Data Processing
            case "timestamp_utc":
                timestampUtc = convertToString(value);
                break;
            case "integration_accumulation_time":
                integrationAccumulationTime = convertToDouble(value);
                break;
            case "number_of_accumulated_pulses":
                numberOfAccumulatedPulses = convertToDouble(value);
                break;
            case "vertical_resolution_bin_size":
                verticalResolutionBinSize = convertToDouble(value);
                break;
            case "temporal_resolution":
                temporalResolution = convertToDouble(value);
                break;
            case "global_snr":
                globalSnr = convertToDouble(value);
                break;
            case "quality_flags":
                qualityFlags = convertToString(value);
                break;
            case "internal_temperatures":
                internalTemperatures = convertToDouble(value);
                break;
            case "laser_readings_energy_voltage_prf":
                laserReadingsEnergyVoltagePrf = convertToDouble(value);
                break;
            case "aod_time_series":
                aodTimeSeries = convertToDouble(value);
                break;
            case "averaged_interval_profiles":
                averagedIntervalProfiles = convertToDouble(value);
                break;
            case "netcdf_ascii_grid_files":
                netcdfAsciiGridFiles = convertToString(value);
                break;
            case "range_time_images":
                rangeTimeImages = convertToString(value);
                break;
            case "ash_cloud_automatic_detection":
                ashCloudAutomaticDetection = convertToString(value);
                break;

            // Motion
            case "motorised_2_axis_mount":
                motorised2AxisMount = convertToString(value);
                break;
            case "three_d_scanning_capability":
                threeDScanningCapability = convertToString(value);
                break;
            case "azimuth_range_0_360_deg":
                azimuthRange0360Deg = convertToDouble(value);
                break;
            case "elevation_range_minus_5_90_deg":
                elevationRangeMinus590Deg = convertToDouble(value);
                break;
            case "pointing_accuracy":
                pointingAccuracy = convertToDouble(value);
                break;
            case "angular_speed_configurable":
                angularSpeedConfigurable = convertToDouble(value);
                break;
            case "mode_stare_fixed":
                modeStareFixed = convertToString(value);
                break;
            case "mode_raster_scan":
                modeRasterScan = convertToString(value);
                break;
            case "mode_cone_scan":
                modeConeScan = convertToString(value);
                break;
            case "mode_volume_scan":
                modeVolumeScan = convertToString(value);
                break;
            case "angular_step_per_bin":
                angularStepPerBin = convertToDouble(value);
                break;
            case "integration_time_per_position":
                integrationTimePerPosition = convertToDouble(value);
                break;
            case "ethernet_api_gui_control":
                ethernetApiGuiControl = convertToString(value);
                break;

            // Commands
            case "cmd_set_az":
                cmdSetAz = convertToString(value);
                break;
            case "cmd_set_el":
                cmdSetEl = convertToString(value);
                break;
            case "cmd_home":
                cmdHome = convertToString(value);
                break;
            case "cmd_park":
                cmdPark = convertToString(value);
                break;
            case "cmd_start_scan":
                cmdStartScan = convertToString(value);
                break;

            // Telemetry
            case "telemetry_status_position_encoder":
                telemetryStatusPositionEncoder = convertToString(value);
                break;
            case "command_latency":
                commandLatency = convertToDouble(value);
                break;
            case "encoder_position_confirmation":
                encoderPositionConfirmation = convertToString(value);
                break;
            case "direct_pointing_commands":
                directPointingCommands = convertToString(value);
                break;
            case "pointing_tolerance":
                pointingTolerance = convertToDouble(value);
                break;
            case "pointing_verification":
                pointingVerification = convertToString(value);
                break;
            case "measurement_strategy_by_pointing":
                measurementStrategyByPointing = convertToString(value);
                break;

            // Safety
            case "position_quality_flags":
                positionQualityFlags = convertToString(value);
                break;
            case "safety_interlocks":
                safetyInterlocks = convertToString(value);
                break;
            case "no_go_zones":
                noGoZones = convertToString(value);
                break;
            case "human_presence_lockout":
                humanPresenceLockout = convertToString(value);
                break;
            case "day_night_modes":
                dayNightModes = convertToString(value);
                break;

            // Measurements
            case "measurement_time_utc":
                measurementTimeUtc = convertToString(value);
                break;
            case "integration_seconds":
                integrationSeconds = convertToDouble(value);
                break;
            case "laser_wavelength_nm":
                laserWavelengthNm = convertToDouble(value);
                break;
            case "channel_id":
                channelId = convertToString(value);
                break;
            case "range_m":
                rangeM = convertToDouble(value);
                break;
            case "signal_counts":
                signalCounts = convertToDouble(value);
                break;
            case "signal_error":
                signalError = convertToDouble(value);
                break;
            case "backscatter_coef_m_sr":
                backscatterCoefMSr = convertToDouble(value);
                break;
            case "extinction_coef_km_1":
                extinctionCoefKm1 = convertToDouble(value);
                break;
            case "depolarization_ratio":
                depolarizationRatio = convertToDouble(value);
                break;
            case "water_vapour_mixing_ratio_g_per_kg":
                waterVapourMixingRatioGPerKg = convertToDouble(value);
                break;
            case "cloud_base_height_m":
                cloudBaseHeightM = convertToDouble(value);
                break;
            case "pbl_height_m":
                pblHeightM = convertToDouble(value);
                break;

            // Pointing Status
            case "pointing_az_deg":
                pointingAzDeg = convertToDouble(value);
                break;
            case "pointing_el_deg":
                pointingElDeg = convertToDouble(value);
                break;
            case "pointing_target_az_deg":
                pointingTargetAzDeg = convertToDouble(value);
                break;
            case "pointing_target_el_deg":
                pointingTargetElDeg = convertToDouble(value);
                break;
            case "pointing_status":
                pointingStatus = convertToString(value);
                break;
            case "pointing_accuracy_deg":
                pointingAccuracyDeg = convertToDouble(value);
                break;

            // Device
            case "scan_mode":
                scanMode = convertToString(value);
                break;
            case "device_status":
                deviceStatus = convertToString(value);
                break;
            case "file_format_version":
                fileFormatVersion = convertToString(value);
                break;

            default:
                // throw new IllegalArgumentException("LIDAR has no attribute: " + name);
                // System.out.println("Ignored attribute: " + name);
                break;
        }
    }

    // ==========================================
    // GET ALL VALUES
    // ==========================================
    public Map<String, Object> getAllValues() {
        Map<String, Object> map = new HashMap<>();

        map.put("state", state);
        map.put("status", status);
        map.put("app_name", appName);
        map.put("opcua_port", opcuaPort);
        map.put("web_port", webPort);
        map.put("app_start_time", appStartTime);
        map.put("serial_number", serialNumber);
        map.put("table_file_name", tableFileName);
        map.put("prosys_sdk_version", prosysSdkVersion);
        map.put("current_session_number", currentSessionNumber);
        map.put("sessions_name", sessionsName);
        map.put("random_generator_code", randomGeneratorCode);
        map.put("verbose_status", verboseStatus);
        map.put("update_time", updateTime);
        map.put("isotstamp", isotstamp);
        map.put("heartbeat", heartbeat);

        map.put("error_number", errorNumber);
        map.put("error_information", errorInformation);
        map.put("error_recovering", errorRecovering);
        map.put("error_number_recovered", errorNumberRecovered);
        map.put("error_number_outofrange", errorNumberOutofrange);

        map.put("elastic_channel_355_nm", elasticChannel355Nm);
        map.put("elastic_channel_532_nm", elasticChannel532Nm);
        map.put("elastic_channel_1064_nm", elasticChannel1064Nm);
        map.put("raman_channel_n2_387_nm", ramanChannelN2387Nm);
        map.put("raman_channel_h2o", ramanChannelH2o);
        map.put("raman_range_signal_counts", ramanRangeSignalCounts);
        map.put("statistical_error_per_bin", statisticalErrorPerBin);
        map.put("integration_time", integrationTime);
        map.put("co_polar_355_nm", coPolar355Nm);
        map.put("cross_polar_355_nm", crossPolar355Nm);
        map.put("co_polar_532_nm", coPolar532Nm);
        map.put("cross_polar_532_nm", crossPolar532Nm);
        map.put("depolarisation_ratio_profile", depolarisationRatioProfile);
        map.put("backscatter_coefficient_beta_z", backscatterCoefficientBetaZ);
        map.put("extinction_coefficient_alpha_z", extinctionCoefficientAlphaZ);
        map.put("aerosol_optical_depth", aerosolOpticalDepth);
        map.put("lidar_ratio_s_z", lidarRatioSZ);
        map.put("humidity_profile_h2o", humidityProfileH2o);
        map.put("pbl_height", pblHeight);
        map.put("cloud_base_height", cloudBaseHeight);
        map.put("snr_per_bin", snrPerBin);

        map.put("timestamp_utc", timestampUtc);
        map.put("integration_accumulation_time", integrationAccumulationTime);
        map.put("number_of_accumulated_pulses", numberOfAccumulatedPulses);
        map.put("vertical_resolution_bin_size", verticalResolutionBinSize);
        map.put("temporal_resolution", temporalResolution);
        map.put("global_snr", globalSnr);
        map.put("quality_flags", qualityFlags);
        map.put("internal_temperatures", internalTemperatures);
        map.put("laser_readings_energy_voltage_prf", laserReadingsEnergyVoltagePrf);
        map.put("aod_time_series", aodTimeSeries);
        map.put("averaged_interval_profiles", averagedIntervalProfiles);
        map.put("netcdf_ascii_grid_files", netcdfAsciiGridFiles);
        map.put("range_time_images", rangeTimeImages);
        map.put("ash_cloud_automatic_detection", ashCloudAutomaticDetection);

        map.put("motorised_2_axis_mount", motorised2AxisMount);
        map.put("three_d_scanning_capability", threeDScanningCapability);
        map.put("azimuth_range_0_360_deg", azimuthRange0360Deg);
        map.put("elevation_range_minus_5_90_deg", elevationRangeMinus590Deg);
        map.put("pointing_accuracy", pointingAccuracy);
        map.put("angular_speed_configurable", angularSpeedConfigurable);
        map.put("mode_stare_fixed", modeStareFixed);
        map.put("mode_raster_scan", modeRasterScan);
        map.put("mode_cone_scan", modeConeScan);
        map.put("mode_volume_scan", modeVolumeScan);
        map.put("angular_step_per_bin", angularStepPerBin);
        map.put("integration_time_per_position", integrationTimePerPosition);
        map.put("ethernet_api_gui_control", ethernetApiGuiControl);

        map.put("cmd_set_az", cmdSetAz);
        map.put("cmd_set_el", cmdSetEl);
        map.put("cmd_home", cmdHome);
        map.put("cmd_park", cmdPark);
        map.put("cmd_start_scan", cmdStartScan);

        map.put("telemetry_status_position_encoder", telemetryStatusPositionEncoder);
        map.put("command_latency", commandLatency);
        map.put("encoder_position_confirmation", encoderPositionConfirmation);
        map.put("direct_pointing_commands", directPointingCommands);
        map.put("pointing_tolerance", pointingTolerance);
        map.put("pointing_verification", pointingVerification);
        map.put("measurement_strategy_by_pointing", measurementStrategyByPointing);

        map.put("position_quality_flags", positionQualityFlags);
        map.put("safety_interlocks", safetyInterlocks);
        map.put("no_go_zones", noGoZones);
        map.put("human_presence_lockout", humanPresenceLockout);
        map.put("day_night_modes", dayNightModes);

        map.put("measurement_time_utc", measurementTimeUtc);
        map.put("integration_seconds", integrationSeconds);
        map.put("laser_wavelength_nm", laserWavelengthNm);
        map.put("channel_id", channelId);
        map.put("range_m", rangeM);
        map.put("signal_counts", signalCounts);
        map.put("signal_error", signalError);
        map.put("backscatter_coef_m_sr", backscatterCoefMSr);
        map.put("extinction_coef_km_1", extinctionCoefKm1);
        map.put("depolarization_ratio", depolarizationRatio);
        map.put("water_vapour_mixing_ratio_g_per_kg", waterVapourMixingRatioGPerKg);
        map.put("cloud_base_height_m", cloudBaseHeightM);
        map.put("pbl_height_m", pblHeightM);

        map.put("pointing_az_deg", pointingAzDeg);
        map.put("pointing_el_deg", pointingElDeg);
        map.put("pointing_target_az_deg", pointingTargetAzDeg);
        map.put("pointing_target_el_deg", pointingTargetElDeg);
        map.put("pointing_status", pointingStatus);
        map.put("pointing_accuracy_deg", pointingAccuracyDeg);

        map.put("scan_mode", scanMode);
        map.put("device_status", deviceStatus);
        map.put("file_format_version", fileFormatVersion);

        return map;
    }

    // ==========================================
    // GETTERS (Generated)
    // ==========================================
    public Integer getState() {
        return state;
    }

    public Integer getStatus() {
        return status;
    }

    public String getAppName() {
        return appName;
    }

    public Integer getOpcuaPort() {
        return opcuaPort;
    }

    public Integer getWebPort() {
        return webPort;
    }

    public String getAppStartTime() {
        return appStartTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getTableFileName() {
        return tableFileName;
    }

    public String getProsysSdkVersion() {
        return prosysSdkVersion;
    }

    public Integer getCurrentSessionNumber() {
        return currentSessionNumber;
    }

    public String getSessionsName() {
        return sessionsName;
    }

    public Integer getRandomGeneratorCode() {
        return randomGeneratorCode;
    }

    public Boolean getVerboseStatus() {
        return verboseStatus;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public String getIsotstamp() {
        return isotstamp;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public Integer getErrorNumber() {
        return errorNumber;
    }

    public String getErrorInformation() {
        return errorInformation;
    }

    public String getErrorRecovering() {
        return errorRecovering;
    }

    public Integer getErrorNumberRecovered() {
        return errorNumberRecovered;
    }

    public Boolean getErrorNumberOutofrange() {
        return errorNumberOutofrange;
    }

    public Double getElasticChannel355Nm() {
        return elasticChannel355Nm;
    }

    public Double getElasticChannel532Nm() {
        return elasticChannel532Nm;
    }

    public Double getElasticChannel1064Nm() {
        return elasticChannel1064Nm;
    }

    public Double getRamanChannelN2387Nm() {
        return ramanChannelN2387Nm;
    }

    public Double getRamanChannelH2o() {
        return ramanChannelH2o;
    }

    public Double getRamanRangeSignalCounts() {
        return ramanRangeSignalCounts;
    }

    public Double getStatisticalErrorPerBin() {
        return statisticalErrorPerBin;
    }

    public Double getIntegrationTime() {
        return integrationTime;
    }

    public Double getCoPolar355Nm() {
        return coPolar355Nm;
    }

    public Double getCrossPolar355Nm() {
        return crossPolar355Nm;
    }

    public Double getCoPolar532Nm() {
        return coPolar532Nm;
    }

    public Double getCrossPolar532Nm() {
        return crossPolar532Nm;
    }

    public Double getDepolarisationRatioProfile() {
        return depolarisationRatioProfile;
    }

    public Double getBackscatterCoefficientBetaZ() {
        return backscatterCoefficientBetaZ;
    }

    public Double getExtinctionCoefficientAlphaZ() {
        return extinctionCoefficientAlphaZ;
    }

    public Double getAerosolOpticalDepth() {
        return aerosolOpticalDepth;
    }

    public Double getLidarRatioSZ() {
        return lidarRatioSZ;
    }

    public Double getHumidityProfileH2o() {
        return humidityProfileH2o;
    }

    public Double getPblHeight() {
        return pblHeight;
    }

    public Double getCloudBaseHeight() {
        return cloudBaseHeight;
    }

    public Double getSnrPerBin() {
        return snrPerBin;
    }

    public String getTimestampUtc() {
        return timestampUtc;
    }

    public Double getIntegrationAccumulationTime() {
        return integrationAccumulationTime;
    }

    public Double getNumberOfAccumulatedPulses() {
        return numberOfAccumulatedPulses;
    }

    public Double getVerticalResolutionBinSize() {
        return verticalResolutionBinSize;
    }

    public Double getTemporalResolution() {
        return temporalResolution;
    }

    public Double getGlobalSnr() {
        return globalSnr;
    }

    public String getQualityFlags() {
        return qualityFlags;
    }

    public Double getInternalTemperatures() {
        return internalTemperatures;
    }

    public Double getLaserReadingsEnergyVoltagePrf() {
        return laserReadingsEnergyVoltagePrf;
    }

    public Double getAodTimeSeries() {
        return aodTimeSeries;
    }

    public Double getAveragedIntervalProfiles() {
        return averagedIntervalProfiles;
    }

    public String getNetcdfAsciiGridFiles() {
        return netcdfAsciiGridFiles;
    }

    public String getRangeTimeImages() {
        return rangeTimeImages;
    }

    public String getAshCloudAutomaticDetection() {
        return ashCloudAutomaticDetection;
    }

    public String getMotorised2AxisMount() {
        return motorised2AxisMount;
    }

    public String getThreeDScanningCapability() {
        return threeDScanningCapability;
    }

    public Double getAzimuthRange0360Deg() {
        return azimuthRange0360Deg;
    }

    public Double getElevationRangeMinus590Deg() {
        return elevationRangeMinus590Deg;
    }

    public Double getPointingAccuracy() {
        return pointingAccuracy;
    }

    public Double getAngularSpeedConfigurable() {
        return angularSpeedConfigurable;
    }

    public String getModeStareFixed() {
        return modeStareFixed;
    }

    public String getModeRasterScan() {
        return modeRasterScan;
    }

    public String getModeConeScan() {
        return modeConeScan;
    }

    public String getModeVolumeScan() {
        return modeVolumeScan;
    }

    public Double getAngularStepPerBin() {
        return angularStepPerBin;
    }

    public Double getIntegrationTimePerPosition() {
        return integrationTimePerPosition;
    }

    public String getEthernetApiGuiControl() {
        return ethernetApiGuiControl;
    }

    public String getCmdSetAz() {
        return cmdSetAz;
    }

    public String getCmdSetEl() {
        return cmdSetEl;
    }

    public String getCmdHome() {
        return cmdHome;
    }

    public String getCmdPark() {
        return cmdPark;
    }

    public String getCmdStartScan() {
        return cmdStartScan;
    }

    public String getTelemetryStatusPositionEncoder() {
        return telemetryStatusPositionEncoder;
    }

    public Double getCommandLatency() {
        return commandLatency;
    }

    public String getEncoderPositionConfirmation() {
        return encoderPositionConfirmation;
    }

    public String getDirectPointingCommands() {
        return directPointingCommands;
    }

    public Double getPointingTolerance() {
        return pointingTolerance;
    }

    public String getPointingVerification() {
        return pointingVerification;
    }

    public String getMeasurementStrategyByPointing() {
        return measurementStrategyByPointing;
    }

    public String getPositionQualityFlags() {
        return positionQualityFlags;
    }

    public String getSafetyInterlocks() {
        return safetyInterlocks;
    }

    public String getNoGoZones() {
        return noGoZones;
    }

    public String getHumanPresenceLockout() {
        return humanPresenceLockout;
    }

    public String getDayNightModes() {
        return dayNightModes;
    }

    public String getMeasurementTimeUtc() {
        return measurementTimeUtc;
    }

    public Double getIntegrationSeconds() {
        return integrationSeconds;
    }

    public Double getLaserWavelengthNm() {
        return laserWavelengthNm;
    }

    public String getChannelId() {
        return channelId;
    }

    public Double getRangeM() {
        return rangeM;
    }

    public Double getSignalCounts() {
        return signalCounts;
    }

    public Double getSignalError() {
        return signalError;
    }

    public Double getBackscatterCoefMSr() {
        return backscatterCoefMSr;
    }

    public Double getExtinctionCoefKm1() {
        return extinctionCoefKm1;
    }

    public Double getDepolarizationRatio() {
        return depolarizationRatio;
    }

    public Double getWaterVapourMixingRatioGPerKg() {
        return waterVapourMixingRatioGPerKg;
    }

    public Double getCloudBaseHeightM() {
        return cloudBaseHeightM;
    }

    public Double getPblHeightM() {
        return pblHeightM;
    }

    public Double getPointingAzDeg() {
        return pointingAzDeg;
    }

    public Double getPointingElDeg() {
        return pointingElDeg;
    }

    public Double getPointingTargetAzDeg() {
        return pointingTargetAzDeg;
    }

    public Double getPointingTargetElDeg() {
        return pointingTargetElDeg;
    }

    public String getPointingStatus() {
        return pointingStatus;
    }

    public Double getPointingAccuracyDeg() {
        return pointingAccuracyDeg;
    }

    public String getScanMode() {
        return scanMode;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public String getFileFormatVersion() {
        return fileFormatVersion;
    }
}