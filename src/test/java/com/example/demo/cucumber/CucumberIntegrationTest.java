package com.example.demo.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.example.demo.cucumber.steps", "com.example.demo.cucumber"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports",
        "json:target/cucumber-reports/Cucumber.json",
        "junit:target/cucumber-reports/Cucumber.xml"
    }
)
@SpringBootTest
@ContextConfiguration
public class CucumberIntegrationTest {
}