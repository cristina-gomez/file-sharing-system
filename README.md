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
