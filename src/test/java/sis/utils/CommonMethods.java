package sis.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import sis.testbase.PageInitializer;

public class CommonMethods extends PageInitializer{
	/**
	 * This method clears a text box and send the text parameter to it.
	 * 
	 * @param element
	 * @param text
	 */
	public static void sendText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * This method checks if the radio/checkbox is enabled, and then clicks on the
	 * element that have the attribute value as selectValue.
	 * 
	 * @param elementList
	 * @param selectValue
	 */
	public static void clickRadioOrCheckbox(List<WebElement> elementList, String selectValue) {
		for (WebElement el : elementList) {
			String elementValue = el.getAttribute("value").trim();

			if (elementValue.equals(selectValue) && el.isEnabled()) {
				el.click();
				break;
			}
		}
	}

	/**
	 * This method pauses the execution for a certain amount of seconds.
	 * 
	 * 
	 * @param seconds
	 */
	public static void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method selects a drop down element based on visible text.
	 * 
	 * @param element
	 * @param visibleText
	 */
	public static void selectDropdown(WebElement element, String visibleText) {
		try {

			Select sl = new Select(element);
			sl.selectByVisibleText(visibleText);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method selects a drop down element based on index.
	 * 
	 * 
	 * @param element
	 * @param index
	 */
	public static void selectDropdown(WebElement element, int index) {
		try {
			Select sl = new Select(element);
			sl.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * This method switches the focus of the driver to an Alert and accepts it if
	 * found. If not found, the NoAlertPresentException is handled.
	 * 
	 */
	public static void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method switches the focus of the driver to an Alert and dismisses it if
	 * found. If not found, the NoAlertPresentException is handled.
	 * 
	 */
	public static void dismissAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will switch the focus of the driver to an alert and return its
	 * text. If alert is not found, the NoAlertPresentException is handled.
	 * 
	 * @return
	 */
	public static String getAlertText() {
		String alertText = null;
		try {
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}

		return alertText;
	}

	/**
	 * This method will switch the focus of the driver to an alert and send text to
	 * it. If alert is not found, the NoAlertPresentException is handled.
	 * 
	 * @param text
	 */
	public static void sendAlertText(String text) {
		try {
			Alert alert = driver.switchTo().alert();
			alert.sendKeys(text);
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * This method switches to a frame using its name or id.
	 * 
	 * @param nameOrId
	 */
	public static void switchToFrame(String nameOrId) {
		try {
			driver.switchTo().frame(nameOrId);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * This method switches to a frame using its index.
	 * 
	 * @param nameOrId
	 */
	public static void switchToFrame(int index) {
		try {
			driver.switchTo().frame(index);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method switches to a frame using a WebElement.
	 * 
	 * @param element
	 */
	public static void switchToFrame(WebElement element) {
		try {
			driver.switchTo().frame(element);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method switches the focus of the driver to the child window.
	 * 
	 */
	public static void switchToChildWindow() {
		String mainWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!mainWindow.equals(handle)) {
				driver.switchTo().window(handle);
			}
		}

	}

	/**
	 * This method creates a wait object based on the driver and the
	 * EXPLICIT_WAIT_TIME.
	 * 
	 * @return
	 */
	public static WebDriverWait getWaitObject() {
		return new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
	}

	/**
	 * This method creates a wait object based on the driver and the given amount of
	 * time.
	 * 
	 * @return
	 */
	public static WebDriverWait getWaitObject(int secondsToWait) {
		return new WebDriverWait(driver, Duration.ofSeconds(secondsToWait));
	}

	/**
	 * This method will wait for the element to be visible.
	 * 
	 * @param element
	 */
	public static WebElement waitForVisibility(By locator) {
		return getWaitObject().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * This method will wait for the element to be visible.
	 * 
	 * @param element
	 */
	public static WebElement waitForVisibility(WebElement element) {
		return getWaitObject().until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method will wait for the element to be clickable.
	 * 
	 * @param element
	 */
	public static WebElement waitForClickability(WebElement element) {
		return getWaitObject().until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * This method will wait for clickability and then click on the passed element.
	 * 
	 * @param element
	 */
	public static void click(WebElement element) {
		waitForClickability(element);
		element.click();
	}

	
	/**
	 * This method will cast the driver to a JavascriptExecutor object and return it.
	 * @return
	 */
	public static JavascriptExecutor getJSObject()
	{
	//	JavascriptExecutor js = (JavascriptExecutor) driver;
	//	return js;

		return (JavascriptExecutor) driver;
	}


	/**
	 * This method will click on an element using JavascriptExecutor.
	 * 
	 * @param element
	 */
	public static void jsClick(WebElement element)
	{
		getJSObject().executeScript("arguments[0].click()", element);
	}


	/**
	 * This method will scroll the page until a specific element is in view.
	 * @param element
	 */
	public static void scrollToElement(WebElement element)
	{
		getJSObject().executeScript("arguments[0].scrollIntoView(true)", element);
	}

	/**
	 * This method scrolls the page up based on the pixel parameter.
	 * 
	 * @param pixel
	 */
	public static void scrollUp(int pixel)
	{
		getJSObject().executeScript("window.scrollBy(0,-"+pixel+")");
	}

	/**
	 * This method scrolls the page down based on the pixel parameter.
	 * @param pixel
	 */
	public static void scrollDown(int pixel)
	{
		getJSObject().executeScript("window.scrollBy(0,"+pixel+")");
	}


	/**
	 * This method will select a date on a calendar given the List<WebElement> and the date to select.
	 * 
	 * @param elements
	 * @param date
	 */
	public static void selectCalendarDate(List<WebElement> elements, String date)
	{
		for(WebElement day : elements)
		{
			if (day.getText().equals(date))
			{
				if(day.isEnabled())
				{
					click(day);
					break;
				}
				else
				{
					System.out.println("This day is not enabled!!!");
					break;
				}
			}
		}

	}
	
	
	/**
	 * This method will take a screenshot and save it with the given fileName. 
	 * 
	 * @param fileName
	 */
	public static byte[] takeScreenShot(String fileName) {

		TakesScreenshot ts = (TakesScreenshot) driver;

		File screenShot = ts.getScreenshotAs(OutputType.FILE);

		String destination = Constants.SCREENSHOT_FILEPATH + fileName + getTimeStamp() + ".png";
		
		try {

			FileUtils.copyFile(screenShot, new File(destination));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to save the screenshot!!!");
		}
		
		byte[] toReturn = ts.getScreenshotAs(OutputType.BYTES);
		return toReturn;
	}
	
	/**
	 * This method returns a timestamp as a String.
	 * 
	 * @return
	 */
	public static String getTimeStamp()
	{
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		
		return sdf.format(date);
	}
	
	
	/**
	 * This method is another substitute for selectDropdown(). It gets a list of WebElements and
	 * a string value to click on the particular element containing the value.
	 * 
	 * @param list
	 * @param value
	 */
	public void clickOnElement(List<WebElement> list, String value)
	{
		for (WebElement option : list)
		{
			if (option.getText().equals(value))
			{
				click(option);
				break;
			}
		}
	}
	
	
	/**
	 * This method takes year and month as WebElement and days as List<WebElement>
	 * then you provide the year, month and days you want to select as String. Whole selection done in one line.
	 * @param yearEl
	 * @param monthEl
	 * @param dayList
	 * @param year
	 * @param month
	 * @param day
	 */
	public static void selectCalendarEmrah(WebElement yearEl, WebElement monthEl, List<WebElement> dayList, String year, String month, String day) {
		
		selectDropdown(yearEl, year);
		selectDropdown(monthEl, month);
		selectCalendarDate(dayList, day);
		
		
	}
	
	   /**
     * Waits for element to be not stale
     *
     * @param element
     */
    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    
    /**
     * Clicks on an element using JavaScript
     *
     * @param element WebElement to be clicked
     */
    public static void clickWithJS(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element); // Scroll to the element
        jsExecutor.executeScript("arguments[0].click();", element); // Click the element
    }



    /**
     * Changes the HTML attribute of a WebElement to the given value using JavaScript
     *
     * @param element WebElement whose attribute needs to be modified
     * @param attributeName Name of the attribute to modify
     * @param attributeValue New value for the attribute
     */
    public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Highlights an element by changing its background and border color
     *
     * @param element WebElement to highlight
     */
    public static void highlight(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        // Highlight the element
        jsExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        wait(1); // Wait for 1 second (helper method)
        // Remove the highlight
        jsExecutor.executeScript("arguments[0].setAttribute('style', '');", element);
    }
    
    public void waitForVisibilityOfAllElements(List<WebElement> elements) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    
    public void selectDropdownByText(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

}