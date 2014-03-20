package nosql.mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoSearch
{
    public static DBCollection coll = null;
    
    /** 
     * <一句话功能简述>
     * <功能详细描述>
     *
     * @param args [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        Mongo m = null;
        String host="129.42.4.19";
       // String host="129.42.4.19";
        try
        {
            m = new Mongo(host, 27017);
      
            DB db = m.getDB("resourceDB");//如果没有对应的users数据库，数据库会为此创建一个  
            //增加一个用户,密码需转换成字符数据  
            //db.addUser("mongo", "123456".toCharArray());
            //db.authenticate("mongo", "123456".toCharArray());
            //System.out.println(db.authenticate("mongo", "123456".toCharArray()));
            
            //如果没有对应的userCollection，数据库会创建一个  
            
            coll = db.getCollection("resourceCollection");
            
            System.out.println("------------number of data-----------"+ coll.getCount());
            
            //for(int i=0;i<20;i++)
            //queryData();
           //simpleQuery();
           // dateQuery();
           // actulQuery();
            
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MongoException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    public static void dateQuery(){
        DBObject query = new BasicDBObject();
        //条件查询  
        long begin= MongoIcacheTable.parseDate("2010-01-01 11:18:18").getTime();
        long end= MongoIcacheTable.parseDate("2011-12-01 11:18:18").getTime();
        
        query.put("create_date", new BasicDBObject("$gt", begin).append("$lte",end));   
       
        
        //分页查询
        long time1=System.currentTimeMillis();
        DBCursor cur = MongoSearch.coll.find(query).limit(40).skip(1);
        System.out.println("------------time need-----------"+(System.currentTimeMillis()-time1));
       
        /*int num = 0;
        while (cur.hasNext())
        {
            System.out.println(cur.next());
            num++;
        }*/
       
    }
    public static void simpleQuery(){
        DBObject query = new BasicDBObject();
        //条件查询  
        //query.put("visit", "100"); new BasicDBObject("$gt", 900).append("$lte", 920)
        query.put("out", new BasicDBObject("$gt", 1).append("$lte",50));   
        long current = System.currentTimeMillis();
        //分页查询
        DBCursor cur = MongoSearch.coll.find(query).limit(40).skip(0);
        
        System.out.println("------------query need time-----------"
                + (System.currentTimeMillis() - current));
        int num = 0;
        while (cur.hasNext())
        {
            System.out.println(cur.next());
            num++;
        }
        System.out.println("------------query number of result-----------"
                + num);
    }
    public static void actulQuery(){
        
        DBObject query = new BasicDBObject();
        //条件查询  
        //query.put("visit", "100"); new BasicDBObject("$gt", 900).append("$lte", 920)
        query.put("file_name","beKEY-Setup-Full.zip");   
        long current = System.currentTimeMillis();
        //分页查询
        DBCursor cur = MongoSearch.coll.find(query).limit(20).skip(100000);
        
        System.out.println("------------actulQuery need time-----------"
                + (System.currentTimeMillis() - current));
      
    }
    
    public static void queryData()
    {
        for (int i = 0; i < 30; i++)
        {
            new MongoSearchThread().start();
        }
      
    }
    
}

class MongoSearchThread extends Thread{
    
    @Override
    public void run(){
        
        for (int i = 0; i < 1000; i++)
        {
            DBObject query = new BasicDBObject();
            //条件查询  
            //query.put("visit", "100"); new BasicDBObject("$gt", 900).append("$lte", 920)
            query.put("out", new BasicDBObject("$gt", 1).append("$lte",50));   
            long current = System.currentTimeMillis();
            //分页查询
            DBCursor cur = MongoSearch.coll.find(query).limit(40).skip(10);
            
            System.out.println("------------query need time-----------"
                    + (System.currentTimeMillis() - current));
            int num = 0;
            while (cur.hasNext())
            {
                System.out.println(cur.next());
                num++;
            }
            System.out.println("------------query number of result-----------"
                    + num);
        }
    }
}
