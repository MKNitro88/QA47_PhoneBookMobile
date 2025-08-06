package ui_mobile;

import config.AppiumConfig;
import dto.UserDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.*;

public class DeleteContactTest extends AppiumConfig {
    ContactsScreen contactsScreen;

    UserDto qa_user = UserDto.builder()
            .username("qa_user_qwerty@mail.com")
            .password("Password123!")
            .build();
    @BeforeMethod
    public void login(){
        new SplashScreen(driver);
        new AuthenticationScreen(driver)
                .fillLoginForm(qa_user);
        contactsScreen = new ContactsScreen(driver);
    }

    @Test
    public void deleteContactTest(){
        contactsScreen.swipeRightToLeft();
        contactsScreen.clickBtnYes();
    }
}