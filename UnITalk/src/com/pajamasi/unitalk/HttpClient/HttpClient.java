package com.pajamasi.unitalk.HttpClient;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.CustomCallBackListener.OnCallBackListener;

public class HttpClient {
	
	private OnCallBackListener m_CallBackListener;
	
	public HttpClient(CallBackListener listener)
	{
		m_CallBackListener = new OnCallBackListener(listener);
	}
	
	public void sendMessageToServer(final String url, final ArrayList<NameValuePair> nameValuePairs) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				DefaultHttpClient http = new DefaultHttpClient();
				try {
					HttpParams params = http.getParams();
					HttpConnectionParams.setConnectionTimeout(params, 5000);
					HttpConnectionParams.setSoTimeout(params, 5000);

					// post 인코딩 설정
					HttpPost httpPost = new HttpPost(url);
					UrlEncodedFormEntity entityRequest = new
					UrlEncodedFormEntity(nameValuePairs, "EUC-KR");
					httpPost.setEntity(entityRequest);

					HttpResponse responsePost 	= http.execute(httpPost);
					HttpEntity   resEntity 		= responsePost.getEntity();
					String res = EntityUtils.toString(resEntity);
					
					System.out.println(res);
					m_CallBackListener.doWork(res);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		th.start();
		
	}
}
