package Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Sender.Server;
import Const.ConstParam;

public class NoteService {

	public NoteService(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("노트 서비스 시작");
		String phone = request.getParameter(ConstParam.RECEIVER);
		String msg    = request.getParameter(ConstParam.MSG);
		
		// decoding
		try {
			 msg = URLDecoder.decode(msg, "UTF-8");
			 phone = URLDecoder.decode(phone, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		new Server().sendNote(phone, msg);
		
	}

}
