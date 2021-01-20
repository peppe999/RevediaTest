package it.ingsw.revedia.utilities;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailManager
{
    private static final String from = "revedia2021@gmail.com";
    private static final String password = "albofiso.42";
    private static Session session;

    protected static Session getSession()
    {
        if(session == null)
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(from, password);
                }
            });
        }

        return session;
    }

    public static void registrationConfirm(String userMail, String userNickname)
    {
        String text = "Ciao " + userNickname + ",\n" +
                "<h2>benvenuto su Revedia!!</h2>\n"+
                "La tua registrazione è andata a buon fine.\n" +
                "Per adesso puoi cercare, visualizzare e recensire tutti i contenuti presenti\n" +
                "ma chissà un giorno potrai anche inserirne di nuovi!!\n" +
                "Maggiori informazioni alla pagina ";

        sendMail(userMail,text,"Benvenuto su Revedia");
    }

    public static void passwordModified(String userMail, String userNickname)
    {
        String text = "Ciao " + userNickname + ",\n" +
                "la tua password è stata modificata";

        sendMail(userMail,text,"Password modificata");
    }

    protected static void sendMail(String mailTo, String text, String subject)
    {
        try
        {
            Transport.send(prepareMessage(mailTo, text, subject));
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    protected static Message prepareMessage(String mailTo, String text, String subject)
    {
        MimeMessage message = new MimeMessage(getSession());
        try
        {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(subject);
            message.setText(text);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

        return message;
    }
}
