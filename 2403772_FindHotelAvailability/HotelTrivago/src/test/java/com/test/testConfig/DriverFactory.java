package com.test.testConfig;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
    // Static WebDriver instance accessible globally
	public static WebDriver driver = null;
	
    // Placeholder for any future use of a shared WebElement (currently unused)
	static WebElement ele = null;
	
    // Browser type and target URL - to be set externally before calling getUrl()
	public static String browser;
	public static String url;
	
	/**
     * Launches the appropriate browser based on the 'browser' variable,
     * navigates to the specified URL, and applies common setup configurations.
     *
     * @return Initialized WebDriver instance
     */
	public static WebDriver getUrl() {
		if(browser.equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
		} else if(browser.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		} 
        // Standard driver setup
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		return driver;
	}		
	
	
	/**
     * Quits the browser session and cleans up the WebDriver instance.
     */
	
	
	public static void driverClose() {
		driver.quit();
	}
}
