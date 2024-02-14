package pages.common;

import org.openqa.selenium.*;

public class Common_Functions {
    private WebDriver driver;

    // Method to scroll the entire page vertically by a specific percentage using JavascriptExecutor
    public static void scrollPageByPercentage(WebDriver driver, int percentage) {
        // Use JavascriptExecutor to scroll the page
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Get the current scroll height
        long scrollHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

        // Calculate the scroll distance based on the percentage
        long scrollDistance = (long) (scrollHeight * percentage / 100.0);

        // Scroll down by the calculated distance
        String script = String.format("window.scrollBy(0, %d);", scrollDistance);
        jsExecutor.executeScript(script);
    }


    public static void scrollElementToTop(WebDriver driver, WebElement element) {
        try {
            // Use JavascriptExecutor to scroll the element to the top
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", element);
        } catch (Exception e) {
            // Handle exceptions if needed
            e.printStackTrace();
        }
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            // Attempt to find the element
            WebElement element = driver.findElement(locator);
            // Check if the element is displayed (visible) on the page
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            // Element not found, return false
            return false;
        }
    }

    public static void displayRow(String[] rowData) {
        for (String data : rowData) {
            System.out.print(String.format("%-20s", data));
        }
        System.out.println();
    }
}
