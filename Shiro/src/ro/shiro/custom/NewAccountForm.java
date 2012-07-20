package ro.shiro.custom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

public class NewAccountForm extends Form {

	private static final long serialVersionUID = 1L;

	private CallbackEvent event;
	private CaptchaField captcha;
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PASS = "pass";
	public static final String PASSC = "passc";
	
	public NewAccountForm() {
		super();
	}

	public NewAccountForm(String name) {
		super(name);
		init();
	}

	private void init() {
		captcha = new CaptchaField();
		FieldSet fs = new FieldSet("New Account");
		fs.add(new TextField(NAME, true));
		fs.add(new TextField(EMAIL, true));
		fs.add(new PasswordField(PASS));
		fs.add(new PasswordField(PASSC));
		fs.add(new Submit("OK", this, "onSubmit"));
		fs.add(captcha);
		add(fs);
	}

	public boolean onSubmit() throws Exception {
		if (event == null) {
			throw new Exception("Login action not set");
		}
		Object r = event.action();
		if (!(r instanceof Boolean)) {
			throw new Exception("Result is not as expected");
		}
		return (Boolean) r;
	}

	@Override
	public void validate() {
		super.validate();
		String password = getFieldValue(PASS);
		String confirmPassword = getFieldValue(PASSC);
		if (!password.equals(confirmPassword)) {
			setError("The passwords do not match.");
		}

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(getFieldValue("email"));
		boolean matchFound = m.matches();

		if (!matchFound) {
			setError("Email not valid");
		}		
		
	}

	public void setEvent(CallbackEvent event) {
		this.event = event;
	}
}
