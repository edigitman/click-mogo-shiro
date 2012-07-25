package ro.shiro.custom;

import org.jongo.MongoCollection;

import ro.shiro.bean.User;
import ro.shiro.mongo.MongoUtils;

public class AppUtils {

	public static void registerUser(User u) throws Exception {
		MongoCollection c = MongoUtils.getMe().getCollection(
				MongoUtils.Collection.USER);

		User eu = c.findOne("{name: #}", u.getName()).as(User.class);
		if (eu != null) {
			throw new Exception("User already exists");
		}
		c.save(u);
		sendEmail(u);
	}

	private static void sendEmail(User u) {
		// TODO Auto-generated method stub
	}

}
