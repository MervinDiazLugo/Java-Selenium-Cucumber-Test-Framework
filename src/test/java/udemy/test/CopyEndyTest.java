package udemy.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class CopyEndyTest {

  private boolean isEndy(int number) {
    return (number >= 0 && number <= 10) || (number >= 90 && number <= 100);
  }

  private List<Integer> copyEndy(int[] endyNumbersArray, int len) {
    List<Integer> endyNumbers = new ArrayList<>();

    for (int number : endyNumbersArray) {
      if (isEndy(number)) {
        endyNumbers.add(number);
      }
      if (endyNumbers.size() == len) {
        break;
      }
    }

    return endyNumbers;
  }

  @Test
  public void testCopyEndy() {
    int[] numberList = {9, 11, 90, 22, 6};
    List<Integer> endyNumbers = copyEndy(numberList, 2);

      Assert.assertEquals(endyNumbers.size(), 2, "Array did not met length");
      Assert.assertEquals((int) endyNumbers.get(0), 9, "Character t mismatch");
      Assert.assertEquals((int) endyNumbers.get(1), 90, "Character a mismatch");

    endyNumbers = copyEndy(numberList, 3);
      Assert.assertEquals(endyNumbers.size(), 3, "Array did not met length");
      Assert.assertEquals((int) endyNumbers.get(0), 9, "Character 9 mismatch");
      Assert.assertEquals((int) endyNumbers.get(1), 90, "Character 90 mismatch");
      Assert.assertEquals((int) endyNumbers.get(2), 6, "Character 6 mismatch");

    numberList = new int[]{12, 1, 1, 13, 0, 20};
    endyNumbers = copyEndy(numberList, 1);
    Assert.assertEquals(endyNumbers.size(), 1, "Array did not met length");
    Assert.assertEquals((int) endyNumbers.get(0), 1, "Character 9 mismatch");
  }
}
