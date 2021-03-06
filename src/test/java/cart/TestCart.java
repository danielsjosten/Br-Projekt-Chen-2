package cart;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import org.junit.Assert;

import org.junit.ComparisonFailure;

/*
 * Tests created by Daniel
 */

public class TestCart {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static FileHandler fh;
	private static Logger log;
	private static Robot robot;

	private static String url = "http://www.br.se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		//log = Logger.getLogger(TestCart.class.getName());
		log = Logger.getLogger(TestCart.class.getMethods().toString());


		try {
			fh = new FileHandler("log.log");
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		log.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
		log.info("Starting testCart tests!");
	}

	@Before
	public void setUp() throws Exception {
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 3000);
		driver.manage().window().maximize();
		driver.get(url);
		robot = new Robot();

	}

	@After
	public void tearDown() throws Exception {
		driver.close();
		Thread.sleep(300);
		driver.quit();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		log.info("All tests finished!");
		fh.close();
	}

	@Test
	public void testAddProductToCart() throws InterruptedException {
		try {
			// Vänta på att sidan laddats och verifiera att url är br.se
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			//Klicka på presentguide för att få upp lista med produkter
			driver.findElement(By.xpath(".//*[@id='nav']/div[1]/div[1]/ul/li[1]/a")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")));


			String productName = driver
					.findElement(By.xpath(".//*[@class='product-list-container']/li[1]/div/div/a/div[4]/span"))
					.getText();
			System.out.println(productName);
			//Klicka på lägg i varukorg
			driver.findElement(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")).click();

			
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_PAGE_UP);
			robot.keyRelease(KeyEvent.VK_PAGE_UP);
			
//			// Vänta på att varukorgsknappen dyker upp
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='basket active'])[1]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='basket active'])[1]")));
			Thread.sleep(500);
//
//			// Klicka på varukorgsknappen
			driver.findElement(By.xpath(".//*[@class='basket active']")).click();
//
//			// Vänta på att produkten dyker upp i varukorgen
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();
//
//			// Verifiera att rätt produkt lags till i varukorgen
			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());
//
//			// Verifiera att antalet av produkten är 1 i varukorgen
			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
			Assert.assertEquals("1", cartValue);

		} catch (org.junit.ComparisonFailure e) {
			log.info(e.getMessage());
			Assert.fail("Assert equals not matching");
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot find element!");
		} catch (StaleElementReferenceException e) {
			log.info(e.getMessage());
			Assert.fail("Stale Element!!");
		} catch (ElementNotVisibleException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot se element!");
		} catch (WebDriverException e) {
			log.info(e.getMessage());
			Assert.fail("Webdriver Exception!");
		}
	}

	@Test
	public void testIncreaseQuantityAndVerifyResult() throws InterruptedException {
		try {
			// Vänta på att sidan laddats och verifiera att url är br.se
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			//Klicka på presentguide för att få upp lista med produkter
			driver.findElement(By.xpath(".//*[@id='nav']/div[1]/div[1]/ul/li[1]/a")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")));


			String productName = driver
					.findElement(By.xpath(".//*[@class='product-list-container']/li[1]/div/div/a/div[4]/span"))
					.getText();
			System.out.println(productName);
			driver.findElement(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")).click();
			
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_PAGE_UP);
			robot.keyRelease(KeyEvent.VK_PAGE_UP);
			
//
//			// Vänta på att varukorgsknappen dyker upp
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='basket active'])[1]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='basket active'])[1]")));
			Thread.sleep(500);
//
//			// Klicka på varukorgsknappen
			driver.findElement(By.xpath(".//*[@class='basket active']")).click();

			// Vänta på att produkten dyker upp i varukorgen
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();

			// Verifiera att rätt produkt lags till i varukorgen
			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());

			// Verifiera att antalet av produkten är 1 i varukorgen
			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
			Assert.assertEquals("1", cartValue);

			// Öka kvantitet av produkt med 1
			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
			String increasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");

			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "2"));
			Assert.assertEquals("2", increasedCartValue);
		} catch (ComparisonFailure e) {
			log.info(e.getMessage());
			Assert.fail("Assert equals not matching");
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot find element!");
		} catch (StaleElementReferenceException e) {
			log.info(e.getMessage());
			Assert.fail("Stale Element!!");
		} catch (ElementNotVisibleException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot se element!");
		} catch (WebDriverException e) {
			log.info(e.getMessage());
			Assert.fail("Webdriver Exception!");
		}
	}

	@Test
	public void testDecreaseQuantityAndVerifyResult() throws InterruptedException {
		try {
			// Vänta på att sidan laddats och verifiera att url är br.se
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			//Klicka på presentguide för att få upp lista med produkter
			driver.findElement(By.xpath(".//*[@id='nav']/div[1]/div[1]/ul/li[1]/a")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")));


			String productName = driver
					.findElement(By.xpath(".//*[@class='product-list-container']/li[1]/div/div/a/div[4]/span"))
					.getText();
			System.out.println(productName);
			driver.findElement(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")).click();
			
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_PAGE_UP);
			robot.keyRelease(KeyEvent.VK_PAGE_UP);
			
//
//			// Vänta på att varukorgsknappen dyker upp
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='basket active'])[1]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='basket active'])[1]")));
			Thread.sleep(500);
//
//			// Klicka på varukorgsknappen
			driver.findElement(By.xpath(".//*[@class='basket active']")).click();

			// Vänta på att produkten dyker upp i varukorgen
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();

			// Verifiera att rätt produkt lags till i varukorgen
			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());

			// Verifiera att antalet av produkten är 1 i varukorgen
			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
			Assert.assertEquals("1", cartValue);

			// Öka kvantitet av produkt med 1
			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
			String increasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");

			// Assert att antal är 2
			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "2"));
			Assert.assertEquals("2", increasedCartValue);

			// Klicka på minusknappen
			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[1]")).click();
			String decreasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");

			// Assert att antal är 1
			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "1"));
			Assert.assertEquals("1", decreasedCartValue);

		} catch (ComparisonFailure e) {
			log.info(e.getMessage());
			Assert.fail("Assert equals not matching");
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot find element!");
		} catch (StaleElementReferenceException e) {
			log.info(e.getMessage());
			Assert.fail("Stale Element!!");
		} catch (ElementNotVisibleException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot se element!");
		} catch (WebDriverException e) {
			log.info(e.getMessage());
			Assert.fail("Webdriver Exception!");
		}
	}


	@Test
	public void testVerifyPriceInCartWithMultipleProducts() throws InterruptedException {
		try {
			
			
			// Vänta på att sidan laddats och verifiera att url är br.se
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			//Klicka på presentguide för att få upp lista med produkter
			driver.findElement(By.xpath(".//*[@id='nav']/div[1]/div[1]/ul/li[1]/a")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")));


			String firstProductName = driver
					.findElement(By.xpath(".//*[@class='product-list-container']/li[1]/div/div/a/div[4]/span"))
					.getText();
			System.out.println(firstProductName);
			driver.findElement(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[1]/div/a")).click();

			// Lägg in en till produkt i varukorgen
			String secondProductName = driver
					.findElement(By.xpath(".//*[@class='product-list-container']/li[2]/div/div/a/div[4]/span"))
					.getText();
			System.out.println(secondProductName);
			driver.findElement(By.xpath(".//*[@id='giftfinder-container']/div/div/div/ul/li[2]/div/a")).click();

			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_PAGE_UP);
			robot.keyRelease(KeyEvent.VK_PAGE_UP);
			
			// Vänta på att varukorgsknappen dyker upp
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='basket active'])[1]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='basket active'])[1]")));
			Thread.sleep(500);
//
//			// Klicka på varukorgsknappen
			driver.findElement(By.xpath(".//*[@class='basket active']")).click();

			
			// Vänta på att produkterna finns i varukorgen
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
			String firstCartProductName = driver.findElement(By.xpath("(.//*[@class='desc'])[1]")).getText();
			String secondCartProductName = driver.findElement(By.xpath("(.//*[@class='desc'])[2]")).getText();

			// Verifiera att rätt produkt lags till i varukorgen
			Assert.assertEquals(firstProductName.toUpperCase(), firstCartProductName.toUpperCase());
			Assert.assertEquals(secondProductName.toUpperCase(), secondCartProductName.toUpperCase());

			// Ta ut pris per produkt och konverterar strängarna till int's
			double firstPrice = Double.parseDouble(
					driver.findElement(By.xpath("(.//*[@class='subtotal'])[1]")).getText().replace(",", "."));
			
			double secondPrice = Double.parseDouble(
					driver.findElement(By.xpath("(.//*[@class='subtotal'])[2]")).getText().replace(",", "."));
			double totalOfFirstAndSecondPrice = firstPrice + secondPrice;
			
			double thirdPrice = firstPrice + secondPrice;

			System.out.println("Totalpriset är: " + totalOfFirstAndSecondPrice + "kr");
			// Tar ut totalpris
			double totalPrice = Double.parseDouble(
					driver.findElement(By.xpath(".//*[@id='basket']/footer/div/div[1]/table/tfoot/tr[1]/th[2]"))
							.getText().replaceAll(",", "."));

			// Assert att produkternas pris stämmer mot totalpris
			Assert.assertTrue(totalPrice == thirdPrice);
			

		} catch (ComparisonFailure e) {
			log.info(e.getMessage());
			Assert.fail("Assert equals not matching");
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot find element!");
		} catch (StaleElementReferenceException e) {
			log.info(e.getMessage());
			Assert.fail("Stale Element!!");
		} catch (ElementNotVisibleException e) {
			log.info(e.getMessage());
			Assert.fail("Cannot se element!");
		} catch (WebDriverException e) {
			log.info(e.getMessage());
			Assert.fail("Webdriver Exception!");
		}
	}
}
