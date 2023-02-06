package appium.utils;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Logger;

public class UtilsProperties {
    private static final Logger logger = Logger.getLogger(String.valueOf(UtilsProperties.class));
    private static String env = System.getProperty("environment");
    private static String platform = System.getProperty("platform");
    public static Properties getRunPropertiesServ() {


        try{
            // Set properties
            Properties props = new Properties();
            String propertiesPath = "src/test/resources/properties/" + "apis-"+env+".properties";
            FileInputStream fis = new FileInputStream(propertiesPath);
            props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
            UtilsFile.updateFile("target/propertiesPath.txt", propertiesPath);
            return props;
        }catch(IOException e){
            e.printStackTrace();
        }

    return null;

    }
    public static Properties getRunPropertiesDB() {

        try{
            // Set properties
            Properties props = new Properties();
            String propertiesPath = "src/test/resources/properties/" + "database-"+env+".properties";
            FileInputStream fis = new FileInputStream(propertiesPath);
            props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
            UtilsFile.updateFile("target/propertiesPath.txt", propertiesPath);
            return props;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Properties getRunPropertiesCap() {
        logger.info("Environment : "+env);
        logger.info("Platform : "+platform);
        try{
            // Set properties
            Properties props = new Properties();
            String propertiesPath = "src/test/resources/properties/" + platform+"-"+env+".properties";
            FileInputStream fis = new FileInputStream(propertiesPath);
            props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
            UtilsFile.updateFile("target/propertiesPath.txt", propertiesPath);
            return props;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
