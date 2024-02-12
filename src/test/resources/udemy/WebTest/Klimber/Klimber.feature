@UdemyWebTest @WebTesting @KlimberWebTest
Feature: An example

  Scenario: User make a request to quote a health insurance
    Given I am in app main site
    And I wait for first step elements are loaded
    Then I fill following text boxes:
      | Nombre    | María Jose Ortiz Rodriguez |
      | Provincia | CABA                       |
      | Cód. área | 11                         |
      | Celular   | 22501118                   |
    And click on Cotizá button
    And I wait for second step elements are loaded
    Then I fill following text boxes:
      | Cantidad de adultos    | 2 |
      | Menores de 12 años     | 1 |
    And click on Siguiente button
    And I wait for last step elements are loaded
    Then I fill registration form with values:
      | Nombre                  | Maria Jose                  |
      | Apellido                | Ortiz Rodrigez              |
      | Fecha de nacimiento     | 13/11/1994                  |
      | DNI                     | 95624541                    |
      | Sexo biológico          | Femenino                    |
      | Género                  | Femenino                    |
      | E-mail                  | ortizmarijo1311@gmail.com   |
      | Cód. área               | 11                          |
      | Celular                 | 22501118                    |
      | Calle                   | Av. Santa Fe                |
      | Número                  | 2306                        |
      | Piso                    | 2                           |
      | Departamento            | C                           |
      | Código Postal           | 1123                        |
      | Ciudad                  | C.A.B.A                     |