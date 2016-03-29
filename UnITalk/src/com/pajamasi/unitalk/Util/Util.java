package com.pajamasi.unitalk.Util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Util {

	/** 디바이스의 핸드폰 번호를 리턴 하는 메소드 */
	public static String getPhoneNumber(Context context)
	{
		String phoneNumber;
		
		TelephonyManager systemService = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber = systemService.getLine1Number();
		
		if(phoneNumber == null)
		{
			phoneNumber = "Insert USIM";
		}
		else
		{
			phoneNumber = phoneNumber.substring(phoneNumber.length()-10,phoneNumber.length());
			phoneNumber ="0"+phoneNumber;
		}
		
		return phoneNumber;
	}
	
}
