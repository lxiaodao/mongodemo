package nosql.mongodb;

import java.net.UnknownHostException;
import java.util.UUID;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoChe {
	private static DBCollection coll;
	private static DB mapche;
	private static String[] ROUTES={"重庆-遂宁-成都","成都—成南高速—渝隧高速—重庆","教育学院-人民商场-成温邛高速-绕城-府城大道-软件园附近",
		"内江－资阳－龙泉","成飞大道-外环-软件园","成乐高速","成飞大道-外环-软件园",
		"成都市区 及郊县市","绕城 南充 遂宁 重庆","东门 西门 北门 南门 郊县市"};
	public static void query(){
		// /.*m.*/
		DBObject query = new BasicDBObject();
        //条件查询  
        //query.put("visit", "100"); new BasicDBObject("$gt", 900).append("$lte", 920)
        //query.put("route","/.*软件园.*/");   
        query.put("route",  java.util.regex.Pattern.compile("^成都"));
        //query.put("route",  java.util.regex.Pattern.compile("软件园^"));
		query.put("link.phone", 95);
       
        //分页查询
        DBCursor cur = coll.find(query).limit(20);
        while (cur.hasNext())
        {
            System.out.println(cur.next());
           
        }
        
		
	}
	public static void main(String[] para){
	   
		try {
			MongoChe.init();
			//MongoChe.dataReady(para);
			MongoChe.query();
			
		} catch (MongoException e) {		
			e.printStackTrace();
		} catch (UnknownHostException e) {		
			e.printStackTrace();
		}
	}
	public static void dataReady(String[] para){

		DBObject info = new BasicDBObject();
		for(int i=0;i<10;i++){
        String uuid = UUID.randomUUID().toString();
        info.put("_id", uuid);
        info.put("type",i%2);
        info.put("route",ROUTES[i]);
        
        BasicDBObject link = new BasicDBObject();
        link.put("phone", 88+i);
        link.put("qq", "45784521"); 
        
        info.put("link", link);
        
        coll.insert(info);
		}
        
        //DBObject myDoc = coll.findOne();
       // System.out.println(myDoc);
	}
	public static void init() throws MongoException,
			UnknownHostException {

		// MongoOptions tions=new MongoOptions();
		// tions.connectionsPerHost=100;
		//System.setProperty("MONGO.POOLSIZE", "20000");
		Mongo mongo = new Mongo("localhost", 27017);
		/**
		 * resDB = mongo.etDB("resourceDB");//如果没有对应的数据库，数据库会为此创建一个
	     */
		mapche = mongo.getDB("mapche");
		coll = mapche.getCollection("infoCollection");
		
	}
}


