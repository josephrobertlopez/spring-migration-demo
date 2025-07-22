package com.example.demo.cucumber.steps;

import com.example.demo.cucumber.CucumberSpringConfiguration;
import com.example.demo.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps extends CucumberSpringConfiguration {
    
    @LocalServerPort
    private int port;
    
    private RequestSpecification request;
    private Response response;
    private User user;
    
    @Given("the application is running")
    public void theApplicationIsRunning() {
        RestAssured.baseURI = "http://localhost:" + port;
        request = RestAssured.given()
            .contentType("application/json");
    }
    
    @Given("a user with the following details:")
    public void aUserWithTheFollowingDetails(Map<String, String> userDetails) {
        user = new User();
        user.setUsername(userDetails.get("username"));
        user.setEmail(userDetails.get("email"));
        user.setFullName(userDetails.get("fullName"));
        user.setActive(Boolean.parseBoolean(userDetails.getOrDefault("active", "true")));
    }
    
    @When("I create the user")
    public void iCreateTheUser() {
        response = request
            .body(user)
            .post("/api/users");
    }
    
    @When("I get all users")
    public void iGetAllUsers() {
        response = request.get("/api/users");
    }
    
    @When("I get user by ID {long}")
    public void iGetUserById(Long id) {
        response = request.get("/api/users/" + id);
    }
    
    @When("I get user by username {string}")
    public void iGetUserByUsername(String username) {
        response = request.get("/api/users/username/" + username);
    }
    
    @When("I update user with ID {long} with the following details:")
    public void iUpdateUserWithId(Long id, Map<String, String> userDetails) {
        User updateUser = new User();
        updateUser.setUsername(userDetails.get("username"));
        updateUser.setEmail(userDetails.get("email"));
        updateUser.setFullName(userDetails.get("fullName"));
        updateUser.setActive(Boolean.parseBoolean(userDetails.getOrDefault("active", "true")));
        
        response = request
            .body(updateUser)
            .put("/api/users/" + id);
    }
    
    @When("I delete user with ID {long}")
    public void iDeleteUserWithId(Long id) {
        response = request.delete("/api/users/" + id);
    }
    
    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }
    
    @Then("the response should contain a user with username {string}")
    public void theResponseShouldContainUserWithUsername(String username) {
        User responseUser = response.as(User.class);
        assertThat(responseUser.getUsername()).isEqualTo(username);
    }
    
    @Then("the response should contain {int} users")
    public void theResponseShouldContainUsers(int count) {
        User[] users = response.as(User[].class);
        assertThat(users).hasSize(count);
    }
    
    @Then("the user should have ID")
    public void theUserShouldHaveId() {
        User responseUser = response.as(User.class);
        assertThat(responseUser.getId()).isNotNull();
        assertThat(responseUser.getId()).isPositive();
    }
}