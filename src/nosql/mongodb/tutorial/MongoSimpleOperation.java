package nosql.mongodb.tutorial;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

public class MongoSimpleOperation {
	static DBCollection deviceColl;
	static DB myDB;

	public static void main(String[] arr) {
		try {
			MongoSimpleOperation.initDB("129.42.13.125");
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// 插入多条数据
		//createMuti();
		//insertDatas();
        //更新
		//update();
		// 相当与跨对象查询
		//mutiQuery();
		// 分页查询
		//pagingQuery();
		// like查询
		likeQuery();
	}
    /**
     * 更新操作
     */
	public static void update() {
		// 21062ad8-174f-4d43-9d5b-40f1e5fe8f30
		DBObject id = new BasicDBObject("_id","21062ad8-174f-4d43-9d5b-40f1e5fe8f30");
		DBObject oldone = MongoSimpleOperation.deviceColl.findOne(id);
		oldone.put("name", "私有协议设备PPlive");
		WriteResult back = MongoSimpleOperation.deviceColl.save(oldone);
		//
		BasicDBObject query = new BasicDBObject();
		query.put("name", java.util.regex.Pattern.compile("PPlive"));
		DBCursor cur = MongoSimpleOperation.deviceColl.find(query);

		int num = 0;
		while (cur.hasNext()) {
			System.out.println(cur.next());
			num++;
		}
		System.out.println("------------query number of result-----------"
				+ num);

	}

	/**
	 * 插入多条数据
	 */
	public static void insertDatas() {
		for (int i = 0; i < 10000; i++) {

			DBObject device = new BasicDBObject();
			String uuid = UUID.randomUUID().toString();
			device.put("_id", uuid);
			device.put("name", "缓存设备vip" + i);
			device.put("ip", "129.45.13.24" + i);
			device.put("area", i);
			MongoSimpleOperation.deviceColl.save(device);
		}
	}
    /**
     * like查询
     */
	public static void likeQuery() {
		BasicDBObject query = new BasicDBObject();
		// ddf%    %uuy% %kkk
		query.put("name",  java.util.regex.Pattern.compile("vip2000"));
		DBCursor cur = MongoSimpleOperation.deviceColl.find(query);

		int num = 0;
		while (cur.hasNext()) {
			System.out.println(cur.next());
			num++;
		}
		System.out.println("------------query number of result-----------"
				+ num);
	}

	/**
	 * 分页查询
	 */
	public static void pagingQuery() {
		DBObject query = new BasicDBObject();

		query.put("area", new BasicDBObject("$gt", 20).append("$lte", 200));
		long current = System.currentTimeMillis();
		// 分页查询
		DBCursor cur = MongoSimpleOperation.deviceColl.find(query).sort(
				new BasicDBObject("area", 1)).limit(30).skip(10);

		System.out.println("------------query need time-----------"
				+ (System.currentTimeMillis() - current));
		int num = 0;
		while (cur.hasNext()) {
			System.out.println(cur.next());
			num++;
		}
		System.out.println("------------query number of result-----------"
				+ num);
	}

	/**
	 * 类似跨对象查询
	 */
	public static void mutiQuery() {

		BasicDBObject query = new BasicDBObject();
		query.put("systems.name", "CSS-BT");
		DBObject result = MongoSimpleOperation.deviceColl.findOne(query);
		System.out.println(result);

	}

	/**
	 * 创建关联数据
	 */
	public static void createMuti() {
		// 演示数据
		DBObject device = new BasicDBObject();
		String uuid = UUID.randomUUID().toString();
		device.put("_id", uuid);
		device.put("name", "缓存设备vip");
		device.put("ip", "129.45.13.242");
		device.put("area", 2);
	

		// 增加子系统信息
		DBObject sys1 = new BasicDBObject();

		sys1.put("_id", UUID.randomUUID().toString());
		sys1.put("name", "CSS-BT");
		sys1.put("upper_class", "CSS");
		sys1.put("type", 5);
		sys1.put("create_time", new Date());
		sys1.put("update_time", new Date());

		DBObject sys2 = new BasicDBObject();
		sys2.put("_id", UUID.randomUUID().toString());
		sys2.put("name", "CSS-HTTP");
		sys2.put("upper_class", "CSS");
		sys2.put("type", 5);
		sys2.put("create_time", new Date());
		sys2.put("update_time", new Date());

		DBObject[] array = new DBObject[] { sys1, sys2 };

		device.put("systems", array);

		MongoSimpleOperation.deviceColl.save(device);
	}

	public static void initDB(String host) throws MongoException,
			UnknownHostException {

		Mongo mongo = new Mongo(new DBAddress(host, 27017, "myDB"));

		// 如果没有对应的数据库，数据库会为此创建一个
		myDB = mongo.getDB("myDB");
		deviceColl = myDB.getCollection("deviceColl");

		// 创建索引
		// 统一collection，相同的字段索引只需要存在一次
		/*
		 * MongoIcacheTable.baseColl.createIndex(new BasicDBObject("deviceid",
		 * -1)); //升序1,-1降序
		 */

	}
}

class Device implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6330814394548209173L;
	private String name;
	private String ip;
	private int area;
	private Set systems = new HashSet(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

}

class SubSystem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3140369319630025020L;
	private String name;
	private String upperClass;
	private int type;
	private Date createTime;
	private Date updateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpperClass() {
		return upperClass;
	}

	public void setUpperClass(String upperClass) {
		this.upperClass = upperClass;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
