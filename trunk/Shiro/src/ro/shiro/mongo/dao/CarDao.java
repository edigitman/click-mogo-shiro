package ro.shiro.mongo.dao;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import ro.shiro.bean.CarBean;
import ro.shiro.mongo.MongoUtils;
import ro.shiro.mongo.MongoUtils.Collection;

public class CarDao {
	private MongoCollection carColl;

	public CarDao() {
		carColl = MongoUtils.getMe().getCollection(Collection.CAR);
	}
	
	public ObjectId save(CarBean bean){
		carColl.save(bean);
		return bean.get_id();
	}
	
	public CarBean getById(ObjectId id){
		return carColl.findOne(id).as(CarBean.class);
	}
	
}
