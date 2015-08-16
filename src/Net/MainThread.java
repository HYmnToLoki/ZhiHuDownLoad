package Net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.URISyntax;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import Been.User;
import Dao.DaoTools;
import MainGUI.Gui;

public class MainThread {

	final static int BUFFER_SIZE = 4096;
	final static String COOKIEPATH_STRING = "E:/cookie.txt";
	final static String USERCOOKIR_STRING = "E:/usercookie.txt";
	final static String XSRF_STRING="2fd8c9059d757ce6d4770c7ea46983a1";

	public static String GetUserFollow(String UserFollowsUri) {
		String pageString = "";
		HttpClient httpClient = new HttpClient();
		// 设置HttpClient的连接管理对象，设置 HTTP连接超时5s
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 让服务器知道访问源为浏览器
		httpClient
				.getParams()
				.setParameter(
						HttpMethodParams.USER_AGENT,
						"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36");

		// 设置连接超时
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);
		// 实例化一个get方法对象
		GetMethod getMethod = new GetMethod(UserFollowsUri);
		// 作用是什么，我也不知道
		// 第一个应该是设置连接超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// System.out.println(MainThread.ReadCookie(USERCOOKIR_STRING).substring(0,750));
		getMethod.addRequestHeader("Cookie",
				MainThread.ReadCookie(USERCOOKIR_STRING).substring(0, 750));

		try {
			int statut = httpClient.executeMethod(getMethod);

			if (statut != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			} else {
				InputStream content = getMethod.getResponseBodyAsStream();
				pageString = InputStreamTOString(content, "UTF-8");
				//fileWriter(pageString);
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

//	static public void fileWriter(String source) {
//		try {
//			FileWriter fWriter = new FileWriter("text.txt");
//			fWriter.write(source);
//			fWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("写入文件失败");
//		}
//	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * 
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
	public static String ReadCookie(String cookie) {
		File file = new File(cookie);
		String sssString = "";
		try {
			if (!file.exists() || file.isDirectory())
				throw new FileNotFoundException();
			FileInputStream fis = new FileInputStream(file);
			byte[] buf = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf));
				buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
			}
			sssString = sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sssString;
	}

	public static User GetUserInfo(String pageString,String id) {
		User user = new User();
		// hash_id = ""; // 用户的hash_id
		Pattern p = Pattern
				.compile("(?<=<script type=\"text/json\" class=\"json-inline\" data-name=\"current_people\">.{0,200}jpg\",\").{32}");
		Matcher m = p.matcher(pageString);
		while (m.find()) {
			user.setHash_id(m.group());
		}
		// id = ""; // 用户的id
		user.setId(id);
		// avatar = ""; // 用户的头像
		Pattern p2 = Pattern
				.compile("(?<=<img class=\"avatar avatar-l\".{0,100}src=\").*(?=\" srcset)");
		Matcher m2 = p2.matcher(pageString);
		while (m2.find()) {
			user.setAvatar(m2.group());
		}
		// name = ""; // 用户的姓名
		Pattern p3 = Pattern
				.compile("(?<=<a class=\"name\".{0,30}>).*(?=</a>)");
		Matcher m3 = p3.matcher(pageString);
		while (m3.find()) {
			user.setName(m3.group());
		}
		// jianjie = ""; // 用户的简介
		Pattern p4 = Pattern
				.compile("(?<=<span class=\"bio\".{0,100}>).*(?=</span>)");
		Matcher m4= p4.matcher(pageString);
		while (m4.find()) {
			user.setJianjie(m4.group());
		}
		// jieshao = ""; // 用户的介绍
		Pattern p5 = Pattern
				.compile("(?<=<span class=\"description unfold-item\">\n<span class=\"content\">\n\n).*(?=\n)");
		Matcher m5 = p5.matcher(pageString);
		while (m5.find()) {
			user.setJieshao(m5.group());
		}
		// location = ""; // 居住地
		Pattern p6 = Pattern
				.compile("(?<=<span class=\"location item\" title=\").*(?=\"><a)");
		Matcher m6 = p6.matcher(pageString);
		while (m6.find()) {
			user.setLocation(m6.group());
		}
		// bussiness = ""; // 从事的行业
		Pattern p7 = Pattern
				.compile("(?<=<span class=\"business item\" title=\").*(?=\"><a)");
		Matcher m7 = p7.matcher(pageString);
		while (m7.find()) {
			user.setBussiness(m7.group());
		}
		// gender = ""; // 性别
		Pattern p8 = Pattern
				.compile("(?<=<span class=\"item gender\" ><i class=\").*(?=\"></i>)");
		Matcher m8 = p8.matcher(pageString);
		while (m8.find()) {
			if(m8.group().equals("icon icon-profile-male"))
			{
				user.setGender("m");
			}else {
				user.setGender("w");
			}
		}
		// employment = ""; // 所在公司
		Pattern p9 = Pattern
				.compile("(?<=<span class=\"employment item\" title=\").*(?=\"><a)");
		Matcher m9 = p9.matcher(pageString);
		while (m9.find()) {
			user.setEmployment(m9.group());
		}
		// position = ""; // 职位
		Pattern p10 = Pattern
				.compile("(?<=<span class=\"position item\" title=\").*(?=\"><a)");
		Matcher m10 = p10.matcher(pageString);
		while (m10.find()) {
			user.setPosition(m10.group());
		}
		// education = ""; // 大学
		Pattern p11 = Pattern
				.compile("(?<=<span class=\"education item\" title=\").*(?=\"><a)");
		Matcher m11 = p11.matcher(pageString);
		while (m11.find()) {
			user.setEducation(m11.group());
		}
		// education_extra = ""; // 专业
		Pattern p12 = Pattern
				.compile("(?<=<span class=\"education-extra item\" title=').*(?='><a)");
		Matcher m12 = p12.matcher(pageString);
		while (m12.find()) {
			user.setEducation_extra(m12.group());
		}
		// followees = 0; // 关注了 人数
		Pattern p13 = Pattern
				.compile("(?<=<span class=\"zg-gray-normal\">关注了</span><br />\n<strong>).*(?=</strong>)");
		Matcher m13 = p13.matcher(pageString);
		while (m13.find()) {
			user.setFollowees(Integer.parseInt(m13.group()));
		}
		// followers = 0; // 关注者 人数
		Pattern p14 = Pattern
				.compile("(?<=<span class=\"zg-gray-normal\">关注者</span><br />\n<strong>).*(?=</strong>)");
		Matcher m14 = p14.matcher(pageString);
		while (m14.find()) {
			user.setFollowers(Integer.parseInt(m14.group()));
		}
		return user;
	}
	public static void GeTFolloweesList(int eenumber,int ernumber,String hash_id,String id) throws UnsupportedEncodingException 
	{
		int nnn=20;
		String erbaseuriString="http://www.zhihu.com/node/ProfileFollowersListV2";
		String eebaseurisString="http://www.zhihu.com/node/ProfileFolloweesListV2";
		
		while(nnn<eenumber)
		{
			String pageString="";
			String paramsString="method=next&params={\"offset\":"+nnn+",\"order_by\":\"created\",\"hash_id\":\""+hash_id+"\"}&_xsrf="+XSRF_STRING+"";
			HttpClient httpClient=new HttpClient();	
			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			// 让服务器知道访问源为浏览器
			httpClient
					.getParams()
					.setParameter(
							HttpMethodParams.USER_AGENT,
							"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36");

			// 设置连接超时
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			String uriString=eebaseurisString+"?"+java.net.URLEncoder.encode(paramsString,"gb2312");
			uriString=uriString.replaceAll("%3D", "=");
			uriString=uriString.replaceAll("%26", "&");
			// 实例化一个get方法对象
			GetMethod getMethod = new GetMethod(uriString);
			// 作用是什么，我也不知道
			// 第一个应该是设置连接超时
			//getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			//System.out.println("_za=e152be18-1ffe-4231-a329-a4552c0efbd3; q_c1=b51f9f918815456bbed34237ea5dfca8|1438085254000|1438085254000; cap_id=\"MjM1MjI5N2MzODk1NDg2M2FjMDZhNjQ3NWZkZTQzMWE=|1438085254|3683d09865d655fd5fa4bde47aa92fb370949523\"; z_c0=\"QUFCQXFQNGlBQUFYQUFBQVlRSlZUWW45M2xVdkNzMTFsSFJudjJnWHdlQzhsQzZlYVR0XzlRPT0=|1438085257|f99e7b6edf3bd2043487071f0ea6ff11c227637a\"; _xsrf=2fd8c9059d757ce6d4770c7ea46983a1; tc=AQAAAFxnuR02twgAjLj3cPF3c9s0Lebx; __utmt=1; __utma=51854390.1379204766.1438085205.1438778718.1438859888.3; __utmb=51854390.27.9.1438862846996; __utmc=51854390; __utmz=51854390.1438085205.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.100-1|2=registration_date=20131226=1^3=entry_date=20131226=1".length());		
			getMethod.addRequestHeader("Referer","http://www.zhihu.com/people/"+id+"/followees");
			getMethod.addRequestHeader("X-Requested-With","XMLHttpRequest");
			getMethod.addRequestHeader("Origin","http://www.zhihu.com");
			getMethod.addRequestHeader("Host","www.zhihu.com");
			getMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			// System.out.println(MainThread.ReadCookie(USERCOOKIR_STRING).substring(0,750));
			
			getMethod.addRequestHeader("Cookie",
					MainThread.ReadCookie(USERCOOKIR_STRING).substring(0, 750));

			try {
				int statut = httpClient.executeMethod(getMethod);

				if (statut != HttpStatus.SC_OK) {
					System.err.println("Method failed: "
							+ getMethod.getStatusLine());
				} else {
					InputStream content = getMethod.getResponseBodyAsStream();
					pageString = InputStreamTOString(content, "UTF-8");
					System.out.println(id+"--------------------------"+nnn+":"+eenumber);
					InsertUserCache(pageString);
					
					//System.out.println("解析 followees..."+nnn);
//					System.out.println(pageString);
//					fileWriter(pageString);
				}
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			nnn+=20;
		}
		nnn=20;
		while(nnn<ernumber)
		{
			String pageString="";
			String paramsString="method=next&params={\"offset\":"+nnn+",\"order_by\":\"created\",\"hash_id\":\""+hash_id+"\"}&_xsrf="+XSRF_STRING+"";
			HttpClient httpClient1=new HttpClient();	
			httpClient1.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			// 让服务器知道访问源为浏览器
			httpClient1
					.getParams()
					.setParameter(
							HttpMethodParams.USER_AGENT,
							"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36");

			// 设置连接超时
			httpClient1.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			String uriString=erbaseuriString+"?"+java.net.URLEncoder.encode(paramsString,"gb2312");
			uriString=uriString.replaceAll("%3D", "=");
			uriString=uriString.replaceAll("%26", "&");
			// 实例化一个get方法对象
			GetMethod getMethod = new GetMethod(uriString);
			// 作用是什么，我也不知道
			// 第一个应该是设置连接超时
			getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			// System.out.println(MainThread.ReadCookie(USERCOOKIR_STRING).substring(0,750));
			getMethod.addRequestHeader("Referer","http://www.zhihu.com/people/"+id+"/followers");
			getMethod.addRequestHeader("X-Requested-With","XMLHttpRequest");
			getMethod.addRequestHeader("Origin","http://www.zhihu.com");
			getMethod.addRequestHeader("Host","www.zhihu.com");
			getMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			getMethod.addRequestHeader("Cookie",
					MainThread.ReadCookie(USERCOOKIR_STRING).substring(0, 750));

			try {
				int statut = httpClient1.executeMethod(getMethod);

				if (statut != HttpStatus.SC_OK) {
					System.err.println("Method failed: "
							+ getMethod.getStatusLine());
				} else {
					InputStream content = getMethod.getResponseBodyAsStream();
					pageString = InputStreamTOString(content, "UTF-8");
					System.out.println(id+"--------------------------"+nnn+":"+ernumber);
					InsertUserCache(pageString);
					//System.out.println("解析 followers..."+nnn);
//					System.out.println(pageString);
//					fileWriter(pageString);
				}
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			nnn+=20;
		}	
	}
	public static void InsertUserCache(String pageString)
	{
		Pattern p = Pattern
				.compile("(?<=class=\"zg-btn zg-btn-unfollow zm-rich-follow-btn small nth-0\">取消关注</button>\n</div>\n.{0,100}\ndata-tip=\".{4}).*(?=\"\n)"
						+ "|(?<=class=\"zg-btn zg-btn-follow zm-rich-follow-btn small nth-0\">关注他</button>\n</div>\n.{0,100}\ndata-tip=\".{4}).*(?=\"\n)"
						+ "|(?<=class=\"zg-btn zg-btn-follow zm-rich-follow-btn small nth-0\">关注她</button>\n</div>\n.{0,100}\ndata-tip=\".{4}).*(?=\"\n)"
						+"|(?<=class=\"zg-btn zg-btn-follow zm-rich-follow-btn small nth-0\">关注</button>\n</div>\n.{0,100}\ndata-tip=\".{4}).*(?=\"\n)");
		Matcher m = p.matcher(pageString);
		while (m.find()) {
			boolean n = false;
			n = DaoTools.InsertUserCatch(m.group());
//			if (n == true) {
//				System.out.println(m.group() + "\t\t...success"+"......."+Gui.numbered);
//			}
		}
	}
}
