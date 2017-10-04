package Common;

import java.util.HashMap;
import java.util.Map;

public class Stash {

   static Map<Object, Object> stash = new HashMap<>();

     public static void addValueToStash(Object key, Object value) {
        stash.put(key, value);
    }

    public static void addValueToStash(Map<String, String> map) {
        map.forEach((key,value)-> {
            stash.put(key,value);
        });
    }

    public static Object getValueFromStash(String key) {
        return stash.get(key);
    }

}




