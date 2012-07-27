package ro.shiro.page.n;

import java.util.HashSet;
import java.util.Set;

import org.apache.click.control.ActionLink;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Label;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.EmailField;
import org.apache.click.extras.control.NumberField;
import org.apache.click.extras.control.TabbedForm;
import org.apache.shiro.SecurityUtils;
import org.bson.types.ObjectId;

import ro.shiro.bean.CarBean;
import ro.shiro.bean.UserBean;
import ro.shiro.custom.AppUtils;
import ro.shiro.custom.TitleSelect;
import ro.shiro.mongo.dao.CarDao;
import ro.shiro.mongo.dao.UserDao;
import ro.shiro.page.Login;
import ro.shiro.page.p.Main;

public class New extends org.apache.click.Page {

	private static final long serialVersionUID = -1676131568520632607L;

	private TabbedForm form = new TabbedForm("form");
	private TextField name = new TextField("name", true);
	private EmailField email = new EmailField("email", true);

	public New() {
		// --- Basic log info
		addControl(new ActionLink("logout", this, "onLogout"));
		addControl(new Label("username", SecurityUtils.getSubject()
				.getPrincipal().toString()));
		// --- Form config
		form.setBackgroundColor("#fb6");
		form.setTabHeight("210px");
		form.setTabWidth("420px");
		form.setErrorsPosition(Form.POSITION_TOP);

		// Contact tab sheet
		
		UserBean u = AppUtils.getUser();
		name.setValue(u.getName());
		name.setDisabled(true);
		email.setValue(u.getEmail());

		FieldSet contactTabSheet = new FieldSet("contactDetails");
		form.addTabSheet(contactTabSheet);
		contactTabSheet.add(new TitleSelect("title"));
		contactTabSheet.add(name);
		contactTabSheet.add(new TextField("firstName"));
		contactTabSheet.add(new TextField("middleNames"));
		contactTabSheet.add(email);

		FieldSet carTabSheet = new FieldSet("carDetails");
		form.addTabSheet(carTabSheet);
		carTabSheet.add(new TextField("number", true));
		carTabSheet.add(new TextField("make"));
		carTabSheet.add(new TextField("model"));
		carTabSheet.add(new TextField("color"));
		carTabSheet.add(new NumberField("year"));

		form.add(new Submit("Save", this, "onSave"));		

		addControl(form);
	}

	public boolean onSave() {
		CarDao cd = new CarDao();
		UserDao ud = new UserDao();
		UserBean u = ud.getByName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		CarBean cb = new CarBean();
		form.copyTo(cb);
		form.copyTo(u);

		ObjectId cid = cd.save(cb);
		u.setCar(cid);
		Set<String> roles = u.getRoles();
		if(roles==null){
			roles = new HashSet<String>();
		}
		roles.add("OWNER");
		u.setRoles(roles);
		ud.update(u);
		setRedirect(Main.class);
		return true;
	}

	public boolean onLogout() {
		SecurityUtils.getSubject().logout();
		setRedirect(Login.class);
		return false;
	}
}