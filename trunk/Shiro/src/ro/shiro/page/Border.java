package ro.shiro.page;

import org.apache.click.control.ActionLink;
import org.apache.click.control.Label;
import org.apache.shiro.SecurityUtils;

public class Border extends org.apache.click.Page {

	private static final long serialVersionUID = -8107017896043626591L;

	public Border() {
		addControl(new ActionLink("logout", this, "onLogout"));
		addControl(new Label("username", SecurityUtils.getSubject()
				.getPrincipal().toString()));
	}

	@Override
	public String getTemplate() {
		return "/borderPage.htm";
	}

	/**
	 * Logs out the user from security shiro and redirects to login
	 * 
	 * @return false
	 */
	public boolean onLogout() {
		SecurityUtils.getSubject().logout();
		setRedirect(Login.class);
		return false;
	}
}