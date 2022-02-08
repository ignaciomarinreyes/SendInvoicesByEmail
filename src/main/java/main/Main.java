package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static Properties properties;

    public static void main(String[] args) {
        loadProperties();
        File[] files = new File(properties.getProperty("path.folder.pdf")).listFiles();
        Reader reader = new Reader();
        Sender sender = new Sender();
        for (File file: files) {
            String email = reader.getEmailReceiver(file);
            sender.sendEmail(email, file.getName(), "Adjunto el archivo de " + file.getName(), file.getPath());
            System.out.println("Enviado con éxito el archivo de " + file.getName());
            file.delete();
        }
    }

    private static void loadProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("properties")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
