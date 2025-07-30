package com.test.testScenario;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.test.testConfig.DriverFactory;
import com.test.testObjects.SetDate;
import com.test.testObjects.FilterResult;
import com.test.testObjects.SetHomePageData;
import com.test.testUtils.ExcelUtil;
import com.test.testUtils.ExtentReportManager;
import com.test.testUtils.ScreenShotUtil;


public class HotelAvailabilityTest {
    static WebDriver driver;
    static String[] finalRes = new String[5];
    static String sheet = "Sheet1";
    static ExcelUtil erw;
    static Object[][] loginData;
    static SetHomePageData hm;
    ExtentReportManager erm;
    static String path = "src\\test\\java\\com\\test\\testScripts\\Bookdata.xlsx";

    @Parameters("browser")
    @BeforeTest
    public void setup(String browser) {
        DriverFactory.browser = browser;
        DriverFactory.url = "https://www.trivago.in/";
        driver = DriverFactory.getUrl();
        Assert.assertNotNull(driver, "WebDriver initialization failed!");

        hm = new SetHomePageData(driver);
        erm = new ExtentReportManager();
    }
    
    
    
    @Test(priority = 1, dataProvider = "dp")
    public void cityInitialize(String city, String check_in, String check_out, String size) throws InterruptedException {
        erm.createTest("City Initialization Test");
        hm.searchBox(city);
        SetDate cal = new SetDate(driver);
        cal.selectDate(check_in, check_out);
        hm.memberRoomSelection();
        hm.setNumberOfAdults(size);
        driver.findElement(By.xpath("//button[@data-testid='search-button-with-loader']//span[text()='Search']")).click();
    }

   
    
    @Test(priority = 2)
    public void result() {
        erm.createTest("Result Extraction Test");
        FilterResult fil = new FilterResult(driver);
        fil.sortByRating();
        fil.guestRating();
        Map<String,String> res = fil.finalResultAsMap();
        LinkedHashMap<String, String> topFive = fil.getTopFive(res);
        int i = 0;
        for (Map.Entry<String, String> entry : topFive.entrySet()) {
            finalRes[i] = entry.getKey(); // hotel name
            i++;
        }
    }
    
    @DataProvider(name = "dp")
    public Object[][] excelDataFetch() {
        erw = new ExcelUtil(path, sheet);
        int rows = erw.getRowsCount();
        int cols = erw.getColumnCount();
        loginData = new Object[2 - 1][5 - 1];
        System.out.println(rows);
        System.out.println(cols);
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < 5 - 1; j++) {
                loginData[i - 1][j] = erw.getCellData(i, j);
            }
        }
        return loginData;
    }
    
   
    @AfterMethod
    public void screnShot() throws IOException {
    	ScreenShotUtil.screenShotTC(driver,"SctreenShotPages");
    }
    

    @BeforeClass
    public void stratReport()
    {
    	erm=new ExtentReportManager();
    	erm.createTest("Trivago Hotels");
    }
  
    @AfterMethod
	public void getResult(ITestResult result) throws IOException {
	       if(result.getStatus() == ITestResult.FAILURE) {
	    	   erm.testLogger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	    	   erm.testLogger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
	    	   ScreenShotUtil.screenShotTC(driver,"failure_Screenshot");
	    	   String screenshotPath = ScreenShotUtil.screenShotTC(driver, result.getName());
	    	   erm.testLogger.fail("Test Case Failed Snapshot is below " + erm.testLogger.addScreenCaptureFromPath(screenshotPath));	    	  
	       }
	       else if(result.getStatus() == ITestResult.SUCCESS) {
	    	   erm.testLogger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
	       }
	       else if(result.getStatus() == ITestResult.SKIP){
	    	   erm.testLogger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
	       }
	   }
 
   
    @AfterClass
    public void writeDataIntoExcel() throws IOException {
        for (int j = 0; j < finalRes.length; j++) {
            ExcelUtil.setCellData(sheet, j + 1, 4, finalRes[j]);
        }
    }

   
    @AfterClass
    public void tearDown() {
        erm.flushReports();
        
    }
    
  
    @AfterClass
    public void erase() {
    	if(driver!=null) {
    		DriverFactory.driverClose();
    	}
    	
    }
}
