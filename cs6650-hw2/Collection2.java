import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Collection2 {

  private static final int NUM_ELEMENTS = 100_000;
  private static final int NUM_THREADS = 100;

  public static void main(String[] args) throws InterruptedException {
    // Single-threaded test
    System.out.println("Single-threaded test:");
    singleThreadedTest();

    // Multi-threaded test
    System.out.println("\nMulti-threaded test:");
    multiThreadedTest();
  }

  private static void singleThreadedTest() {
    // Test Hashtable
    Hashtable<Integer, Integer> hashtable = new Hashtable<>();
    long startTimeHashtable = System.currentTimeMillis();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
      hashtable.put(i, i);
    }
    long endTimeHashtable = System.currentTimeMillis();
    long timeHashtable = endTimeHashtable - startTimeHashtable;
    System.out.println("Time taken by Hashtable: " + timeHashtable + " ms");

    // Test HashMap
    HashMap<Integer, Integer> hashMap = new HashMap<>();
    long startTimeHashMap = System.currentTimeMillis();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
      hashMap.put(i, i);
    }
    long endTimeHashMap = System.currentTimeMillis();
    long timeHashMap = endTimeHashMap - startTimeHashMap;
    System.out.println("Time taken by HashMap: " + timeHashMap + " ms");

    // Test ConcurrentHashMap
    ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    long startTimeConcurrentHashMap = System.currentTimeMillis();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
      concurrentHashMap.put(i, i);
    }
    long endTimeConcurrentHashMap = System.currentTimeMillis();
    long timeConcurrentHashMap = endTimeConcurrentHashMap - startTimeConcurrentHashMap;
    System.out.println("Time taken by ConcurrentHashMap: " + timeConcurrentHashMap + " ms");

    // Calculate synchronization overheads
    System.out.println("\nSynchronization Overheads (Single-threaded):");
    System.out.println("Hashtable overhead vs HashMap: " + (timeHashtable - timeHashMap) + " ms");
    System.out.println("ConcurrentHashMap overhead vs HashMap: " + (timeConcurrentHashMap - timeHashMap) + " ms");
  }

  private static void multiThreadedTest() throws InterruptedException {
    // Test Hashtable
    Hashtable<Integer, Integer> hashtable = new Hashtable<>();
    long startTimeHashtable = System.currentTimeMillis();
    Thread[] threadsHashtable = createThreads(() -> {
      for (int i = 0; i < NUM_ELEMENTS / NUM_THREADS; i++) {
        hashtable.put(i, i);
      }
    });
    for (Thread thread : threadsHashtable) thread.start();
    for (Thread thread : threadsHashtable) thread.join();
    long endTimeHashtable = System.currentTimeMillis();
    long timeHashtable = endTimeHashtable - startTimeHashtable;
    System.out.println("Time taken by Hashtable: " + timeHashtable + " ms");

    // Test synchronized HashMap
    Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    long startTimeSynchronizedMap = System.currentTimeMillis();
    Thread[] threadsSynchronizedMap = createThreads(() -> {
      for (int i = 0; i < NUM_ELEMENTS / NUM_THREADS; i++) {
        synchronizedMap.put(i, i);
      }
    });
    for (Thread thread : threadsSynchronizedMap) thread.start();
    for (Thread thread : threadsSynchronizedMap) thread.join();
    long endTimeSynchronizedMap = System.currentTimeMillis();
    long timeSynchronizedMap = endTimeSynchronizedMap - startTimeSynchronizedMap;
    System.out.println("Time taken by synchronized HashMap: " + timeSynchronizedMap + " ms");

    // Test ConcurrentHashMap
    ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    long startTimeConcurrentHashMap = System.currentTimeMillis();
    Thread[] threadsConcurrentHashMap = createThreads(() -> {
      for (int i = 0; i < NUM_ELEMENTS / NUM_THREADS; i++) {
        concurrentHashMap.put(i, i);
      }
    });
    for (Thread thread : threadsConcurrentHashMap) thread.start();
    for (Thread thread : threadsConcurrentHashMap) thread.join();
    long endTimeConcurrentHashMap = System.currentTimeMillis();
    long timeConcurrentHashMap = endTimeConcurrentHashMap - startTimeConcurrentHashMap;
    System.out.println("Time taken by ConcurrentHashMap: " + timeConcurrentHashMap + " ms");

    // Calculate synchronization overheads
    System.out.println("\nSynchronization Overheads (Multi-threaded):");
    System.out.println("Hashtable overhead vs synchronized HashMap: " + (timeHashtable - timeSynchronizedMap) + " ms");
    System.out.println("ConcurrentHashMap overhead vs synchronized HashMap: " + (timeConcurrentHashMap - timeSynchronizedMap) + " ms");
  }

  private static Thread[] createThreads(Runnable task) {
    Thread[] threads = new Thread[NUM_THREADS];
    for (int i = 0; i < NUM_THREADS; i++) {
      threads[i] = new Thread(task);
    }
    return threads;
  }
}
