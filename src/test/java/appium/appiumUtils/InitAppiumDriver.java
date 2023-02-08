package appium.appiumUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

/*Esta clase contiene  la configuracion necesaria para las capabilities las cuales ayudan a conectar el dispositivo*/
public class InitAppiumDriver {

    private final Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    protected Map<String, String> config;
    public InitAppiumDriver(Map<String, String> config) {
        this.config = config;
    }

    public AndroidDriver<MobileElement> initDriverAndroid() {


        String appiumIp = this.config.get("appiumip");
        String appiumPort = this.config.get("appiumport");

        DesiredCapabilities cap = new DesiredCapabilities();

        /* Local settings */
        cap.setCapability(MobileCapabilityType.UDID, this.config.get("udid"));
        cap.setCapability(MobileCapabilityType.DEVICE_NAME,  this.config.get("deviceName"));
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, this.config.get("platformVersion"));
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1200");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, this.config.get("automationName"));
        cap.setCapability("app",this.config.get("app"));
        cap.setCapability("uiautomator2ServerInstallTimeout", 30000);
        cap.setCapability("uiautomator2ServerLaunchTimeout", 30000);
        cap.setCapability("adbExecTimeout", 60000);
        cap.setCapability("locationServicesAuthorized", true);
        cap.setCapability("autoGrantPermissions", true);
        cap.setCapability("autoAcceptAlerts", true);
        // Reset keyboard to its original state
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);
        cap.setCapability("no-reset", true);
        cap.setCapability("full-reset", false);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME,this.config.get("platformName"));


        // Display capabilities
        logger.info("Used capabilities: " + cap.toJson().toString());

        // Create appium driver
        AndroidDriver<MobileElement> driver = null;

        // Set local driver settings
        try {
            String url = "http://" + appiumIp + ":" + appiumPort + "/wd/hub";
            driver = new AndroidDriver(new URL(url), cap);
            logger.info("Appium url: " + url);
        } catch (MalformedURLException e) {
            logger.info("appiumIp or appiumPort parameter incorrect: "
                    + appiumIp + ":" + appiumPort);
        }
        return driver;
    }

    public AndroidDriver<MobileElement> initDriverAndroidBrowserstack() {

        DesiredCapabilities cap = new DesiredCapabilities();

        // Set your access credentials
        cap.setCapability("browserstack.user", this.config.get("user"));
        cap.setCapability("browserstack.key", this.config.get("key"));
        cap.setCapability("browserstack.idleTimeout", "300");

        // Specify device and os_version for testing
        cap.setCapability("device", this.config.get("device"));
        cap.setCapability("os_version", this.config.get("os_version"));
        cap.setCapability("realMobile", "true");

        // Set other BrowserStack capabilities
        cap.setCapability("project", this.config.get("project"));
        cap.setCapability("build", this.config.get("build"));
        cap.setCapability("name", this.config.get("name"));



        // Display capabilities
        logger.info("Used capabilities: " + cap.toJson().toString());

        // Create appium driver
        AndroidDriver<MobileElement> driver = null;
        return driver;
    }

}