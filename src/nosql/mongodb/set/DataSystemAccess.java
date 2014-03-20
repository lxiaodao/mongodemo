/**
 * Copyright (c) 2011-2013 iTel Technology Inc,All Rights Reserved.
 */
	
package nosql.mongodb.set;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

/**
 * @ClassName: DataSystemAccess
 * @Description: TODO
 * @author Administrator
 * @date 2013年10月29日 下午3:41:01
 *
 */

public class DataSystemAccess {

	/**
	 * <描述>
	 * @author Administrator
	 * @param args void
	 * @throws
	 */

	public static void main(String[] args) {
		try {
			Mongo mongoClient = new Mongo(Arrays.asList(new ServerAddress("localhost", 27017),
			        new ServerAddress("localhost", 27018),
			        new ServerAddress("localhost", 27019)));
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
