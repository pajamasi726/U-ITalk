package com.pajamasi.unitalk.Util;

import java.io.File;

import android.os.Environment;

public class Const 
{
	public static String APP = "U&ITalk";
	
	public static String NAME = "";
	
	public static String RegID = "";
	
	public static String PHONE_NUM = "";
	
	
	/** 채팅 내용 저장 폴더 Sdcard+/UNITalk */
	public static String SDCARD    = "";
	public static String CHAT_PATH = "/UNITalk";
	
	
	/** 상위 액티비티 정의 */
	public static String MainActivity = "com.pajamasi.unitalk.MainActivity";
	public static String ChattingActivity = "com.pajamasi.unitalk.activity.ChattingActivity";

	/**
     * 단말 등록을 위한 필요한 ID
     */
    public static final String PROJECT_ID = "844799359964";

    
    /**
     * 서버 : 푸시 메시지 전송을 위해 필요한 KEY
     */
    public static final String GOOGLE_API_KEY = "AIzaSyDm1B8BZ2d850B65hFYdvN1VWcqE4KRjwU";
    
    
    public static final String SERVER_ADDRESS = "http://192.168.0.14:8181/GCM_Server/ServiceController";
    
}
