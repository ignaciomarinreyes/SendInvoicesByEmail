package main;

import java.io.File;
import static main.MyProperties.properties;

public class Main {

    public static void main(String[] args) {
        new MyProperties();
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

}
