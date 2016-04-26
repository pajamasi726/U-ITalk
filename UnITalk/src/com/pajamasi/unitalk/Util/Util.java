package com.pajamasi.unitalk.Util;

import java.util.Random;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Util {

	/** ����̽��� �ڵ��� ��ȣ�� ���� �ϴ� �޼ҵ� */
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
	
}
