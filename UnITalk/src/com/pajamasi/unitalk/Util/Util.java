package com.pajamasi.unitalk.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Util {
	
	private static final String CODE = "UTF-8";

	/** 디바이스의 핸드폰 번호를 리턴 하는 메소드 */
	public static String getPhoneNumber(Context context)
	{
		Random ran = new Random();
		
		String phoneNumber;
		
		TelephonyManager systemService = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber = systemService.getLine1Number();
		
		if(phoneNumber == null)
		{
			int r = ran.nextInt(999);
			phoneNumber = "Insert USIM"+r;
		}
		else
		{
			phoneNumber = phoneNumber.substring(phoneNumber.length()-10,phoneNumber.length());
			phoneNumber ="0"+phoneNumber;
		}
		
		return phoneNumber;
	}
	
	public static String URLIncoding(String before)
	{
		String after = "";
		try {
			after = URLEncoder.encode(before, CODE);
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return after;
	}
	
	public static String URLDecoding(String before)
	{
		String after = "";
		try {
			after = URLDecoder.decode(before, CODE);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return after;
	}
	
}
