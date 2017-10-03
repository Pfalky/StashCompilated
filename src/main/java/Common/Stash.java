package Common;

import java.util.HashMap;
import java.util.Map;

public class Stash {

   static Map<Object, Object> stash = new HashMap<>();

     public static void add(Object key, Object value) {
        stash.put(key, value);
    }

    public static Object get(Object value) {
        return stash.get(value);
    }

}




