package udemy.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestConfigurations {

  public int multi(int numA, int numB) {
    int result;
    result = numA * numB;

    return result;
  }

  @Test
  public void testCuil() {

    int[] cuitStartNumbers = {20, 24, 27, 30, 34};
    int randomStartNumberIndex = (int) (Math.random() * 5);
    int randomStartNumber = cuitStartNumbers[randomStartNumberIndex];
    int randomNumber = (int) (Math.random() * 89999999 + 10000000);
    String cuit = Integer.toString(randomStartNumber) + randomNumber;
    System.out.println(cuit);

    int suma = 0;
    for (int i = 0; i < cuit.length(); i++) {
      int digit = Character.getNumericValue(cuit.charAt(cuit.length() - i - 1));
      suma += digit * (2 + (i % 6));
    }

    int verificador = (11 - (suma % 11));
    verificador = (verificador == 11) ? 0 : verificador; // edge case where verifier is 11
    String result = cuit + verificador;
    System.out.println(result);
  }

  @Test
  @DisplayName("Test multiplicationOfZero")
  public void multiplicationOfZero_one() {
    assertEquals(0, multi(10, 0), "10 x 0 debe ser 0");
  }
}
