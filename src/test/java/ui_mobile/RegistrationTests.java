package ui_mobile;

import config.AppiumConfig;
import dto.UserDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.ErrorScreen;
import screens.SplashScreen;
import static Utils.UserFabric.*;

public class RegistrationTests extends AppiumConfig {
    AuthenticationScreen authenticationScreen;
    SoftAssert softAssert = new SoftAssert();
    UserDto qa_user = UserDto.builder()
            .username("qa_user_myUser@mail.com")
            .password("Password123!")
            .build();

    @BeforeMethod
    public void goToAuthScreen(){
        new SplashScreen(driver);

    }
    @Test
    public void registrationPositiveTest() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createValidUser());
        softAssert.assertTrue(new ContactsScreen(driver).validateContactsScreenOpen("Contact list"));
        softAssert.assertAll();

    }
    //test disabled - app allows incorerect email format - email without domain
    @Test(enabled = false)
    public void registrationNegativeTest_invalidEmail1() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidEmail("Email@mail"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must be a well-formed email address"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidEmail2() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidEmail("@mail.com"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must be a well-formed email address"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidEmail3() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidEmail("invalidEmail.com"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must be a well-formed email address"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidEmail4() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidEmail("invalid email@mail.com"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must be a well-formed email address"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidEmail5() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidEmail(" invalidemail@mail.com"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must be a well-formed email address"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidEmail6() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidEmail("invalidemail@mail.com "));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must be a well-formed email address"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidPass1_NoUpper() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidPassword("password123!"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("Must contain at least 1 uppercase letter"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidPass2_NoLower() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidPassword("PASSWORD123!"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("1 lowercase letter"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidPass3_NoDigit() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidPassword("Password!"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("and 1 number"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidPass4_NoSpecialChar() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidPassword("Password123"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("[@$#^&*!]"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_invalidPass5_tooShort() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidPassword("invalid password"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("At least 8 characters"));
        softAssert.assertAll();
    }
    //test disabled - unknown limit on password max length
    @Test(enabled = false)
    public void registrationNegativeTest_invalidPass6_tooLong() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(createUser_invalidPassword("Password123456789012345678901234567890!"));
        softAssert.assertTrue(authenticationScreen.validateAlertOpen());
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("Must contain at least 1 uppercase letter"));
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_DuplicateEmail() {
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.fillRegistrationForm(qa_user);
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorMessage("User already exists"));
        softAssert.assertAll();

    }
}
