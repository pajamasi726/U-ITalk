package Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Const.ConstParam;
import Sender.Server;

public class BroadCastService {

	public BroadCastService(HttpServletRequest request, HttpServletResponse response) {
		String rawData       = request.getParameter(ConstParam.MSG);
		String msg = "";
		try {
			 msg = URLDecoder.decode(rawData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		new Server().BroadCast(msg);
	}

}
