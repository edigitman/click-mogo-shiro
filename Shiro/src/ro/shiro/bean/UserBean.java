package ro.shiro.bean;

import java.util.Set;

import org.bson.types.ObjectId;

public class UserBean {
	private ObjectId _id;
	private String name;
	private String pass;
	private String username;
	private String email;
	private boolean active;
	private String key;
	private Set<String> roles;
	private ObjectId car;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public ObjectId getCar() {
		return car;
	}

	public void setCar(ObjectId car) {
		this.car = car;
	}
}
