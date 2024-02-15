@ApiTesting @integration_api
Feature: Pets

  Scenario: Get pet By Status available
    Given I do a GET in /pet/findByStatus?status=available
    Then I print the api Response GET
    And I validate status code is 200


  Scenario: POST - Create a new Pet
    Given I Save in scenario data following customCat and Fantasy Category
    Given I do a POST in /pet using body ./Post_Pet.json
    Then I print the api Response
    Then I save the response key id as id_petId
    And I validate status code is 200


  Scenario: PUT - Change pet data
    Given I do a PUT in /pet using body ./Put_Pet.json
      | name      | Little Pony |
    Then I print the api Response
    And I validate status code is 200



