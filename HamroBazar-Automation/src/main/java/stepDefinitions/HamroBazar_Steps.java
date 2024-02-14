package stepDefinitions;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HamroBazar_Page;
import pages.common.Common_Functions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HamroBazar_Steps {
    private WebDriver driver;
    private HamroBazar_Page hamroBazarPage;
    private Common_Functions commonFunctions = new Common_Functions();
    String url = "https://hamrobazaar.com/";
    String itemToSearch = "Monitor";

    String locationKeyWord = "New Road";
    String locationToChoose = "naya sadak newroad, New Road, Kathmandu";

    String sortByOptionToChoose = "Low to High (Price)";

    Integer numberOfItemsToStore = 50;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @Given("I access the hamrobazar landing page")
    public void i_access_the_hamrobazar_landing_page() throws InterruptedException {
        driver.get(url);
        Thread.sleep(1000);

        hamroBazarPage = new HamroBazar_Page(driver);
    }

    @When("The website completes loading")
    public void the_website_completes_loading() {
        hamroBazarPage.validatingTheLogoIsVisible();
    }

    @And("I enter the Monitor as a keyword to filter monitor in the Search bar")
    public void i_enter_the_monitor_as_a_keyword_to_filter_monitor_in_the_search_bar() throws InterruptedException {
        hamroBazarPage.typeKeywordInSearchBar(itemToSearch);
        Thread.sleep(1000);
    }

    @And("I hit enter to search with the entered keyword")
    public void i_will_click_on_the_search_icon() throws InterruptedException {
        hamroBazarPage.hitEnterOnTheSearchBar();
        Thread.sleep(2000);
    }

    @Then("I will verify the search results")
    public void i_will_verify_the_search_results() throws InterruptedException {
        hamroBazarPage.validatingTheTextOnTheSearchedKeywordTextField(itemToSearch);
        Thread.sleep(1000);
    }

    @And("I will filter the search result by location by entering the New Road as the location and selecting naya sadak newroad, New Road, Kathmandu")
    public void i_will_filter_the_search_result_by_location_by_entering_the_new_road_as_the_location_and_selecting_naya_sadak_newroad_new_road_kathmandu() throws InterruptedException {
        hamroBazarPage.enteringTheLocationKeywordInTheLocationFilterInputField(locationKeyWord);
        Thread.sleep(1000);
        hamroBazarPage.selectingTheLocationWePassAsAParameterFromTheLocationSuggestionDropdown(locationToChoose);
        Thread.sleep(3000);
    }

    @And("Also applying the location radius to 5000m")
    public void also_applying_the_location_radius_to_5000m() throws InterruptedException {
        hamroBazarPage.slideTheLocationRadiusSliderToTheGivenRadius(5000);
        Thread.sleep(2000);
    }

    @And("Clicking the Filter button to filter out the data with the selected filter")
    public void clicking_the_filter_button_to_filter_out_the_data_with_the_selected_filter() {
        hamroBazarPage.clickingTheFilterButton();
    }

    @Then("Sorting the result by Low to High Price")
    public void sorting_the_result_by_low_to_high_price() throws InterruptedException {
        hamroBazarPage.expandTheSortAdsByButton();
        Thread.sleep(500);
        hamroBazarPage.selectTheSortAdsByOptionText(sortByOptionToChoose);
        Thread.sleep(3000);

    }

    //    @And("Saving the details of the top fifty items that appears after applying the filter on the csv file named Search_Result.csv with the columns \"Title of the product\", \"Description of the product\", \"Price of the product\", \"Condition of the product\", \"Ad posted date\" and \"The name of the seller\"")
    @And("Saving the details of the top fifty items that appears after applying the filter on the csv file named Search_Result.csv")
    public void saving_the_details_of_the_top_items_that_appears_after_applying_the_filter_on_the_csv_file() throws InterruptedException {
        try (CSVWriter writer = new CSVWriter(new FileWriter("Search_Result.csv"))) {
            // Write headers to the CSV file
            writer.writeNext(new String[]{"Title of the product", "Description of the product", "Price of the product", "Condition of the product", "Ad Posted Date", "Name of the seller"});
            for (int i = 0; i < numberOfItemsToStore; i++) {

                if (commonFunctions.isElementPresent(driver, By.xpath(hamroBazarPage.productByIndex(i)))) {
                    System.out.println("Element with xpath '" + hamroBazarPage.productByIndex(i) + "' exists on the page.");

                } else {
                    WebElement element = driver.findElement(By.xpath(hamroBazarPage.productByIndex(i - 1)));
                    String text = element.getText();
                    System.out.println(text);
                    ;
                    System.out.println("Element with xpath '" + hamroBazarPage.productByIndex(i) + "' does not exist on the page.");
                    commonFunctions.scrollElementToTop(driver, element);
                    Thread.sleep(1000);
                }

                String title = hamroBazarPage.titleOfTheProductAtIndex(i);
                String description = hamroBazarPage.descriptionOfTheProductAtIndex(i);
                String price = hamroBazarPage.priceOfTheProductAtIndex(i);
                String condition = hamroBazarPage.conditionOfTheProductAtIndex(i);
                String adPostedDate = hamroBazarPage.adPostedDateOfTheProductAtIndex(i);
                String sellerName = hamroBazarPage.nameOfTheSellerOfTheProductAtIndex(i);

                // Write data to CSV
                writer.writeNext(new String[]{title, description, price, condition, adPostedDate, sellerName});

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("Displaying the data of the Search_Result.csv file in tabular format")
    public void displaying_the_data_of_the_search_result_csv_file_in_tabular_format() {
        String csvFilePath = "Search_Result.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] headers = reader.readNext();

            if (headers != null) {
                // Display headers
                commonFunctions.displayRow(headers);

                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    // Display data rows
                    commonFunctions.displayRow(nextLine);
                }
            } else {
                System.out.println("No data found in the CSV file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
