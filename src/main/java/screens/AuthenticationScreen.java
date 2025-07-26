package screens;

import dto.UserDto;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class AuthenticationScreen extends BaseScreen{
    public AuthenticationScreen(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "com.sheygam.contactapp:id/inputEmail")
    WebElement emailInput;
    @FindBy(id = "com.sheygam.contactapp:id/inputPassword")
    WebElement passwordInput;
    @FindBy(id = "com.sheygam.contactapp:id/loginBtn")
    WebElement loginButton;
    @FindBy(id = "com.sheygam.contactapp:id/regBtn")
    WebElement registerButton;
    @FindBy(id = "android:id/alertTitle")
    WebElement alertTitle;

    public void fillRegistrationForm(UserDto user) {
        emailInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        registerButton.click();
    }
    public void fillLoginForm(UserDto user) {
        emailInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        loginButton.click();
    }
    public boolean validateAlertOpen() {
        return isElementDisplayed(alertTitle,15);
    }
}
