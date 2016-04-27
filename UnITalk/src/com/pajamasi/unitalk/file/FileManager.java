package com.pajamasi.unitalk.file;

import java.io.File;

import com.pajamasi.unitalk.Util.Const;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileManager {

	private Context m_Context;
	private File  m_Sdcard;
	
	public FileManager()
	{
		this.m_Sdcard = Environment.getExternalStorageDirectory();
		Const.SDCARD = this.m_Sdcard.getPath();
	}
	
	public FileManager(Context context)
	{
		this.m_Context = context;
		this.m_Sdcard = Environment.getExternalStorageDirectory();
		Const.SDCARD = this.m_Sdcard.getPath();
	}
	
	
	public void setRoot()
	{
		File path = new File(m_Sdcard.getPath()+Const.CHAT_PATH);
		
		// �ش� ������ ���� ���� ������
		if(!path.isDirectory())
		{
			path.mkdir();
			Log.i(Const.APP, "ä�� ������ ���� �Ͽ����ϴ� : "+path.getPath());
		}
		else
		{
			Log.i(Const.APP, "ä�� ������ ���� �մϴ�");
		}
	}
	
	
	
}
