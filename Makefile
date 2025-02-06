# Define the main Java classes for execution
CENTRAL_NODE=com.example.CentralNode
FUEL_PUMP=com.example.FuelPump
CREDIT_CARD_SERVER=com.example.CreditCardServer

# Build the project
.PHONY: build
build:
	mvn clean install

# Run the Central Node server
.PHONY: run-central
run-central:
	mvn exec:java -Dexec.mainClass="$(CENTRAL_NODE)"

# Run the Fuel Pump client
.PHONY: run-fuelpump
run-fuelpump:
	mvn exec:java -Dexec.mainClass="$(FUEL_PUMP)"

# Run the Credit Card Server
.PHONY: run-credit
run-credit:
	mvn exec:java -Dexec.mainClass="$(CREDIT_CARD_SERVER)"

# Run all servers in separate tmux panes
.PHONY: run-all
run-all:
	tmux new-session -d -s gaspump 'make run-central'
	tmux split-window -h 'make run-credit'
	tmux split-window -v 'make run-fuelpump'
	tmux attach-session -t gaspump

# Clean the project
.PHONY: clean
clean:
	mvn clean
