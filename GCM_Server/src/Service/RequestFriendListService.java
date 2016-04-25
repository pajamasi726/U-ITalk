package Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Const.ConstProtocol;
import DTO.User;
import USER.UserList;

public class RequestFriendListService {

	public RequestFriendListService(HttpServletRequest request,HttpServletResponse response) {
		
		// 인코딩 설정
		response.setCharacterEncoding("EUC-KR");
		response.setContentType("text/html; charset=EUC-KR");
		PrintWriter pw = null;

		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String json = getUserList();
		
		pw.write(json);
		pw.flush();
		pw.close();
	}
	
	public String getUserList()
	{
		String json = "";

		// User Data
		ArrayList<User> list = UserList.getUserList();

		// imsi code
		User imsi = new User("홍길동","010-8754-1703" , "abcdef");
		list.add(imsi);
		
		User imsi2 = new User("유관순","010-1234-5678" , "abcdef");
		list.add(imsi2);
		
		// JSON Array
		JSONArray user_list = new JSONArray();
		
		for(int i = 0 ; i < list.size(); i ++)
		{
			User user = list.get(i);
			
			JSONObject data = new JSONObject();
			data.put("NAME", user.getName());
			data.put("PHONENUMBER", user.getPhoneNumber());
			data.put("REGID", user.getRegID());
			
			user_list.add(data);
		}
		
		
		// JSON DATA 준비
		JSONObject root = new JSONObject();
		
		root.put(Const.ConstProtocol.PROTOCOL, Const.ConstProtocol.REQUEST_FRIEND_LIST);
		root.put(Const.ConstParam.FRIEND_LIST, user_list);
		
		json = root.toJSONString();
		System.out.println("유저 리스트 JSON : "+json);
		
		return json;
	}

}
