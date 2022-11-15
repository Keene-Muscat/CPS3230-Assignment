package um.cps3230;

import edu.um.cps3230.utilities.UnexpectedErrorDetector;
import edu.um.cps3230.utilities.WebsiteStatusProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.openqa.selenium.WebElement;
import um.cps3230.utilities.ResponseCodeGenerator;

import java.io.IOException;
import java.util.List;

public class ProgramTests {

    Program program = new Program();

    @Test
    public void testNavigationWhenWebsiteIsUp(){
        //Setup
        WebsiteStatusProvider websiteStatusProvider = Mockito.mock(WebsiteStatusProvider.class);

        Mockito.when(websiteStatusProvider.isWebsiteUp()).thenReturn(true);

        program.setWebsiteStatusProvider(websiteStatusProvider);

        //Exercise
        boolean result = program.navigation();

        //Assertions
        Assertions.assertTrue(result);
    }

    @Test
    public void testNavigationWhenWebsiteIsDown(){
        //Setup
        WebsiteStatusProvider websiteStatusProvider = Mockito.mock(WebsiteStatusProvider.class);

        Mockito.when(websiteStatusProvider.isWebsiteUp()).thenReturn(false);

        program.setWebsiteStatusProvider(websiteStatusProvider);

        //Exercise
        boolean result = program.navigation();

        //Assertions
        Assertions.assertFalse(result);
    }

    @Test
    public void testSearchWhenWebsiteIsUp() {
        //Setup
        WebsiteStatusProvider websiteStatusProvider = Mockito.mock(WebsiteStatusProvider.class);

        Mockito.when(websiteStatusProvider.isWebsiteUp()).thenReturn(true);

        program.setWebsiteStatusProvider(websiteStatusProvider);

        //Exercise
        boolean result = program.search();

        //Assertions
        Assertions.assertTrue(result);
    }

    @Test
    public void testSearchWhenWebsiteIsDown() {
        //Setup
        WebsiteStatusProvider websiteStatusProvider = Mockito.mock(WebsiteStatusProvider.class);

        Mockito.when(websiteStatusProvider.isWebsiteUp()).thenReturn(false);

        program.setWebsiteStatusProvider(websiteStatusProvider);

        //Exercise
        boolean result = program.search();

        //Assertions
        Assertions.assertFalse(result);
    }

    @Test
    public void testSearchWhenNoUnexpectedErrorOccurs() {
        //Setup
        UnexpectedErrorDetector unexpectedErrorDetector = Mockito.mock(UnexpectedErrorDetector.class);

        Mockito.when(unexpectedErrorDetector.hasUnexpectedErrorOccurred()).thenReturn(false);

        program.setUnexpectedErrorDetector(unexpectedErrorDetector);

        //Exercise
        boolean result = program.search();

        //Assertions
        Assertions.assertTrue(result);
    }

    @Test
    public void testSearchWhenUnexpectedErrorOccurs() {
        //Setup
        UnexpectedErrorDetector unexpectedErrorDetector = Mockito.mock(UnexpectedErrorDetector.class);

        Mockito.when(unexpectedErrorDetector.hasUnexpectedErrorOccurred()).thenReturn(true);

        program.setUnexpectedErrorDetector(unexpectedErrorDetector);

        //Exercise
        boolean result = program.search();

        //Assertions
        Assertions.assertFalse(result);
    }

    @Test
    public void testGetProducts(){
        //Exercise
        program.search();

        List<WebElement> products = program.getProducts();

        //Assertions
        Assertions.assertFalse(products.isEmpty());
    }

    @Test
    public void testUpload5ValidAlerts() throws IOException {
        //Setup
        ResponseCodeGenerator responseCodeGenerator = Mockito.mock(ResponseCodeGenerator.class);

        Mockito.when(responseCodeGenerator.getResponseCode()).thenReturn(201);

        program.setResponseCodeGenerator(responseCodeGenerator);

        //Exercise
        boolean result = program.uploadAlerts();

        //Assertions
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpload0ValidAlerts() throws IOException {
        //Setup
        ResponseCodeGenerator responseCodeGenerator = Mockito.mock(ResponseCodeGenerator.class);

        Mockito.when(responseCodeGenerator.getResponseCode()).thenReturn(400);

        program.setResponseCodeGenerator(responseCodeGenerator);

        //Exercise
        boolean result = program.uploadAlerts();

        //Assertions
        Assertions.assertFalse(result);
    }

    @Test
    public void testUpload5AlertsWhenUnexpectedErrorOccurrs() throws IOException {
        //Setup
        UnexpectedErrorDetector unexpectedErrorDetector = Mockito.mock(UnexpectedErrorDetector.class);

        Mockito.when(unexpectedErrorDetector.hasUnexpectedErrorOccurred()).thenReturn(true);

        program.setUnexpectedErrorDetector(unexpectedErrorDetector);

        //Exercise
        boolean result = program.uploadAlerts();

        //Assertions
        Assertions.assertFalse(result);
    }

    @Test
    public void testUpload5AlertsWhenWebsiteIsDown() throws IOException {
        //Setup
        WebsiteStatusProvider websiteStatusProvider = Mockito.mock(WebsiteStatusProvider.class);

        Mockito.when(websiteStatusProvider.isWebsiteUp()).thenReturn(false);

        program.setWebsiteStatusProvider(websiteStatusProvider);

        //Exercise
        boolean result = program.uploadAlerts();

        //Assertions
        Assertions.assertFalse(result);
    }
}
