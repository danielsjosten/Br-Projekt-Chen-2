package cart;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.Assert;
import junit.framework.ComparisonFailure;
import other.TestOther;

public class TestCart {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static FileHandler fh;
	private static Logger log;
	
	private static String url = "http://www.br.se";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log = Logger.getLogger(TestCart.class.getName());
		
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
		driver.manage().window().maximize();
		driver.get(url);
		log.info("@Before - setUp()\n");
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
		
		driver.quit();
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		
		log.info("@AfterClass - tearDownAfterClass()\n");
		fh.close(); 

	 }

//	@Test
//	public void testAddProductToCart() {
//		try {
//			//Vänta på att sidan laddats och verifiera att url är br.se
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
//			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
//			
//			//Vänta och klicka på produkt
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")));
//			String productName = driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/div/a/div[3]/span")).getText();
//			System.out.println(productName);
//			driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")).click();
//			
//			//Vänta på att varukorgsknappen dyker upp
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='icon'])[6]")));
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='icon'])[6]")));
//			
//			//Klicka på varukorgsknappen
//			driver.findElement(By.xpath("(.//*[@class='icon'])[6]")).click();
//			
//			//Vänta på att produkten dyker upp i varukorgen
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
//			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();
//			
//			//Verifiera att rätt produkt lags till i varukorgen
//			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());
//			
//			//Verifiera att antalet av produkten är 1 i varukorgen
//			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
//			Assert.assertEquals("1", cartValue);
//			
//		} 
//		catch (ComparisonFailure e) {
//			log.info(e.getMessage());
//			Assert.fail("Assert equals not matching");
//		}catch (NoSuchElementException e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
//		catch (AssertionError e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
//	}
	
	
	
	
	
//	@Test
//	public void testIncreaseQuantityAndVerifyResult() {
//		try {
//			//Vänta på att sidan laddats och verifiera att url är br.se
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
//			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
//			
//			//Vänta och klicka på produkt
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")));
//			String productName = driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/div/a/div[3]/span")).getText();
//			System.out.println(productName);
//			driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")).click();
//			
//			//Vänta på att varukorgsknappen dyker upp
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='icon'])[6]")));
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='icon'])[6]")));
//			
//			//Klicka på varukorgsknappen
//			driver.findElement(By.xpath("(.//*[@class='icon'])[6]")).click();
//			
//			//Vänta på att produkten dyker upp i varukorgen
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
//			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();
//			
//			//Verifiera att rätt produkt lags till i varukorgen
//			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());
//			
//			//Verifiera att antalet av produkten är 1 i varukorgen
//			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
//			Assert.assertEquals("1", cartValue);
//			
//			//Öka kvantitet av produkt med 1
//			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
//			String increasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
//			
//			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "2"));
//			Assert.assertEquals("2", increasedCartValue);
//			
//			
//		} 
//		catch (ComparisonFailure e) {
//			log.info(e.getMessage());
//			Assert.fail("Assert equals not matching");
//		}catch (NoSuchElementException e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
//		catch (AssertionError e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
//	}
	
	
	
//	@Test
//	public void testDecreaseQuantityAndVerifyResult() {
//		try {
//			//Vänta på att sidan laddats och verifiera att url är br.se
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
//			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
//			
//			//Vänta och klicka på produkt
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")));
//			String productName = driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/div/a/div[3]/span")).getText();
//			System.out.println(productName);
//			driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")).click();
//			
//			//Vänta på att varukorgsknappen dyker upp
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='icon'])[6]")));
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='icon'])[6]")));
//			
//			//Klicka på varukorgsknappen
//			driver.findElement(By.xpath("(.//*[@class='icon'])[6]")).click();
//			
//			//Vänta på att produkten dyker upp i varukorgen
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
//			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();
//			
//			//Verifiera att rätt produkt lags till i varukorgen
//			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());
//			
//			//Verifiera att antalet av produkten är 1 i varukorgen
//			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
//			Assert.assertEquals("1", cartValue);
//			
//			//Öka kvantitet av produkt med 1
//			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
//			String increasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
//			
//			//Assert att antal är 2
//			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "2"));
//			Assert.assertEquals("2", increasedCartValue);
//			
//			//Klicka på minusknappen
//			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[1]")).click();
//			String decreasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
//			
//			//Assert att antal är 1
//			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "1"));
//			Assert.assertEquals("1", decreasedCartValue);
//			
//		} 
//		catch (ComparisonFailure e) {
//			log.info(e.getMessage());
//			Assert.fail("Assert equals not matching");
//		}catch (NoSuchElementException e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
//		catch (AssertionError e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
//	}
	
	
	
	@Test
	public void testVerifyPriceInCartWithMultipleProducts() {
		try {
			//Vänta på att sidan laddats och verifiera att url är br.se
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			//Vänta och klicka på produkt
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")));
			String productName = driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/div/a/div[3]/span")).getText();
			System.out.println(productName);
			driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")).click();
			
			//Vänta på att varukorgsknappen dyker upp
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='icon'])[6]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='icon'])[6]")));
			
			//Klicka på varukorgsknappen
			driver.findElement(By.xpath("(.//*[@class='icon'])[6]")).click();
			
			//Vänta på att produkten dyker upp i varukorgen
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[1]/img")));
			String cartProductName = driver.findElement(By.xpath(".//*[@class='desc']/h3")).getText();
			
			//Verifiera att rätt produkt lags till i varukorgen
			Assert.assertEquals(productName.toUpperCase(), cartProductName.toUpperCase());
			
			//Verifiera att antalet av produkten är 1 i varukorgen
			String cartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
			Assert.assertEquals("1", cartValue);
			
			//Öka kvantitet av produkt med 1
			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
			String increasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
			
			//Assert att antal är 2
			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "2"));
			Assert.assertEquals("2", increasedCartValue);
			
			//Klicka på minusknappen
			driver.findElement(By.xpath(".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[1]")).click();
			String decreasedCartValue = driver.findElement(By.xpath(".//*[@id='b_quantity_1']")).getAttribute("value");
			
			//Assert att antal är 1
			wait.until(ExpectedConditions.attributeContains(By.xpath(".//*[@id='b_quantity_1']"), "value", "1"));
			Assert.assertEquals("1", decreasedCartValue);
			
		} 
		catch (ComparisonFailure e) {
			log.info(e.getMessage());
			Assert.fail("Assert equals not matching");
		}catch (NoSuchElementException e) {
			log.info(e.getMessage());
			Assert.fail("No such element");
		}
		catch (AssertionError e) {
			log.info(e.getMessage());
			Assert.fail("No such element");
		}
	}
	
	

}
