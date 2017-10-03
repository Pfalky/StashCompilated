package StepsDefinition;

import Common.Annotations.ActionTitle;
import Common.Annotations.PageTitle;
import Common.AutotestError;
import Common.ReflectionHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static Common.DriverPage.getDriver;
import static Common.Props.getProps;


/**
 * Created by drygoi on 20.05.17.
 */
public class CommonStepDefinitions {

    public static Class Init;

    @Given("^Пользователь открывает сайт \"([^\"]+)\"$")
    public static void openSite(String siteAddress) throws IOException, InterruptedException {
        getDriver().get(siteAddress);
        Thread.sleep(5000);
    }

    @Given("^Пользователь открывает браузер на странице тестируемого ресурса")
    public static void openSite() throws IOException {
        getDriver().get(getProps().getProperty("base_url"));
    }


    @When("^Пользователь находится на странице \"([^\"]+)\"$")
    public void userIsOnPage(String pageName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections("PageObjectsPackage");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(PageTitle.class);
        if (annotated.size() > 1) {
            throw new AutotestError("Не уникальное имя страницы");
        }
        Init = annotated.iterator().next();
        Constructor cons = Init.getConstructor();
        cons.newInstance();
    }

    @When("^Пользователь \\(([^\"]+)\\) \"([^\"]+)\"$")
    public void actionOneParam(String actionName, String param) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Assert.assertTrue("Не найдено метода с заданным названием : \"" + actionName + "\"",
                ReflectionHelper.getMethodsAnnotatedWith(Init, ActionTitle.class, actionName).size() != 0);
        for (Method m : ReflectionHelper.getMethodsAnnotatedWith(Init, ActionTitle.class, actionName)) {
            if (m.getParameterCount() == 1)
                m.invoke(Init.newInstance(), param);
            return;
        }
        throw new AutotestError("Отсутсвует метод с указанным колличеством параметров. \n" +
                "Имеется метод с колличеством параметров равным: " + 1);
    }

    @When("^Пользователь \\(([^\"]+)\\) \"([^\"]+)\" \"([^\"]+)\"$")
    public void actionTwoParam(String actionName, String param, String param2) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Assert.assertTrue("Не найдено метода с заданным названием : \"" + actionName + "\"",
                ReflectionHelper.getMethodsAnnotatedWith(Init, ActionTitle.class, actionName).size() != 0);
        for (Method m : ReflectionHelper.getMethodsAnnotatedWith(Init, ActionTitle.class, actionName)) {
            if (m.getParameterCount() == 2)
                m.invoke(Init.newInstance(), param, param2);
            return;
        }
        throw new AutotestError("Отсутсвует метод с указанным колличеством параметров. \n" +
                "Имеется метод с колличеством параметров равным: " + 2);
    }
/*
    @When("^пользователь сохраняет значение <поля> в стеш") //добавить regex для слова "Поле"
    public void saveToStash(Object stashKey, Object stashValue) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Assert.assertTrue("Не найдено метода с заданным названием : \"" + actionName + "\"",
                ReflectionHelper.getMethodsAnnotatedWith(Init, ActionTitle.class, actionName).size() != 0);
        for (Method m : ReflectionHelper.getMethodsAnnotatedWith(Init, ActionTitle.class, actionName)) {
            if (m.getParameterCount() == 2)
                m.invoke(Init.newInstance(), param, param2);
            return;
        }*/


    @Then("^Скачать проект построчно$")
    public void listFilesForFolder() throws Throwable {
        listFilesForFolder(new File("/home/drygoi/IdeaProjects/BDD/src/main"));
    }

    String s = "";

    public void listFilesForFolder(final File folder) throws IOException {
        int i;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                i = 1;
                BufferedReader br = new BufferedReader(new FileReader(fileEntry));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(i++ + " : " + line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                s += sb.toString();
                System.out.println(fileEntry.getName());
            }
        }
        System.out.println("\n");
        System.out.println("Класс " + folder.getName());
        System.out.println("\n \n");
        System.out.println(s);
        s="";
    }

}
