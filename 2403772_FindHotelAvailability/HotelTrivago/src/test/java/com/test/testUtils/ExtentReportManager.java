package com.test.testUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import org.openqa.selenium.WebDriver;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentReportManager {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extentreport;
	public ExtentTest testLogger;
	WebDriver driver;
	
	public ExtentReportManager() {
		
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		String reportFolderPath = System.getProperty("user.dir") + "\\reports\\";
 
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath+"\\TrivagoAutomationReport"+timestamp);
 
		sparkReporter.config().setDocumentTitle("Automation Extent Report");
		sparkReporter.config().setReportName("Trivago Test Report");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm:ss a '('zzz')'");
		
		extentreport = new ExtentReports();
		extentreport.attachReporter(sparkReporter);
		
	}
 
	public void createTest(String testName) {
		testLogger = extentreport.createTest(testName);
	}
 
	public void logPass(String message) {
		testLogger.pass(message);
	}
	
	public void flushReports() {
		extentreport.flush();
	}
 
}