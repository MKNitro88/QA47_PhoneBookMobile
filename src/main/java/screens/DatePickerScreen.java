package screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DatePickerScreen extends  BaseScreen{

    public DatePickerScreen(AppiumDriver driver) {
        super(driver);
    }


    @FindBy(id = "com.sheygam.contactapp:id/dateBtn")
    WebElement btnChangeDate;

    public CalendarScreen clickBtnChangeDate(){
        btnChangeDate.click();
        return new CalendarScreen(driver);
    }
}