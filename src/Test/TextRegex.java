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
		Pattern p=Pattern.compile("(?<=<span class=\"name\">).*(?=</span>)");
		Matcher m = p.matcher(pageString);
		while (m.find()) {
			System.out.println(m.group());
		}

		}
}
