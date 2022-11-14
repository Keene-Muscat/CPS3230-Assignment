package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObject {
    WebDriver driver;

    public LoginPageObject(WebDriver driver){
        this.driver = driver;
    }

    public void setUserID(String userID){
        driver.findElement(By.id("UserId")).sendKeys(userID);
    }

    public void submitUserID(){
        driver.findElement(By.id("UserId")).submit();
    }

    public void login(String userId){
        this.setUserID(userId);

        this.submitUserID();
    }
}