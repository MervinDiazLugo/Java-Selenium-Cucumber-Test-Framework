@MobileTesting @MobileYapeBooking
Feature: Yape Test values

  Scenario: book a reservation
    Given User is on Main app screen
    Then User sets Destination textbox as CUSCO
    And User sets travel duration dates From: 14 February 2024 to 28 February 2024
    And User sets following Occupancy values
      | room       | 1 |
      | adults     | 2 |
    And User sets following Children values
      | 5 years old |
    Then User click on Search button
    When User selects option number 0 of result list



