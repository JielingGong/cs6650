/**
 * This approach is to write all the strings from one thread after they are generated
 * and just before a thread terminates
 */
import java.io.*;
import java.util.concurrent.*;

public class Approach2 {
  public static void main(String[] args) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    File file = new File("output.txt");

    ExecutorService executorService = Executors.newFixedThreadPool(500);

    for (int i = 0; i < 500; i++) {
      executorService.submit(() -> {
        StringBuilder dataBuilder = new StringBuilder();
        for (int j = 0; j < 1000; j++) {
          dataBuilder.append(System.currentTimeMillis())
              .append(", ")
              .append(Thread.currentThread().getId())
              .append(", ")
              .append(j)
              .append("\n");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
          writer.write(dataBuilder.toString());
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
