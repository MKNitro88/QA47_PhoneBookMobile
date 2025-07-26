package screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SplashScreen extends BaseScreen{

    public SplashScreen(AppiumDriver driver) {
        super(driver);
    }
    @FindBy(id="com.sheygam.contactapp:id/version_text")
    WebElement versionText;

    public boolean validateAppVersion(String expectedVersion) {
        return isTextPresentInElement(versionText, expectedVersion, 15);
    }
    public boolean validateSplashscreenToDisappear(long expectedTime) {
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        if (new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.invisibilityOf(versionText))) {
            endTime = System.currentTimeMillis();
        }
        long duration = endTime - startTime;
        System.out.println("duration --> " + duration);
        if (duration <= expectedTime)
            return true;
        return false;
    }
}
