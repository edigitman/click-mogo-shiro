package ro.shiro.page;

import org.apache.click.control.ActionLink;

public class Border extends org.apache.click.Page {

	private static final long serialVersionUID = -8107017896043626591L;

	public Border() {
		addControl(new ActionLink("logout", this, "onLogout"));
	}

	@Override
	public String getTemplate() {
		return "/borderPage.htm";
	}

}