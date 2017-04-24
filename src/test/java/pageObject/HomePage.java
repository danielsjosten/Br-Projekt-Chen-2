package pageObject;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {


    public WebElement newsletterButton(WebDriver driver){

        return driver.findElement(By.xpath(".//*[@class='btn-newsletter']"));
    }

    public WebElement loginButton(WebDriver driver){

        return driver.findElement(By.xpath("(.//*[@class=\"clubbr__icon\"])[1]"));
    }

    public WebElement giftFinder (WebDriver driver){

        return driver.findElement(By.xpath("(.//*[@href=\"/GiftFinder\"])[1]"));
    }

}
