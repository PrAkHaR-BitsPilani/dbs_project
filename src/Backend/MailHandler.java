package Backend;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class MailHandler extends Thread {
	
	public String from,  password,  to,  sub,  msg;
	
	public MailHandler(String from, String password, String to, String sub, String msg) {
		this.from = from;
		this.password = password;
		this.to = to;
		this.sub = sub;
		this.msg = msg;
	}
	public  void run() 
	{
		Properties prop = new Properties();    
        prop.put("mail.smtp.host", "smtp.gmail.com");    
        prop.put("mail.smtp.socketFactory.port", "465");    
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");    
        prop.put("mail.smtp.auth", "true");    
        prop.put("mail.smtp.port", "465");    
	
        Session session = Session.getDefaultInstance(prop,    
                new javax.mail.Authenticator() {    
                protected PasswordAuthentication getPasswordAuthentication() {    
                return new PasswordAuthentication(from,password);  
                }    
               });    
               try {    
                MimeMessage message = new MimeMessage(session);    
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
                message.setSubject(sub);    
                message.setText(msg);    
                //send message  
                Transport.send(message);    
                System.out.println("message sent successfully");    
               } catch (MessagingException e) {throw new RuntimeException(e);}    
                  
     }
	
}