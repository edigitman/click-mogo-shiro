package ro.shiro.page;

import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class Index extends org.apache.click.Page {

	private static final long serialVersionUID = 1285577893064407025L;
	private Form f = new Form("form");

	public Index() {
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
		// 2. Get the current Subject:
		Subject currentUser = SecurityUtils.getSubject();

		// 3. Login:
		try {
			currentUser.login(token);
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return true;
	}

}