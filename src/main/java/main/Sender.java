package main;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static main.MyProperties.properties;

public class Sender {

    private Session session;

    public Sender () {
        init();
    }

    private void init() {
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.mail.sender"), properties.getProperty("password"));
            }
        });
    }

    public void sendEmail(String receiver, String subject, String content, String pathAttachment){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            BodyPart text = new MimeBodyPart();
            text.setText(content);
            BodyPart attachment = new MimeBodyPart();
            attachment.setDataHandler(new DataHandler(new FileDataSource(pathAttachment)));
            attachment.setFileName(pathAttachment.substring(pathAttachment.lastIndexOf('/') + 1, pathAttachment.length()));
            MimeMultipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(text);
            multiPart.addBodyPart(attachment);
            message.setContent(multiPart);
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), "clave");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }catch (MessagingException me){
            me.printStackTrace();
            return;
        }
    }

    public void sendEmail(String receiver, String subject, String content){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            message.setText(content);
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), "clave");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }catch (MessagingException me){
            me.printStackTrace();
            return;
        }
    }
}

