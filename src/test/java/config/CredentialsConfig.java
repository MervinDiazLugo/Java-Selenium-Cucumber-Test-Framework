package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.SkipException;

@Log
public class CredentialsConfig {

  public JSONObject getUserData() {
    JSONObject usersBundle = new JSONObject();
    String usersPath;
    try {
      usersPath = new String(Files.readAllBytes(Paths.get("src/test/resources/data/users.json")));
      JSONParser parser = new JSONParser();
      usersBundle = (JSONObject) parser.parse(usersPath);
    } catch (IOException | ParseException e) {
      throw new SkipException("Credentials are empty " + e.getMessage());
    }

    return usersBundle;
  }

  public JSONArray currentUserCredentials(String user) {
    JSONObject usersBundle = getUserData();
    JSONArray userdata;
    try {
      userdata = (JSONArray) usersBundle.get(user);
    } catch (NullPointerException e) {
      throw new SkipException("User does not exist " + e.getMessage());
    }
    return userdata;
  }
}
