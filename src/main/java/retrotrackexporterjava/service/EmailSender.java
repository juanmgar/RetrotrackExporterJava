package retrotrackexporterjava.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String to, String subject, String text, String[] attachments) throws Exception {
        final String username = "probandoretrotrack@gmail.com";
        final String password = "xsmqtlexrodhizpf";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        // Texto + adjuntos
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Adjuntar archivos
        for (String filePath : attachments) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));
            multipart.addBodyPart(attachmentPart);
        }

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("Correo enviado a " + to);
    }
}
