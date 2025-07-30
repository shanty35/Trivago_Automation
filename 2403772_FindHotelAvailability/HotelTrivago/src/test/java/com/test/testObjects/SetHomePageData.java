package com.test.testObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SetHomePageData {
	WebDriver driver;
	Actions act;
	public SetHomePageData(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@type='search']")
	WebElement searchBox;
	
	@FindBy(xpath = "//input[@data-testid='adults-amount']")
	WebElement adSize;
	
	@FindBy(xpath = "//button[@data-testid='search-form-guest-selector']")
	WebElement memSelection;
	
	
	public void searchBox(String search) {
		searchBox.sendKeys(search);
		act = new Actions(driver);
		WebElement selection = driver.findElement(By.xpath("//*[@id=\"suggestion-list\"]/ul/li[1]"));
		act.moveToElement(selection).click().build().perform();
	}
	
	
	public void memberRoomSelection() {
//		act.moveToElement(memSelection)
//		.click().click().build().perform();
		memSelection.click();
		memSelection.click();
	}
	
	 
	public void setNumberOfAdults(String size) {
		//adSize.click();
		adSize.sendKeys(size);
		WebElement apply = driver.findElement(By.xpath("//button[text() = 'Apply']"));
		apply.click();
		
	}
}
