package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by mattiassvensson on 2017-04-24.
 */

public class LoginPage {


    public WebElement comebackHeadline(WebDriver driver){

        return driver.findElement(By.xpath(".//*[@class=\"headline\"]/h2"));
    }

    public WebElement usernameInput(WebDriver driver){

        return driver.findElement(By.id("j_username"));
    }

    public WebElement passwordInput(WebDriver driver){

        return driver.findElement(By.id("j_password"));
    }

    public WebElement loginButton(WebDriver driver){

        return driver.findElement(By.xpath(".//*[@class=\"btn btn-primary btn-block\"]"));
    }

    public WebElement alert(WebDriver driver){

        return driver.findElement(By.xpath(".//*[@class=\"alert alert-danger alert-dismissable\"]"));
    }

}
