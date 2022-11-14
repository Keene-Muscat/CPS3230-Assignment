package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AlertsPageObject {
    WebDriver driver;

    public AlertsPageObject(WebDriver driver){
        this.driver = driver;
    }

    public int getNumberOfIcons(){
        return driver.findElements(By.xpath("//h4//img")).size();
    }

    public int getNumberOfHeadings(){
        return driver.findElements(By.xpath("//h4[ text() = ' Test Heading ' ]")).size();
    }

    public int getNumberOfDescriptions(){
        return driver.findElements(By.xpath("//td[ text() = 'Test Description' ]")).size();
    }

    public int getNumberOfImages(){
        return driver.findElements(By.xpath("//img[ @width = '200' ]")).size();
    }

    public int getNumberOfPrices(){
        return driver.findElements(By.xpath("//td[ contains (text(), '€' ) ]")).size();
    }

    public int getNumberOfLinks(){
        return driver.findElements(By.xpath("//td//a")).size();
    }

    public int getNumberOfAlerts(){
        return driver.findElements(By.xpath("//table")).size();
    }

    public String getIconFileName(){
        return driver.findElement(By.xpath("//img")).getAttribute("src");
    }
}