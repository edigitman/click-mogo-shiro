package ro.shiro.security;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.jongo.MongoCollection;

import ro.shiro.bean.User;
import ro.shiro.mongo.MongoUtils;

public class CustomJdbcRealm extends JdbcRealm {

	protected static final String DEFAULT_AUTHENTICATION_QUERY = "{name: #}";

	public CustomJdbcRealm() {
		super();
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		// Null username is invalid
		if (username == null) {
			throw new AccountException(
					"Null usernames are not allowed by this realm.");
		}

		AuthenticationInfo info = null;
		try {
			MongoUtils m = MongoUtils.getMe();
			MongoCollection mc = m.getCollection(MongoUtils.Collection.USER);
			User user = mc.findOne(DEFAULT_AUTHENTICATION_QUERY, username).as(
					User.class);

			if (user == null) {
				throw new AccountException("User not founs.");
			}

			if (!user.getPass().equals(new String(upToken.getPassword()))) {
				throw new AccountException("Invalid user or password");
			}

			info = new SimpleAuthenticationInfo(username, user.getPass()
					.toCharArray(), getName());

		} catch (Exception e) {
			final String message = "There was a SQL error while authenticating user ["
					+ username + "]";
			throw new AuthenticationException(message, e);
		}

		return info;
	}
}
