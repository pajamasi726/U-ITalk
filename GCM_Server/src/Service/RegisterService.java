package Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Const.ConstParam;
import DTO.User;
import Data.UserList;
import Sender.Server;

public class RegisterService {
	
	/** 폰 정보 등록 
	 * @param response */
	public RegisterService(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("EUC-KR");
		response.setContentType("text/html; charset=EUC-KR");
		PrintWriter pw = null;
		
		try {
			 pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String phone = request.getParameter(ConstParam.REGISTER_PHONENUM);
		String id       = request.getParameter(ConstParam.REGISTER_ID);
		
		System.out.println("등록 요청 : "+phone+"-"+id);
		
		// 사용자 생성 
		User u = new User(phone,id);
		
		// 전체 리스트에 추가
		ArrayList<User> list = UserList.getUserList();
		list.add(u);
		System.out.println(list.size());
		pw.write("register success 한글 테스트");
		pw.flush();
		pw.close();
		
	}

}
