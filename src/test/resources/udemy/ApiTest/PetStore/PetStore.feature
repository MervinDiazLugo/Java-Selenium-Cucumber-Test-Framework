@ApiTesting @integration_api
Feature: Pets

  Scenario: Get pet By Status available
    Given I do a GET in /pet/findByStatus?status=available
    Then I print the api Response GET
    And I validate status code is 200

  Scenario: PUT - Change pet data
    Given I Save in scenario data following customCat and Fantasy Category
    Given I do a PUT in /pet using body ./pet2.json
      | name | Little Pony |
    Then I print the api Response
    And I validate status code is 200
