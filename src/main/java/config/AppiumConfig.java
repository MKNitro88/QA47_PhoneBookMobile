package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AppiumConfig {
    protected static AppiumDriver driver;
/*
{
  "platformName": "Android",
  "deviceName": "Android_SDK_built_for_x86 device",
  "platformVersion": "10.0",
  "appPackage": "com.sheygam.contactapp",
  "appActivity": ".SplashActivity"
}
 */
    @BeforeMethod
    public void setUp(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "Android_SDK_built_for_x86");
        desiredCapabilities.setCapability("platformVersion", "10.0");
        desiredCapabilities.setCapability("appPackage", "com.sheygam.contactapp");
        desiredCapabilities.setCapability("appActivity", ".SplashActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Appium");
        String url = "http://localhost:4723/wd/hub";
        try {
            driver = new AndroidDriver(new URL(url), desiredCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
