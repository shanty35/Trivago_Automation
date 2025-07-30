package com.test.testObjects;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FilterResult {
	WebDriver driver;
	Actions act;

	public FilterResult(WebDriver driver) {
		this.driver = driver;
		this.act = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

    // WebElements used for sorting and filtering
	@FindBy(xpath = "//button[@title = 'Featured stays']")
	WebElement sortByRate;

	@FindBy(xpath = "//button[@data-testid = 'filters-popover-apply-button']")
	WebElement sortApply;

	@FindBy(xpath = "//button[@name='guest_rating_filters']")
	WebElement guestRate;

	@FindBy(xpath = "//button[@data-testid='8.5-rating-hotels-filter']")
	WebElement rateSelect;

	
	public void sortByRating() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(sortByRate));
		sortByRate.click();

		WebElement choose = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//label[text()='Rating & Recommended']")));
		act.moveToElement(choose).click().perform();

		wait.until(ExpectedConditions.elementToBeClickable(sortApply));
		sortApply.click();
	}

	public void guestRating() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		wait.until(ExpectedConditions.elementToBeClickable(guestRate));
		act.moveToElement(guestRate).click().perform();

		wait.until(ExpectedConditions.elementToBeClickable(rateSelect));
		act.moveToElement(rateSelect).click().perform();

		wait.until(ExpectedConditions.elementToBeClickable(sortApply));
		act.moveToElement(sortApply).click().perform();
	}

	
	public Map<String, String> finalResultAsMap() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Map<String, String> hotelMap = new LinkedHashMap<>();

	    List<WebElement> list = driver.findElements(
	        By.xpath("//ol[@data-testid ='accommodation-list' and @class='PNIRi0']/li"));

	    for (int j = 0; j < list.size(); j++) {
	        String nameText = "";
	        String ratingText = "";

	        WebElement hotelElement = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("(//ol[@data-testid='accommodation-list' and @class='PNIRi0']/li)[" + (j + 1) + "]")));
	        act.scrollToElement(hotelElement).perform();

	        try {
	            WebElement name = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("(//button/span[@itemprop='name'])[" + (j + 1) + "]")));
	            nameText = name.getText();
	        } catch (StaleElementReferenceException e) {
	            System.out.println("name list stale exception");
	        }

	        try {
	            WebElement rating = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("(//span[@itemprop='ratingValue'])[" + (j + 1) + "]")));
	            ratingText = rating.getText();
	        } catch (StaleElementReferenceException e) {
	            System.out.println("rating stale");
	        }

	        hotelMap.put(nameText, ratingText);
	    }

	    return hotelMap;
	}


	public LinkedHashMap<String, String> getTopFive(Map<String, String> hotelMap) {
    return hotelMap.entrySet()
        .stream()
        .sorted((e1, e2) -> {
            double r1 = Double.parseDouble(e2.getValue());
            double r2 = Double.parseDouble(e1.getValue());
            return Double.compare(r1, r2); // Descending order
        })
        .limit(5)
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new
        ));
}

	}

