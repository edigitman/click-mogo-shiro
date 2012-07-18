package ro.shiro.bean;

import org.bson.types.ObjectId;

public class Role {
	ObjectId _id;
	private String name;

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

}
