package ro.shiro.page;

import org.apache.click.control.PageLink;

public class NewAccountDone extends org.apache.click.Page {

	private static final long serialVersionUID = -9107754785299126220L;

	public NewAccountDone() {
		addControl(new PageLink("login", Login.class));
	}

}