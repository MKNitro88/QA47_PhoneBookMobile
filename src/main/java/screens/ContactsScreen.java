package screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactsScreen extends BaseScreen{
    public ContactsScreen(AppiumDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView")
    WebElement textContactList;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/add_contact_btn']")
    WebElement btnAddNewContactPlus;

    public boolean validateContactsScreenOpen(String text){

        return isTextPresentInElement(textContactList, text, 15);
    }
    public void clickBtnPlus(){
        btnAddNewContactPlus.click();
    }
}
