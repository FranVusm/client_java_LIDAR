package lidar.client;

import lidar.application.monitor.PollingMonitor;
import lidar.application.monitor.SubscriptionMonitor;
import lidar.client.domain.LIDAR;
import lidar.client.domain.dto.LIDARGetters;
import lidar.client.domain.dto.LIDARSetters;
import lidar.infrastructure.MemoryHistoryRepo;
import lidar.infrastructure.OpcUaConnector;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Map<String, String> ATTR_MAP = new HashMap<>();

    static {
        ATTR_MAP.put("state", "ns=2;s=lidar_get_state");
        ATTR_MAP.put("status", "ns=2;s=lidar_get_status");
        ATTR_MAP.put("app_name", "ns=2;s=lidar_get_serverApplicationName");
        ATTR_MAP.put("opcua_port", "ns=2;s=lidar_get_opcuaPort");
        ATTR_MAP.put("web_port", "ns=2;s=lidar_get_webPort");
        ATTR_MAP.put("app_start_time", "ns=2;s=lidar_get_startTime");
        ATTR_MAP.put("serial_number", "ns=2;s=lidar_get_serialNumber");
        ATTR_MAP.put("table_file_name", "ns=2;s=lidar_get_fileNameTable");
        ATTR_MAP.put("prosys_sdk_version", "ns=2;s=lidar_get_sdkversion");
        ATTR_MAP.put("current_session_number", "ns=2;s=lidar_get_currentsessionnumber");
        ATTR_MAP.put("sessions_name", "ns=2;s=lidar_get_sessionsname");
        ATTR_MAP.put("random_generator_code", "ns=2;s=lidar_get_randomgeneratorcode");
        ATTR_MAP.put("verbose_status", "ns=2;s=lidar_get_verbosestatus");
        ATTR_MAP.put("update_time", "ns=2;s=lidar_get_updatetime");
        ATTR_MAP.put("isotstamp", "ns=2;s=lidar_get_isotstamp");
        ATTR_MAP.put("error_number", "ns=2;s=lidar_get_error_number");
        ATTR_MAP.put("error_information", "ns=2;s=lidar_get_error_information");
        ATTR_MAP.put("error_recovering", "ns=2;s=lidar_get_error_recovering");
        ATTR_MAP.put("error_number_recovered", "ns=2;s=lidar_get_error_number_recovered");
        ATTR_MAP.put("error_number_outofrange", "ns=2;s=lidar_get_error_number_outofrange");

        ATTR_MAP.put("elastic_channel_355_nm", "ns=2;s=lidar_get_ElasticChannel355Nm");
        ATTR_MAP.put("elastic_channel_532_nm", "ns=2;s=lidar_get_ElasticChannel532Nm");
        ATTR_MAP.put("elastic_channel_1064_nm", "ns=2;s=lidar_get_ElasticChannel1064Nm");

        ATTR_MAP.put("raman_channel_n2_387_nm", "ns=2;s=lidar_get_RamanChannelN2387Nm");
        ATTR_MAP.put("raman_channel_h2o", "ns=2;s=lidar_get_RamanChannelH2o");
        ATTR_MAP.put("raman_range_signal_counts", "ns=2;s=lidar_get_RamanRangeSignalCounts");
        ATTR_MAP.put("statistical_error_per_bin", "ns=2;s=lidar_get_StatisticalErrorPerBin");

        ATTR_MAP.put("integration_time", "ns=2;s=lidar_get_IntegrationTime");

        ATTR_MAP.put("co_polar_355_nm", "ns=2;s=lidar_get_CoPolar355Nm");
        ATTR_MAP.put("cross_polar_355_nm", "ns=2;s=lidar_get_CrossPolar355Nm");
        ATTR_MAP.put("co_polar_532_nm", "ns=2;s=lidar_get_CoPolar532Nm");
        ATTR_MAP.put("cross_polar_532_nm", "ns=2;s=lidar_get_CrossPolar532Nm");

        ATTR_MAP.put("depolarisation_ratio_profile", "ns=2;s=lidar_get_DepolarisationRatioProfile");
        ATTR_MAP.put("backscatter_coefficient_beta_z", "ns=2;s=lidar_get_BackscatterCoefficientBetaZ");
        ATTR_MAP.put("extinction_coefficient_alpha_z", "ns=2;s=lidar_get_ExtinctionCoefficientAlphaZ");
        ATTR_MAP.put("aerosol_optical_depth", "ns=2;s=lidar_get_AerosolOpticalDepth");
        ATTR_MAP.put("lidar_ratio_s_z", "ns=2;s=lidar_get_LidarRatioSZ");

        ATTR_MAP.put("humidity_profile_h2o", "ns=2;s=lidar_get_HumidityProfileH2o");
        ATTR_MAP.put("pbl_height", "ns=2;s=lidar_get_PblHeight");
        ATTR_MAP.put("cloud_base_height", "ns=2;s=lidar_get_CloudBaseHeight");
        ATTR_MAP.put("snr_per_bin", "ns=2;s=lidar_get_SnrPerBin");

        ATTR_MAP.put("timestamp_utc", "ns=2;s=lidar_get_Timestamp_Utc");
        ATTR_MAP.put("heartbeat", "ns=2;s=heartbeat");

        ATTR_MAP.put("integration_accumulation_time", "ns=2;s=lidar_get_IntegrationAccumulationTime");
        ATTR_MAP.put("number_of_accumulated_pulses", "ns=2;s=lidar_get_NumberOfAccumulatedPulses");
        ATTR_MAP.put("vertical_resolution_bin_size", "ns=2;s=lidar_get_VerticalResolutionBinSize");
        ATTR_MAP.put("temporal_resolution", "ns=2;s=lidar_get_TemporalResolution");

        ATTR_MAP.put("global_snr", "ns=2;s=lidar_get_GlobalSnr");
        ATTR_MAP.put("quality_flags", "ns=2;s=lidar_get_QualityFlags");
        ATTR_MAP.put("internal_temperatures", "ns=2;s=lidar_get_InternalTemperatures");
        ATTR_MAP.put("laser_readings_energy_voltage_prf", "ns=2;s=lidar_get_LaserReadingsEnergyVoltagePrf");

        ATTR_MAP.put("aod_time_series", "ns=2;s=lidar_get_AodTimeSeries");
        ATTR_MAP.put("averaged_interval_profiles", "ns=2;s=lidar_get_AveragedIntervalProfiles");
        ATTR_MAP.put("netcdf_ascii_grid_files", "ns=2;s=lidar_get_NetcdfAsciiGridFiles");
        ATTR_MAP.put("range_time_images", "ns=2;s=lidar_get_RangeTimeImages");
        ATTR_MAP.put("ash_cloud_automatic_detection", "ns=2;s=lidar_get_AshCloudAutomaticDetection");

        ATTR_MAP.put("motorised_2_axis_mount", "ns=2;s=lidar_get_Motorised2AxisMount");
        ATTR_MAP.put("three_d_scanning_capability", "ns=2;s=lidar_get_ThreeDScanningCapability");
        ATTR_MAP.put("azimuth_range_0_360_deg", "ns=2;s=lidar_get_AzimuthRange0360Deg");
        ATTR_MAP.put("elevation_range_minus_5_90_deg", "ns=2;s=lidar_get_ElevationRange_590Deg");
        ATTR_MAP.put("pointing_accuracy", "ns=2;s=lidar_get_PointingAccuracy");
        ATTR_MAP.put("angular_speed_configurable", "ns=2;s=lidar_get_AngularSpeedConfigurable");

        ATTR_MAP.put("mode_stare_fixed", "ns=2;s=lidar_get_ModeStareFixed");
        ATTR_MAP.put("mode_raster_scan", "ns=2;s=lidar_get_ModeRasterScan");
        ATTR_MAP.put("mode_cone_scan", "ns=2;s=lidar_get_ModeConeScan");
        ATTR_MAP.put("mode_volume_scan", "ns=2;s=lidar_get_ModeVolumeScan");

        ATTR_MAP.put("angular_step_per_bin", "ns=2;s=lidar_get_AngularStepPerBin");
        ATTR_MAP.put("integration_time_per_position", "ns=2;s=lidar_get_IntegrationTimePerPosition");

        ATTR_MAP.put("ethernet_api_gui_control", "ns=2;s=lidar_get_EthernetApiGuiControl");

        ATTR_MAP.put("cmd_set_az", "ns=2;s=lidar_get_CmdSetAz");
        ATTR_MAP.put("cmd_set_el", "ns=2;s=lidar_get_CmdSetEl");
        ATTR_MAP.put("cmd_home", "ns=2;s=lidar_get_CmdHome");
        ATTR_MAP.put("cmd_park", "ns=2;s=lidar_get_CmdPark");
        ATTR_MAP.put("cmd_start_scan", "ns=2;s=lidar_get_CmdStartScan");

        ATTR_MAP.put("telemetry_status_position_encoder", "ns=2;s=lidar_get_TelemetryStatusPositionEncoder");
        ATTR_MAP.put("command_latency", "ns=2;s=lidar_get_CommandLatency");
        ATTR_MAP.put("encoder_position_confirmation", "ns=2;s=lidar_get_EncoderPositionConfirmation");
        ATTR_MAP.put("direct_pointing_commands", "ns=2;s=lidar_get_DirectPointingCommands");
        ATTR_MAP.put("pointing_tolerance", "ns=2;s=lidar_get_PointingTolerance");
        ATTR_MAP.put("pointing_verification", "ns=2;s=lidar_get_PointingVerification");
        ATTR_MAP.put("measurement_strategy_by_pointing", "ns=2;s=lidar_get_MeasurementStrategyByPointing");

        ATTR_MAP.put("position_quality_flags", "ns=2;s=lidar_get_PositionQualityFlags");
        ATTR_MAP.put("safety_interlocks", "ns=2;s=lidar_get_SafetyInterlocks");
        ATTR_MAP.put("no_go_zones", "ns=2;s=lidar_get_NoGoZones");
        ATTR_MAP.put("human_presence_lockout", "ns=2;s=lidar_get_HumanPresenceLockout");
        ATTR_MAP.put("day_night_modes", "ns=2;s=lidar_get_DayNightModes");

        ATTR_MAP.put("measurement_time_utc", "ns=2;s=lidar_get_MeasurementTimeUtc");
        ATTR_MAP.put("integration_seconds", "ns=2;s=lidar_get_IntegrationSeconds");
        ATTR_MAP.put("laser_wavelength_nm", "ns=2;s=lidar_get_LaserWavelengthNm");
        ATTR_MAP.put("channel_id", "ns=2;s=lidar_get_ChannelId");
        ATTR_MAP.put("range_m", "ns=2;s=lidar_get_RangeM");
        ATTR_MAP.put("signal_counts", "ns=2;s=lidar_get_SignalCounts");
        ATTR_MAP.put("signal_error", "ns=2;s=lidar_get_SignalError");
        ATTR_MAP.put("backscatter_coef_m_sr", "ns=2;s=lidar_get_BackscatterCoefMSr");
        ATTR_MAP.put("extinction_coef_km_1", "ns=2;s=lidar_get_ExtinctionCoefKm1");
        ATTR_MAP.put("depolarization_ratio", "ns=2;s=lidar_get_DepolarizationRatio");
        ATTR_MAP.put("water_vapour_mixing_ratio_g_per_kg", "ns=2;s=lidar_get_WaterVapourMixingRatioGPerKg");

        ATTR_MAP.put("cloud_base_height_m", "ns=2;s=lidar_get_CloudBaseHeightM");
        ATTR_MAP.put("pbl_height_m", "ns=2;s=lidar_get_PblHeightM");

        ATTR_MAP.put("pointing_az_deg", "ns=2;s=lidar_get_PointingAzDeg");
        ATTR_MAP.put("pointing_el_deg", "ns=2;s=lidar_get_PointingElDeg");
        ATTR_MAP.put("pointing_target_az_deg", "ns=2;s=lidar_get_PointingTargetAzDeg");
        ATTR_MAP.put("pointing_target_el_deg", "ns=2;s=lidar_get_PointingTargetElDeg");

        ATTR_MAP.put("pointing_status", "ns=2;s=lidar_get_PointingStatus");
        ATTR_MAP.put("pointing_accuracy_deg", "ns=2;s=lidar_get_PointingAccuracyDeg");

        ATTR_MAP.put("scan_mode", "ns=2;s=lidar_get_ScanMode");
        ATTR_MAP.put("device_status", "ns=2;s=lidar_get_DeviceStatus");
        ATTR_MAP.put("file_format_version", "ns=2;s=lidar_get_FileFormatVersion");

    }

    private static final String MENU = "================= LIDAR OPC UA =================\n" +
            "1) Show verbosity (all data received)\n" +
            "2) Test setters OPC UA\n" +
            "3) Test getters OPC UA\n" +
            "q) Exit\n" +
            "==============================================\n" +
            "Option: ";

    private static final AtomicBoolean verboseMode = new AtomicBoolean(false);
    private static final AtomicBoolean inBackground = new AtomicBoolean(false);
    private static final AtomicBoolean shouldExit = new AtomicBoolean(false);
    private static final AtomicBoolean connectionLost = new AtomicBoolean(false);

    // Queue to receive commands from the input thread
    private static final BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(
                    "Usage: java -jar lidar-java-client-1.0.0-jar-with-dependencies.jar <opc_url> [--RATE <seconds>]");
            System.exit(1);
        }

        String opcUrl = args[0];
        Double pollingRate = null;

        boolean isSecure = false;
        String certPath = "client-cert.der";
        String keyPath = "client-key.pem";

        // Parse --RATE
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("--RATE") && i + 1 < args.length) {
                pollingRate = Double.parseDouble(args[i + 1]);
                i++;
            } else if (arg.equals("--secure")) {
                isSecure = true;
            } else if (arg.equals("--cert") && i + 1 < args.length) {
                certPath = args[i + 1];
                i++;
            } else if (arg.equals("--key") && i + 1 < args.length) {
                keyPath = args[i + 1];
                i++;
            }
        }
        if (isSecure) {
            System.out.println("[CONFIG] Security Enabled: Sign & Encrypt (Basic256Sha256)");
            System.out.println("[CONFIG] Cert Path: " + Paths.get(certPath).toAbsolutePath());
            System.out.println("[CONFIG] Key Path: " + Paths.get(keyPath).toAbsolutePath());
        }
        // Start input reading thread
        Thread inputThread = new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                while (!shouldExit.get()) {
                    try {
                        if (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            commandQueue.offer(line);
                        } else {
                            // EOF or stream closed
                            Thread.sleep(100);
                        }
                    } catch (Exception e) {
                        // Ignore scanner errors
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ie) {
                        }
                    }
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        // Main reconnection loop
        while (!shouldExit.get()) {
            connectionLost.set(false); // Reset lost connection flag
            OpcUaConnector connector = new OpcUaConnector(opcUrl, isSecure, certPath, keyPath);
            LIDAR lidar = new LIDAR();
            MemoryHistoryRepo history = new MemoryHistoryRepo(10);
            PollingMonitor pollingMonitor = null;
            SubscriptionMonitor subMonitor = null;

            try {
                connector.connect();
                System.out.println("[CONNECTION] Successfully connected to: " + opcUrl);

                // Start monitoring
                if (pollingRate != null) {
                    System.out.println("[Mode] POLLING @ " + pollingRate + " seconds");
                    pollingMonitor = new PollingMonitor(connector, ATTR_MAP, lidar, history, pollingRate);
                    pollingMonitor.start();
                    runMenu(connector, lidar, pollingMonitor, null, opcUrl, pollingRate);
                } else {
                    System.out.println("[Mode] OPC UA SUBSCRIPTION (period_ms=500)");
                    subMonitor = new SubscriptionMonitor(connector, ATTR_MAP, lidar, history, 500.0);
                    subMonitor.start();
                    runMenu(connector, lidar, null, subMonitor, opcUrl, pollingRate);
                }
            } catch (Exception e) {

                // Stop monitors if they exist
                if (pollingMonitor != null) {
                    pollingMonitor.stop();
                }
                if (subMonitor != null) {
                    subMonitor.stop();
                }

                // Attempt to disconnect
                try {
                    connector.disconnect();
                } catch (Exception ex) {
                }

                // If we shouldn't exit, try to reconnect
                if (!shouldExit.get()) {
                    System.out.println("\n[RECONNECTION] Connection lost. Attempting to reconnect in 10 seconds...");
                    System.out.println("[RECONEXIÓN] URL: " + opcUrl);

                    try {
                        Thread.sleep(10000); // Wait 10 seconds
                    } catch (InterruptedException ie) {
                        break;
                    }

                    System.out.println("[RECONNECTION] Attempting to reconnect now...\n");
                }
            }
        }
    }

    private static boolean checkConnection(OpcUaConnector connector) {
        try {
            if (connector != null && connector.getClient() != null) {
                connector.readNode(ATTR_MAP.get("heartbeat"));
                return true;
            }
        } catch (Exception e) {
            // Connection lost
            if (!connectionLost.get()) {
                connectionLost.set(true);
            }
        }
        return false;
    }

    private static void handleConnectionError(Exception e, String opcUrl) {
        String errorMsg = e.getMessage();
        if (errorMsg != null && (errorMsg.contains("Connection refused") ||
                errorMsg.contains("ConnectionRejected") ||
                errorMsg.contains("SessionIdInvalid"))) {
            if (!connectionLost.get()) {
                System.out.println("\n[DISCONNECTION] Connection loss detected.");
                System.out.println("[DESCONEXIÓN] URL: " + opcUrl);
                connectionLost.set(true);
            }
        } else {
            System.err.println("[ERROR] " + errorMsg);
        }
    }

    private static void runMenu(OpcUaConnector connector, LIDAR lidar,
            PollingMonitor pollingMonitor, SubscriptionMonitor subMonitor,
            String opcUrl, Double pollingRate) {

        final Thread[] verboseThreadRef = new Thread[1];
        Thread verboseThread = new Thread(() -> {
            while (!inBackground.get() && !connectionLost.get()) {
                if (verboseMode.get() && !connectionLost.get()) {
                    System.out.println("\n=== VERBOSITY ===");
                    Map<String, Object> values = lidar.getAllValues();

                    // --- MODIFICACIÓN INICIO ---
                    if (values.isEmpty()) {
                        System.out.println("ADVERTENCIA: El objeto LIDAR no tiene valores mapeados.");
                    } else {
                        // Imprimir TODO, incluso si es NULL, para ver si el monitor está intentando
                        // leer
                        for (Map.Entry<String, Object> entry : values.entrySet()) {
                            String valString = (entry.getValue() == null) ? "NULL (Sin datos)"
                                    : entry.getValue().toString();
                            System.out.println(entry.getKey() + ": " + valString);
                        }
                    }
                    System.out.println("==================");
                    System.out.println("To exit verbosity press 1\n");
                }
                try {
                    Thread.sleep(2000); // Update every 2 seconds
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        verboseThread.setDaemon(true);
        verboseThreadRef[0] = verboseThread;
        verboseThread.start();

        // Thread to monitor connection and detect disconnections (every 2 seconds)
        ScheduledExecutorService connectionMonitor = Executors.newSingleThreadScheduledExecutor();
        connectionMonitor.scheduleAtFixedRate(() -> {
            try {
                // Attempt to read a node to verify connection
                if (connector != null && connector.getClient() != null) {
                    connector.readNode(ATTR_MAP.get("heartbeat"));
                }
            } catch (Exception e) {
                if (!connectionLost.get()) {
                    System.out.println("\n[DISCONNECTION] Connection loss detected.");
                    System.out.println("[DESCONEXIÓN] URL: " + opcUrl);

                    // Stop monitors
                    if (pollingMonitor != null) {
                        pollingMonitor.stop();
                    }
                    if (subMonitor != null) {
                        subMonitor.stop();
                    }

                    // Disable verbosity and stop verbosity thread
                    verboseMode.set(false);
                    if (verboseThreadRef[0] != null) {
                        verboseThreadRef[0].interrupt();
                    }

                    connectionLost.set(true);
                    connectionMonitor.shutdown();
                }
            }
        }, 2, 2, TimeUnit.SECONDS);

        boolean menuShown = false;

        while (true) {
            // Check if connection was lost - exit immediately without showing menu
            if (connectionLost.get()) {
                connectionMonitor.shutdown();
                if (verboseThreadRef[0] != null) {
                    verboseThreadRef[0].interrupt();
                }
                // Clear command queue
                commandQueue.clear();
                // Throw exception to activate reconnection loop
                throw new RuntimeException("Connection lost");
            }

            if (inBackground.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }

            // Only show menu if connection is active
            if (!connectionLost.get()) {
                if (!menuShown) {
                    System.out.print(MENU);
                    menuShown = true;
                }

                // Read input from queue with timeout
                String choice = null;
                try {
                    choice = commandQueue.poll(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    // Interrupted
                }

                // If no input (timeout), return to start of loop to verify connection
                if (choice == null) {
                    continue;
                }

                menuShown = false;

                choice = choice.trim().toLowerCase();

                // Verify connection before processing command
                if (connectionLost.get()) {
                    continue; // Exit menu loop if connection lost
                }

                try {
                    switch (choice) {
                        case "1":
                            verboseMode.set(!verboseMode.get());
                            System.out.println("Verbosity " + (verboseMode.get() ? "ENABLED" : "DISABLED"));
                            break;

                        case "2":
                            // Verify connection before calling method
                            if (!checkConnection(connector)) {
                                System.out.println("[ERROR] No connection available.");
                                break;
                            }

                            System.out.println("\n[TEST SETTERS] Running hardcoded test...");
                            try {
                                // 1. Set Setup DateTime (Double)
                                boolean newLaserEnable = false;
                                System.out.println("Setting LaserEnable to: " + newLaserEnable);
                                LIDARSetters.setLaserEnable(connector, newLaserEnable);

                                // 2. Set Error Index (Integer)
                                Integer setLaserPrf = 42;
                                System.out.println("Setting ErrorIndex to: " + setLaserPrf);
                                LIDARSetters.setLaserPrf(connector, setLaserPrf);

                                // 3. Set Simul On (Boolean Command)
                                Double setTargetAz = 10.1;
                                System.out.println("Setting SimulOn to: " + setTargetAz);
                                LIDARSetters.setTargetAz(connector, setTargetAz);

                                System.out.println("[TEST SETTERS] Test finished successfully.\n");
                            } catch (Exception e) {
                                System.out.println("[ERROR] Setters test failed: " + e.getMessage());
                                handleConnectionError(e, opcUrl);
                            }
                            break;

                        case "3":
                            // Verify connection before using getters
                            if (!checkConnection(connector)) {
                                System.out.println("[ERROR] No connection available.");
                                break;
                            }
                            System.out.println("\n[GETTERS] Testing getter functions...");
                            try {
                                Integer state = LIDARGetters.getLidarState(connector);
                                Integer status = LIDARGetters.getLidarStatus(connector);
                                String appName = LIDARGetters.getLidarAppName(connector);
                                String tableFileName = LIDARGetters.getLidarTableFileName(connector);
                                Integer randomGeneratorCode = LIDARGetters.getLidarRandomGeneratorCode(connector);
                                String errorInformation = LIDARGetters.getLidarErrorInformation(connector);
                                System.out.println("  State: " + state);
                                System.out.println("  Status: " + status);
                                System.out.println("  App Name: " + appName);
                                System.out.println("  Table File Name: " + tableFileName);
                                System.out.println("  Random Generator Code: " + randomGeneratorCode);
                                System.out.println("  Error Information: " + errorInformation);
                                System.out.println("\n[GETTERS] Example completed.\n");
                            } catch (Exception e) {
                                handleConnectionError(e, opcUrl);
                            }
                            break;

                        case "q":
                        case "quit":
                        case "exit":
                            if (pollingMonitor != null) {
                                pollingMonitor.stop();
                            }
                            if (subMonitor != null) {
                                subMonitor.stop();
                            }
                            connectionMonitor.shutdown();
                            shouldExit.set(true);
                            System.out.println("Exiting...");
                            return;

                        default:
                            System.out.println("Invalid option.");
                    }
                } catch (Exception e) {
                    System.err.println("[ERROR] " + e.getMessage());
                }
            }
        }
    }
}