package ro.shiro.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoUtils {

	private Mongo mongo = null;
	private Jongo jongo = null;
	private static MongoUtils instance = null;
	private Properties properties;

	private MongoUtils() {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("mongo.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MongoUtils getMe() {
		if (instance == null) {
			instance = new MongoUtils();
		}
		return instance;
	}

	private Mongo getMongo() {
		if (mongo == null) {
			try {
				mongo = new Mongo(properties.getProperty("host"),
						Integer.parseInt(properties.getProperty("port")));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}
		}
		return mongo;
	}

	public MongoCollection getCollection(Collection coll) {
		if (jongo == null) {
			jongo = new Jongo(getMongo().getDB(
					properties.getProperty("db_name")));
		}
		return jongo.getCollection(coll.name());
	}

	public static enum Collection {
		USER, ROLE, USER_ROLE
	}
}
