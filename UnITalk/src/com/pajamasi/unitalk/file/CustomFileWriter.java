package com.pajamasi.unitalk.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.pajamasi.unitalk.Util.Const;

import android.os.Environment;

public class CustomFileWriter {
	
	private BufferedWriter bw;				//
	private FileWriter fw;					//
	
	private String m_path = "default.txt";	//
	private boolean mode = true; 			// 이어쓰기 기본
	
	public CustomFileWriter()
	{
		init();
	}
	
	public CustomFileWriter(String path)
	{
		this.m_path = path;
		init();
	}
	
	private void init()
	{
		String path = Environment.getExternalStorageDirectory().getPath()+Const.CHAT_PATH+"/"+this.m_path;
		System.out.println("연결할 파일의 주소 : "+path);
		try
		{
			fw = new FileWriter(path, mode);
			bw = new BufferedWriter(fw);
		}
		catch(Exception e){
			System.out.println("파일 열기 실패 : "+e.getMessage());
			e.printStackTrace();}
	}
	
	public void write(String msg)
	{
	
		try {
			bw.write(msg+"\n");
			bw.flush();
		} catch (IOException e) {
			System.out.println("파일쓰기 에러 : "+e.getMessage());
			e.printStackTrace();
		}
		System.out.println("파일쓰기 완료");
	}
	
	public void writeAll(ArrayList<String> list)
	{
		if(list.size() > 0)
		{
			for(int i = 0 ; i < list.size(); i ++)
			{
				try {
					bw.write(list.get(i)+"\n");
					bw.flush();
				} catch (IOException e) {
						e.printStackTrace();
				}
			}
		}
	}
	
	public void close()
	{
		if(fw == null || bw == null)
			return;
		
		try {
			fw.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
