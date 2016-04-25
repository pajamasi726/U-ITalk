package Sender;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Const.Const;
import Const.ConstParam;
import Const.ConstProtocol;
import DTO.User;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;


public class Server {
	
	// ���� ���̵� ���� ��ü
	Sender sender;
	
	private int TTLTime = 600;

    /**
     * �ܸ��⿡ �޽��� ���� ��õ� Ƚ��
     */
    private	int RETRY = 3;
	
    
    private String m_MessageKey;
    
	public Server()
	{
		Random random = null;
		sender = new Sender(Const.GOOGLE_API_KEY);
		
		if( random == null){
            random = new Random(System.currentTimeMillis());
        }

		m_MessageKey = String.valueOf(Math.abs(random.nextInt()));
		
        
// 		Result resultMessage = null;
// 		try {
// 			 resultMessage = sender.send(gcmMessage, regId, RETRY);
// 		} catch (IOException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
//         // ���� �ܸ��� �޽��� ���� �� ��� Ȯ��
//         String output = "GCM ���� �޽��� ��� => " + resultMessage.getErrorCodeName()
//                 + "," + resultMessage.getCanonicalRegistrationId() + "," + resultMessage.getMessageId();
	}
	
	public void BroadCast(String msg)
	{
		 Message.Builder gcmMessageBuilder = new Message.Builder();
         gcmMessageBuilder.collapseKey(m_MessageKey)
         .delayWhileIdle(true)
         .timeToLive(TTLTime);
         
         gcmMessageBuilder.addData(ConstProtocol.PROTOCOL, ConstProtocol.BROADCAST);
         
         try {
			gcmMessageBuilder.addData(ConstParam.MSG, URLEncoder.encode(msg, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

         Message gcmMessage = gcmMessageBuilder.build();
         
         
        List<String> list = new ArrayList<String>();
        ArrayList<User> user_list = Data.UserList.getUserList();
        
        for(int i =0; i < user_list.size(); i++)
        {
        	list.add(user_list.get(i).getRegID());
        }
        
        // ��ü���� ����
        MulticastResult resultMessage;
		try {
			resultMessage = sender.send(gcmMessage, list, RETRY);
			
			  String output = "GCM ���� �޽��� ��� => " + resultMessage.getMulticastId()
		                + "," + resultMessage.getRetryMulticastIds() + "," + resultMessage.getSuccess();
				
			  System.out.println(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
      
	}
}
