package screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorScreen extends BaseScreen{
    public ErrorScreen(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "android:id/message")
    WebElement errorText;

    public boolean validateErrorMessage(String text){
        return isTextPresentInElement(errorText, text, 20);
    }

    public String getErrorMessage() {
        return errorText.getText();
    }
}