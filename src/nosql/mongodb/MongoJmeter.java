package nosql.mongodb;

import java.lang.reflect.Method;
import java.net.UnknownHostException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.mongodb.MongoException;

public class MongoJmeter extends AbstractJavaSamplerClient
{
    private SampleResult results = new SampleResult();
    
   
    
    byte[] lock = new byte[0];
    
    static boolean isInitial = false;
    
    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context)
    {
        //初始化，准备连接mongo
        synchronized (lock)
        {
            if (isInitial)
            {
                return;
            }
            String host = context.getParameter("host");
           
            
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
            isInitial = true;
            
            System.out.println("-------------------------初始化，运行一次host-------------------------"
                    + host);
        }
    }
    
    // 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
    public Arguments getDefaultParameters()
    {
        Arguments params = new Arguments();
        params.addArgument("host", "129.42.4.19"); // 定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
        params.addArgument("method", "httpResourceData");
        
        return params;
    }
    
    // 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext context)
    {
        String[] methods=new String[10];
        String method = context.getParameter("method");
        if ("".equals(method) || method == null)
        {
            method = "httpResourceData";
        }
        if (method.indexOf(",") != -1)
        {
            methods = method.split(",");
        }
        else
        {
            methods = new String[]{method };
        }
        results.sampleStart(); // 定义一个事务，表示这是事务的起始点，类似于LoadRunner的lr.start_transaction
      
        MongoIcacheTable.httpResourceData();
        
        /* for (int i = 0; null!=methods[i]&&!"".equals(methods[i]) && i < methods.length; i++)
        {
            
            
            try
            {
                Method methodInvoke = MongoIcacheTable.class.getDeclaredMethod(methods[i]);
                methodInvoke.invoke(methods[i]);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }*/
        results.sampleEnd(); // 定义一个事务，表示这是事务的结束点，类似于LoadRunner的lr.end_transaction
        
        results.setSuccessful(false); // 用于设置运行结果的成功或失败，如果是"false"则表示结果失败，否则则表示成功
        
        results.setSuccessful(true);
        return results;
    }
    
    // 结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext arg0)
    {
    }
}
