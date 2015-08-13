package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextRegex {


	 public static String ReadPageString(){
        File file=new File("text.txt");
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
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			String pageString="";
			pageString=TextRegex.ReadPageString();
		//1、匹配html中img标签的正则：<img.*src=(.*?)[^>]*?>
		//2、匹配img标签中得src中http路径的正则：http:\"?(.*?)(\"|>|\\s+)
		//获得用户的姓名
		//Pattern p=Pattern.compile("(?<=<a class=\"name\".{0,100}>).*(?=</a>)");
		//获得用户头像
		//Pattern p=Pattern.compile("(?<=<img class=\"avatar avatar-l\".{0,100}src=\").*(?=\" srcset)");
		//获得用户的个人简介
		//Pattern p=Pattern.compile("(?<=<span class=\"bio\".{0,100}>).*(?=</span>)");
		//获得用户的详细介绍
		//Pattern p=Pattern.compile("(?<=<span class=\"description unfold-item\">\n<span class=\"content\">\n\n).*(?=\n)");
		//获得用户的地址
		//Pattern p=Pattern.compile("(?<=<span class=\"location item\" title=\").*(?=\"><a)");
		//获得用户从事的行业
		//Pattern p=Pattern.compile("(?<=<span class=\"business item\" title=\").*(?=\"><a)");
		//获得用户的性别
		//Pattern p=Pattern.compile("(?<=<span class=\"item gender\" ><i class=\").*(?=\"></i>)");
		//获得用户的公司
		//Pattern p=Pattern.compile("(?<=<span class=\"employment item\" title=\").*(?=</span>)");
		//获得用户的职位
		//Pattern p=Pattern.compile("(?<=<span class=\"position item\" title=\").*(?=\"><a)");
		//获得用户的大学
		//Pattern p=Pattern.compile("(?<=<span class=\"education item\" title=\").*(?=\"><a)");
		//获得用户的专业
		//Pattern p=Pattern.compile("(?<=<span class=\"education-extra item\" title=').*(?='><a)");
		//获得关注了人数
		//Pattern p=Pattern.compile("(?<=<span class=\"zg-gray-normal\">关注了</span><br />\n<strong>).*(?=</strong>)");
		//获得关注者人数
		Pattern p=Pattern.compile("(?<=<span class=\"zg-gray-normal\">关注者</span><br />\n<strong>).*(?=</strong>)");
		//获得用户的hash_id
		//Pattern p=Pattern.compile("(?<=<script type=\"text/json\" class=\"json-inline\" data-name=\"current_user\">.{0,100}\n{0,1}.{0,100}jpg\",\").{32}");
		Matcher m = p.matcher(pageString);
		while (m.find()) {
			System.out.println(m.group());
		}

		}
}
