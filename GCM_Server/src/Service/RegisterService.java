package Service;

import javax.servlet.http.HttpServletRequest;

import Const.ConstParam;

public class RegisterService {
	
	/** �� ���� ��� */
	public RegisterService(HttpServletRequest request)
	{
		String phone = request.getParameter(ConstParam.REGISTER_PHONENUM);
		String id       = request.getParameter(ConstParam.REGISTER_ID);
		
		System.out.println("��� ��û : "+phone+"-"+id);
	}

}
