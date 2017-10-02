package PageObjectsPackage;

import Common.Annotations.ActionTitle;
import Common.ReflectionHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.logging.Logger;

import static Common.DriverPage.getDriver;


/**
 * Created by drygoi on 03.06.17.
 */

//todo от этой странице предполагается общее наследование по шаблону Page Object

public class MainPage {

    WebDriverWait wait = new WebDriverWait(getDriver(), 10);

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MainPage() throws IOException {
    }

    protected void initElements() throws IOException {
        PageFactory.initElements(getDriver(), this);
    }

    @ActionTitle(name = "заполняет поле")
    public void fillsField(String field, String value) throws ClassNotFoundException, IllegalAccessException {
        WebElement el = ReflectionHelper.findElementByTitle(this, field);
        el.click();
        el.clear();
        el.sendKeys(value);
        LOGGER.info("Заполнено поле : " + field + "Значением : " + value);
    }

    @ActionTitle(name = "нажимает на кнопку")
    public void filterClicking(String field) throws ClassNotFoundException, IllegalAccessException {
        ReflectionHelper.findElementByTitle(this, field).click();
    }


}
