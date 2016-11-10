package mnassa.pages;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;


@RunWith(SerenityRunner.class)
public class FacebookPage  extends PageObject {
    String parentWindowHandler;
    private final By text_emptyNewsFeed = By.xpath("//h1[@class='create-list-info']");

    public void facebookLogin(WebDriver driver, String Email, String Password) throws Exception {
        driver.get("https://www.facebook.com/");
        System.out.println(driver.getTitle());

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(Email);
        System.out.println("Email");
        WebElement pass = driver.findElement(By.id("pass"));
        pass.sendKeys(Password);
        System.out.println("Password");

        //driver.findElement(By.id("registration_container"));

        //WebDriverWait wt = new WebDriverWait (driver, 400);
       //wt.until(ExpectedConditions.visibilityOfElementLocated(By.id("u_0_o")));
        //driver.findElement(By.id("u_0_o")).click();
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        System.out.println("click");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            WebDriverWait wt = new WebDriverWait (driver, 600);
            wt.until(ExpectedConditions.visibilityOfElementLocated(By.id("contentCol")));
            driver.findElement(By.id("contentCol"));
            System.out.println("content");
        } catch (WebDriverException e) {
            Assert.fail();
        }
    }

    public void confirmFbReg(WebDriver driver, String Email, String Password) {
        parentWindowHandler = getDriver().getWindowHandle();
        System.out.println("parentWindowHandler " + parentWindowHandler);

        for(String winHandle : driver.getWindowHandles())
        {
            driver.switchTo().window(winHandle);
            System.out.println("winHandle " + winHandle);
        }
        WebElement continueAs = driver.findElement(By.xpath("//button[@name='__CONFIRM__']"));
        continueAs.click();
    }

    public void successRegistrationFb(WebDriver driver) {
        String modalWindowHandle = "";
        Set<String> handles = getDriver().getWindowHandles();
        System.out.println("handles " + getDriver().getWindowHandles());
        for (String handle : handles) {
            System.out.println("handle " + modalWindowHandle);
            if (handle.equals(parentWindowHandler))
                modalWindowHandle = handle;

            getDriver().switchTo().window(modalWindowHandle);
            System.out.println(getDriver().getCurrentUrl());
            Assert.assertTrue(getDriver().getTitle().contains("Mnassa"));

            WebDriverWait wt = new WebDriverWait (driver, 400);
            wt.until(ExpectedConditions.visibilityOfElementLocated(text_emptyNewsFeed));
            Assert.assertEquals( "Mnassa is a social interactive platform for business communication.",find(text_emptyNewsFeed).getText());
        }
    }
}
