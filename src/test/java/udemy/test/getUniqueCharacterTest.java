package udemy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class getUniqueCharacterTest {

  private List<String> getUniqueCharacter(String wordToTest) {
    List<String> uniqueCharacters = new ArrayList<>();
    wordToTest = wordToTest.toLowerCase(Locale.ROOT);

    for (int i = 0; i < wordToTest.length(); i++) {
      String character = StringUtils.substring(wordToTest, i, i + 1);

      int times = 0;
      for (int j = 0; j < wordToTest.length(); j++) {
        if (StringUtils.equals(character, StringUtils.substring(wordToTest, j, j + 1))) {
          ++times;
          if (times > 1) {
            break;
          }
        }
      }
      if (times == 1) {
        uniqueCharacters.add(character);
      }
    }

    return uniqueCharacters;
  }

  @Test
  public void testGetUniqueCharacter() {
    List<String> uniqueCharacters = getUniqueCharacter("otorrinolaringologo");

    Assert.assertTrue(StringUtils.equals(uniqueCharacters.get(0), "t"), "Character t mismatch");
    Assert.assertTrue(StringUtils.equals(uniqueCharacters.get(1), "a"), "Character a mismatch");

    uniqueCharacters = getUniqueCharacter("statistics");

    Assert.assertTrue(StringUtils.equals(uniqueCharacters.get(0), "a"), "Character a mismatch");
    Assert.assertTrue(StringUtils.equals(uniqueCharacters.get(1), "c"), "Character c mismatch");
  }
}
