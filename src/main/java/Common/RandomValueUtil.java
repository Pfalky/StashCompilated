package Common;

import java.util.Random;

/**
 * Created by drygoi on 17.07.2017.
 */
public class RandomValueUtil {

    /**
     * Генерирует случайный набор символов указанного типа и длинны.
     * @param type - тип генерируемых значений
     * @param length - длинна генерируемого значения
     */
    public static String generateValue(String type, Integer length) {
        String value = "";
        char[] chars = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЪЬЭЮЯабвгдеёжзиклмнопрстуфхцчшщъьэюя0123456789 ".toCharArray();//72
        char[] charsNumber = "0123456789".toCharArray();//10
        char[] charsEnglish = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ".toCharArray();//72

        Random rnd = new Random();

        switch (type) {
            case "строка": {
                for (int i = 0; i < length; i++)
                    value += (chars[rnd.nextInt(chars.length)]);
                break;
            }
            case "число": {
                for (int i = 0; i < length; i++)
                    value += (charsNumber[rnd.nextInt(charsNumber.length)]);
                break;
            }
            case "строка латиница": {
                for (int i = 0; i < length; i++)
                    value += (charsEnglish[rnd.nextInt(charsEnglish.length)]);
                break;
            }
            default:
                throw new AutotestError("Некорректный тип данных либо такого генератора нет и его нужно добавить.");
        }

        return value;
    }
}
