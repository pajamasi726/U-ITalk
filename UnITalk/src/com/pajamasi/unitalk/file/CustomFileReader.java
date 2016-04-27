package com.pajamasi.unitalk.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.pajamasi.unitalk.Util.Const;

public class CustomFileReader {
	
	private FileReader fr;
	private BufferedReader br;
	
	private String m_path = "default.txt";
	
	public CustomFileReader()
	{
		init();
	}
	
	public CustomFileReader(String path)
	{
		this.m_path = path;
		init();
	}
	
	private void init()
	{
		String path = Const.SDCARD+Const.CHAT_PATH+"/"+this.m_path;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public String read()
	{
		String msg = "";
		
		try {
			msg = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	
	public void close()
	{
		if(fr == null || br == null)
			return;
		
		try {
			fr.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
