package com.test.testUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import  org.openqa.selenium.io.FileHandler;
 
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
 
public class ScreenShotUtil {
	public static String filepath = "\\ScreenShots\\";

	public static String screenShotTC(WebDriver scdriver,String fileName)throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a z");
		Date date = new Date();
		
		String dest =System.getProperty("user.dir")+filepath+fileName+"_"+dateFormat.format(date)+".png";
		
		File src=((TakesScreenshot)scdriver).getScreenshotAs(OutputType.FILE);       
		try
		{  
			FileHandler.copy(src, new File(dest));
			return dest;
		}catch (IOException e)      
		{
			throw new RuntimeException("Screenshot Capture Failed: " + e.getMessage());      
		}
		
	}
}
 
