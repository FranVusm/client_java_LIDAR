# SI3 OPC UA Java Client

A clean architecture OPC UA client for SI3 monitoring and history, built with Java and Eclipse Milo.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Client Features](#client-features)
- [Getter Functions](#getter-functions)
- [Setter Functions](#setter-functions)
- [Project Structure](#project-structure)

## Installation

### Prerequisites

- Java 17 or higher
- Maven (Build tool)

### Step 1: Clone the Repository

```bash
git clone https://github.com/isaiash/si3-java-client.git
cd si3-java-client
```

### Step 2: Build the Project

Use Maven to build the project and create a standalone JAR with dependencies:

```bash
mvn clean package
```

This will generate the executable JAR file in the `target/` directory:
`target/si3-java-client-1.0.0-jar-with-dependencies.jar`

## Usage

### Basic Usage

Run the client with the OPC UA server URL:

```bash
java -jar target/si3-java-client-1.0.0-jar-with-dependencies.jar opc.tcp://localhost:4840
```

### With Polling Mode

To use polling instead of subscriptions (useful for slower update rates):

```bash
java -jar target/si3-java-client-1.0.0-jar-with-dependencies.jar opc.tcp://localhost:4840 --RATE 1.0
```

The `--RATE` parameter specifies the polling interval in seconds (e.g., 0.5, 1.0, 2.5).

### Secure Mode

To use secure mode (Sign & Encrypt, Basic256Sha256), add the following parameters:

```bash
java -jar target/si3-java-client-1.0.0-jar-with-dependencies.jar opc.tcp://localhost:4840 --secure --cert <cert_path> --key <key_path>
```

- `--secure`: Enables security (Basic256Sha256 / SignAndEncrypt).
- `--cert`: Path to the client certificate (.der or .pem). Default: `client-cert.der` in current dir.
- `--key`: Path to the client private key (.pem). Default: `client-key.pem` in current dir.

### Interactive Menu

Once running, the client provides an interactive menu in the console:

```
================= SI3 OPC UA =================
1) Show verbosity (all data received)
2) Test setters OPC UA
3) Test getters OPC UA
q) Exit
==============================================
Option: 
```

**Menu Options:**
- **1** - Toggles verbosity mode to print all received data values to the console.
- **2** - Runs a hardcoded test sequence for OPC UA setters.
- **3** - Runs a test sequence for OPC UA getters, printing current values.
- **q** - Exits the client.

## Client Features

### 1. OPC UA Connection Management
- Automatic connection and reconnection logic.
- Connection health monitoring (heartbeat check).
- Support for both Subscriptions (default) and Polling modes.
- Secure connection support (X.509 certificates).

### 2. Real-time Monitoring
- **Subscription Mode**: Uses OPC UA subscriptions for efficient real-time updates (default 500ms period).
- **Polling Mode**: Periodic reads at specified intervals.

### 3. Data History
- In-memory history repository (`MemoryHistoryRepo`).
- Stores recent values for monitored nodes.

### 4. Clean Architecture
- Separation of concerns:
  - **Domain**: Entities and DTOs (`SI3`, `SI3Getters`, `SI3Setters`).
  - **Application**: Use cases and monitors (`PollingMonitor`, `SubscriptionMonitor`).
  - **Infrastructure**: OPC UA implementation (`OpcUaConnector`).
  - **Client**: Main entry point and UI (`Main`).

## Getter Functions

All getter functions are located in `src/main/java/si3/client/domain/dto/SI3Getters.java`.
They provide static methods to synchronously read values from the server.

**Examples:**
- `getState(connector)`
- `getStatus(connector)`
- `getHeartbeat(connector)`
- `getPr59Temp1(connector)`
- `getTdcCounter1(connector)`
- `getXimeaTemperature(connector)`
- `getSimulFlag(connector)`

## Setter Functions

All setter functions are located in `src/main/java/si3/client/domain/dto/SI3Setters.java`.
They provide static methods to write values to the server.

**Examples:**
- `setSetupDateTime(connector, value)`
- `setErrorIndex(connector, value)`
- `cmdUpdateStop(connector, value)`
- `cmdSimulOn(connector, value)`

## Project Structure

```
si3-java-client/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── si3/
│   │   │       ├── application/
│   │   │       │   ├── controls/   # Control logic
│   │   │       │   └── monitor/    # Monitoring logic (Polling/Subscription)
│   │   │       ├── client/
│   │   │       │   ├── domain/
│   │   │       │   │   ├── dto/    # Getters and Setters
│   │   │       │   │   └── SI3.java # Domain Entity
│   │   │       │   └── Main.java   # Entry point
│   │   │       └── infrastructure/
│   │   │           ├── MemoryHistoryRepo.java
│   │   │           └── OpcUaConnector.java # Eclipse Milo wrapper
```

## Troubleshooting

### Connection Issues
- Verify the OPC UA server is running.
- Check the URL format: `opc.tcp://hostname:port`.
- If using `--secure`, ensure certificates are valid and trusted by the server.

### Build Errors
- Ensure Java 17+ is installed: `java -version`.
- Ensure Maven is installed: `mvn -version`.
