package appium.appiumUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.Rule;
import org.junit.rules.Timeout;
import appium.utils.UtilsFile;
import appium.utils.UtilsProperties;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;


public class Hooks {

    // Set logger
    private static Logger logger = Logger.getLogger(String.valueOf(Hooks.class));

    // Set Appium Driver
    private static AndroidDriver<MobileElement> driver;

    // Set classes
    private static InitAppiumDriver initAppiumDriver;
    private static AppiumUtils appiumUtils;

    // Set configuration variables
    public static String platform = System.getProperty("platform");
    public static Map<String, String> config = new HashMap<>();
    private boolean useBrowserstack = false;

    public boolean isUseBrowserstack() {return useBrowserstack;}

    public void setUseBrowserstack(boolean useBrowserstack) {this.useBrowserstack = useBrowserstack;}

    private Properties props;



    // Set global tests timeout
    @Rule
    public Timeout globalTimeout = Timeout.seconds(1200000); // max time per test

    @Before
    public void setUp(Scenario scenario) throws Exception {


        // Set properties
        this.props = UtilsProperties.getRunPropertiesCap();

        if(platform.equals("browserstack")){
            setUseBrowserstack(true);
            // Set configuration variables
            config.put("platformName", props.getProperty("platformName"));
            config.put("device", this.props.getProperty("device"));
            config.put("os_version", props.getProperty("os_version"));
            config.put("app", props.getProperty("app"));
            config.put("project", props.getProperty("project"));
            config.put("build", props.getProperty("build"));
            config.put("name", scenario.getName());
            config.put("deviceName", props.getProperty("deviceName"));
            config.put("user", props.getProperty("user"));
            config.put("key", props.getProperty("key"));
            // Start appium driver
            initAppiumDriver = new InitAppiumDriver(config);
            driver = initAppiumDriver.initDriverAndroidBrowserstack();
        }
        else{
            // Set configuration variables
            config.put("version", props.getProperty("version"));
            config.put("appiumip", this.props.getProperty("appiumip"));
            config.put("appiumport", props.getProperty("appiumport"));
            config.put("app", new File(props.getProperty("app")).getAbsolutePath());
            config.put("udid", props.getProperty("udid"));
            config.put("deviceName", props.getProperty("deviceName"));
            config.put("platformVersion", props.getProperty("platformVersion"));
            config.put("platformName", props.getProperty("platformName"));
            config.put("automationName", props.getProperty("automationName"));
            config.put("systemLanguage", props.getProperty("systemLanguage"));
            config.put("systemLocale", props.getProperty("systemLocale"));
            config.put("appPackage", props.getProperty("appPackage"));
            config.put("urlPerfil", props.getProperty("urlPerfil"));

            // Start appium driver
            initAppiumDriver = new InitAppiumDriver(config);
            driver = initAppiumDriver.initDriverAndroid();
        }


        // Get Instance class to set driver
        appiumUtils = AppiumUtils.getInstance();
        appiumUtils.setDriver(driver);

        if(!isUseBrowserstack()){
            // Start video recording
            appiumUtils.startRecordingScreen();
        }


        addEnvironmentInformation();


    }

    @After
    public void tearDown(Scenario scenario) {

        if(!isUseBrowserstack()){
            // Stop video recording
            appiumUtils.stopRecordingScreenAndClean();
        }

        // Close driver after each test
        driver.quit();

        // Remove temporary files
        UtilsFile.removeFile("target/propertiesPath.txt");
    }


    @SuppressWarnings("UnusedReturnValue")
    private boolean addEnvironmentInformation() {

        boolean success = false;
        try {
            String allurePath = System.getProperty("user.dir") + "/target/allure-results";
            File allureDir = new File(allurePath);
            if (!allureDir.isDirectory()) {
                success = allureDir.mkdirs();
            }
            File environmentProperties = new File(allurePath, "environment.properties");
            props.store(new FileWriter(environmentProperties), null);
        } catch (IOException e) {
            logger.warning("Could not store environment properties");
            e.printStackTrace();
        }
        return success;
    }



}
