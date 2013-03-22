package cscie160.hw6;

import java.util.LinkedList;

/**
 * This Thread has a reference to the request queue and retrieves the ATMRunnable from the list
 * and executes the request contained in it.
 */
public class ATMThread extends Thread {
    private LinkedList<ATMRunnable> requestQueue = new LinkedList<ATMRunnable>();

    /**
     * Accept a reference to the requestQueue of ATMRunnable objects
     * @param requestQueue the referenced object to a list of ATMRunnable objects
     */
    public ATMThread(LinkedList<ATMRunnable> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void run() {

        // If the requestQueue is empty, then wait
        while(requestQueue.isEmpty()) {
            try {
                synchronized (requestQueue) {
                    requestQueue.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("Collection error: " + e);
                e.printStackTrace();
            }
        }

        // While the requestQueue is not empty, run the ATMRunnable objects in the queue
        while (!requestQueue.isEmpty()) {

            synchronized (requestQueue) {

                System.out.println("Running request in thread: Thread-" + this.getName());

                // Retrieve some work; block if the queue is empty
                ATMRunnable r = requestQueue.removeFirst();

                // Terminate if the end-of-stream marker was retrieved
                if (r != null) {
                    r.run();

                    // Call wait in order to be notified of additional incoming requests
                    try {
                        requestQueue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Collection error: " + e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}