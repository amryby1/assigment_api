package testing_assignment.qaproject;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserApiTest {
 
 private Map<String, String> user;
 public ExtentReports extent = new ExtentReports();
 public ExtentSparkReporter reporter;
 public ExtentTest test;

 @BeforeSuite
 public void setupSuite() {
	 String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	 String path = System.getProperty("user.dir")+"/reports/report_"+timeStamp+".html";

     reporter = new ExtentSparkReporter(path);
	
	
 }
 
 @BeforeClass
 public void setup() {
  user = Factory.getAuth();
  RestAssured.baseURI = Environment.QA;
 }
 
 @Test
 public void getUserWithValidCredentials() { 
	 try {
  test = extent.createTest("Scenario 1 :Get User With Valid Token");
  Response response = UserApi.getUsers(user);
  System.out.println(response.getBody().asString());
  response.then().assertThat().statusCode(200);
  test.pass("The api resposne status code  is as expected");
  String user= response.then().extract().path("login");
  AssertJUnit.assertEquals(user, "amryby1","The user is not expected .Expected user: amryby1, Response+ "+response.getBody().asString());
 
	 }
	 catch (AssertionError e) {
		// TODO: handle exception
		 Assert.fail("The test is failed due to "+e.getMessage());
		 throw e;
	}
 }
 @Test
 public void updateUserBioWithValidCredentials() { 
	 try {
  test = extent.createTest("Scenario 2 :Update User Bio With Valid Token");
Map<String, String> data = new HashMap<>();
  
  
  data.put("bio", "Bearer Your new bio content here.");
  
  Response response = UserApi.updateUsers(user,data);
  System.out.println(response.getBody().asString());
  
  
  response.then().assertThat().statusCode(200);
  test.pass("The api resposne status code  is as expected");
  String bio= response.then().extract().path("bio");
  AssertJUnit.assertEquals(bio, "Bearer Your new bio content here.","The patch is not working as expected, The+ expected bio is 'Bearer Your new bio content here.'. The actual is  "+bio);
 
	 }
	 catch (AssertionError e) {
		// TODO: handle exception
		 Assert.fail("The test is failed due to "+e.getMessage());
		 throw e;
	}
 }
 
 @Test
 public void invokeGetWithNoToken() { 
	 try {
  test = extent.createTest("Scenario 3 :No Token Provided");

  
Map<String, String> data = new HashMap<>();

data.put("Authorization", "");
  
  Response response = UserApi.getUsers(data);
  System.out.println(response.getBody().asString());
  
  
  response.then().assertThat().statusCode(401);
  test.pass("The api resposne status code  is as expected");
 
	 }
	 catch (AssertionError e) {
		// TODO: handle exception
		 Assert.fail("The test is failed due to "+e.getMessage());
		 throw e;
	}
 }

 @Test
 public void invokeGetWithInvalidToken() { 
	 try {
  test = extent.createTest("Scenario 4 :Invalid Token Provided:");

  
Map<String, String> data = new HashMap<>();

data.put("Authorization", "qa");
  
  Response response = UserApi.getUsers(data);
  System.out.println(response.getBody().asString());
  
  
  response.then().assertThat().statusCode(401);
  test.pass("The api resposne status code  is as expected");
 
	 }
	 catch (AssertionError e) {
		// TODO: handle exception
		 Assert.fail("The test is failed due to "+e.getMessage());
		 throw e;
	}
 }
 
 @Test
 public void invokeGetWithTokenWithNoProperPermission() { 
	 try {
  test = extent.createTest("Scenario 5 :Forbidden Access (Token Without Necessary Permissions)");

  
Map<String, String> data = new HashMap<>();

data.put("Authorization", "<<token without permission>>");
  
  Response response = UserApi.getUsers(data);
  System.out.println(response.getBody().asString());
  
  
  response.then().assertThat().statusCode(403);
  test.pass("The api resposne status code  is as expected");
 
	 }
	 catch (AssertionError e) {
		// TODO: handle exception
		 Assert.fail("The test is failed due to "+e.getMessage());
		 throw e;
	}
 }
 
 @AfterSuite
 public void afterSuite() {
	  extent.attachReporter(reporter);
      extent.flush();
	
 }
}