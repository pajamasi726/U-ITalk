package com.pajamasi.unitalk.json;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.itemDTO.User_ItemDTO;

public class UNTalkJsonParser {
	
	private JSONObject obj;
	
	public Object ParserController(String json_data)
	{
		Object object = null;
		
		String protocol = getProtocol(json_data);
		
		// 친구 목록 요청 일때
		if(protocol.equals(ConstProtocol.REQUEST_FRIEND_LIST))
		{
			object = getUserListParsing(json_data);
		}
		
		
		return object;
	}
	
	private String getProtocol(String json_data)
	{
		String protocol = "";
		
		/** 파싱 하기 */
		JSONParser paser = new JSONParser();
		
		// 파싱 
		try 
		{
			obj = (JSONObject) paser.parse(json_data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 프로토콜 알아오기
		protocol = (String) obj.get(ConstProtocol.PROTOCOL);
		
		return protocol;
	}
	
	/** json 데이터에서 친구 데이터를 추출 */
	private ArrayList<User_ItemDTO> getUserListParsing(String json_data)
	{
		ArrayList<User_ItemDTO> list = new ArrayList<User_ItemDTO>(1);
		
		// JSON 파싱
		JSONArray jsonList = (JSONArray)obj.get(ConstParam.FRIEND_LIST);
		
		
		for(int i = 0 ; i < jsonList.size(); i++)
		{
			JSONObject imsi = (JSONObject)jsonList.get(i);
			
			String name   = (String)imsi.get("NAME");
			String number = (String)imsi.get("PHONENUMBER");
			String regid  = (String)imsi.get("REGID");
			
			User_ItemDTO u = new User_ItemDTO(name,number,regid);
			list.add(u);
		}
		
		return list;
	}

}
