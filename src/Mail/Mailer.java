/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mail;

/**
 *
 * @author Vanessa
 */
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

    public static final String MAIL_SERVER = "mail.tecnoweb.org.bo";
    static final String MAIL_USER = "grupo03sc";
    static final String MAIL_PASS = "grup003grup003";
    static final String MAIL_PORT = "25";
    static final String MAIL_USER_MAIL = "grupo03sc@tecnoweb.org.bo";

    public void sendHtmlEmail(String toAddress, String subject, String message)
            throws AddressException, MessagingException {

// Porpiedades del servidor SMTP
        Properties properties = new Properties();  //pop 110
        properties.put("mail.smtp.host", MAIL_SERVER);

//properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.port", MAIL_PORT);
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "false");

// crea nueva session con un autenticador
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_USER, MAIL_PASS);
            }
        };

        Session session = Session.getInstance(properties, auth);

// crea el mensaje de correo
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(MAIL_USER_MAIL));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

// set plain text message
            msg.setContent(message, "text/html; charset=UTF-8");

// sends the e-mail
            Transport.send(msg);
            System.out.println("Envie MAIL: to=" + toAddress + " subject=" + subject + " data:" + message);

        } catch (MessagingException mex) {
            System.err.println("ERROR AL ENVIAR SMTP");
        }
    }
}

