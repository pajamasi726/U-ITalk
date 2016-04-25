package Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Const.ConstParam;
import DTO.User;
import Sender.Server;
import USER.UserList;

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
		
		String name  = request.getParameter(ConstParam.REGISTER_NAME);
		String phone = request.getParameter(ConstParam.REGISTER_PHONENUM);
		String regid       = request.getParameter(ConstParam.REGISTER_ID);
		
		System.out.println("등록 요청 : "+name+"-"+phone+"-"+regid);
		
		// 사용자 생성 
		User u = new User(name, phone, regid);
		
		// 전체 리스트에 추가
		ArrayList<User> list = UserList.getUserList();
		list.add(u);
		System.out.println(list.size());
		pw.write("register success 한글 테스트");
		pw.flush();
		pw.close();
		
	}

}
