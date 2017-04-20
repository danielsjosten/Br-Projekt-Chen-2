package searchFunction;

import static org.junit.Assert.*;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestSearchFunction {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static FileHandler fh;
    private static Logger log;

    private static String url = "http://www.br.se";

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
        driver = new FirefoxDriver();
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
            String [] searchItem = {"lego", "godis", "kalle"};
            // Random for take out one of the item from searchItem array list
            int rnd = new Random().nextInt(searchItem.length);

            // Send the random product to searchfield
            driver.findElement(By.id("js-site-search-input")).sendKeys(searchItem[rnd]);

            // Split the random pickt item into separated letters in a array to
            // This because search alternative will give you alternative that include all individual letters we search on
            String splitItem [] = searchItem[rnd].split("");


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
