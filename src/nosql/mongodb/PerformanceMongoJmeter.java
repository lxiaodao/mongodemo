package nosql.mongodb;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class PerformanceMongoJmeter extends AbstractJavaSamplerClient {
	private SampleResult results = new SampleResult();

	static boolean isInit = false;
	static int reflectErrorCount = 0;
	
	final byte[] lock=new byte[0];

	// 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
	public void setupTest(JavaSamplerContext context) {
		// 初始化，准备连接mongo
		String host="";
		synchronized (lock) {
			if (!isInit) {
				host = context.getParameter("host");
				System.out.println("-------------------------初始化，运行一次-------------------------"+host);
				try {
					MongoIcacheTable.initDB(host);
				} catch (MongoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				return;
			}
			isInit = true;
		}

		
	}

	// 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("host", "129.42.13.171"); // 定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
		params.addArgument("method", "resourceQuery");

		return params;
	}

	// 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
	public SampleResult runTest(JavaSamplerContext context) {
		String method = context.getParameter("method");
		try {
			Method methodInvo = PerformanceFactory.class
					.getDeclaredMethod(method,SampleResult.class);
			methodInvo.invoke(method,results);
		} catch (Exception e) {

			reflectErrorCount++;

			e.printStackTrace();
		}

		return results;
	}

	// 结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
	public void teardownTest(JavaSamplerContext arg0) {
	}
}
