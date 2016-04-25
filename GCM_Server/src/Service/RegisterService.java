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
		
		String phone = request.getParameter(ConstParam.REGISTER_PHONENUM);
		String id       = request.getParameter(ConstParam.REGISTER_ID);
		
		System.out.println("��� ��û : "+phone+"-"+id);
		
		// ����� ���� 
		User u = new User(phone,id);
		
		// ��ü ����Ʈ�� �߰�
		ArrayList<User> list = UserList.getUserList();
		list.add(u);
		System.out.println(list.size());
		pw.write("register success �ѱ� �׽�Ʈ");
		pw.flush();
		pw.close();
		
	}

}
