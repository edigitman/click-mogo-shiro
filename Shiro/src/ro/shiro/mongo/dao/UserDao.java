package ro.shiro.mongo.dao;

import org.jongo.MongoCollection;

import ro.shiro.bean.UserBean;
import ro.shiro.mongo.MongoUtils;
import ro.shiro.mongo.MongoUtils.Collection;

public class UserDao {

	private MongoCollection userColl;

	public UserDao() {
		userColl = MongoUtils.getMe().getCollection(Collection.USER);
	}

	public UserBean getByName(String name) {
		return userColl.findOne("{name: #}", name).as(UserBean.class);
	}
	
	public void update(UserBean bean){
		userColl.save(bean);
	}

}
