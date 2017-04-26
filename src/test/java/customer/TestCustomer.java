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
			
		// Vänta till en header finns sen verifiera att man är på rätt sida
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-text")));
		Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
		
		// klicka på logga in knappen sen vänta på att en viss element finns sen verifiera att man är på rätt sida
		driver.findElement(By.className("clubbr__icon")).click();	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[2]/div[2]/div/div[2]/form/div[3]/button")));
		Assert.assertEquals("https://www.br.se/login",driver.getCurrentUrl());
		
		// Skriv in epost och lösenord på fälten E-postadress och Lösenord
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("document.getElementById('j_username').value='test@live.se';");
		executor.executeScript("document.getElementById('j_password').value='123456';");	
		
		// Klicka på knappen logga in
		driver.findElement(By.xpath("html/body/div[8]/div[2]/div[2]/div/div[2]/form/div[3]/button")).click();
		
		//Vänta på länken uppdatera personuppgifter och verifiera att man är på rätt sida
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[3]/div/a[2]")));
		Assert.assertEquals("https://www.br.se/my-account/profile", driver.getCurrentUrl());
		
		//Klicka på länken uppdatera personuppgifter
		driver.findElement(By.xpath("html/body/div[8]/div[3]/div/a[2]")).click();
		
		//Vänta på knappen spara uppdateringar och verifiera att man är på rätt sida
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[3]/div/div[2]/form/div[4]/div[1]/button")));
		Assert.assertEquals("https://www.br.se/my-account/update-profile", driver.getCurrentUrl());
		
		//Ändra värdet på Förnamnet till ett annat värde sen klicka på knappen spara uppdateringar
		executor.executeScript("document.getElementById('profile.firstName').setAttribute('value', 'panna')");
		driver.findElement(By.xpath("html/body/div[8]/div[3]/div/div[2]/form/div[4]/div[1]/button")).click();
		
		// Klicka på min profil för att se om ändringen har lyckats
		driver.findElement(By.xpath("html/body/div[8]/div[1]/div/ul/li[1]/a")).click();
		
		//Vänta på knappen spara uppdateringar och verifiera att man är på rätt sida
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[8]/div[3]/div/a[2]")));
		Assert.assertEquals("https://www.br.se/my-account/profile", driver.getCurrentUrl());
		
		// Fetcha förnamnet och verifiera att det har ändrats
		String text = driver.findElement(By.xpath("html/body/div[8]/div[3]/div/table/tbody/tr[1]")).getText();	
		Assert.assertTrue(text.contains("panna"));
		
		
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
				
				//Vänta på knappen bli medlem och verifiera att man är på rätt sida
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='btn-newsletter']")));
				Assert.assertEquals("https://www.br.se/", driver.getCurrentUrl());
				
				//Klicka på knappen bli medlem
				driver.findElement(By.xpath(".//*[@class='btn-newsletter']")).click();
				
				//Vänta på knappen registrera och verifiera att man är på rätt sida
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='btn btn-default btn-block']")));
				Assert.assertEquals("https://www.br.se/login", driver.getCurrentUrl());
				
				// Fyller i nödvändig information för att kunna bli medlem med en mailadress som redan finns
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("register.firstName")));
				executor.executeScript("document.getElementById('register.firstName').value='Anders';");
				executor.executeScript("document.getElementById('register.lastName').value='Borg';");
				executor.executeScript("document.getElementById('register.email').value='test@live.se';");
				executor.executeScript("document.getElementById('register.emailConfirm').value='test@live.se';");
				executor.executeScript("document.getElementById('register.line1').value='Vallhallavägen';");
				executor.executeScript("document.getElementById('register.postcode').value='123 22';");
				
				// välja första valet i dropdownlista för att sedan verifiera att valet är gjort
				Select dropDown = new Select(driver.findElement(By.id("register.favStore")));
				dropDown.selectByIndex(1);
				Assert.assertEquals("Alingsås",dropDown.getFirstSelectedOption().getText());
				
				// Fylla i fälten för lösenord
				executor.executeScript("document.getElementById('password').value='123456';");
				executor.executeScript("document.getElementById('register.checkPwd').value='123456';");
				
				// Klicka på checkboxen villkor sedan verifiera att den är klickad
				driver.findElement(By.id("terms")).click();
				Assert.assertTrue(driver.findElement(By.id("terms")).isSelected());
				
				// Klicka på knappen registrera
				driver.findElement(By.xpath(".//*[@class='btn btn-default btn-block']")).click();
				
				// Vänta på errormeddelandet och verifiera att man är på rätt sida
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='email.errors']")));
				Assert.assertEquals("https://www.br.se/login/register", driver.getCurrentUrl());
				
				//Verifiera att texten från errormeddelandet stämmer överens med strängen nedan
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
