@Hover
Feature: Hover Tests
  Scenario Outline: Verify Hover Functionality
    Given User Clicks on link "Hovers"
    When User Hovers on Image number "<index>"
    And User Enters Profile number "<index>"
    Then Verify message "Not Found" appears

    Examples: 
      | index |
      |     1 |
      |     2 |
      |     3 |
