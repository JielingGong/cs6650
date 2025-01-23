/**
 * This approach is to write every string to the file immediately after it is generated in the loop in each thread
 */
import java.io.*;
import java.util.concurrent.*;

public class Approach1 {
  public static void main(String[] args) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    File file = new File("output.txt");

    ExecutorService executorService = Executors.newFixedThreadPool(500); // Using thread pool for better management

    for (int i = 0; i < 500; i++) {
      executorService.submit(() -> {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
          for (int j = 0; j < 1000; j++) {
            String data = System.currentTimeMillis() + ", " +
                Thread.currentThread().getId() + ", " + j;
            writer.write(data);
            writer.newLine();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }

    executorService.shutdown();
    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    long endTime = System.currentTimeMillis();
    System.out.println("Time taken: " + (endTime - startTime) + " ms");
  }
}

