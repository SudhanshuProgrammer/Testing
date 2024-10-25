package api.utilities;

import java.io.File;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	public void onStart(ITestContext context) {

		File reportDir = new File(System.getProperty("user.dir") + "//reports"); 
		sparkReporter = new ExtentSparkReporter(reportDir + "//Report.html");
		
		sparkReporter.config().setDocumentTitle("API Automation Report"); // Set the Title of the Automation Report
		sparkReporter.config().setReportName("Automation Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Compuet Name", "LocalHost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester Name", "Sudhanshu Kumar");
		extent.setSystemInfo("Operating System", "Mac");
		extent.setSystemInfo("Broweser Name", "Chrome");
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName()); // create a new entry in the report
		test.log(Status.PASS, "Test Case PASSED is: " + result.getName()); // update status Passed
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName()); // create a new entry in the report
		test.log(Status.FAIL, "Test Case FAILED is: " + result.getName()); // update status Failed
		test.log(Status.FAIL, "Test Case FAILED Cause is: " + result.getThrowable()); // define the cause of Failure
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName()); // create a new entry in the report
		test.log(Status.SKIP, "Test Case SKIPPER is: " + result.getName()); // update status Skipped
	}

	public void onTestFinish(ITestContext context) {
		extent.flush();
	}
}