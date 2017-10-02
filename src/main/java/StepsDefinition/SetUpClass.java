package StepsDefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.io.IOException;
import java.util.logging.Logger;

import static Common.DriverPage.getDriver;

/**
 * Created by drygoi on 20.05.17.
 */
public class SetUpClass {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Before
    public static void setUp(Scenario scenario){
        LOGGER.info("Запускается тест: " + scenario.getName());

    }

    @After
    public static void dispose() throws IOException {
        getDriver().quit();
    }

}
