Feature: basic authentication

  Scenario: successful login
    Given a user with name "petter" and password "s1kr1t"

    When a login attempt is made with "petter" and "s1kr1t"

    Then the result is "SUCCESS"

  Scenario: failed login
    Given a user with name "petter" and password "s1kr1t"

    When a login attempt is made with "petter" and "h4XX0r"

    Then the result is "FAILURE"
