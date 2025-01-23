import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SimpleCounter {
  private static final int DEFAULT_THREADS = 1000;
  private static final int INCREMENTS_PER_THREAD = 10;
  private static final Object LOCK = new Object();
  private static int counter = 0;

  public static void main(String[] args) {
    // Set the number of threads based on input or default
    int numThreads = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_THREADS;

    System.out.println("Starting with " + numThreads + " threads.");

    // Take initial timestamp
    long startTime = System.currentTimeMillis();

    // Create and start threads
    Thread[] threads = new Thread[numThreads];
    for (int i = 0; i < numThreads; i++) {
      threads[i] = new Thread(() -> {
        for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
          incrementCounter();
        }
      });
      threads[i].start();
    }

    // Wait for all threads to complete
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // Take final timestamp
    long endTime = System.currentTimeMillis();

    // Print results
    System.out.println("Final counter value: " + counter);
    System.out.println("Total duration: " + (endTime - startTime) + " ms");
  }

  private static void incrementCounter() {
    synchronized (LOCK) {
      counter++;
    }
  }
}



