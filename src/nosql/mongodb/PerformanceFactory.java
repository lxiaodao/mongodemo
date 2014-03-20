package nosql.mongodb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.jmeter.samplers.SampleResult;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class PerformanceFactory
{
   
    public static void updateSubsystem(SampleResult results){
        //update subsystem s set s.mss_status = 1 where s.systemid=1;
                
        DBObject query = new BasicDBObject("systemid", "1");
        DBObject needupdate = MongoIcacheTable.subsystem.findOne(query);      
        
        
        DBObject newOne = MongoIcacheTable.subsystem.findOne(query);        
        newOne.put("mss_status", "1");
        
      
        results.sampleStart();
        try
        {
            //分页查询
            WriteResult back=MongoIcacheTable.subsystem.update(newOne, needupdate);  
            results.sampleEnd();
            results.setSuccessful(true);
          
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    public static void updateDevice(SampleResult results){
        
        //update device d set d.state = 1 where d.deviceid=1;
      
        DBObject query = new BasicDBObject("deviceid", "1");
        DBObject needupdate = MongoIcacheTable.device.findOne(query);      
        
        
        DBObject newOne = MongoIcacheTable.device.findOne(query);        
        newOne.put("state", "1");
        
      
        results.sampleStart();
        try
        {
            //分页查询
            WriteResult back=MongoIcacheTable.device.update(newOne, needupdate);  
            results.sampleEnd();
            results.setSuccessful(true);
          
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    
    
    /**
     * http_resource精确查询     
     * @param results [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void resourceQuery(SampleResult results){
        
        
        //select * from http_resource where uri='cbe40ef0b9bb2284c496ffffffffffffffffffffffffffffffffffffffffffffff';
        
        DBObject query = new BasicDBObject();      
        
        query.put("resourceid", "10000000");
      
        results.sampleStart();
        try
        {
            //分页查询
            DBCursor cur = MongoIcacheTable.http_resource.find(query);            
            results.sampleEnd();
            results.setSuccessful(true);
          
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    
    /**
     * redirect_version
     * @param results
     */
    public static void rangQuery(SampleResult results){
        
        //SELECT * FROM redirect_version where version between 50000000 and 500001000;
       
        DBObject query = new BasicDBObject();
        //查询  
        
        query.put("version", new BasicDBObject("$gt", 50000000).append("$lt",50001000));
          
       
        results.sampleStart();
        try
        {
        	 MongoIcacheTable.webnms.requestStart();
        	//分页查询
            DBCursor cur =  MongoIcacheTable.redirect_version.find(query);  
            
            int num=0;
            while(cur.hasNext()){
            	cur.next();
            	num++;
            }
            MongoIcacheTable.webnms.requestDone();
            results.sampleEnd();
            results.setSuccessful(num>0);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
        
    }
    public static void simpleQuery(SampleResult results)
    {
        DBObject query = new BasicDBObject();
        //条件查询  
        //query.put("visit", "100"); new BasicDBObject("$gt", 900).append("$lte", 920)
        query.put("contribute_flow", new BasicDBObject("$gt", 1).append("$lte",
                10));
          
        
        //
        results.sampleStart();
        try
        {
            //分页查询
            DBCursor cur = MongoIcacheTable.http_resource.find(query).limit(40).skip(0);
            
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    
    public static void deviceData(SampleResult results)
    {
        //INSERT INTO `device` (`deviceid`,`alias`,`create_time`,`description`,`device_code`,`device_ip`,`state`,`update_time`,`validate_code`,`areaid`,`type`) 
        //VALUES         (1,'Master','2011-01-06 00:00:00',NULL,NULL,'129.42.13.126',2,NULL,'210235G317Z0A5000040',1,NULL);
        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
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
        
      
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.device.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
    }
    
    public static void subsystemData(SampleResult results)
    {
        //INSERT INTO `subsystem` (`systemid`,`counter`,`create_time`,`monitor_time`,
        //`mss_status`,`name`,`number`,`portal`,
        //`server_type`,`state`,`update_time`,`upper_class`,`verification_code`,`version`,`areaid`,`deviceid`) VALUES 
        //(1,0,'2011-01-06 10:00:01',NULL,2,'DSS',0,NULL,
        //4,-1,'2011-01-09 10:00:01','DSS','210235G317Z0A5000040',NULL,1,1);
        
        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
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
        
       
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.subsystem.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    
    public static void dayVisitData(SampleResult results)
    {
        //`id` bigint(20) NOT NULL AUTO_INCREMENT,
        //`areaid` bigint(20) NOT NULL,
        //`create_date` varchar(10) NOT NULL,
        //`type` int(10) unsigned DEFAULT NULL,
        //`uri` char(50) NOT NULL,
        //`visit_count` int(11) NOT NULL,
        //
        //INSERT INTO `day_visit` VALUES (1344447,4,'2010-12-31',1,'ab4256b24c2e8d74aaf1e77ea6176b4c',3);
        
        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
        resources.put("_id", uuid);
        resources.put("id", uuid);
        resources.put("areaid", 4);
        resources.put("create_date", new Date().getTime());
        resources.put("type", "beKEY-Setup-Full.zip");
        resources.put("uri", "ab4256b24c2e8d74aaf1e77ea6176b4c");
        resources.put("visit_count", 3);
        
                
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.day_visit.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
    }
    
    public static void totalVisitData(SampleResult results)
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
        String uuid=UUID.randomUUID().toString();
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
        
       
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.total_visit.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
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
    public static void httpResourceData(SampleResult results)
    {
        
        //
        // INSERT INTO `http_resource` (`create_date`,`download_percent`,`download_times`,`file_name`,
        //`file_size`,`areaid`,`systemid`,`resourceid`,`state`,`cache_location`,
        //`resource_hash`,`uri`,`contribute_flow`,`contributory`,`type`,`outter_url`,`update_date`) VALUES 
        //('2010-08-26 11:18:18',NULL,NULL,'beKEY-Setup-Full.zip',
        //31924576,3,6,653,0,'/70/70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0/beKEY-Setup-Full.zip',
        //'70E1487DA17FF3A4BA2BA85A9FC501519B12A1A0','cbe40ef0b9bb2284c496128ddc0bcc08',NULL,NULL,1,'http://mdj.newhua.com/down/beKEY-Setup-Full.zip','2010-08-26 11:18:18');
        
        DBObject resources = new BasicDBObject();
        resources.put("create_date", new Date().getTime());
        resources.put("download_percent", 100);
        resources.put("download_times", 1);
        resources.put("file_name", "beKEY-Setup-Full.zip");
        
        resources.put("file_size", 31924576);
        resources.put("areaid", 3);
        resources.put("systemid", 6);
        String uuid=UUID.randomUUID().toString();
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
        resources.put("update_date", parseDate("2010-08-26 11:18:18").getTime());
        
        
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.http_resource.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
    }
    
    /**
     *bt resource构造数据
     * <一句话功能简述>
     * <功能详细描述>
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void btResourceData(SampleResult results)
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

        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
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
        
        resources.put("update_date", parseDate("2010-08-31 10:01:00").getTime());
        
       
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.bt_resource.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
    }
    
    /**
    *bt resource构造数据
    * <一句话功能简述>
    * <功能详细描述>
    * [参数说明]
    * @return void [返回类型说明]
    * @see [类、类#方法、类#成员]
    */
    public static void torrentResourceData(SampleResult results)
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

        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
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
        resources.put("update_date", parseDate("2010-08-31 10:01:00").getTime());
        
        
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.torrent_resource.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
    }
    
    /**
     * 
     *重定向数据
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void redirectVersionData(SampleResult results)
    {
        /*INSERT INTO `redirect_version` (`taskid`,`create_date`,`state`,`operate_type`,
         * `protocol_type`,`systemid`,`uri`,`version`) VALUES 
        (1,NULL,2,3,
        1,2,'cbe40ef0b9bb2284c496128ddc0bcc08',1);*/
        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
        resources.put("_id", uuid);
        resources.put("taskid", uuid);
        resources.put("create_date", new Date().getTime());
        resources.put("state", 2);
        resources.put("operate_type", 3);
        
        resources.put("protocol_type", 1);
        resources.put("systemid", 2);
        resources.put("uri", "cbe40ef0b9bb2284c496128ddc0bcc08");
        resources.put("version", 1);
        
        
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.redirect_version.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    
    /**
     * 
     * 设备性能统计数据
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static void deviceStatisData(SampleResult results)
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

        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
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
        
        
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.mss_device_statistics.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
    }
    
    /**
     * 
     *协议流量数据
     * [参数说明]
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    
    public static void fluxData(SampleResult results)
    {
        /*INSERT INTO `mss_device_flux_statistics` (`id`,`device_id`,`eth_ip`,`area_id`,
         * `type`,`create_date`,`flowin`,`flowout`) VALUES 
        (1,2,'129.42.1.3',3,
        '8001900003','2010-11-25 14:13:00',0,0);*/
        DBObject resources = new BasicDBObject();
        String uuid=UUID.randomUUID().toString();
        resources.put("_id", uuid);
        resources.put("id", uuid);
        
        resources.put("device_id", 2);
        resources.put("eth_ip", "129.42.1.3");
        resources.put("area_id", 3);
        
        resources.put("type", "8001900003");
        resources.put("create_date", new Date().getTime());
        resources.put("flowin", 0);
        resources.put("flowout", 0);
        
        
        
        results.sampleStart();
        try
        {
            
            MongoIcacheTable.mss_device_flux_statistics.insert(resources);
            results.sampleEnd();
            results.setSuccessful(true);
        }
        catch (MongoException ex)
        {
            results.setSuccessful(false);
            ex.printStackTrace();
            
        }
        
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
