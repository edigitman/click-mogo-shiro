package ro.shiro.custom;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ro.shiro.bean.UserBean;

public class MailSender {

	private Properties properties;

	public MailSender() {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("mail.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMail(UserBean u, String ctx) {
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"edi.gitman@gmail.com", "patruori16");
					}
				});

		String url = ctx.replace("newAccout.htm", "")
				+ "servlet/register?key=" + u.getKey() + "&u=" + u.getName();

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(u.getEmail()));
			message.setSubject("Account validation");
			message.setText("Dear user," + "\n\n account validation &lt;a href=\""
					+ url + "\"&gt;path&lt;/a&gt;");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
