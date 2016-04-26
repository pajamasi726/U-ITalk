package Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Const.ConstParam;
import DTO.User_ItemDTO;
import USER.UserList;

public class RegisterService {
	
	/** �� ���� ��� 
	 * @param response */
	public RegisterService(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = null;
		
		try {
			 pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String name  = request.getParameter(ConstParam.REGISTER_NAME);
		String phone = request.getParameter(ConstParam.REGISTER_PHONENUM);
		String regid       = request.getParameter(ConstParam.REGISTER_ID);
		
		// decoding
				try {
					name = URLDecoder.decode(name, "UTF-8");
					 phone = URLDecoder.decode(phone, "UTF-8");
					 regid = URLDecoder.decode(regid, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		
		System.out.println("��� ��û : "+name+"-"+phone+"-"+regid);
		
		// ����� ���� 
		User_ItemDTO u = new User_ItemDTO(name, phone, regid);
		
		// ��ü ����Ʈ�� �߰�
		ArrayList<User_ItemDTO> list = UserList.getUserList();
		list.add(u);
		System.out.println(list.size());
		pw.write("register success �ѱ� �׽�Ʈ");
		pw.flush();
		pw.close();
		
	}

}
