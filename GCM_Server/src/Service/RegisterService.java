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
	
	/** �� ���� ��� 
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
		
		System.out.println("��� ��û : "+name+"-"+phone+"-"+regid);
		
		// ����� ���� 
		User u = new User(name, phone, regid);
		
		// ��ü ����Ʈ�� �߰�
		ArrayList<User> list = UserList.getUserList();
		list.add(u);
		System.out.println(list.size());
		pw.write("register success �ѱ� �׽�Ʈ");
		pw.flush();
		pw.close();
		
	}

}
