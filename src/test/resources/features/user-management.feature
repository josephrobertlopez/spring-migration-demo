Feature: User Management
  As an API consumer
  I want to manage users
  So that I can perform CRUD operations on user data

  Background:
    Given the application is running

  Scenario: Create a new user
    Given a user with the following details:
      | username | john_doe |
      | email    | john@example.com |
      | fullName | John Doe |
    When I create the user
    Then the response status should be 201
    And the response should contain a user with username "john_doe"
    And the user should have ID

  Scenario: Get all users
    When I get all users
    Then the response status should be 200

  Scenario: Get user by ID
    Given a user with the following details:
      | username | jane_doe |
      | email    | jane@example.com |
      | fullName | Jane Doe |
    When I create the user
    And I get user by ID 1
    Then the response status should be 200

  Scenario: Get user by username
    Given a user with the following details:
      | username | bob_smith |
      | email    | bob@example.com |
      | fullName | Bob Smith |
    When I create the user
    And I get user by username "bob_smith"
    Then the response status should be 200
    And the response should contain a user with username "bob_smith"

  Scenario: Update existing user
    Given a user with the following details:
      | username | alice_jones |
      | email    | alice@example.com |
      | fullName | Alice Jones |
    When I create the user
    And I update user with ID 1 with the following details:
      | username | alice_smith |
      | email    | alice.smith@example.com |
      | fullName | Alice Smith |
    Then the response status should be 200
    And the response should contain a user with username "alice_smith"

  Scenario: Delete existing user
    Given a user with the following details:
      | username | temp_user |
      | email    | temp@example.com |
      | fullName | Temporary User |
    When I create the user
    And I delete user with ID 1
    Then the response status should be 204

  Scenario: Try to get non-existent user
    When I get user by ID 999
    Then the response status should be 404

  Scenario: Try to create user with duplicate username
    Given a user with the following details:
      | username | duplicate_user |
      | email    | first@example.com |
      | fullName | First User |
    When I create the user
    Then the response status should be 201
    Given a user with the following details:
      | username | duplicate_user |
      | email    | second@example.com |
      | fullName | Second User |
    When I create the user
    Then the response status should be 400