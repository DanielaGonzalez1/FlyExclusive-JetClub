package appium.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import appium.utils.UtilsFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofMillis;

public abstract class CommonPage {
    private final Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private AppiumDriver driver;
    private WebDriverWait wait;
    public CommonPage(){}
    public CommonPage(AppiumDriver<MobileElement> driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public Properties loadProperties() {
        Properties props = new Properties();
        try {
            String propertiesPath = UtilsFile.readFile("target/propertiesPath.txt");
            FileInputStream fis = new FileInputStream(propertiesPath);
            props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
            //logger.info("Loaded " + propertiesPath + " properties file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**************************************************
     * Método: tap
     * Descripción: Realiza click sobre un elemento.
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void tap(MobileElement el) {
        wait.until(ExpectedConditions.visibilityOf(el)).click();
    }

    /**************************************************
     * Método: sendKeys
     * Descripción: Realiza el input de un texto sobre un elemento.
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void sendKeys(MobileElement el, String txt){
        wait.until(ExpectedConditions.visibilityOf(el)).click();
        el.sendKeys(txt);
    }

    /**************************************************
     * Método: elementContainsText
     * Descripción: Valida que el elemento contenga un texto determinado.
     * Parámetros: -
     * Salida: boolean
     **************************************************/
    public boolean elementContainsText(MobileElement el, String text) {
        String elText = getText(el);
        return elText.contains(text);
    }

    /**************************************************
     * Método: getText
     * Descripción: Obtiene el texto de un elemento
     * Parámetros: -
     * Salida: String
     **************************************************/
    public String getText(MobileElement el) {
        return wait.until(ExpectedConditions.visibilityOf(el)).getText();
    }

    /**************************************************
     * Método: pressByElement
     * Descripción: Mantiene presionado un elemento sobre un determinado tiempo
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void pressByElement(MobileElement element, long seconds) {
        new TouchAction(driver)
                .press(element(element))
                .waitAction(waitOptions(ofMillis(seconds))).perform();
    }

    /**************************************************
     * Método:     checkIfExists
     * Descripción: Valida si un elemento existe
     * Parámetros: -
     * Salida: Boolean
     **************************************************/
    public boolean checkIfExists(MobileElement element) {
        return element != null;
    }

    /**************************************************
     * Método:     swipeUP
     * Descripción: Desliza la pantalla hacia arriba
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void swipeUP(){
        swipeOrScroll(Direction.UP);
    }

    /**************************************************
     * Método:     swipeDOWN
     * Descripción: Desliza la pantalla hacia abajo
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void swipeDOWN(){
        swipeOrScroll(Direction.DOWN);
    }

    /**************************************************
     * Método:     swipeRIGHT
     * Descripción: Desliza la pantalla hacia la derecha
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void swipeRIGHT(){
        swipeOrScroll(Direction.RIGHT);
    }

    /**************************************************
     * Método:     swipeLEFT
     * Descripción: Desliza la pantalla hacia la izquierda
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void swipeLEFT(){
        swipeOrScroll(Direction.LEFT);
    }

    /**************************************************
     * Método:     hideKeyboard
     * Descripción: Oculta el teclado
     * Parámetros: -
     * Salida: n/a
     **************************************************/
    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    /**************************************************
     * Método: isEnabled
     * Descripción: Verifica si un elemento se muestra y esta habilitado.
     * Parámetros:
     *  - MobileElement element: Elemento que se desea verificar.
     * Salida: Retorna true si elemento se muestra habilitado, caso contratrio devuelve false.
     **************************************************/
    public boolean isEnabled(MobileElement element) {
        logger.info("Se ejecuta isEnabled(MobileElement element)");
        return element.isDisplayed() && element.isEnabled();
    }

    /**************************************************
     * Método: isDisabled
     * Descripción: Verifica si un elemento se muestra y esta deshabilitado.
     * Parámetros:
     *  - MobileElement element: Elemento que se desea verificar.
     * Salida: Retorna false si elemento se muestra deshabilitado, caso contratrio devuelve true.
     **************************************************/
    public boolean isDisabled(MobileElement element) {
        logger.info("Se ejecuta isDisabled(MobileElement element)");
        return element.isDisplayed() && !element.isEnabled();
    }

    /**************************************************
     * Método: typeClean
     * Descripción: Limpia el elemento y escribe un texto en el mismo.
     * Parámetros:
     *  - MobileElement element: Elemento sobre el cual se desea escribir.
     *  - String text: Texto que se desea escribir.
     * Salida: -
     **************************************************/
    public void typeClean(MobileElement element, String text) {
        logger.info("Se ejecuta typeClean(MobileElement element, String text)");
        element.clear();
        element.sendKeys(text);
    }

    /**************************************************
     * Método: isSelected
     * Descripción: Verifica si el elemento se muestra y el mismo esta o no seleccionado.
     * Parámetros:
     *  - MobileElement element: Elemento que se desea verificar.
     * Salida: Returna true si el elemento se muestra y esta seleccionado, caso contrario devuelve false.
     **************************************************/
    public boolean isSelected(MobileElement element) {
        logger.info("Se ejecuta isSelected(MobileElement element)");
        return element.isDisplayed() && element.isSelected();
    }


    private void swipeOrScroll(Direction direction) {
        Dimension size = driver.manage().window().getSize();
        TouchAction action = new TouchAction(driver);
        PointOption p1 = new PointOption();
        int startY = 0;
        int startX = 0;
        int endX = 0;
        int endY = 0;
        switch (direction) {
            case RIGHT:
                startY = size.height / 2;
                startX = (int) (size.width * 0.90);
                endX = (int) (size.width * 0.05);
                action.press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
                System.out.println("\n Successfully swiped right! \n");
                break;
            case LEFT:
                startY = size.height / 2;
                startX = (int) (size.width * 0.05);
                endX = (int) (size.width * 0.90);
                action.press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
                System.out.println("\n Successfully swiped left! \n");
                break;
            case UP:
                endY = (int) (size.height * 0.9);
                startY = (int) (size.height * 0.55);
                startX = size.width / 2;
                action.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(startX, endY)).release().perform();
                System.out.println("\n Successfully scrolled up \n");
                break;
            case DOWN:
                startY = (int) (size.height * 0.90);
                endY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                action.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(startX, endY)).release().perform();
                System.out.println("\n Successfully scrolled down! \n");
                break;
        }
    }

    public enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    private File screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    private ScreenOrientation getOrientation() {
        return driver.getOrientation();
    }

    private Dimension getScreenSize() {
        return driver.manage().window().getSize();
    }

    private Location getLocation() {
        return driver.location();
    }

    private void setLocation(Location location) {
        driver.setLocation(location);
    }

    public String getUrl() {
        logger.info("Current application url: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }


    public String getCurrentAppContext() {
        logger.info("Current application context: " + driver.getContext());
        return driver.getContext();
    }
    public void makeAnAssert(String message, String expected, String actual) {
        Assert.assertEquals(message, expected, actual);
    
    
    }
    public void makeAnAssert(boolean actual, boolean expected, String message) {
        Assert.assertEquals(message, actual, expected);
    
    
    }
    public void makeAnAssert(int actual, int expected, String message) {
        Assert.assertEquals(String.valueOf(actual), expected, message);
    
    }
    public void makeAnAssert(boolean condition, String message) {
        Assert.assertTrue(message, condition);
    
    }
    public void makeAnAssertNot(String actual, String expected, String message) {
        Assert.assertNotEquals(actual, expected, message);
    
    }
    public void makeAnAssertFalse(boolean condition, String message) {
        Assert.assertFalse(message, condition);
    
    }

    public String getTextOfAnAttribute(MobileElement el, String attribute){
        return el.getAttribute(attribute);

    }
}
