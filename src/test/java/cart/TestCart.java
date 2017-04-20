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
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;
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
		log = Logger.getLogger(TestOther.class.getName());
		
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
		driver.close();
		
		driver.quit();
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		
		log.info("@AfterClass - tearDownAfterClass()\n");
		fh.close(); 

	 }

	@Test
	public void testAddProductToCart() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			//Vänta och klicka på produkt
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")));
			String productName = driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/div/a/div[3]/span")).getText();
			System.out.println(productName);
			driver.findElement(By.xpath(".//*[@id='content']/div[4]/div/div/ul/li[2]/div/a")).click();
			
			//Vänta på att varukorgsknappen dyker upp
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='icon'])[6]")));
			
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
//		catch (ElementNotVisibleException e) {
//			log.info(e.getMessage());
//			Assert.fail("No such element");
//		}
	}

}
