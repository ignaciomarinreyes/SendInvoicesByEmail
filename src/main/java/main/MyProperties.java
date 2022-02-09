package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {

    public static Properties properties;

    public MyProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("properties"));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
