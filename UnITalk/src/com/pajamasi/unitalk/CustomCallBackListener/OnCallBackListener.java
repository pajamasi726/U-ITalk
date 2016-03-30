package com.pajamasi.unitalk.CustomCallBackListener;

public class OnCallBackListener {
	
	private CallBackListener listener;

	public OnCallBackListener(CallBackListener listener)
	{
		this.listener = listener;
	}
	
	public void addListener(CallBackListener listener)
	{
		this.listener = listener;
	}
	
	public void doWork(String str)
	{
		listener.callBackMethod(str);
	}
	
	public void doWork(int i)
	{
		listener.callBackMethod(i);
	}
	
}
