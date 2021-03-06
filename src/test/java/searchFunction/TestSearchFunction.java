package searchFunction;

import com.beust.jcommander.Parameters;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by mattiassvensson on 2017-04-24.
 */

public class TestSearchFunction {

    private static WebDriverWait wait;
    private static FileHandler fh;
    private static Logger log;

    private static String url = "http://www.br.se";

    private static WebDriver driver;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        log = Logger.getLogger(TestSearchFunction.class.getName());

        try {
            fh = new FileHandler("log.log");
        } catch (Exception e) {	log.warning(e.getMessage());}

        log.addHandler(fh);
        fh.setFormatter(new SimpleFormatter());
        log.info("@BeforeClass - setUpBeforeClass()\n");
    }


    @Before
    public void setUp() throws Exception {

        // use chrome for this one
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 3000);
        driver.get(url);
        log.info("@Before - setUp()\n");
    }

    @After
    public void tearDown() throws Exception {
       // driver.close();

        driver.quit();

    }

    @AfterClass
    public static void tearDownAfterClass() {

        log.info("@AfterClass - tearDownAfterClass()\n");
        fh.close();

    }

    @Test
    public void testSearchAlternativ() throws Exception {
        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
            Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());

            // Array of item to search for
            String [] searchItems = {"lego", "hello", "trolls"};
            // Random for take out one of the item from searchItem array list
            int rnd = new Random().nextInt(searchItems.length);

            // Send the random product to searchfield
            driver.findElement(By.id("js-site-search-input")).sendKeys(searchItems[rnd]);

            // Split the random picked item into separated letters in a array to
            // This because search alternative will give you alternative that include all individual letters we search on
            String splitItem [] = searchItems[rnd].split("");


            Thread.sleep(3000);
            // Save all alternative in list
            List<WebElement> elements = driver.findElements(By.xpath(".//*[@id='ui-id-1']/li"));
            // loop all the alternative end assert that the search alternative is correct
            for(WebElement ele : elements){
                // get name from alternative
                String searchText = ele.getText();
                // Make everything lowercase
                String searchTextLowC = searchText.toLowerCase();
                // Loop trow every letter in search item
                for (int i = 0; i < splitItem.length; i++){
                    System.out.println(splitItem[i]);
                    System.out.println(searchTextLowC);
                    // assert if letter exist in search alternative
                    Assert.assertTrue(searchTextLowC.contains(splitItem[i]));
                }
            }
        }

        catch (NoSuchElementException e) {
            log.info(e.getMessage());
            Assert.fail("No such element");
        }
        catch (AssertionError e) {
            log.info(e.getMessage());
            Assert.fail("No such element");
        }
    }

}
