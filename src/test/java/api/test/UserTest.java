package api.test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {

	Faker fk;
	User userPayload;
	public Logger logger;
	public ExtentTest test;
	public ExtentReports report;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	Date date = new Date();
	String timeStamp = sdf.format(date);
	String reportPath = System.getProperty("user.dir") + "/reports//Report" + "{" + timeStamp + "}" + ".html";

	@BeforeClass
	public void setup() {

		report = new ExtentReports(reportPath);
		test = report.startTest("Extent API Automation Report");

		fk = new Faker();
		userPayload = new User();

		userPayload.setId(fk.idNumber().hashCode());
		userPayload.setUsername(fk.name().username());
		userPayload.setFirstName(fk.name().firstName());
		userPayload.setLastName(fk.name().lastName());
		userPayload.setEmail(fk.internet().safeEmailAddress());
		userPayload.setPassword(fk.internet().password(5, 10));
		userPayload.setPhone(fk.phoneNumber().cellPhone());

		// logs
		logger = LogManager.getLogger(this.getClass());

	}

	// Create User
	@Test(priority = 1)
	public void testPostUser() {

		logger.info("******** Creating User *********");

		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200);
			test.log(LogStatus.PASS, "User is Created : Test Case PASSED ");
		} else {
			test.log(LogStatus.FAIL, "User is not Created : Test Case FAILED ");
		}

		logger.info("******* User is Created *******");
	}

	// Retrieve User
	@Test(priority = 2)
	public void testGetUserById() {

		logger.info("**** Reading User ****");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());

		response.then().log().all();
		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200);
			test.log(LogStatus.PASS, "User is Displayed : Test Case PASSED ");
		} else {
			test.log(LogStatus.FAIL, "User is not Displayed : Test Case FAILED ");
		}

		logger.info("**** User info is displayed ****");
	}

	// Update User
	@Test(priority = 3)
	public void testUpdateUserByName() {

		logger.info("**** Updating User ****");

		userPayload.setFirstName(fk.name().firstName());
		userPayload.setLastName(fk.name().lastName());
		userPayload.setEmail(fk.internet().safeEmailAddress());

		Response response = UserEndPoints.updateUser(userPayload, this.userPayload.getUsername());
		response.then().log().body();
		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200);
			test.log(LogStatus.PASS, "User is Updated : Test Case PASSED ");
		} else {
			test.log(LogStatus.FAIL, "User is not Updated : Test Case FAILED ");
		}
		// Checking data after update
		Response responseAfterUpdate = UserEndPoints.readUser(userPayload.getUsername());
		AssertJUnit.assertEquals(responseAfterUpdate.getStatusCode(), 200);

		logger.info("**** User is Updated ****");
	}

	// Delete User
	@Test(priority = 4)
	public void testDeleteUserByName() {

		logger.info("**** Deleting User ****");
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().log().body().statusCode(200); // Chai Assertion

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200);
			test.log(LogStatus.PASS, "User is Deleted : Test Case PASSED ");
		} else {
			test.log(LogStatus.FAIL, "User is not Deleted : Test Case FAILED ");
		}
		response.then().log().all();
		logger.info("**** User is Deleted ****");

	}

	@AfterClass
	public void endTest() {
		report.endTest(test);
		report.flush();

		try {
			File extentReport = new File(reportPath);
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
