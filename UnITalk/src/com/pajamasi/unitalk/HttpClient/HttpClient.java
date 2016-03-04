package com.pajamasi.unitalk.HttpClient;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.pajamasi.unitalk.Util.Const;

public class HttpClient {
	
	public void sendMessageToServer(String url, ArrayList<NameValuePair> nameValuePairs) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
