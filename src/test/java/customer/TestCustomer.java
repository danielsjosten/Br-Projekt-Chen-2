package customer;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import inputs.LoginInput;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.HomePage;
import pageObject.LoginPage;


public class TestCustomer {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static FileHandler fh;
	private static Logger log;
	
	private static String url = "https://www.br.se/";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log = Logger.getLogger(TestCustomer.class.getName());
		
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
	public void testCustomerInformationChange() {
		try{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
		Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
		
		driver.findElement(By.className("clubbr__icon")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[2]/div[2]/div/div[2]/form/div[3]/button")));
		Assert.assertEquals("https://www.br.se/login",driver.getCurrentUrl());
		
	//	driver.findElement(By.id("j_username")).click();
		
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("document.getElementById('j_username').value='hej@live.se';");
		executor.executeScript("document.getElementById('j_password').value='123456';");	
		
		//driver.findElement(By.xpath("html/body/div[8]/div[2]/div[2]/div/div[2]/form/div[2]/div/input")).sendKeys("123456");	
		driver.findElement(By.xpath("html/body/div[8]/div[2]/div[2]/div/div[2]/form/div[3]/button")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[3]/div/a[2]")));
		Assert.assertEquals("https://www.br.se/my-account/profile", driver.getCurrentUrl());
		
		driver.findElement(By.xpath("html/body/div[8]/div[3]/div/a[2]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[3]/div/div[2]/form/div[4]/div[1]/button")));
		Assert.assertEquals("https://www.br.se/my-account/update-profile", driver.getCurrentUrl());
		
		executor.executeScript("document.getElementById('profile.firstName').setAttribute('value', 'Hanna')");
		driver.findElement(By.xpath("html/body/div[8]/div[3]/div/div[2]/form/div[4]/div[1]/button")).click();
		driver.findElement(By.xpath("html/body/div[8]/div[1]/div/ul/li[1]/a")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[3]/div/a[2]")));
		Assert.assertEquals("https://www.br.se/my-account/profile", driver.getCurrentUrl());
		
		String text = driver.findElement(By.xpath("html/body/div[8]/div[3]/div/table/tbody/tr[1]")).getText();
		
		Assert.assertTrue(text.contains("Hanna"));
		
		//String td1 = rows.get(0).getText();
		//String td2 = rows.get(1).getText();
		
		//Assert.assertEquals("Förnamn:",td1);
		//Assert.assertEquals("Hanna",td2);
		
		}
		catch(NoSuchElementException e){
			log.info(e.getMessage());
			Assert.fail("No such element");
		}
		catch(AssertionError e) {
			log.info(e.getMessage());
			Assert.fail("Assertion fail");
		}
		
	}
		
		@Test
		public void testCustomerEmail() {
			try{
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='btn-newsletter']")));
				Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
				
				driver.findElement(By.xpath(".//*[@class='btn-newsletter']")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='btn btn-default btn-block']")));
				Assert.assertEquals("https://www.br.se/login", driver.getCurrentUrl());
				
				//driver.findElement(By.id("register.firstName")).click();
				//driver.findElement(By.id("register.firstName")).sendKeys("Test");
				
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("register.firstName")));
				executor.executeScript("document.getElementById('register.firstName').value='Anders';");
				executor.executeScript("document.getElementById('register.lastName').value='Borg';");
				executor.executeScript("document.getElementById('register.email').value='hej@live.se';");
				executor.executeScript("document.getElementById('register.emailConfirm').value='hej@live.se';");
				executor.executeScript("document.getElementById('register.line1').value='Vallhallavägen';");
				executor.executeScript("document.getElementById('register.postcode').value='123 22';");
				
				Select dropDown = new Select(driver.findElement(By.id("register.favStore")));
				dropDown.selectByIndex(1);
				Assert.assertEquals("Alingsås",dropDown.getFirstSelectedOption().getText());
				
				executor.executeScript("document.getElementById('password').value='123456';");
				executor.executeScript("document.getElementById('register.checkPwd').value='123456';");
				
				driver.findElement(By.id("terms")).click();
				//WebElement element = driver.findElement(By.id("terms"));
				Assert.assertTrue(driver.findElement(By.id("terms")).isSelected());
				
				driver.findElement(By.xpath(".//*[@class='btn btn-default btn-block']")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='email.errors']")));
				Assert.assertEquals("https://www.br.se/login/register", driver.getCurrentUrl());
				
				String test = driver.findElement(By.xpath(".//*[@id='email.errors']")).getText();
				Assert.assertEquals("Det finns redan ett konto kopplat till denna mejladress", test);
				
				
				
			}

			catch(NoSuchElementException e){
				log.info(e.getMessage());
				Assert.fail("No such element");
			}
			catch(AssertionError e) {
				log.info(e.getMessage());
				Assert.fail("Assertion fail");
			}
	}


	/**
	 * Created by mattiassvensson on 2017-04-24.
	 */
	@Test
	public void testLoginWrongPassword() throws Exception {
		try {

			// Instans of my page objects
			HomePage homePage = new HomePage();
			LoginPage loginPage = new LoginPage();
			LoginInput loginInput = new LoginInput();

			wait.until(ExpectedConditions.visibilityOf(homePage.newsletterButton(driver)));
			Assert.assertEquals(url, driver.getCurrentUrl());

			// Click on loginButton on homepage
			homePage.loginButton(driver).click();

			// I try to get page object to work with .visibilityOf but i hade to use visibilityOfAllElementsLocatedBy to make it work
			// It could be something i miss or .visibilityOf is broken in selenium lib
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[@class=\"headline\"]/h2")));
			wait.until(ExpectedConditions.visibilityOf(loginPage.comebackHeadline(driver)));
			// Assert that you get this message to verify that you or on login page
			Assert.assertEquals("Återvändande kund", loginPage.comebackHeadline(driver).getText());

			// Send login data to input fields
			loginPage.usernameInput(driver).sendKeys(loginInput.username);
			loginPage.passwordInput(driver).sendKeys(loginInput.wrongPassword);
			loginPage.loginButton(driver).click();

			// Wait for alert message to show that you use wrong login data
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class=\"alert alert-danger alert-dismissable\"]")));
			wait.until(ExpectedConditions.visibilityOf(loginPage.alert(driver)));
			String Alert = loginPage.alert(driver).getText();

			// Assert that you right message show
			Assert.assertEquals("× Kontot hittades inte", Alert );


		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			Assert.fail("No such element");
		} catch (AssertionError e) {
			log.info(e.getMessage());
			Assert.fail("Assertion fail");
		}
	}
}
