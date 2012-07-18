package ro.shiro.page.p;

import org.apache.click.control.ActionLink;
import org.apache.click.control.Label;
import org.apache.shiro.SecurityUtils;

import ro.shiro.page.Login;

public class Main extends org.apache.click.Page {

	private static final long serialVersionUID = 6942872232397531271L;

	public Main() {
		addControl(new Label("name", SecurityUtils.getSubject().toString()));
		addControl(new ActionLink("logout", this, "onLogout"));
	}

	public boolean onLogout() {
		SecurityUtils.getSubject().logout();
		setRedirect(Login.class);
		return false;
	}

}