Feature: HamroBazar-Automation
  Scenario: Navigating to the HamroBazar page and searching for the monitor
    Given I access the hamrobazar landing page
    When The website completes loading
    And I enter the Monitor as a keyword to filter monitor in the Search bar
    And I hit enter to search with the entered keyword
    Then I will verify the search results
    And I will filter the search result by location by entering the New Road as the location and selecting naya sadak newroad, New Road, Kathmandu
    And Also applying the location radius to 5000m
    And Clicking the Filter button to filter out the data with the selected filter
    Then Sorting the result by Low to High Price
    And Saving the details of the top fifty items that appears after applying the filter on the csv file named Search_Result.csv
    Then Displaying the data of the Search_Result.csv file in tabular format
#  with the columns "Title of the product", "Description of the product", "Price of the product", "Condition of the product", "Ad posted date" and "The name of the seller"

