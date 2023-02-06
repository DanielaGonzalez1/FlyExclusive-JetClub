@Login
Feature: Login

  Background:
    Given The user is on the login screen

  @test-01
  Scenario Outline: Login with existing user
    When enters their username "<username>"
    And enters their password "<password>"
    Examples:
      | username    | password     |
      | daniela.gonzalez@dbp.com | 12345678. |
      | FelixRuiz | Ortega123. |
