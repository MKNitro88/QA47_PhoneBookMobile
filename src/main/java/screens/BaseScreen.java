package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseScreen {
    AppiumDriver driver;

    public BaseScreen(AppiumDriver driver){
        this.driver = driver;
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),this);

    }
    @FindBy(xpath = "//hierarchy/android.widget.Toast")
    WebElement messageSuccess;

    public boolean validateMessageSuccess(String text){
        return isTextPresentInElement(messageSuccess, text, 10);
    }
    public void pause(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isTextPresentInElement(WebElement element, String text, int time) {
       return new WebDriverWait(driver,Duration.ofSeconds(time))
               .until(ExpectedConditions.textToBePresentInElement(element, text));

    }
    public boolean isElementDisplayed(WebElement element,int time) {
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
}
