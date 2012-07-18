package ro.shiro.page;

import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Label;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

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
	}

	public boolean onOK() {
		AuthenticationToken token = new UsernamePasswordToken(
				f.getFieldValue("name"), f.getFieldValue("pass"));
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			setRedirect(Main.class);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}