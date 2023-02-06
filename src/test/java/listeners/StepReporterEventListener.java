package listeners;

import appium.appiumUtils.AppiumUtils;
import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.json.JsonException;
import org.openqa.selenium.logging.LogEntry;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class StepReporterEventListener implements StepLifecycleListener {
    private static final Logger logger = Logger.getLogger(String.valueOf(StepReporterEventListener.class));
    private static String platform = System.getProperty("platform");
    @Override
    public void beforeStepStart(StepResult result) {
        logger.info("Step to be Completed: " + result.getName());
    }

    @Override
    public void afterStepUpdate(StepResult result) {
        logger.info("Step Completed: " + result.getName());
        if (result.getStatus() == Status.FAILED || result.getStatus() == Status.BROKEN) {
            logger.info("Attached logs after failed or broken step");
            String filePath = System.getProperty("user.dir") + "/target/log/";
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = filePath + "step_error_log";
            takeScreenShot();
        }
        if(platform.equals("browserstack")){
            if (result.getStatus() == Status.FAILED || result.getStatus() == Status.BROKEN) {
                setBrowserStackTestStatus("failed", result.getName());
            } else {
                setBrowserStackTestStatus("passed", result.getName());
            }
        }
    }

    @Override
    public void afterStepStop(StepResult result) {
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = ".png")
    public byte[] takeScreenShot() {
        byte[] screenShot = new byte[0];
        try {
            screenShot = ((TakesScreenshot) AppiumUtils.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            logger.warning("Error while taking screenshot");
            e.printStackTrace();
        }
        return screenShot;
    }

    @Attachment(value = "App log", type = "text/plain", fileExtension = ".txt")
    public byte[] saveAppLog() {
        byte[] logs = new byte[0];
        try {
            List<LogEntry> logEntries = AppiumUtils.getInstance().getDriver().manage().logs().get("logcat").getAll();
            logs = this.saveLog(logEntries);
        } catch (JsonException e) {
            logger.warning("Network log is not available for " + System.getenv("BROWSER"));
            e.printStackTrace();
        }
        return logs;
    }
    
    private byte[] saveLog(List<LogEntry> logEntries) {
        ByteArrayOutputStream logOutputStream = new ByteArrayOutputStream();
        for (LogEntry entry : logEntries) {
            try {
                logOutputStream.write(String.format("%s\n", entry).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logOutputStream.toByteArray();
    }

    @Attachment(value = "page source", type = "text/plain", fileExtension = ".xml")
    public String savePageSource() {
        return AppiumUtils.getInstance().getDriver().getPageSource();
    }

    public void setBrowserStackTestStatus(String status, String reason) {
        JavascriptExecutor jse = (JavascriptExecutor)AppiumUtils.getInstance().getDriver();
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"<" + status + ">\", \"reason\": \"<" + reason + ">\"}}");
        logger.info("Set BrowserStack status as " + status);
    }
}