@UdemyWebTest @WebTesting @KlimberWebTest
Feature: An example

  Scenario: User make a request to quote a health insurance
    Given I am in app main site
    And I wait for first step elements are loaded
    Then I fill following text boxes:
      | Nombre    | Patricia Jose Ortiz Palermo |
      | Provincia | CABA                        |
      | Cód. área | 11                          |
      | Celular   | 22500000                    |
    And click on Cotizá button
    And I wait for second step elements are loaded
    Then I fill following text boxes:
      | Cantidad de adultos    | 2 |
      | Menores de 12 años     | 1 |
    And click on Siguiente button
    And I wait for last step elements are loaded
    Then I fill registration form with values:
      | Nombre                  | Patricia Jose               |
      | Apellido                | Ortiz Palermo               |
      | Fecha de nacimiento     | 13/11/1980                  |
      | DNI                     | 950624500                   |
      | Sexo biológico          | Femenino                    |
      | Género                  | Femenino                    |
      | E-mail                  | correodepruebas@gmail.com   |
      | Cód. área               | 11                          |
      | Celular                 | 22500000                    |
      | Calle                   | Av. Santa Fe                |
      | Número                  | 500                         |
      | Piso                    | 2                           |
      | Departamento            | C                           |
      | Código Postal           | 1123                        |
      | Ciudad                  | C.A.B.A                     |