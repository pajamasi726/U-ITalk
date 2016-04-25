package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BroadCastService;
import Service.RegisterService;
import Const.ConstProtocol;

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
		
		System.out.println("���� ��û");
		
		String protocol = request.getParameter(ConstProtocol.PROTOCOL);
		System.out.println("��û �ؿ� ���� : "+protocol);
		
		switch(protocol)
		{
			case ConstProtocol.REGISTER_PHONE :
				new RegisterService(request , response);
			break;
			
			case ConstProtocol.BROADCAST :
				new BroadCastService(request , response);
			break;
			
		}
	}

}
