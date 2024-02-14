package pages;

import io.cucumber.java.en_old.Ac;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.logging.Logger;

public class HamroBazar_Page {
    private WebDriver driver;

    private static final Logger logger = Logger.getLogger(HamroBazar_Page.class.getName());

    @FindBy(xpath = "//input[@name='searchValue']")
    private WebElement searchBar;

    @FindBy(className = "main-nav-logo")
    private WebElement hamroBazarLogo;

    @FindBy(xpath = "//div[@class='sticky--part vh--part']//input[@name='location']")
    private WebElement locationFilterInputField;

    @FindBy(xpath = "//div[@class='search--titles']//strong")
    private WebElement searchKeywordTitleField;

//    @FindBy(xpath = "//div[@class='place--suggestions  ']")
//    private WebElement placeSuggestionOptions;
//
//    public getPlaceSuggestionOptions(String place) {
//        String dynamicXpath = String.format("//div[@class='place--suggestions']//li[contains(text(),'%s')]", place);
//        return WebElement placeSuggestionOptions.findElement(By.xpath(dynamicXpath));
//    }

    private static final String placeSuggestionOptions = "//div[@class='place--suggestions  ']//li/strong[contains(text(), '%s')]";
//    private static final String placeSuggestionOptions = "//div[@class='place--suggestions']";
//            driver.findElement(By.xpath("//div[@class='place--suggestions']//li/strong[contains(text(),'" + place +"')]"))

    public static String productByIndex(int index) {
        return String.format("//div[@data-index='%d']", index);
    }

    @FindBy(xpath = "//div[@class='rs-slider']//input[@value='500']")
    private WebElement locationRadiusSlider;

    @FindBy(xpath = "//div[@class='sticky--part vh--part']//button[@class='btn']")
    private WebElement filterButton;

    @FindBy(xpath = "//div[@class='sticky--part vh--part']//select[@name='sortParam']")
    private WebElement sortAdsBySelectField;

    @FindBy(xpath = "//section[@class='home--listings']//div[@data-test-id='virtuoso-item-list']")
    private WebElement searchResultSection;

    public HamroBazar_Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Method related to Logo
    public void validatingTheLogoIsVisible() {
        Assert.assertTrue(hamroBazarLogo.isDisplayed(), "The logo is not present on the page.");
    }

    //Methods related to the search bar
    public void typeKeywordInSearchBar(String keyword) {
        searchBar.sendKeys(keyword);
    }

    public void hitEnterOnTheSearchBar() {
        searchBar.sendKeys(Keys.ENTER);
    }

    // Methods related to search result page
    public void validatingTheTextOnTheSearchedKeywordTextField(String expectedText) {
        String searchResultTitleText = searchKeywordTitleField.getText();
        Assert.assertEquals(searchResultTitleText, expectedText, "The text of the searched result keyword field is not what we searched for.");

        logger.info(searchResultTitleText);
    }

    // Methods related to location filter input field
    public void enteringTheLocationKeywordInTheLocationFilterInputField(String locationKeyword) {
        locationFilterInputField.sendKeys(locationKeyword);
    }

    // Selecting the location from the suggestion which we pass as a parameter
    public void selectingTheLocationWePassAsAParameterFromTheLocationSuggestionDropdown(String location) {
        driver.findElement(By.xpath(String.format(placeSuggestionOptions, location))).click();
//        Assert.assertTrue(placeSuggestionOptions.isDisplayed(), "The locations suggestion is not present on the page.");
    }

    public void slideTheLocationRadiusSliderToTheGivenRadius(int radius) {
        int minRadius = 500;
        int maxRadius = 10000;

        double percentage = (radius - minRadius) / (double) (maxRadius - minRadius);
        int xOffset = (int) (percentage * locationRadiusSlider.getSize().getWidth());

        Actions actions = new Actions(driver);
        actions.clickAndHold(locationRadiusSlider).moveByOffset(xOffset, 0).release().perform();


//        double locationRadiusValue = minRadius + percentage * (maxRadius - minRadius);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].value = arguments[1]", locationRadiusSlider, locationRadiusValue);
    }

    public void expandTheSortAdsByButton() {
        sortAdsBySelectField.click();
    }

    public void selectTheSortAdsByOptionText(String optionText) {
        Select dropdown = new Select(sortAdsBySelectField);
        dropdown.selectByVisibleText((optionText));
    }

    public String titleOfTheProductAtIndex(Integer index) {
        String dynamicXpath = productByIndex(index);
        dynamicXpath = dynamicXpath + "//div[@class='nameAndDropdown']//h2[@class='product-title']";

        WebElement element = driver.findElement(By.xpath(dynamicXpath));

        return element.getText();
    }

    public String descriptionOfTheProductAtIndex(Integer index) {
        String dynamicXpath = productByIndex(index);
        dynamicXpath = dynamicXpath + "//p[@class='description']";

        WebElement element = driver.findElement(By.xpath(dynamicXpath));

        return element.getText();
    }

    public String priceOfTheProductAtIndex(Integer index) {
        String dynamicXpath = productByIndex(index);
        dynamicXpath = dynamicXpath + "//div[@class='priceAndCondition']//span[@class='regularPrice']";

        WebElement element = driver.findElement(By.xpath(dynamicXpath));

        return element.getText();
    }

    public String conditionOfTheProductAtIndex(Integer index) {
        String dynamicXpath = productByIndex(index);
        dynamicXpath = dynamicXpath + "//div[@class='priceAndCondition']//span[@class='condition']";

        WebElement element = driver.findElement(By.xpath(dynamicXpath));

        return element.getText();
    }

    public String adPostedDateOfTheProductAtIndex(Integer index) {
        String dynamicXpath = productByIndex(index);
        dynamicXpath = dynamicXpath + "//div[@class='locationAndTime']//span[@class='time']";

        WebElement element = driver.findElement(By.xpath(dynamicXpath));

        return element.getText();
    }

    public String nameOfTheSellerOfTheProductAtIndex(Integer index) {
        String dynamicXpath = productByIndex(index);
        dynamicXpath = dynamicXpath + "//div[@class='usernameAndSave']//span[@class='username-fullname']";

        WebElement element = driver.findElement(By.xpath(dynamicXpath));

        return element.getText();
    }

    public void scrollProductSectionByGivenPercentage(int percentage) {
        long scrollHeight = (long) ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollHeight;", searchResultSection);
        long scrollDistance = (long) (scrollHeight * percentage / 100.0);

        String script = String.format("arguments[0].scrollTop = %d;", scrollDistance);
        ((JavascriptExecutor) driver).executeScript(script, searchResultSection);
    }
    public void clickingTheFilterButton() {
        filterButton.click();
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
