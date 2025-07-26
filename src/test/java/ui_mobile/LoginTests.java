package ui_mobile;

import config.AppiumConfig;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.ErrorScreen;
import screens.SplashScreen;

import static Utils.PropertiesReader.getProperty;

public class LoginTests extends AppiumConfig {

    AuthenticationScreen authenticationScreen;

    @BeforeMethod
    public void goToAuthScreen() {
        new SplashScreen(driver);
        authenticationScreen = new AuthenticationScreen(driver);
    }

    @Test
    public void loginPositiveTest() {
        authenticationScreen.fillLoginForm(UserDto.builder()
                .username(getProperty("login.properties","email"))
                .password(getProperty("login.properties","password"))
                .build());
        Assert.assertTrue(new ContactsScreen(driver)
                .validateContactsScreenOpen("Contact list"));
    }
    @Test
    public void loginNegativeTest_unRegistered() {
        authenticationScreen.fillLoginForm(UserDto.builder()
                .username("unregistered@mail.co.il")
                .password("Password123!")
                .build());
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("Login or Password incorrect"));
    }
    @Test
    public void loginNegativeTest_wrongPass() {
        authenticationScreen.fillLoginForm(UserDto.builder()
                .username(getProperty("login.properties","email"))
                .password("Wrongpassword123!")
                .build());
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("Login or Password incorrect"));
    }
}