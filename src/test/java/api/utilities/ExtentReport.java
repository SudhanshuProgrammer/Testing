package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReport {

	public ExtentTest test;
	public ExtentReports report;
	public String repName;

	@SuppressWarnings({ "deprecation" })
	public void reportSetup() {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); 
		Date dt = new Date(); 
		
		String currentdatetimeStamp = df.format(dt);
		
		String path = System.getProperty("user.dir") + "/reports//Automation-Report"+currentdatetimeStamp+".html";
		report = new ExtentReports(path);
		test = report.startTest("Extent API Automation Report");

		report.config().documentTitle("Rest Assured Automation Report"); // Set the Title of the Automation Report
		report.config().reportName("RestAssured Functional Testing");
	
		
		report.addSystemInfo("Compuet Name", "LocalHost");
		report.addSystemInfo("Environment", "QA");
		report.addSystemInfo("Tester Name", "Sudhanshu Kumar");
		report.addSystemInfo("Operating System", "Mac");
		report.addSystemInfo("Broweser Name", "Chrome");

		report.getProjectName();
		report.getReportId();

	}

	public void endTest() {
		report.endTest(test);
		report.flush();
	}

}