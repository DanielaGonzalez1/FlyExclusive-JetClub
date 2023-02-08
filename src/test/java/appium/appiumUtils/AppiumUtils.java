package appium.appiumUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;


public class AppiumUtils {

    /*Configuration of drivers and Web driver to connect to applications*/
    private static AppiumUtils instance = null;
    protected Logger logger = Logger.getLogger(String.valueOf(getClass()));
    private AndroidDriver<MobileElement> driver;
    private static WebDriverWait wait;
    private AppiumUtils() {
    }
    public static AppiumUtils getInstance() {
        if (instance == null) instance = new AppiumUtils();
        return instance;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public AndroidDriver<MobileElement> getDriver() {
        return driver;
    }
    public void setDriver(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }


    @SuppressWarnings("UnusedReturnValue")
    private String startRecording() {
        return driver.startRecordingScreen();
    }
    @SuppressWarnings("UnusedReturnValue")
    public String stopRecording() {
        return driver.stopRecordingScreen();
    }

    public void startRecordingScreen() {
        startRecording();
        logger.info("Started test recording");
    }


    @Attachment(value = "Screen recorder", type = "video/mp4", fileExtension = ".mp4")
    public byte[] stopRecordingScreenAndClean() {
            // Write and delete recorded file to remove from device
                String base64String = stopRecording();
                logger.info("Stopped test recording");
                byte[] data = Base64.decodeBase64(base64String);
                logger.info("Discarded test recording file");
            return data;
        }
}
