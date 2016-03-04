package Service;

import javax.servlet.http.HttpServletRequest;

import Const.ConstParam;

public class RegisterService {
	
	/** 폰 정보 등록 */
	public RegisterService(HttpServletRequest request)
	{
		String phone = request.getParameter(ConstParam.REGISTER_PHONENUM);
		String id       = request.getParameter(ConstParam.REGISTER_ID);
		
		System.out.println("등록 요청 : "+phone+"-"+id);
	}

}
