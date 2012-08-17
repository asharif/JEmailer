package org.orphanware.jemailer;

import java.net.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @param args all the args for this.  They are as follows:
 * @author Arash Sharif
 *
 * -t who to send the mail to
 * -f who is the mail from
 * -s subject of the mail message
 * -b body of mail message
 * -h smtp server to use
 * -p port to use for smtp server
 * -xu user for authentication
 * -xp password for authentication
 * --help help output
 *
 */
public class Emailer {

    private String to;
    private String from;
    private String subject;
    private String body;
    private String host;
    private String port;
    private String user;
    private String pass;

    public Emailer(String[] args) throws Exception{

        handleArgs(args);
        checkForNulls();
        sendMail();
    }

    private void handleArgs(String[] args) {

        if (args.length == 0) {
            displayHelp();
        }

        for (int i = 0; i < args.length; i++) {

            String arg = args[i];

            if (arg.equals("--help")) {
                displayHelp();
                return;
            }

            if (arg.equals("-t")) {
                to = args[i + 1];
            }

            if (arg.equals("-f")) {
                from = args[i + 1];
            }

            if (arg.equals("-s")) {
                subject = args[i + 1];
            }

            if (arg.equals("-b")) {
                body = args[i + 1];
            }

            if (arg.equals("-t")) {
                to = args[i + 1];
            }

            if (arg.equals("-h")) {
                host = args[i + 1];
            }

            if (arg.equals("-p")) {
                port = args[i + 1];
            }

            if (arg.equals("-xu")) {
                user = args[i + 1];
            }

            if (arg.equals("-xp")) {
                pass = args[i + 1];
            }

        }

    }

   

    private void checkForNulls() throws Exception {

        if (to == null || from == null || subject == null || body == null
                || host == null || port == null || user == null || pass == null) {
            throw new Exception("you need to specify all options to send mail");
        }


    }

    private void displayHelp() {

        System.out.println("JEmailer v1.0 Copyright (c) 2011 Arash Sharif\n\n");
        System.out.println("usage: emailer [OPTIONS] \n\n");
        System.out.println("OPTIONS:\n\n");
        System.out.println("-t who to send the mail to\n");
        System.out.println("-f who is the mail from\n");
        System.out.println("-s subject of the mail message\n");
        System.out.println("-b body of mail message\n");
        System.out.println("-h smtp server to use\n");
        System.out.println("-p port to use for smtp server\n");
        System.out.println("-xu user to authenticate with smtp provider\n");
        System.out.println("-xu password to authenticate with smtp provider\n\n");

    }

    private void sendMail() throws NoSuchProviderException {

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", host);
        props.put("mail.smtps.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);

        Transport transport = mailSession.getTransport();
        
        Message simpleMessage = new MimeMessage(mailSession);


        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        } catch (AddressException e) {

            e.printStackTrace();
        }

        try {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(Message.RecipientType.TO, toAddress);
            simpleMessage.setSubject(subject);
            simpleMessage.setText(body);

            transport.connect(host, Integer.parseInt(port), user, pass);


            transport.sendMessage(simpleMessage, simpleMessage.getRecipients(Message.RecipientType.TO));

            transport.close();

        } catch (MessagingException e) {

            e.printStackTrace();
        }

    }
}
