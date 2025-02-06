# ⛽ Gas Pump System 🚀

This is a **distributed fuel pump system** built using **gRPC** in Java. It consists of three main components:

1. **Central Node** 🏢 - Manages fuel stock, processes fuel requests, and records transactions.
2. **Fuel Pump** ⛽ - Interacts with users, requests fuel, and processes payments.
3. **Credit Card Server** 💳 - Handles credit card payments and validates transactions.


---

## 🔧 Installation

### **1️⃣ Prerequisites**
- **Java 17** or later installed.
- **Maven** installed (`mvn -version` to check).
- **tmux** installed (for running multiple servers in separate panes).
  ```sh
  brew install tmux  # macOS
  sudo apt install tmux  # Ubuntu
