package nosql.mongodb;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

public class MongoIcacheTable
{
    
    static String host = "129.42.13.171";
    
    
    static DBCollection bt_resource;
    static DBCollection day_visit;
    static DBCollection device;
    static DBCollection http_resource;
    static DBCollection mss_device_flux_statistics;
    static DBCollection mss_device_statistics;
    static DBCollection redirect_version;
    static DBCollection subsystem;
    
    static DBCollection torrent_resource;
    static DBCollection total_visit;
    
    
    static  DB webnms;
   
    public static void reflect()
    {
        Method method;
        try
        {
            method = MongoIcacheTable.class.getDeclaredMethod("simpleQuery");
            method.invoke("simpleQuery");
        }
        catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
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
        // TODO Auto-generated method stub
        
        try
        {
            
            MongoIcacheTable.initDB(host);
            
            //MongoIcacheTable.httpResourceData();
            MongoIcacheTable.reflect();
            //MongoIcacheTable.simpleQuery();
            
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
        
    }
    
    public static void initDB(String host) throws MongoException,
            UnknownHostException
    {
        
        //MongoOptions tions=new MongoOptions();
        //tions.connectionsPerHost=100;
        System.setProperty("MONGO.POOLSIZE", "20000");
        Mongo mongo = new Mongo(new DBAddress(host, 27017, "WebNmsDB"));
       /** 
        resDB = mongo.etDB("resourceDB");//如果没有对应的数据库，数据库会为此创建一个  
       
        visitDB = mongo.getDB("visitDB");
        statisDB = mongo.getDB("statisDB");
        redirectDB = mongo.getDB("redirectDB");
        
        baseDB = mongo.getDB("baseDB");
        */
        
        webnms= mongo.getDB("WebNmsDB");
        
        /*bt_resource
        day_visit
        device
        http_resource
        mss_device_flux_statistics
        mss_device_statistics
        redirect_version
        subsystem
       
        torrent_resource
        total_visit*/
        bt_resource=webnms.getCollection("bt_resource");
        day_visit=webnms.getCollection("day_visit");
        device=webnms.getCollection("device");
        http_resource=webnms.getCollection("http_resource");
        mss_device_flux_statistics=webnms.getCollection("mss_device_flux_statistics");
        mss_device_statistics=webnms.getCollection("mss_device_statistics");
        redirect_version=webnms.getCollection("redirect_version");
        subsystem=webnms.getCollection("subsystem");
        
        torrent_resource=webnms.getCollection("torrent_resource");
        total_visit=webnms.getCollection("total_visit");
        
        
        //创建索引
        //统一collection，相同的字段索引只需要存在一次
        /*
        MongoIcacheTable.baseColl.createIndex(new BasicDBObject("deviceid", -1)); //升序1,-1降序            
        MongoIcacheTable.baseColl.createIndex(new BasicDBObject("areaid", -1));
        MongoIcacheTable.baseColl.createIndex(new BasicDBObject(
                "validate_code", -1));
        
        MongoIcacheTable.baseColl.createIndex(new BasicDBObject("systemid", -1)); //升序1,-1降序            
        //MongoIcacheTable.baseColl.createIndex(new BasicDBObject("areaid",
        //       -1));
        MongoIcacheTable.baseColl.createIndex(new BasicDBObject("deviceid", -1));
        
        MongoIcacheTable.dayVisitColl.createIndex(new BasicDBObject(
                "create_date", -1)); //升序1,-1降序            
        MongoIcacheTable.dayVisitColl.createIndex(new BasicDBObject("areaid",
                -1));
        MongoIcacheTable.dayVisitColl.createIndex(new BasicDBObject("uri", -1));
        MongoIcacheTable.dayVisitColl.createIndex(new BasicDBObject("id", -1));
        
        //MongoIcacheTable.totalVisitColl.createIndex(new BasicDBObject(
        //        "id", -1)); //升序1,-1降序            
        //MongoIcacheTable.totalVisitColl.createIndex(new BasicDBObject("areaid",
        //       -1));
        //MongoIcacheTable.totalVisitColl.createIndex(new BasicDBObject("uri", -1));
        MongoIcacheTable.totalVisitColl.createIndex(new BasicDBObject("type",
                -1));
        MongoIcacheTable.totalVisitColl.createIndex(new BasicDBObject("state",
                -1));
        
        MongoIcacheTable.resColl.createIndex(new BasicDBObject("areaid", -1)); //升序1,-1降序    
        MongoIcacheTable.resColl.createIndex(new BasicDBObject("systemid", -1));
        MongoIcacheTable.resColl.createIndex(new BasicDBObject(
                "contribute_flow", -1));
        MongoIcacheTable.resColl.createIndex(new BasicDBObject("contributory",
                -1));
        MongoIcacheTable.resColl.createIndex(new BasicDBObject("resourceid", -1));
        //MongoIcacheTable.resColl.createIndex(new BasicDBObject("areaid", -1)); //升序1,-1降序    
        //MongoIcacheTable.resColl.createIndex(new BasicDBObject("systemid",
        //        -1));
        //MongoIcacheTable.resColl.createIndex(new BasicDBObject(
          //       "contribute_flow", -1));
         //MongoIcacheTable.resColl.createIndex(new BasicDBObject(
          //       "contributory", -1));
         //MongoIcacheTable.resColl.createIndex(new BasicDBObject(
           //      "resourceid", -1));
        redirectColl.createIndex(new BasicDBObject("systemid", -1)); //升序1,-1降序            
        redirectColl.createIndex(new BasicDBObject("version", -1));
        redirectColl.createIndex(new BasicDBObject("uri", -1));
        redirectColl.createIndex(new BasicDBObject("taskid", -1));
        
        statisColl.createIndex(new BasicDBObject("id", -1)); //升序1,-1降序            
        statisColl.createIndex(new BasicDBObject("device_id", -1));
        statisColl.createIndex(new BasicDBObject("eth_ip", -1));
        statisColl.createIndex(new BasicDBObject("type", -1));
        
        statisColl.createIndex(new BasicDBObject("area_id", -1));
        
        */
       
    }
    
    
    
    public static void buildData()
    {
        MongoIcacheTable.deviceData();
        
        MongoIcacheTable.subsystemData();
        
        MongoIcacheTable.dayVisitData();
        MongoIcacheTable.totalVisitData();
        
        MongoIcacheTable.httpResourceData();
        MongoIcacheTable.btResourceData();
        MongoIcacheTable.torrentResourceData();
        
        MongoIcacheTable.redirectVersionData();
        MongoIcacheTable.deviceStatisData();
        MongoIcacheTable.fluxData();
        
    }
    
    public static void simpleQuery()
    {
        DBObject query = new BasicDBObject();
        //条件查询  
        //query.put("visit", "100"); new BasicDBObject("$gt", 900).append("$lte", 920)
        query.put("contribute_flow", new BasicDBObject("$gt", 0).append("$lt",10));
        long current = System.currentTimeMillis();
        //分页查询
        DBCursor cur = MongoIcacheTable.http_resource.find(query).limit(40).skip(0);
        
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
    
    public static void deviceData()
    {
        //INSERT INTO `device` (`deviceid`,`alias`,`create_time`,`description`,`device_code`,`device_ip`,`state`,`update_time`,`validate_code`,`areaid`,`type`) 
        //VALUES         (1,'Master','2011-01-06 00:00:00',NULL,NULL,'129.42.13.126',2,NULL,'210235G317Z0A5000040',1,NULL);
        DBObject resources = new BasicDBObject();
        String uuid = UUID.randomUUID().toString();
        resources.put("_id", uuid);
        resources.put("deviceid", uuid);
        resources.put("alias", "Master");
        resources.put("create_date", new Date().getTime());
        resources.put("description", null);
        resources.put("device_code", null);
        resources.put("device_ip", "129.42.13.126");
        resources.put("state", 2);
        resources.put("update_time", null);
        resources.put("validate_code", "210235G317Z0A5000040");
        resources.put("areaid", 1);
        resources.put("type", null);
        
        MongoIcacheTable.device.insert(resources);
    }
    
    public static void subsystemData()
    {
        //INSERT INTO `subsystem` (`systemid`,`counter`,`create_time`,`monitor_time`,
        //`mss_status`,`name`,`number`,`portal`,
        //`server_type`,`state`,`update_time`,`upper_class`,`verification_code`,`version`,`areaid`,`deviceid`) VALUES 
        //(1,0,'2011-01-06 10:00:01',NULL,2,'DSS',0,NULL,
        //4,-1,'2011-01-09 10:00:01','DSS','210235G317Z0A5000040',NULL,1,1);
        
        DBObject resources = new BasicDBObject();
        String uuid = UUID.randomUUID().toString();
        resources.put("_id", uuid);
        resources.put("systemid", uuid);
        resources.put("counter", 0);
        resources.put("create_time", new Date().getTime());
        resources.put("monitor_time", null);
        
        resources.put("mss_status", 2);
        resources.put("name", "DSS");
        resources.put("number", 0);
        resources.put("portal", null);
        
        resources.put("server_type", 4);
        resources.put("state", -1);
        resources.put("update_time", new Date().getTime());
        resources.put("upper_class", "DSS");
        resources.put("verification_code", "210235G317Z0A5000040");
        resources.put("version", null);
        resources.put("areaid", 1);
        resources.put("deviceid", 1);
        
        MongoIcacheTable.subsystem.insert(resources);
    }
    
    public static void dayVisitData()
    {
        //`id` bigint(20) NOT NULL AUTO_INCREMENT,
        //`areaid` bigint(20) NOT NULL,
        //`create_date` varchar(10) NOT NULL,
        //`type` int(10) unsigned DEFAULT NULL,
        //`uri` char(50) NOT NULL,
        //`visit_count` int(11) NOT NULL,
        //
        //INSERT INTO `day_visit` VALUES (1344447,4,'2010-12-31',1,'ab4256b24c2e8d74aaf1e77ea6176b4c',3);
        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("id", uuid);
            resources.put("areaid", 4);
            resources.put("create_date", new Date().getTime());
            resources.put("type", "beKEY-Setup-Full.zip");
            resources.put("uri", "ab4256b24c2e8d74aaf1e77ea6176b4c");
            resources.put("visit_count", 3);
            
            MongoIcacheTable.day_visit.insert(resources);
        }
        MongoIcacheTable.webnms.requestDone();
        
    }
    
    public static void totalVisitData()
    {
        /*`id` bigint(20) NOT NULL AUTO_INCREMENT,
        `areaid` bigint(20) NOT NULL,
        `create_date` varchar(10) DEFAULT NULL,
        `hot_point` int(11) NOT NULL,
        `location` varchar(500) DEFAULT NULL,
        `state` tinyint(3) NOT NULL,
        `type` int(10) unsigned NOT NULL,
        `uri` char(50) NOT NULL,
        `visit_count` int(11) NOT NULL,*/
        //INSERT INTO `total_visit` VALUES (431677,4,'2010-12-31',2,'http://129.42.14.241/http/50M.dat',2,1,'ab4256b24c2e8d74aaf1e77ea6176b4c',3);
     
            
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("id", uuid);
            resources.put("areaid", 4);
            resources.put("create_date", new Date().getTime());
            resources.put("hot_point", 2);
            resources.put("location", "http://129.42.14.241/http/50M.dat");
            
            resources.put("state", 2);
            resources.put("type", 1);
            resources.put("uri", "ab4256b24c2e8d74aaf1e77ea6176b4c");
            resources.put("visit_count", 3);
            long begin = System.currentTimeMillis();
            try
            {
            	
            MongoIcacheTable.total_visit.insert(resources);
            writeToCSV(true + "," + (System.currentTimeMillis() - begin));
            }
            catch (MongoException ex)
            {
            	writeToCSV(false + "," + (System.currentTimeMillis() - begin));
                
            }
        
    }
    final static byte[] lcok=new byte[0];
    public static void writeToCSV(String data) {


        synchronized(lcok){
          
        	System.out.println(data);
        	
        }
		

	}
    
    /**
     *http resource构造数据
     * <一句话功能简述>
     * <功能详细描述>
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void httpResourceData()
    {
        
        //
        // INSERT INTO `http_resource` (`create_date`,`download_percent`,`download_times`,`file_name`,
        //`file_size`,`areaid`,`systemid`,`resourceid`,`state`,`cache_location`,
        //`resource_hash`,`uri`,`contribute_flow`,`contributory`,`type`,`outter_url`,`update_date`) VALUES 
        //('2010-08-26 11:18:18',NULL,NULL,'beKEY-Setup-Full.zip',
        //31924576,3,6,653,0,'/70/70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0/beKEY-Setup-Full.zip',
        //'70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0','cbe40ef0b9bb2284c496128ddc0bcc08',NULL,NULL,1,'http://mdj.newhua.com/down/beKEY-Setup-Full.zip','2010-08-26 11:18:18');
        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            DBObject resources = new BasicDBObject();
            resources.put("create_date", new Date().getTime());
            resources.put("download_percent", 100);
            resources.put("download_times", 1);
            resources.put("file_name", "beKEY-Setup-Full.zip");
            
            resources.put("file_size", 31924576);
            resources.put("areaid", 3);
            resources.put("systemid", 6);
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("resourceid", uuid);
            resources.put("state", 0);
            resources.put("cache_location",
                    "/70/70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0/beKEY-Setup-Full.zip");
            
            resources.put("resource_hash",
                    "70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0");
            resources.put("uri", "cbe40ef0b9bb2284c496128ddc0bcc08");
            resources.put("contribute_flow", 1);
            resources.put("contributory", 10);
            resources.put("type", 1);
            resources.put("outter_url",
                    "http://mdj.newhua.com/down/beKEY-Setup-Full.zip");
            resources.put("update_date",
                    parseDate("2010-08-26 11:18:18").getTime());
            
            MongoIcacheTable.http_resource.insert(resources);
        }
        MongoIcacheTable.webnms.requestDone();
        
    }
    
    /**
     *bt resource构造数据
     * <一句话功能简述>
     * <功能详细描述>
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void btResourceData()
    {
        
        /* INSERT INTO `bt_resource` (`resourceid`,`create_date`,`download_percent`,`download_times`,
         `file_name`,`file_size`,`resource_hash`,`state`,
         `type`,`uri`,`cache_location`,`outter_url`,
         `resource_peer`,`resource_port`,`areaid`,`systemid`,
         `contribute_flow`,`contributory`,`update_date`) VALUES 
         (550,'2010-08-31 10:01:00',NULL,NULL, 
         '2m3.exe',2097152,'86BBCE3F014CEBE364E4649A7B6408D805E825DB',0,
         6,'86BBCE3F014CEBE364E4649A7B6408D805E825DB','-LT0F00-zj6!hdWsF.40',
                 'http://pt.shlady.com/announce.php?pid=eaf7fc06b984d385572f13f7edb259c6&amp;no_peer_id=1',
                 NULL,NULL,6,24,
                 NULL,NULL,'2010-08-31 10:01:00');*/
        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("resourceid", uuid);
            resources.put("create_date", new Date().getTime());
            resources.put("download_percent", 100);
            resources.put("download_times", 1);
            
            resources.put("file_name", "2m3.exe");
            resources.put("file_size", 31924576);
            resources.put("resource_hash",
                    "70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0");
            resources.put("state", 0);
            
            resources.put("type", 6);
            resources.put("uri", "86BBCE3F014CEBE364E4649A7B6408D805E825DB");
            resources.put("cache_location", "-LT0F00-zj6!hdWsF.40");
            resources.put("outter_url",
                    "http://pt.shlady.com/announce.php?pid=eaf7fc06b984d385572f13f7edb259c6&amp;no_peer_id=1");
            
            resources.put("resource_peer", null);
            resources.put("resource_port", null);
            resources.put("areaid", 6);
            resources.put("systemid", 24);
            
            resources.put("contribute_flow", 1);
            resources.put("contributory", 10);
            
            resources.put("update_date",
                    parseDate("2010-08-31 10:01:00").getTime());
            
            MongoIcacheTable.bt_resource.insert(resources);
        }
        MongoIcacheTable.webnms.requestDone();
        
    }
    
    /**
    *bt resource构造数据
    * <一句话功能简述>
    * <功能详细描述>
    * [参数说明]
    * @return void [返回类型说明]
    * @see [类、类#方法、类#成员]
    */
    public static void torrentResourceData()
    {
        
        /*  INSERT INTO `torrent_resource` (`resourceid`,`contribute_flow`,`contributory`,`create_date`,
         * `download_percent`,`download_times`,`file_name`,`file_size`,
         * `resource_hash`,`state`,`type`,`uri`,
         * `cache_location`,`outter_url`,`resource_infohash`,`areaid`,
         * `systemid`,`update_date`) VALUES 
            (530,NULL,NULL,'2010-08-26 14:10:06',
            NULL,NULL,'',885125120,
            '67F12F1319F403F4CC6EFD4779A17287F03B13B2',0,3,'1f9e128a188a804af72a04a5ae35c6aa',
            '/5E/5EC9A30761358B807C15F907E4659443BA70B1CD/67F12F1319F403F4CC6EFD4779A17287F03B13B2.torrent',NULL,'67F12F1319F403F4CC6EFD4779A17287F03B13B2',3,
            9,'2010-08-26 14:10:06'),
        */
        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("resourceid", uuid);
            resources.put("contribute_flow", 1);
            resources.put("contributory", null);
            resources.put("create_date", new Date().getTime());
            
            resources.put("download_percent", null);
            resources.put("download_times", null);
            resources.put("file_name", "");
            resources.put("file_size", 885125120);
            
            resources.put("resource_hash",
                    "67F12F1319F403F4CC6EFD4779A17287F03B13B2");
            resources.put("state", 0);
            resources.put("type", 3);
            resources.put("uri", "1f9e128a188a804af72a04a5ae35c6aa");
            
            resources.put("cache_location",
                    "/5E/5EC9A30761358B807C15F907E4659443BA70B1CD/67F12F1319F403F4CC6EFD4779A17287F03B13B2.torrent");
            resources.put("outter_url", null);
            resources.put("resource_infohash",
                    "67F12F1319F403F4CC6EFD4779A17287F03B13B2");
            
            resources.put("areaid", 3);
            
            resources.put("systemid", 9);
            resources.put("update_date",
                    parseDate("2010-08-31 10:01:00").getTime());
            
            MongoIcacheTable.torrent_resource.insert(resources);
        }
        MongoIcacheTable.webnms.requestDone();
        
    }
    
    /**
     * 
     *重定向数据
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void redirectVersionData()
    {
        /*INSERT INTO `redirect_version` (`taskid`,`create_date`,`state`,`operate_type`,
         * `protocol_type`,`systemid`,`uri`,`version`) VALUES 
        (1,NULL,2,3,
        1,2,'cbe40ef0b9bb2284c496128ddc0bcc08',1);*/
        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("taskid", uuid);
            resources.put("create_date", new Date().getTime());
            resources.put("state", 2);
            resources.put("operate_type", 3);
            
            resources.put("protocol_type", 1);
            resources.put("systemid", 2);
            resources.put("uri", "cbe40ef0b9bb2284c496128ddc0bcc08");
            resources.put("version", 1);
            
            MongoIcacheTable.redirect_version.insert(resources);
        }
        MongoIcacheTable.webnms.requestDone();
    }
    
    /**
     * 
     * 设备性能统计数据
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void deviceStatisData()
    {
        /*INSERT INTO `mss_device_statistics` (`id`,`sequence`,`device_id`,`area_id`,
         * `type`,`create_date`,`flowin`,`flowout`,
         * `total_disk`,`used_disk`,`cpu_percent`,`total_memory`,
         * `used_memory`,`total_link`,`total_disk_data`,`total_disk_system`,
         * `used_disk_data`,`used_disk_system`) VALUES 
        (1,NULL,1,1,
        '4 ','2010-11-22 14:10:00',0,0
        ,23,6.3,25,1538744,
        200344,NULL,NULL,NULL,
        NULL,NULL);*/

        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("id", uuid);
            resources.put("sequence", null);
            resources.put("device_id", 1);
            resources.put("area_id", 1);
            
            resources.put("type", 1);
            resources.put("create_date", new Date().getTime());
            resources.put("flowin", 0);
            resources.put("flowout", 0);
            
            resources.put("total_disk", 23);
            resources.put("used_disk", 6.3);
            resources.put("cpu_percent", 25);
            resources.put("total_memory", 1538744);
            
            resources.put("used_memory", 200344);
            resources.put("total_link", null);
            resources.put("total_disk_data", null);
            resources.put("total_disk_system", null);
            
            resources.put("used_disk_data", null);
            resources.put("used_disk_system", null);
            
            MongoIcacheTable.mss_device_statistics.insert(resources);
        }
        MongoIcacheTable.webnms.requestDone();
    }
    
    /**
     * 
     *协议流量数据
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    
    public static void fluxData()
    {
        /*INSERT INTO `mss_device_flux_statistics` (`id`,`device_id`,`eth_ip`,`area_id`,
         * `type`,`create_date`,`flowin`,`flowout`) VALUES 
        (1,2,'129.42.1.3',3,
        '8001900003','2010-11-25 14:13:00',0,0);*/

        MongoIcacheTable.webnms.requestStart();
        for (int i = 0; i < 1000; i++)
        {
            
            DBObject resources = new BasicDBObject();
            String uuid = UUID.randomUUID().toString();
            resources.put("_id", uuid);
            resources.put("id", uuid);
            
            resources.put("device_id", 2);
            resources.put("eth_ip", "129.42.1.3");
            resources.put("area_id", 3);
            
            resources.put("type", "8001900003");
            resources.put("create_date", new Date().getTime());
            resources.put("flowin", 0);
            resources.put("flowout", 0);
            
            MongoIcacheTable.mss_device_flux_statistics.insert(resources);
        }
        
        MongoIcacheTable.webnms.requestDone();
        
    }
    
    public static Date parseDate(String str)
    {
        
        if (str == null)
        {
            return null;
        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            
            return dateFormat.parse(str);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return new Date();
        }
        
    }
    
}
