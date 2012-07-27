package ro.shiro.custom;

import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import ro.shiro.bean.CarBean;
import ro.shiro.bean.UserBean;
import ro.shiro.mongo.dao.CarDao;
import ro.shiro.mongo.dao.UserDao;

public class AppUtils {

	public static void registerUser(UserBean u, String ctx) throws Exception {
		UserDao ud = new UserDao();

		UserBean eu = ud.getByName(u.getName());
		if (eu != null) {
			throw new Exception("User already exists");
		}
		sendEmail(u, ctx);
		ud.update(eu);
	}

	public static UserBean getUser() {
		UserDao ud = new UserDao();
		return ud.getByName(SecurityUtils.getSubject().getPrincipal()
				.toString());
	}

	public static CarBean getCar() {
		CarDao cd = new CarDao();
		return cd.getById(getUser().getCar());
	}

	private static void sendEmail(UserBean u, String ctx) {
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
		UserDao ud = new UserDao();

		UserBean eu = ud.getByName(user);
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
		ud.update(eu);

		AuthenticationToken token = new UsernamePasswordToken(eu.getName(),
				eu.getPass());

		return login(token);
	}

}
