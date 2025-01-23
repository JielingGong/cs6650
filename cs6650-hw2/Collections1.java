import java.util.ArrayList;
import java.util.Vector;

public class Collections1 {
  private static final int NUM_ELEMENTS = 100_000;

  public static void main(String[] args) {
    // Test with Vector
    Vector<Integer> vector = new Vector<>();
    long startTimeVector = System.currentTimeMillis();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
      vector.add(i);
    }
    long endTimeVector = System.currentTimeMillis();
    long timeVector = endTimeVector - startTimeVector;
    System.out.println("Time taken to add elements to Vector: " + timeVector + " ms");

    // Test with ArrayList
    ArrayList<Integer> arrayList = new ArrayList<>();
    long startTimeArrayList = System.currentTimeMillis();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
      arrayList.add(i);
    }
    long endTimeArrayList = System.currentTimeMillis();
    long timeArrayList = endTimeArrayList - startTimeArrayList;
    System.out.println("Time taken to add elements to ArrayList: " + timeArrayList + " ms");

    // Calculate and print the synchronization overhead
    if (timeVector > timeArrayList) {
      long overhead = timeVector - timeArrayList;
      double overheadPercentage = (overhead / (double) timeArrayList) * 100;
      System.out.println("Synchronization overhead: " + overhead + " ms (" + overheadPercentage + "%)");
    } else {
      System.out.println("No significant synchronization overhead detected.");
    }
  }

}
