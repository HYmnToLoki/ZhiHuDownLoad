package Net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class MainThread {
	
	final static int BUFFER_SIZE = 4096;
	public static String GetUserFollow(String UserFollowsUri)
	
	{
		
		String pageString="";
		HttpClient httpClient=new HttpClient();
		//�������ӳ�ʱ
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		//ʵ����һ��get��������
		GetMethod getMethod=new GetMethod(UserFollowsUri);
		//������ʲô����Ҳ��֪��
		//��һ��Ӧ�����������ӳ�ʱ
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		//���http����״̬
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

	/**
	 * ��InputStreamת����ĳ���ַ������String
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
}
