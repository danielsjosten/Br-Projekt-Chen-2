package product;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.GiftFinderPage;
import pageObject.HomePage;
import pageObject.ProductPage;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestProduct {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static FileHandler fh;
	private static Logger log;

	private static String url = "http://www.br.se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log = Logger.getLogger(TestProduct.class.getName());

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

		//driver.quit();

	}

	@AfterClass
	public static void tearDownAfterClass() {

		log.info("@AfterClass - tearDownAfterClass()\n");
		fh.close();

	}

	@Test
	public void testProductSitePrice() throws Exception {
		try {
			// product i want to use
			String product;
			String productInfo;

			// Pageobjects
			HomePage homePage = new HomePage();
			GiftFinderPage giftFinder = new GiftFinderPage();
			ProductPage productPage = new ProductPage();

			// Se if we are on firstpage
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());

			//
			Thread.sleep(3000);
			homePage.giftFinder(driver).click();

			Thread.sleep(5000);
			// I want to use page object but i dont get it to work with visibilityOf
			wait.until(ExpectedConditions.visibilityOf(giftFinder.firstProductInList(driver)));
			// Take in product name and price from gift finder page so i can compare it later that im on the same product site
			product = giftFinder.firstProductInList(driver).getText();
			giftFinder.firstProductInList(driver).click();


			// I want to use page object but i dont get it to work with visibilityOf so i have both
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class=\"product-information\"]")));
			wait.until(ExpectedConditions.visibilityOf(productPage.productInfo(driver)));

			// Assert that im on a product site
			Assert.assertEquals("Information", productPage.productInfoHedline(driver).getText());

			// Get info for the product on product site
			productInfo = productPage.productInfo(driver).getText();
			// Assert that its the same product on product site thats show in product site we
			Assert.assertTrue(productInfo.contains(product));

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
