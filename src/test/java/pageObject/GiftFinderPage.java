package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by mattiassvensson on 2017-04-24.
 */
public class GiftFinderPage {

    public WebElement firstProductInList(WebDriver driver){

        return driver.findElement(By.xpath("(.//*[@class=\"product-list-container\"]/li)[1]/div/div"));
    }
}
