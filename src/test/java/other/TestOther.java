package other;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

//import junit.framework.Assert;
import org.junit.Assert;


public class TestOther {

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
	public void testAboutUs() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
			Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
			
			driver.findElement(By.xpath(".//*[@href='http://www.br.se/om-br']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='header-content-box']/h1")));
			
			Assert.assertEquals("https://www.br.se/om-br", driver.getCurrentUrl());
			
			String textBoxTopic = driver.findElement(By.xpath(".//*[@class='header-content-box']/h1")).getText();
			Assert.assertEquals("Om BR", textBoxTopic);
			
			
			
		} 
//		catch (ComparisonFailure e) {
//			log.info(e.getMessage());
//			Assert.fail("Assert equals not matching");
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
