package pages.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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
}
