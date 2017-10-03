package PageObjectsPackage;

import Common.Annotations.ActionTitle;
import Common.Annotations.PageTitle;
import Common.Annotations.Title;
import cucumber.api.DataTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

import java.util.logging.Logger;

import static Common.DriverPage.getDriver;
import static StepsDefinition.CommonStepDefinitions.makeValues;

@PageTitle(name = "Страница поиска")

public class YandexSearchPage extends MainPage {

    WebDriverWait wait = new WebDriverWait(getDriver(), 10);

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public YandexSearchPage() throws IOException {
        PageFactory.initElements(getDriver(), this);
    }

    WebDriver driver = getDriver();

    protected void initElements() throws IOException {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//a[@data-statlog='news.news.links.1.main']")
    @Title("Первая новость на странице")
    private List<WebElement> firstNews;

    @FindBy(xpath = "//input[@id=\"text\"]")
    @Title("Поле для ввода")
    private WebElement textInputField;

  /*  @ActionTitle(name = "Пользователь подготовавливает значения")
    public void yandexPageFirstNewsSearch(){
        makeValues(DataTable);
    }*/
}
