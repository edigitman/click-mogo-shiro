package ro.shiro.page;

import ro.shiro.bean.User;
import ro.shiro.custom.AppUtils;
import ro.shiro.custom.CallbackEvent;
import ro.shiro.custom.NewAccountForm;

public class NewAccout extends org.apache.click.Page {

	private static final long serialVersionUID = 7652031964187105226L;

	private NewAccountForm f = new NewAccountForm("form");

	public NewAccout() {
		f.setEvent(new CallbackEvent() {
			@Override
			public Object action() {
				return newAccount();
			}
		});
		addControl(f);
	}

	private boolean newAccount() {
		if (f.isValid()) {
			String cap = (String) getContext().getSessionAttribute("captcha");
			if (cap.equalsIgnoreCase(f.getFieldValue(NewAccountForm.CAPINPUT))) {
				User u = new User();
				u.setActive(false);
				u.setName(f.getFieldValue(NewAccountForm.NAME));
				u.setEmail(f.getFieldValue(NewAccountForm.EMAIL));
				u.setPass(f.getFieldValue(NewAccountForm.PASS));
				try {
					AppUtils.registerUser(u, getContext().getRequest().getRequestURL().toString());
				} catch (Exception e) {
					f.setError(e.getMessage());
					return false;
				}
			} else {
				f.setError("Are you human ? ");
				return false;
			}
		}
		setForward(NewAccountDone.class);
		return true;
	}
}