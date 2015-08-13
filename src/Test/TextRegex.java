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
			     buf=new byte[1024];//�������ɣ�������ϴζ�ȡ�������ظ�
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
		//1��ƥ��html��img��ǩ������<img.*src=(.*?)[^>]*?>
		//2��ƥ��img��ǩ�е�src��http·��������http:\"?(.*?)(\"|>|\\s+)
		//����û�������
		//Pattern p=Pattern.compile("(?<=<a class=\"name\".{0,100}>).*(?=</a>)");
		//����û�ͷ��
		//Pattern p=Pattern.compile("(?<=<img class=\"avatar avatar-l\".{0,100}src=\").*(?=\" srcset)");
		//����û��ĸ��˼��
		//Pattern p=Pattern.compile("(?<=<span class=\"bio\".{0,100}>).*(?=</span>)");
		//����û�����ϸ����
		//Pattern p=Pattern.compile("(?<=<span class=\"description unfold-item\">\n<span class=\"content\">\n\n).*(?=\n)");
		//����û��ĵ�ַ
		//Pattern p=Pattern.compile("(?<=<span class=\"location item\" title=\").*(?=\"><a)");
		//����û����µ���ҵ
		//Pattern p=Pattern.compile("(?<=<span class=\"business item\" title=\").*(?=\"><a)");
		//����û����Ա�
		//Pattern p=Pattern.compile("(?<=<span class=\"item gender\" ><i class=\").*(?=\"></i>)");
		//����û��Ĺ�˾
		//Pattern p=Pattern.compile("(?<=<span class=\"employment item\" title=\").*(?=</span>)");
		//����û���ְλ
		//Pattern p=Pattern.compile("(?<=<span class=\"position item\" title=\").*(?=\"><a)");
		//����û��Ĵ�ѧ
		//Pattern p=Pattern.compile("(?<=<span class=\"education item\" title=\").*(?=\"><a)");
		//����û���רҵ
		//Pattern p=Pattern.compile("(?<=<span class=\"education-extra item\" title=').*(?='><a)");
		//��ù�ע������
		//Pattern p=Pattern.compile("(?<=<span class=\"zg-gray-normal\">��ע��</span><br />\n<strong>).*(?=</strong>)");
		//��ù�ע������
		Pattern p=Pattern.compile("(?<=<span class=\"zg-gray-normal\">��ע��</span><br />\n<strong>).*(?=</strong>)");
		//����û���hash_id
		//Pattern p=Pattern.compile("(?<=<script type=\"text/json\" class=\"json-inline\" data-name=\"current_user\">.{0,100}\n{0,1}.{0,100}jpg\",\").{32}");
		Matcher m = p.matcher(pageString);
		while (m.find()) {
			System.out.println(m.group());
		}

		}
}
