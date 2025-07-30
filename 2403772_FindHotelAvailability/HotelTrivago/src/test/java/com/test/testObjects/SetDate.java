package com.test.testObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetDate {
	WebDriver driver;
    JavascriptExecutor js;
 
    public SetDate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
 
    @FindBy(xpath = "//button[@data-testid='search-form-calendar-checkin']")
    WebElement checkin;
 
    @FindBy(xpath = "//button[@data-testid='search-form-calendar-checkout']")
    WebElement checkout;

    
    public void selectDate(String checkIn, String checkOut) throws InterruptedException {
        Actions act = new Actions(driver);
 
        checkin.sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkin);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//time[@datetime='" + checkIn + "']")));
        WebElement date1 = driver.findElement(By.xpath("//time[@datetime='" + checkIn + "']"));
        act.moveToElement(date1).perform();
        act.click().perform();
 
       WebElement date2 = driver.findElement(By.xpath("//time[@datetime='" + checkOut + "']"));
       act.moveToElement(date2).click().perform();
    }
}
