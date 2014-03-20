package nosql.mongodb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

public class MongoDemo
{
    
    public static String methodName = "httpResourceData";
    
    public static int loopNum = 10000;
    
    public static int threadNum = 1;
    
    public static Method methodRun = null;
    
    static DBCollection coll;
    
    public static void main(String[] arr)
    {
        //String host="129.42.13.125";
        //String host = "129.42.4.19";
        String host = "129.42.13.171";
       
        
        if (arr.length>0)
        {
            if (arr[0] != null && !"".equals(arr[0]))
            {
                methodName = arr[0];//方法名
            }
            
           
            if (arr[1] != null && !"".equals(arr[1]))
            {
                threadNum = Integer.valueOf(arr[1]);//线程数
            }
            if (arr[2] != null && !"".equals(arr[2]))
            {
                loopNum = Integer.valueOf(arr[2]);//循环次数
            }
            
            if (arr.length>3&&arr[3] != null && !"".equals(arr[3]))
            {
                host = arr[3];//host ip
            }
        }
        System.out.println("------------host ip-----------:"+host);
        try
        {
            MongoIcacheTable.initDB(host);
        }
        catch (MongoException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //MongoDemo.buildData();
        System.out.println("------------method name-----------:"+methodName);
        System.out.println("------------thread number-----------:"+threadNum);
        System.out.println("------------loop number-----------:"+loopNum);
        
        System.out.println("------------insert record number(not include device ,subsystem)-----------:"+(threadNum*loopNum*1000));
        
        try
        {
            methodRun = MongoIcacheTable.class.getDeclaredMethod(methodName);
            
        }
        catch (IllegalArgumentException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try
        {
            for (int i = 0; i < threadNum; i++)
            {
                new MongoThread().start();
               
            }
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
   
    public static void buildLittleData()
    {
      
        for (int i = 0; i < 10; i++)
        {
          
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("resourceid", uuid);
            resources.put("http", "http://sohu.com/pic?id=" + i);
            resources.put("bt", "bhjgk14fjkeikeldoll");
            resources.put("out", i);
            if (i % 2 == 0)
                resources.put("create_date",
                        MongoIcacheTable.parseDate("2010-08-26 11:18:18")
                                .getTime());
            else
                resources.put("create_date", new Date().getTime());
            
            MongoDemo.coll.createIndex(new BasicDBObject("out", -1)); //鍗囧簭1,-1闄嶅簭
            MongoDemo.coll.createIndex(new BasicDBObject("resourceid", -1)); //鍗囧簭1,-1闄嶅簭
            long begin = System.currentTimeMillis();
            MongoDemo.coll.insert(resources);
           
            writeToCSV(true + "," + (System.currentTimeMillis() - begin));
        }
       
    }
    
    final static byte[] lcok=new byte[0];
    public static void writeToCSV(String data) {


        synchronized(lcok){
          
        	System.out.println(data);
        	
        }
		

	}
    
   
    
    public static DBObject findDBObject(String id)
    {
        ObjectId oid = new ObjectId(id);
        DBObject query = new BasicDBObject("_id", oid);
        return query;
    }
}

class MongoThread extends Thread
{
    
    @Override
    public void run()
    {
        for (int i = 0; i < MongoDemo.loopNum; i++)
        {
            
            try
            {  
                
                MongoDemo.methodRun.invoke(MongoDemo.methodRun);
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
}
