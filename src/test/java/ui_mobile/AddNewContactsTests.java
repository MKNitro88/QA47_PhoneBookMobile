package ui_mobile;

import config.AppiumConfig;

import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.*;

import static Utils.ContactFactory.*;
import static Utils.PropertiesReader.getProperty;

public class AddNewContactsTests extends AppiumConfig{
    ContactsScreen contactsScreen;
    AddNewContactScreen addNewContactScreen;

    UserDto qa_user = UserDto.builder()
            .username(getProperty("login.properties","email"))
            .password(getProperty("login.properties","password"))
            .build();

    @BeforeMethod
    public void login(){
        new SplashScreen(driver);
        new AuthenticationScreen(driver)
                .fillLoginForm(qa_user);
        contactsScreen = new ContactsScreen(driver);
        contactsScreen.clickBtnPlus();
        addNewContactScreen = new AddNewContactScreen(driver);
    }
    @Test
    public void addNewContactPositiveTest(){
        addNewContactScreen.typeContactForm(createPositiveContact());
        Assert.assertTrue(addNewContactScreen
                .validateMessageSuccess("Contact was added!"));
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail(){
        addNewContactScreen.typeContactForm(createNegativeContact_wrongEmail(""));
    }

    @Test
    public void addNewContactNegativeTest_wrongPhone(){
        addNewContactScreen.typeContactForm(createNegativeContact_wrongPhone(""));
        Assert.assertTrue(new ErrorScreen(driver)
                .validateErrorMessage("Phone number must contain only"));
    }

    @Test
    public void addNewContactNegativeTest_wrongName(){
        addNewContactScreen.typeContactForm(createNegativeContact_wrongName("     "));
        Assert.assertTrue(new ErrorScreen(driver)
                .validateErrorMessage("must not be blank"));
    }
}
