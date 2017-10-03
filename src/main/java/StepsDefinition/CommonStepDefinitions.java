package StepsDefinition;

        import Common.Annotations.ActionTitle;
        import Common.Annotations.PageTitle;
        import Common.AutotestError;
        import Common.ReflectionHelper;
        import com.gargoylesoftware.htmlunit.Page;
        import cucumber.api.DataTable;
        import cucumber.api.java.en.Given;
        import cucumber.api.java.en.When;
        import org.junit.Assert;
        import org.reflections.Reflections;

        import java.io.IOException;
        import java.lang.reflect.Constructor;
        import java.lang.reflect.InvocationTargetException;
        import java.lang.reflect.Method;
        import java.util.Set;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import static Common.DriverPage.getDriver;
        import static Common.Props.getProps;
        import static Common.Stash.addValueToStash;
        import static Common.Stash.getValueFromStash;


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

    @When("^Пользователь подготовавливает значения")
    public static void makeValues(DataTable data) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println("Подготовливаем значения");
        data.asLists(String.class).stream()
                //Фильтруем "Комментарии" можно расширить своими
                .filter(rowData ->
                        !("[Подготавливаемое значение, Правило, Ключ преобразованного значения]".equals(rowData.toString())
                                ||//или
                                "[,,]".equals(rowData.toString())
//                                ||//можно добавить еще что нибудь

                        ))
                //Работа с каждой строкой таблицы
                .forEach(rowData -> {

                    String oldValue = "";//Ключ или значение старое
                    String rule = ""; //Правило преобразования
                    String ruleArg = "";//Параметр преобразования
                    String newKey = ""; //Ключ для сохранения получившегося значения

                    //В этом блоке заменяем все значения $ключ_из_стэша$ -> значение_из_стэша
                    //можно придумать и для XLS - тогда следует данный блок повторить заменив соответсвующие регулярки и getValueFormStash -> getParamByName
                    //Хотя я лично за то что бы где нибудь в before парсить XLS в Stash
                    for (int i = 0; i < 3; i++) {
                        String str = rowData.get(i);
                        //парсим значения стеша $значение$
                        Pattern stashPattern = Pattern.compile("\\$[^$]+\\$");
                        Matcher matcher = stashPattern.matcher(str);
                        StringBuilder parsedStashBuilder = new StringBuilder();
                        int pos = 0;
                        while (matcher.find()) {
                            parsedStashBuilder.append(str, pos, matcher.start());
                            pos = matcher.end();
                            parsedStashBuilder.append(getValueFromStash(matcher.group().replaceAll("\\$", "")));
                        }
                        parsedStashBuilder.append(str, pos, str.length());
/*
                        Pattern xlsPattern = Pattern.compile("@[^@]+@");
                        matcher = xlsPattern.matcher(parsedStashBuilder);
                        StringBuilder parsedXlsBuilder = new StringBuilder();
                        pos = 0;
                        while (matcher.find()) {
                            parsedXlsBuilder.append(parsedStashBuilder, pos, matcher.start());
                            pos = matcher.end();
                            String[] sectionParam = matcher.group().replaceAll("@", "").split("\\.");
                            parsedXlsBuilder.append(Page.getParamByName(sectionParam[0], sectionParam[1]));
                        }
                        parsedXlsBuilder.append(parsedStashBuilder, pos, parsedStashBuilder.length());*/

                        switch (i) {
                            case 0:
                                oldValue = parsedStashBuilder.toString();
                                break;
                            case 1:
                                rule = parsedStashBuilder.toString();
                                break;
                            case 2:
                                newKey = parsedStashBuilder.toString();
                                break;
                            default:
                                //  logError("Заглушка, для будущих модификаций метода");
                                break;
                        }
                    }

                    if (rule.contains(":")) {//Rule может быть в виде Правило:Аргументы правила
                        ruleArg = rule.split(":")[1];//аргументы правила
                        rule = rule.split(":")[0];//Само правило
                    }
                    String newValue = "";
                    switch (rule) {
                        case "SAVE"://Просто сохраняем/пересохраняем значение в STASH с новым(а можно и прежним) ключом
                            newValue = oldValue;
                            System.out.println("Ввод значений в стеш");
                            break;
                        case "GET"://метод получения данных из стеша в переменную
                            System.out.println("Вывод значений из стеша");
                            break;
                    }
                    addValueToStash(newKey, newValue);

                });
    }
}

