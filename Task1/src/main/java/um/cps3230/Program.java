package um.cps3230;

import edu.um.cps3230.utilities.UnexpectedErrorDetector;
import edu.um.cps3230.utilities.WebsiteStatusProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import um.cps3230.utilities.ResponseCodeGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Program {
    protected WebDriver driver;
    protected WebsiteStatusProvider websiteStatusProvider;
    protected UnexpectedErrorDetector unexpectedErrorDetector;
    protected ResponseCodeGenerator responseCodeGenerator;

    public void setWebsiteStatusProvider(WebsiteStatusProvider websiteStatusProvider) {
        this.websiteStatusProvider = websiteStatusProvider;
    }

    public void setUnexpectedErrorDetector(UnexpectedErrorDetector unexpectedErrorDetector) {
        this.unexpectedErrorDetector = unexpectedErrorDetector;
    }

    public void setResponseCodeGenerator(ResponseCodeGenerator responseCodeGenerator) {
        this.responseCodeGenerator = responseCodeGenerator;
    }

    public boolean navigation() {
        System.setProperty("webdriver.chrome.driver", "/Users/keene/web-test/chromedriver.exe");

        driver = new ChromeDriver();

        driver.get("https://www.amazon.co.uk/");

        List<WebElement> cookiesPopUp = driver.findElements(By.id("sp-cc-rejectall-link"));

        if(!cookiesPopUp.isEmpty()) {
            cookiesPopUp.get(0).click();
        }

        if (websiteStatusProvider != null) {
            boolean status = websiteStatusProvider.isWebsiteUp();

            if (!status) {
                return false;
            }
        }
        return true;
    }

    public boolean search() {
        if(!navigation()) {
            return false;
        }

        List<WebElement> searchBox = driver.findElements(By.id("twotabsearchtextbox"));

        if(!searchBox.isEmpty()) {
            searchBox.get(0).sendKeys("Gaming Laptops");
            searchBox.get(0).submit();
        }

        List<WebElement> alternativeSearchBox = driver.findElements(By.id("nav-bb-search"));

        if(!alternativeSearchBox.isEmpty()) {
            alternativeSearchBox.get(0).sendKeys("Gaming Laptops");
            alternativeSearchBox.get(0).submit();
        }

        List<WebElement> cookiesPopUp = driver.findElements(By.id("sp-cc-rejectall-link"));

        if(!cookiesPopUp.isEmpty()) {
            cookiesPopUp.get(0).click();
        }

        if (unexpectedErrorDetector != null) {
            boolean occurrence = unexpectedErrorDetector.hasUnexpectedErrorOccurred();

            if (occurrence) {
                return false;
            }
        }
        return true;
    }

    public List<WebElement> getProducts() {
        return driver.findElements(By.className("s-image"));
    }

    public String getHeading() {
        String[] title = driver.findElement(By.id("title")).getText().split(" ");

        return title[0] + " " + title[1];
    }

    public String getDescription() {
        return driver.findElement(By.id("title")).getText().replace
                ('"', '`');
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getImgUrl() {
        return driver.findElement(By.id("landingImage")).getAttribute("src");
    }

    public String getPrice() {
        String price_whole = driver.findElement(By.className("a-price-whole")).getText().replaceAll(",", "");
        String price_fraction = driver.findElement(By.className("a-price-fraction")).getText();

        return price_whole.concat(price_fraction);
    }

    public String screenScraper(int i){
        getProducts().get(i).click();

        return "{\n\"alertType\": 6," +
                "\n\"heading\": \"" + getHeading() + "\" , " +
                "\n\"description\": \"" + getDescription() + "\" , " +
                "\n\"url\": \"" + getUrl() + "\" , " +
                "\n\"imageUrl\": \"" + getImgUrl() + "\" , " +
                "\n\"postedBy\": \"c2f7a631-9e15-43b3-82ee-b63f2b8e194f\"," +
                "\n\"priceInCents\": \"" + getPrice() + "\"\n}";
    }

    public boolean uploadAlerts() throws IOException {
        int count = 0;
        if(search()) {
            for (int i = 0; i < 5; i++) {

                if (POST(screenScraper(i)) == 201) {
                    count++;
                }

                driver.navigate().back();
            }
        }else{
            return false;
        }

        if(count == 5){
            return true;
        }
        return false;
    }

    public int POST(String data) throws IOException {
        URL url = new URL("https://api.marketalertum.com/Alert");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setRequestMethod("POST");
        http.setDoOutput(true);

        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();

        stream.write(out);

        http.disconnect();

        return responseCodeGenerator.getResponseCode();
    }
}