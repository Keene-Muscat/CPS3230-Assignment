package test.Task2;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.AlertsPageObject;
import pageobjects.LoginPageObject;
import um.edu.cps3230.Admin;

import java.io.IOException;

public class Steps {
    WebDriver driver;
    LoginPageObject loginPageObject;
    AlertsPageObject alertsPageObject;

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum(){
        System.setProperty("webdriver.chrome.driver", "/Users/keene/web-test/chromedriver.exe");

        driver = new ChromeDriver();

        driver.get("https://www.marketalertum.com/Alerts/Login");

        loginPageObject = new LoginPageObject(driver);
        alertsPageObject = new AlertsPageObject(driver);
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        loginPageObject.login("c2f7a631-9e15-43b3-82ee-b63f2b8e194f");
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        Assertions.assertTrue(driver.getCurrentUrl().contains("List"));
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        loginPageObject.login("invalid credentials");
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        Assertions.assertTrue(driver.getCurrentUrl().contains("Login"));
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int alerts) throws IOException {
        Admin admin = new Admin();

        admin.uploadAlerts(alerts);
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        loginPageObject.login("c2f7a631-9e15-43b3-82ee-b63f2b8e194f");
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        Assertions.assertEquals(3, alertsPageObject.getNumberOfIcons());
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        Assertions.assertEquals(3, alertsPageObject.getNumberOfHeadings());
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        Assertions.assertEquals(3, alertsPageObject.getNumberOfDescriptions());
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        Assertions.assertEquals(3, alertsPageObject.getNumberOfImages());
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        Assertions.assertEquals(3, alertsPageObject.getNumberOfPrices());
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        Assertions.assertEquals(3, alertsPageObject.getNumberOfLinks());
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int alerts) throws IOException {
        Admin admin = new Admin();

        admin.uploadAlerts(alerts + 1);
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int alerts) {
        Assertions.assertEquals(alerts, alertsPageObject.getNumberOfAlerts());
    }

    @Given("I am an administrator of the website and I upload an alert of type {string}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType(String alertType) throws IOException {
        Admin admin = new Admin();

        admin.uploadAlertOfType(alertType);
    }

    @And("the icon displayed should be {string}")
    public void theIconDisplayedShouldBe(String arg0) {
        Assertions.assertEquals("https://www.marketalertum.com/images/" + arg0, alertsPageObject.getIconFileName());
    }
}