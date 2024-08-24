package udemy.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

public class TestConfigurations {

  public int multi(int numA, int numB) {
    int result = numA * numB;
    return result;
  }

  public int suma(int numA, int numB) {
    int result = numA + numB;
    return result;
  }

  private String fullName(String firstName, String lastName) {
    return firstName + " " + lastName;
  }

  private List<String> nameList() {
    List<String> myList = new ArrayList<>();

    myList.add("Slinky");
    myList.add("Dukko");
    myList.add("Diana");
    myList.add("Pancho");

    return myList;
  }

  private String[] nameArrayList() {
    // String[] myPetList = {"Slinky", "Dukko", "Diana", "Pancho"};
    String[] myList = {"Slinky", "Dukko", "Diana", "Pancho"};
    return myList;
  }

  private boolean getMyPet(String pet) {
    boolean isPresent = false;

    List<String> myPetList = nameList();

    for (String petName : myPetList) {
      isPresent = StringUtils.equalsIgnoreCase(petName, pet);
      if (isPresent) {
        break;
      }
    }
    return isPresent;
  }

  private boolean getMyPet2(String pet) {
    boolean isPresent = false;

    List<String> myPetList = nameList();

    for (int i = 0; i < myPetList.size(); i++) {
      isPresent = StringUtils.equalsIgnoreCase(myPetList.get(i), pet);
      if (isPresent) {
        break;
      }
    }
    return isPresent;
  }

  @Test
  public void mainTest() {
    String fullName = fullName("Ana", "Sanchez");
    assertEquals(fullName, "Ana Sanchez", fullName + " debe ser Ana Sanchez");

    List<String> myList = nameList();
    assertEquals(myList.size(), 4, myList.size() + " La lista no es de 4");

    boolean isPresent = getMyPet2("Diana");
    assertTrue(isPresent, "Diana no esta en la lista");

    String getName =
        nameList().stream()
            .filter(petName -> StringUtils.equalsIgnoreCase(petName, "Diana"))
            .findFirst()
            .orElse("");
    StringUtils.equalsIgnoreCase(getName, "Diana");
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
    assertEquals(multi(10, 0), 0, "10 x 0 debe ser 0");
  }
}
