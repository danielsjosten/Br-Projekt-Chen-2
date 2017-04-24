package payment;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import other.TestOther;

public class TestPayment {

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
	public void testPaymentOptions() throws Exception {
		
		// Öppnar www.br.se och verifierar att man är på rätt sida
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
		Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
		
		//Väntar på att bannern för sortimentet dyker upp sen klicka på den för att komma till produkter
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='frontpage-banner-img']")));
		driver.findElement(By.xpath(".//*[@class='frontpage-banner-img']")).click();
		
		//Vänta tills en viss knapp syns sen lägg till en produkt till varukorg
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[2]/div[5]/div[3]/button")));
		driver.findElement(By.xpath("html/body/div[8]/div[2]/div[5]/div[2]/div/ul/li[1]/div/a")).click();
		
		
		//Väntar tills varukorgen finns sen klicka på den
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='icon'])[6]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@class='icon'])[6]")));
		Thread.sleep(300);
		driver.findElement(By.xpath("(.//*[@class='icon'])[6]")).click();
		
		
		//Väntar tills "gå till kassan" knappen finns sen klicka på den
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//*[@class='btn action js-once btn-once'])[2]")));
		driver.findElement(By.xpath("(.//*[@class='btn action js-once btn-once'])[2]")).click();
		
		//Vänta tills fältet Epostadress finns och verifiera att man är på rätt sida
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest.email")));
		Assert.assertEquals("https://www.br.se/login/checkout", driver.getCurrentUrl());
		
		//Fyll i fältet epost och verifiera mejladress
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("document.getElementById('guest.email').value='hhh@live.se';");
		executor.executeScript("document.getElementById('guest.emailConfirm').value='hhh@live.se';");
		
		//Klicka på knappen fortsätt till kassan som gäst
		driver.findElement(By.xpath("(.//*[@class='btn btn-default btn-block'])[1]")).click();
		
		//verifiera att man är på rätt sida och ser rätt header
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='header_text']")));
		Assert.assertEquals("https://www.br.se/checkout/multi/your-information", driver.getCurrentUrl());
		
		//verifiera att knappen för betalkort finns och den är selectad
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("brSe-creditcard")));
		Assert.assertTrue(driver.findElement(By.id("brSe-creditcard")).isSelected());
		
		//Klicka på knappen banköverföring och verifiera att den är selectad
		driver.findElement(By.id("brSe-bank")).click();
		Assert.assertTrue(driver.findElement(By.id("brSe-bank")).isSelected());
		
		//Klicka på knappen faktura och verifiera att den är selectad
		driver.findElement(By.id("brSe-onlineinvoice")).click();
		Assert.assertTrue(driver.findElement(By.id("brSe-onlineinvoice")).isSelected());
	}

}
