package brew.cmm.service.ppt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class BrewProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrewProperties.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = BrewProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                LOGGER.error("Sorry, unable to find application.properties");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            LOGGER.error("IOException occurred while loading properties file", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
