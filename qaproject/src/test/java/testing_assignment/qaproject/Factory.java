package testing_assignment.qaproject;

import java.util.Map;
import java.util.HashMap;
public class Factory {
 
 public static Map<String, String> getAuth() {
  Map<String, String> auth = new HashMap<>();
  
  
  auth.put("Authorization", "Bearer <personal token>");
  
  return auth;
 }
}