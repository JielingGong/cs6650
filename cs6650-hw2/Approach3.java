/**
 * This approach is to store all the strings from all threads in a shared collection,
 * and write this to a file from your main() thread after all threads are completed
 */
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Approach3 {
  public static void main(String[] args) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    List<String> allStrings = Collections.synchronizedList(new ArrayList<>());

    ExecutorService executorService = Executors.newFixedThreadPool(500);

    for (int i = 0; i < 500; i++) {
      executorService.submit(() -> {
        for (int j = 0; j < 1000; j++) {
          String data = System.currentTimeMillis() + ", " +
              Thread.currentThread().getId() + ", " + j;
          allStrings.add(data);
        }
      });
    }

    executorService.shutdown();
    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    // Writing to file in the main thread after all threads are completed
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
      for (String str : allStrings) {
        writer.write(str);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    long endTime = System.currentTimeMillis();
    System.out.println("Time taken: " + (endTime - startTime) + " ms");
  }
}
