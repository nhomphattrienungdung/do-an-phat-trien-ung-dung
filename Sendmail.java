package abc;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Sendmail {

	public static void send(String smtpServer, String to, String from, String psw, String subject, String body)
			throws Exception {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		final String login = from;
		final String pwd = psw;
		Authenticator pa = null;
		if (login != null && pwd != null) {
			props.put("mail.smtp.auth", "true");
			pa = new Authenticator() {

				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(login, pwd);
				}
			};
		}
		Session session = Session.getInstance(props, pa);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

		msg.setSubject(subject);
		msg.setText(body);

		// msg.setHeader("X-Mailer", "LOTONtechEmail");
		msg.setSentDate(new Date());
		msg.saveChanges();

		Transport.send(msg);
		System.out.println("Mail da duoc gui !!");

	}
	
	
//	 public static void main(String[] args) throws Exception {
//	 send("smtp.gmail.com", "nhansadsmiles@gmail.com", "ngomnhan96@gmail.com",
//	 "nmnmt96.", "TES1T", "chao m t la nhan");
//	 }
	
}
