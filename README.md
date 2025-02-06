\documentclass{article}
\usepackage{graphicx}
\usepackage{hyperref}
\hypersetup{colorlinks=true, linkcolor=blue, urlcolor=blue}
\title{Gas Pump System \textendash{} README}
\author{}
\date{\today}

\begin{document}

\maketitle

\section{Overview}
This document provides a detailed guide for the Gas Pump System, a distributed fuel pump system built using gRPC in Java. The system consists of three primary components:

\begin{itemize}
    \item \textbf{Central Node} \textendash{} Manages fuel stock, processes fuel requests, and records transactions.
    \item \textbf{Fuel Pump} \textendash{} Interacts with users, requests fuel, and processes payments.
    \item \textbf{Credit Card Server} \textendash{} Handles credit card payments and validates transactions.
\end{itemize}

\section{Project Structure}
\begin{verbatim}
GasPumpSystem/
│── src/main/java/com/example/
│   ├── CentralNode.java        # Main server handling fuel transactions
│   ├── FuelPump.java           # Client for making fuel requests
│   ├── CreditCardServer.java   # Handles credit card payments
│── src/main/proto/
│   ├── pumpservice.proto       # gRPC service definition
│── pom.xml                     # Maven configuration file
│── Makefile                    # Makefile to automate execution
│── README.md                   # Project documentation
\end{verbatim}

\section{Installation}
\subsection{Prerequisites}
\begin{itemize}
    \item Java 17 or later installed.
    \item Maven installed (check with \texttt{mvn -version}).
    \item tmux installed for running multiple servers in separate panes.
    \begin{verbatim}
    brew install tmux  # macOS
    sudo apt install tmux  # Ubuntu
    \end{verbatim}
    \item Git installed for version control.
\end{itemize}

\subsection{Clone the Repository}
\begin{verbatim}
git clone <your-github-repo-url>
cd GasPumpSystem
\end{verbatim}

\subsection{Build the Project}
\begin{verbatim}
make build
# or manually:
mvn clean install
\end{verbatim}

\section{Running the System}
\subsection{Start the Central Node}
\begin{verbatim}
make run-central
# or manually:
mvn exec:java -Dexec.mainClass="com.example.CentralNode"
\end{verbatim}

\subsection{Start the Fuel Pump}
\begin{verbatim}
make run-fuelpump
# or manually:
mvn exec:java -Dexec.mainClass="com.example.FuelPump"
\end{verbatim}

\subsection{Start the Credit Card Server}
\begin{verbatim}
make run-credit
# or manually:
mvn exec:java -Dexec.mainClass="com.example.CreditCardServer"
\end{verbatim}

\subsection{Run Everything at Once}
\begin{verbatim}
make run-all
\end{verbatim}
\textbf{Note}: This will launch all three components in separate tmux panes.

\section{Fuel Request Process}
\begin{enumerate}
    \item \textbf{Pump Authentication}
    \begin{itemize}
        \item The pump enters a 4-digit ID and 3-digit passcode.
        \item If valid, authentication is successful.
        \item The pump displays current fuel prices.
    \end{itemize}
    \item \textbf{User Inputs Fuel Request}
    \begin{itemize}
        \item Select Fuel Type (R, M, S).
        \item Enter liters of fuel.
        \item The system calculates the total cost.
    \end{itemize}
    \item \textbf{Payment Process}
    \begin{itemize}
        \item Choose Cash or Credit Card.
        \item If Cash, enter the amount manually.
        \item If Credit Card, the transaction is validated by the Credit Card Server.
    \end{itemize}
    \item \textbf{Fuel Approval}
    \begin{itemize}
        \item The Central Node approves the request if stock is available and payment is valid.
        \item The transaction is recorded in logs.
    \end{itemize}
    \item \textbf{Loop Until Exit}
    \begin{itemize}
        \item The pump can continue requesting fuel.
        \item To exit, type "exit".
    \end{itemize}
\end{enumerate}

\section{Example Output}
\subsection{Starting Central Node}
\begin{verbatim}
💰 Enter price for Regular fuel: 1.0
💰 Enter price for Mid-Grade fuel: 2.5
💰 Enter price for Super fuel: 3.0
⛽ Enter starting fuel stock for Regular: 5000
⛽ Enter starting fuel stock for Mid-Grade: 3000
⛽ Enter starting fuel stock for Super: 2000
✅ Prices set: {R=1.0, M=2.5, S=3.0}
✅ Fuel stock initialized: {R=5000.0, M=3000.0, S=2000.0}
🚀 Central Node is running on port 50051...
\end{verbatim}

\subsection{Running the Fuel Pump}
\begin{verbatim}
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
💳 Processing payment... ✅ Approved
📢 Server Response: ✅ Fuel approved for 10 liters of R.
\end{verbatim}

\section{Version Control and Deployment}
\subsection{Push to GitHub}
\begin{verbatim}
git init  # (Only if not already initialized)
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin <your-github-repo-url>
git push -u origin main
\end{verbatim}

\end{document}

