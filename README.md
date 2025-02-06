# Gas Pump System

## Overview
This document provides a detailed guide for the Gas Pump System, a distributed fuel pump system built using gRPC in Java. The system consists of three primary components:

- **Central Node** – Manages fuel stock, processes fuel requests, and records transactions.
- **Fuel Pump** – Interacts with users, requests fuel, and processes payments.
- **Credit Card Server** – Handles credit card payments and validates transactions.

## Installation

### Prerequisites
- Java 17 or later installed.
- Maven installed (check with `mvn -version`).
- **tmux** installed for running multiple servers in separate panes.
    ```sh
    # macOS
    brew install tmux
    # Ubuntu
    sudo apt install tmux
    ```
- Git installed for version control.

### Clone the Repository
```sh
git clone <repository_url>
cd GasPumpSystem
```
Build the Project
```sh
make build
# Or manually:
mvn clean install
```
Running the System
Start the Central Node
```sh
make run-central
# Or manually:
mvn exec:java -Dexec.mainClass="com.example.CentralNode"
```
Start the Fuel Pump
```sh
make run-fuelpump
# Or manually:
mvn exec:java -Dexec.mainClass="com.example.FuelPump"
```
Start the Credit Card Server
```sh
make run-credit
# Or manually:
mvn exec:java -Dexec.mainClass="com.example.CreditCardServer"
```
Run Everything at Once
```sh
make run-all
Note: This will launch all three components in separate tmux panes.
```

Fuel Request Process
Pump Authentication

The pump enters a 4-digit ID and 3-digit passcode.
If valid, authentication is successful.
The pump displays current fuel prices.
User Inputs Fuel Request

Select Fuel Type (R, M, S).
Enter liters of fuel.
The system calculates the total cost.
Payment Process

Choose Cash or Credit Card.
If Cash, enter the amount manually.
If Credit Card, the transaction is validated by the Credit Card Server.
Fuel Approval

The Central Node approves the request if stock is available and payment is valid.
The transaction is recorded in logs.
Loop Until Exit

The pump can continue requesting fuel.
To exit, type "exit".
Example Output
Starting Central Node
```sh
💰 Enter price for Regular fuel: 1.0
💰 Enter price for Mid-Grade fuel: 2.5
💰 Enter price for Super fuel: 3.0
⛽ Enter starting fuel stock for Regular: 5000
⛽ Enter starting fuel stock for Mid-Grade: 3000
⛽ Enter starting fuel stock for Super: 2000
✅ Prices set: {R=1.0, M=2.5, S=3.0}
✅ Fuel stock initialized: {R=5000.0, M=3000.0, S=2000.0}
🚀 Central Node is running on port 50051...
```
Running the Fuel Pump
```sh
🔢 Enter Pump ID (4-digit): 1234
🔑 Enter Passcode (3-digit): 567
✅ Authentication successful. You can request fuel.
📢 Current Fuel Prices:
  ⛽ R: $1.0 per liter
  ⛽ M: $2.5 per liter
  ⛽ S: $3.0 per liter

🛢 Select Fuel Type (R - Regular, M - Mid-Grade, S - Super): R
⛽ Enter Fuel Liters: 10
💲 Total Cost: $10.0
💳 Select Payment Method (cash/card): card
💳 Processing payment...
✅ Approved
📢 Server Response: ✅ Fuel approved for 10 liters of R.
```
