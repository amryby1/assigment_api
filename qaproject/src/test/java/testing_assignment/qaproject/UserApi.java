package testing_assignment.qaproject;
import java.util.Map;

import javax.ws.rs.core.Response;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class UserApi {

 private static String USERS_ENDPOINT = "user";
 
 public static io.restassured.response.Response getUsers(Map<String, String> requestBody) {
  return RestAssured
   .given()
    .contentType(ContentType.JSON)
    .headers(requestBody)
  
   .when()
    .get(USERS_ENDPOINT);
 }
 
 public static io.restassured.response.Response updateUsers(Map<String, String> headertBody,Map<String, String> requesttBody) {
	  return RestAssured
	   .given()
	    .contentType(ContentType.JSON)
	    .headers(headertBody)
	   .body(requesttBody)
	   .when()
	    .patch(USERS_ENDPOINT);
	 }

}