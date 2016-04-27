package com.pajamasi.unitalk.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.pajamasi.unitalk.Util.Const;

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
		String path = Const.SDCARD+Const.CHAT_PATH+"/"+this.m_path;
		
		try
		{
			fw = new FileWriter(path, mode);
			bw = new BufferedWriter(fw);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void write(String msg)
	{
	
		try {
			bw.write(msg+"\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
