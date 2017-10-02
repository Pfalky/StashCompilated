package Common;

import Common.Annotations.ActionTitle;
import Common.Annotations.Title;
import org.openqa.selenium.WebElement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static StepsDefinition.CommonStepDefinitions.Init;

/**
 * Created by drygoi on 03.06.17.
 */
public class ReflectionHelper {

    /**
     *
     * @param type
     * @param annotation
     * @param value
     * @return
     */
    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation, String value) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> cl = type;
        while (cl != Object.class) {
            //проходитм вверх вплоть до Object класса, для захвата всех родительских методов.
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(cl.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation) && method.getAnnotation(ActionTitle.class).name().equals(value)) {

                    methods.add(method);
                }
            }
            //шаг вверх до родителя
            cl = cl.getSuperclass();
        }
        return methods;
    }

    /**
     *
     * @param type
     * @param annotation
     * @param value
     * @return
     */
    public static List<Field> getFieldsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation, String value) {
        final List<Field> fields = new ArrayList<Field>();
        Class<?> cl = type;
        while (cl != Object.class) {
            //проходитм вверх вплоть до Object класса, для захвата всех родительских полей.
            final List<Field> allFields = new ArrayList<Field>(Arrays.asList(cl.getDeclaredFields()));
            for (final Field field : allFields) {
                if (field.isAnnotationPresent(annotation) && field.getAnnotation(Title.class).value().equals(value)) {

                    fields.add(field);
                }
            }
            //шаг вверх до родителя
            cl = cl.getSuperclass();
        }
        return fields;
    }

    /**
     * Возвращает список веблементов по искомому тегу
     *
     * @param page - страница для поиса полей
     * @param title - название искомой аннотации поля
     * @returnа
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */

    public static List<WebElement> findElementsByTitle(Object page, final String title) throws ClassNotFoundException, IllegalAccessException {
        Class cl = Class.forName(Init.getName());
        List<Field> fields = getFieldsAnnotatedWith(Init, Title.class, title);

        int fieldNumber = fields.size();
        checkForUnique(title, fields, fieldNumber);

        Field field = fields.get(0);
        field.setAccessible(true);
        List<WebElement> el = (List<WebElement>) field.get(page);
        return el;
    }

    /**
     * Возвращает вебэлемент по искомому тегу
     *
     * @param page - страница для поиса полей
     * @param title - название искомой аннотации поля
     * @returnа
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static WebElement findElementByTitle(Object page, String title) throws ClassNotFoundException, IllegalAccessException {
        Class cl = Class.forName(Init.getName());
        List<Field> fields = getFieldsAnnotatedWith(Init, Title.class, title);

        int fieldNumber = fields.size();

        checkForUnique(title, fields, fieldNumber);

        Field field = fields.get(0);
        field.setAccessible(true);
        WebElement el = (WebElement) field.get(page);
        return el;
    }

    private static void checkForUnique(String title, List<Field> fields, int fieldNumber) {
        if (fieldNumber > 1) {
            String fieldNames = "";
            for (Field field : fields) {
                fieldNames += "\n" + field.getName();
            }
            throw new AutotestError("Нарушена уникальность именования полей, найдено более одного поля с указанным title : \""
                    + title + "\". Найдены поля: " + fieldNames);
        }
    }
}
