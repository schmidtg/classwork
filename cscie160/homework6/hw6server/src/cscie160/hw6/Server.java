package cscie160.hw6;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 * Creates a Server socket that can connects with a client. The two communicate through a proxy.
 */
public class Server {
    private LinkedList<ATMRunnable> requestQueue = new LinkedList<ATMRunnable>();
    private ServerSocket serverSocket;
    private ATMImplementation atmImplementation;

    /**
     * Initialize server class based on given port (or use default)
     * @param port number of port
     * @throws java.io.IOException In case there's invalid command input
     */
    public Server(int port) throws java.io.IOException {

        // Create some threads and start them
        for(int i = 0; i < 5; i++) {
            ATMThread t = new ATMThread(requestQueue);
            t.setName(String.valueOf(i));
            t.start();
        }

        System.out.println("Starting server socket on port " + port + "...");

        // Create a new serverSocket on the provide port (default 1099)
        serverSocket = new ServerSocket(port);

        // Create a new instance of an ATM with a starting balance of $0.00
        atmImplementation = new ATMImplementation(0.0f);
    }

    /** serviceClient accepts a client connection and reads lines from the socket.
     *  Each line is handed to executeCommand for parsing and execution.
     * @throws java.io.IOException error
     */
    public void serviceClient() throws java.io.IOException {

        try {
            System.out.println("Listening for a client request...");
            Socket clientConnection = serverSocket.accept();

            // Arrange to read input from the Socket
            InputStream inputStream = clientConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Arrange to write result across Socket back to client
            OutputStream outputStream = clientConnection.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            try {
                String commandLine;
                // Read input from the client
                while ((commandLine = bufferedReader.readLine()) != null) {
                    // Create a worker thread and pass it data
                    ATMRunnable worker = new ATMRunnable(commandLine, atmImplementation, printStream);

                    // Add the worker thread to the requestQueue
                    requestQueue.addLast(worker);

                    // Notify waiting threads that a request must be processed
                    synchronized (requestQueue) {
                        requestQueue.notifyAll();
                    }
                }
            }
            catch (SocketException sException) {
                // client has stopped sending commands.  Exit gracefully.
                System.out.println("done");
            }

        } catch (IOException ex) {
            System.out.println("There was a problem connecting to the client.");
            ex.printStackTrace();
        }

	}

	public static void main(String argv[]) {
		int port = 1099; // Default port
		if(argv.length > 0) {
			try {
				port = Integer.parseInt(argv[0]);
			} 
			catch (Exception e) {
				e.printStackTrace(); 
			}
		}
		try {
			Server server = new Server(port);
            server.serviceClient();
			System.out.println("Client serviced");
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
