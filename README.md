# Distributed File Sharing System
Java-based hybrid P2P file-sharing system. It features a central server for user coordination and direct client-to-client transfers via dynamic port allocation. Highlights include manual implementations of OS synchronization algorithms to manage concurrency, paired with a multithreaded Swing GUI.

## Features and Concepts
### Networking and Communication
* **TCP/IP Sockets**: Maintains persistent, bidirectional communication between multiple clients and the server.
* **P2P Transfer**: File payloads are sent directly between peers using dedicated 'Emisor' and 'Receptor' threads to ensure high performance and scalability.
* **Custom Protocol**: Commands are exchanged via Object Serialization, using a proprietary messaging protocol.

## Low-Level Concurrency and Synchronization
* **Manual Mutual Exclusion**: Implementation of classic synchronization algorithms from scratch.
* **Readers-Writers Pattern**: A custom Monitor manages the global user catalog, allowing multiple simultaneous reads but exclusive writes.
* **Producer-Consumer Pattern**: A synchronized circular buffer managed with Semaphores coordinates dynamic port assignment.

## Graphical User Interface (GUI)
* **Multithreaded Swing GUI**: The interface remains responsive during network operations and file transfers by offloading tasks to background threads.
* **Window Persistence**: Features a 'WindowTracker' system to maintain window position across different application states.

## Project Structure

The source code is divided into different modules:
* **servidor**: Core server logic and connection management.
* **cliente**: User state management, background listeners and P2P transfer logic.
* **mensaje**: Defines the communication protocol.
* **usuario**: User entities and the file catalog protected by a Readers-Writers monitor.
* **cerrojos**: Low-level implementation of OS synchronization algorithms.
* **gui**: Swing-based user interfaces and window controllers.

## Installation and Execution

### 1. Compilation
Navigate to the project root (where the 'src' folder is located) and run:

**Linux/MacOS:**
```bash
mkdir bin
javac -d bin src/*/*.java
```

**Windows (CMD/PowerShell):**
```cmd
mkdir bin
javac -d bin src/cliente/*.java src/servidor/*.java src/mensaje/*.java src/usuario/*.java src/cerrojos/*.java src/gui/*.java
```


### 2. Running the System
To run the system, open separate terminal windows to simulate different nodes:

**Terminal 1: Server**
```bash
java -cp bin servidor.MainServidor
```
Must be running first to coordinate the network.

**Terminals 2 & 3: Clients (Peers)**
```bash
java -cp bin cliente.MainCliente
```
Run this command in two (or more) different windows. Every client has the same capabilities: they can all share files and download from others.

### 3. Usage and Testing (P2P Simulation)
Since this is a Peer-to-Peer system, any user can be both a sender and a receiver.

1. Sign in as 'UserA' in one window and 'UserB' in the other.
2. The system creates *./datos/UserA/* and *./datos/UserB/* automatically.
3. Drop some files into both directories.
4. Interacting:
   * From UserA's window, you can see and download UserB's files.
   * From UserB's window, you can see and download UserA's files.
5. When a download starts, the data moves directly between the two client windows using dynamic ports.

## Project Context
This system was developed as part of the **Concurrent Programming** (*Programación Concurrente*) subject.

## Author
Cristina Gómez


