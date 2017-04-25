package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by mattiassvensson on 2017-04-24.
 */
public class ProductPage {

    public WebElement productInfo(WebDriver driver){

        return driver.findElement(By.xpath(".//*[@class=\"product-information\"]"));
    }

    public WebElement productInfoHedline(WebDriver driver){

        return driver.findElement(By.xpath(".//*[@id='details']/article/header/h2"));
    }
}
