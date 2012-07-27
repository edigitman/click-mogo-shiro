package ro.shiro.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jongo.MongoCollection;

import ro.shiro.bean.UserBean;
import ro.shiro.mongo.MongoUtils;

public class CustomJdbcRealm extends JdbcRealm {

	protected static final String DEFAULT_AUTHENTICATION_QUERY = "{name: #, active: #}";

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
			UserBean user = mc
					.findOne(DEFAULT_AUTHENTICATION_QUERY, username, true).as(
							UserBean.class);

			if (user == null) {
				throw new AccountException("User not found.");
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

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		MongoCollection c = MongoUtils.getMe().getCollection(
				MongoUtils.Collection.USER);
		AuthorizationInfo ai = new SimpleAuthorizationInfo(c
				.findOne("{name: #}",
						principals.getPrimaryPrincipal().toString())
				.as(UserBean.class).getRoles());
		return ai;
	}
}
