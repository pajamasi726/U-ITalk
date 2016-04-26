package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Const.ConstProtocol;
import Service.BroadCastService;
import Service.NoteService;
import Service.RegisterService;
import Service.RequestFriendListService;

/**
 * Servlet implementation class ServiceController
 */
@WebServlet("/ServiceController")
public class ServiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("서비스 요청");
		
		String protocol = request.getParameter(ConstProtocol.PROTOCOL);
		System.out.println("요청 해온 서비스 : "+protocol);
		
		switch(protocol)
		{
			case ConstProtocol.REGISTER_PHONE : // 폰등록 
				new RegisterService(request , response);
			break;
			
			case ConstProtocol.BROADCAST : // 브로드캐스트
				new BroadCastService(request , response);
			break;
			
			case ConstProtocol.REQUEST_FRIEND_LIST : // 친구리스트 요청 
				new RequestFriendListService(request , response);
			break;
			
			case ConstProtocol.NOTE : // 1:1 쪽지
				new NoteService(request, response);
			break;
		}
	}

}
