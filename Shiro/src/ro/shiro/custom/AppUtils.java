package ro.shiro.custom;

import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jongo.MongoCollection;

import ro.shiro.bean.User;
import ro.shiro.mongo.MongoUtils;

public class AppUtils {

	public static void registerUser(User u, String ctx) throws Exception {
		MongoCollection c = MongoUtils.getMe().getCollection(
				MongoUtils.Collection.USER);

		User eu = c.findOne("{name: #}", u.getName()).as(User.class);
		if (eu != null) {
			throw new Exception("User already exists");
		}
		sendEmail(u, ctx);
		c.save(u);
	}
	
	

	private static void sendEmail(User u, String ctx) {
		u.setKey(String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
		new MailSender().sendMail(u, ctx);		
	}

	public static boolean login(AuthenticationToken token) {
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean validateAccount(String user, String key)
			throws Exception {

		MongoCollection c = MongoUtils.getMe().getCollection(
				MongoUtils.Collection.USER);

		User eu = c.findOne("{name: #}", user).as(User.class);
		if (eu == null) {
			throw new Exception("User doesen't exists");
		}

		if (!eu.isActive()) {
			if (eu.getKey().equals(key)) {
				eu.setActive(true);
			} else {
				throw new Exception("Invalid activation key");
			}
		}

		c.save(eu);

		AuthenticationToken token = new UsernamePasswordToken(eu.getName(),
				eu.getPass());

		return login(token);
	}

}
