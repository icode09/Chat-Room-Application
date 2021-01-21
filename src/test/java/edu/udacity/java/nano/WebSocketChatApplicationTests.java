package edu.udacity.java.nano;

// For a review of the use of Selenium, see
// https://docs.seleniumhq.org/docs/03_webdriver.jsp#introducing-webdriver

import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebSocketChatApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketChatApplicationTests {

    // For a review of the use of ChromeDriverService to control ChromeDriver's lifetime
    // see: https://chromedriver.chromium.org/getting-started

    private static ChromeDriverService service;
    private static RemoteWebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("ChromeDriver/chromedriver"))
                .usingAnyFreePort()
                .build();
        service.start();
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
    }

    @AfterClass
    public static void stopService() {
        driver.close();
        driver.quit();
        service.stop();
    }

    @Test
    public void login() {
        // Test login is correct
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" LOGIN TEST: We check the title of the page before and after login                                  ");
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Create a new instance of the ChromeDriver driver
        // WebDriver driver = new ChromeDriver();
        // WebDriver driver = new HtmlUnitDriver();

        // We visit the main page of the application (Chat Room Login)
        driver.get("http://localhost:8080/");

        // The title of the page should be "Chat Room Login" before login
        System.out.println("Page title before login is: " + driver.getTitle());
        String expected = "Chat Room Login";
        String was = driver.getTitle();
        try {
            Assert.assertEquals(expected, was);
        } catch (Exception error) {
            //  Block of code to handle errors
            System.out.println("Expected" + expected + "but was" + was);
        }

        // Find the text input element username by its name
        WebElement element = driver.findElement(By.id("username"));

        // Enter a user name into the username field of the "Chat Room Login" page
        element.sendKeys("Esteban");

        // Find the login button element username by its name
        WebElement button = driver.findElement(By.id("button"));

        // Click the login button
        button.click();

        // If everything goes according to plan, we will now navigate to the "Chat Room" page
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver newDriver) {
                System.out.println("Page title after login is: " + newDriver.getTitle());
                // The title of the page should be "Chat Room" after successful login
                String expected = "Chat Room";
                String was = newDriver.getTitle();
                Assert.assertEquals(expected, was);
                return expected.equals(was);
            }
        });

        // Close the browser
        // driver.quit();

        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    @Test
    public void join() {
        // Test user join is correct
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" USER JOIN TEST: We check Clark has joined the chat after login                                     ");
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Create a new instance of the ChromeDriver driver
        // WebDriver driver = new ChromeDriver();
        // WebDriver driver = new HtmlUnitDriver();

        // We visit the main page of the application (Chat Room Login)
        driver.get("http://localhost:8080/");

        // The title of the page should be "Chat Room Login" before login
        System.out.println("Page title before login is: " + driver.getTitle());

        // Find the text input element username by its name
        WebElement element = driver.findElement(By.id("username"));

        // Enter a user name into the username field of the "Chat Room Login" page
        String username = "Clark";
        System.out.println("User name entered at the Chat Room Login page (before login) is: " + username);
        element.sendKeys(username);

        // Find the login button element username by its name
        WebElement button = driver.findElement(By.id("button"));

        // Click the login button
        button.click();

        // If everything goes according to plan, we will now see how Clark has successfully joined the
        // chat. His name should now be displayed at the "Chat Room" page.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver newDriver) {
                System.out.println("Page title after login is: " + newDriver.getTitle());
                WebElement element = newDriver.findElement(By.id("username"));
                System.out.println("User name at the Chat Room page (after login) is: " + element.getText());
                String expected = "Clark";
                String was = element.getText();
                Assert.assertEquals(expected, was);
                return expected.equals(was);
            }
        });
        // Close the browser
        // driver.quit();

        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    @Test
    public void chat() {
        // Test chat is correct
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" CHAT TEST: We check that the user sends a message, and receives it.                                ");
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Create a new instance of the ChromeDriver driver
        // WebDriver driver = new ChromeDriver();
        // WebDriver driver = new HtmlUnitDriver();

        // We visit the main page of the application (Chat Room Login)
        driver.get("http://localhost:8080/");

        // Find the text input element username by its name
        WebElement element = driver.findElement(By.id("username"));

        // Enter a user name into the username field of the "Chat Room Login" page
        element.sendKeys("James");

        // Find the login button element username by its name
        WebElement button = driver.findElement(By.id("button"));

        // Click the login button
        button.click();

        // Wait for the Chat Room page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver newDriver) {
                // Find the element where James will input a message
                WebElement element = newDriver.findElement(By.id("msg"));
                String testMessage = "Hello world, I am James and this is my message";
                // James enters a message into the message field of the "Chat Room" page
                element.sendKeys(testMessage);
                System.out.println("This is the message written by James at the Chat Room page:         " + testMessage);

                // Find the send button element by its name
                WebElement button = newDriver.findElement(By.id("send-button"));
                // Click the login button
                button.click();

                // Wait for the message sent by James to be received by himself, timeout after 10 seconds
                WebDriverWait wait = new WebDriverWait(newDriver, 10);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message2")));

                // Find the element where the message sent is now received. This is message number 2 because
                // message number 1 is the one where it is announced that James has joined the chat
                WebElement element2 = newDriver.findElement(By.id("message2"));

                System.out.println("This is the message received by James at the Chat Room page: " + element2.getText());
                String expected = "James：Hello world, I am James and this is my message";
                String was = element2.getText();
                Assert.assertEquals(expected, was);

                expected = "Chat Room";
                was = newDriver.getTitle();
                return expected.equals(was);
            }
        });
        // Close the browser
        // driver.quit();

        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    @Test
    public void leave() {
        // Test leave is correct
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println(" LEAVE TEST: We open and then close a second window tab, and check the leave message on the first window tab.");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        // Create a new instance of the ChromeDriver driver
        // WebDriver driver = new ChromeDriver();
        // WebDriver driver = new HtmlUnitDriver();

        // We visit the main page of the application (Chat Room Login), and John logs in and joins the chat
        driver.get("http://localhost:8080/");
        WebElement element = driver.findElement(By.id("username"));
        String username = "John";
        System.out.println("User name entered at the Chat Room Login page (before login and first window tab) is: " + username);
        element.sendKeys(username);
        WebElement button = driver.findElement(By.id("button"));
        button.click();

        // To review how window tabs are managed in Selenium, please see
        // https://www.edureka.co/community/14143/how-open-link-new-tab-chrome-browser-using-selenium-webdriver
        // https://stackoverflow.com/questions/22382628/selenium-webdriver-chrome-switch-window-and-back-unable-to-receive-message
        // https://stackoverflow.com/questions/12729265/switch-tabs-using-selenium-webdriver-with-java
        // Open a new window tab and Steve logs in and joins the chat
        String currentWindowHandle = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open(\"http://localhost:8080/\",'_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        // Log in as a new user Steve
        element = driver.findElement(By.id("username"));
        username = "Steve";
        System.out.println("User name entered at the Chat Room Login page (before login and second window tab) is: " + username);
        element.sendKeys(username);
        button = driver.findElement(By.id("button"));
        button.click();

        // The new window tab is closed, which means Steve leaves the chat
        //((JavascriptExecutor) driver).executeScript("window.close();");
        //System.out.println("Second window tab is closed, which means Steve leaves the chat");
        System.out.println("Steve clicks the exit button on the second window tab, which means he leaves the chat");
        button = driver.findElement(By.id("exit"));
        button.click();
        // Let's switch to the window tab where John is located
        driver.switchTo().window(currentWindowHandle);

        // John waits for the leave message announcing Steve has left the chat, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message3")));

        // This is message number 3 because
        // message number 1 is the one where it is announced that John has joined the chat, and
        // message number 2 is the one where it is announced that Steve has joined the chat
        element = driver.findElement(By.id("message3"));

        System.out.println("This is the message received by John at the Chat Room page (first window tab): " + element.getText());
        String expected = "Chat Server：Steve has left the chat";
        String was = element.getText();
        Assert.assertEquals(expected, was);

        // Close the browser
        // driver.quit();

        System.out.println("----------------------------------------------------------------------------------------------------");
    }
}







