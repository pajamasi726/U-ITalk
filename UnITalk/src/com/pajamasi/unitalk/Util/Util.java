package com.pajamasi.unitalk.Util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Util {

	/** ����̽��� �ڵ��� ��ȣ�� ���� �ϴ� �޼ҵ� */
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
