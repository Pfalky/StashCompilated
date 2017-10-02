package Common;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public  class Helper {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void testStashGen() {
        Map<String, String> stash = new HashMap<String, String>();
        stash.put(new String("Key1"), new String("Value1"));
        stash.put(new String("Key2"), new String("Value2"));
        stash.put(new String("Key3"), new String("Value3"));
    }

    public void preparationValue(HashMap stash) {
        LOGGER.info("Подготовливаем значения");
        stash.forEach((k, v) -> System.out.println("key: " + k + " value:" + v));        {

            String oldValue = "";//Ключ или значение старое
            String rule = ""; //Правило преобразования
            String ruleArg = "";//Параметр преобразования
            String newKey = ""; //Ключ для сохранения получившегося значения
            String newValue = "";

            //получение значений из стеша
            for (int i = 0; i < 3; i++) {

                switch (i) {
                    case 0:
                        oldValue = getValueFromStash(stash);
                        break;
                    case 1:
                        rule = parsedXlsBuilder.toString();
                        break;
                    case 2:
                        newKey = getKeyFromStash(stash);
                        break;
                    default:
                        LOGGER.info("Заглушка, для будущих модификаций метода");
                        break;
                }
            }

            switch (rule) {
                case "SAVE"://Просто сохраняем/пересохраняем значение в STASH с новым(а можно и прежним) ключом
                    newValue = oldValue;
                    break;


                case "GET"://ruleArgs: получаем сохраненное значение из стеша
                //описание работы правила
                break;
                default:
                    LOGGER.info("Указанное правило '" + rule + "' еще не реализовано!");
                    throw new AutotestError("Указанное правило '" + rule + "' еще не реализовано!");
            }
            setValueToStash(stash,newKey, newValue);
        }
    }

    private String getValueFromStash( HashMap<String,String> stash) {
        String value = new String();
        if (stash.size()==0){return null;}
        for (String v : stash.values()) {
            value=v;
        }
        return value;
    }

    private void setValueToStash( HashMap<String,String> stash,String key, String value) {
        stash.put(key,value);
    }

    private String getKeyFromStash( HashMap<String,String> stash) {
        String key = new String();
        if (stash.size()==0){return null;}
        for (String k : stash.keySet()) {
            key=k;
        }
        return key;
    }

    }
