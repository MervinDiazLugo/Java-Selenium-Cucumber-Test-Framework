@WebTesting @Orange
Feature: Login Test in Orange portal


  Scenario: Test Login Portal steps
      Given I am in app main site
      And The User fill username text box
      And The User fill password text box
      When The User clicks Login button
      Then Verify the user is logged in


  Scenario: Get System Users from User list
    Given I am in app main site
    When the user is Logged in
    And The user go to System user list
    When Verify Admin user is present in the list

  Scenario: With an Admin User get System Users from User list
    Given I am in app main site
    When the Admin user is Logged in
    And The user go to System user list
    When Verify Admin user is present in the list
