package ro.shiro.custom;

import org.apache.click.control.AbstractControl;
import org.apache.click.control.TextField;

public class CaptchaField extends AbstractControl {

	private static final long serialVersionUID = 5294307399381964353L;
	private String value;
	private TextField input;

	public CaptchaField() {
		setName("captcha");
		input = new TextField("rezult");
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getTag() {
		// Return the HTML tag
		return "br/><i>To verify that you are human</i><br/>"
				+ "<table><tr><td>[ 2 + 6 ]</td><td> " + input.toString()
				+ " </td></tr></table";
	}

	public boolean onProcess() {
		// Bind the request parameter to the field value
		System.out.println(input.getValue());
		
		// Invoke any listener of MyField
		return true;
	}

}
