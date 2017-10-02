package Common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static Common.Const.propertiesDirectory;

/**
 * Created by drygoi on 13.07.17.
 */
public class Props {

    private static Properties prop = new Properties();

    public static final Properties getProps() throws IOException {
        FileInputStream in = new FileInputStream(propertiesDirectory);
        prop.load(in);
        in.close();
        return prop;
    }

}
