package Net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class MainThread {
	
	final static int BUFFER_SIZE = 4096;
	final static String COOKIEPATH_STRING="E:/cookie.txt";
	public static String GetUserFollow(String UserFollowsUri)
	{
		String pageString="";
		HttpClient httpClient = new HttpClient();
		// 设置HttpClient的连接管理对象，设置 HTTP连接超时5s
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		 //让服务器知道访问源为浏览器
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36");	
		
		//设置连接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		//实例化一个get方法对象
		GetMethod getMethod=new GetMethod(UserFollowsUri);
		//作用是什么，我也不知道
		//第一个应该是设置连接超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		getMethod.addRequestHeader("Cookie",MainThread.ReadCookie().substring(0,699));

		try {
			int statut=httpClient.executeMethod(getMethod);
			
			if(statut!=HttpStatus.SC_OK)
			{
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}else 
			{
				InputStream content = getMethod.getResponseBodyAsStream();
				pageString = InputStreamTOString(content, "UTF-8");
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pageString;
	}
	
	
	static public void fileWriter(String source)
	{
		try {
			FileWriter fWriter=new FileWriter("text.txt");
			fWriter.write(source);
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("写入文件失败");
		}
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		
		data = null;
		return new String(outStream.toByteArray(), encoding);
	}
	/**
	 * 读取cookie中的信息
	 */
	 public static String ReadCookie(){
         File file=new File(COOKIEPATH_STRING);
         String sssString="";
		try {
			if(!file.exists()||file.isDirectory())
			     throw new FileNotFoundException();
			 FileInputStream fis=new FileInputStream(file);
			 byte[] buf = new byte[1024];
			 StringBuffer sb = new StringBuffer();
			 while((fis.read(buf))!=-1){
			     sb.append(new String(buf));    
			     buf=new byte[1024];//重新生成，避免和上次读取的数据重复
			 }
			 sssString=sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return sssString;
     }
}
