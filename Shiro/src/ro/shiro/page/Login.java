package ro.shiro.page;

import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.PageLink;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import ro.shiro.custom.AppUtils;
import ro.shiro.page.p.Main;

public class Login extends org.apache.click.Page {

	private static final long serialVersionUID = 1285577893064407025L;
	private Form f = new Form("form");

	public Login() {
		FieldSet fs = new FieldSet("Login");
		fs.add(new TextField("name", true));
		fs.add(new PasswordField("pass"));
		fs.add(new Submit("ok", this, "onOK"));
		f.add(fs);
		addControl(f);
		addControl(new PageLink("newAcc", "new account", NewAccout.class));
	}

	public boolean onOK() {
		AuthenticationToken token = new UsernamePasswordToken(
				f.getFieldValue("name"), f.getFieldValue("pass"));
		if (AppUtils.login(token)) {
			setRedirect(Main.class);
			return false;
		}
		return true;
	}

}