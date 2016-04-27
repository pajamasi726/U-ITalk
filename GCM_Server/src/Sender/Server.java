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
import DTO.User_ItemDTO;
import USER.UserList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


public class Server {
	
	// ���� ���̵� ���� ��ü
	Sender send;
	
	private int TTLTime = 600;

    /**
     * �ܸ��⿡ �޽��� ���� ��õ� Ƚ��
     */
    private	int RETRY = 3;
	
    
    private String m_MessageKey;
    
	public Server()
	{
		Random random = null;
		send = new Sender(Const.GOOGLE_API_KEY);
		
		if( random == null){
            random = new Random(System.currentTimeMillis());
        }

		m_MessageKey = String.valueOf(Math.abs(random.nextInt()));
	}
	
	// 1���� ã�Ƽ� ������ �޼ҵ�
	public void sendNote(String sender, String senderphonenum, String phone, String msg)
	{
		 Message.Builder gcmMessageBuilder = new Message.Builder();
         gcmMessageBuilder.collapseKey(m_MessageKey)
         .delayWhileIdle(true)
         .timeToLive(TTLTime);
         
         gcmMessageBuilder.addData(ConstProtocol.PROTOCOL, ConstProtocol.NOTE);
         
         try {
        	 gcmMessageBuilder.addData(ConstParam.SENDER, URLEncoder.encode(sender, "UTF-8"));
        	 gcmMessageBuilder.addData(ConstParam.SENDERPHONENUM, URLEncoder.encode(senderphonenum, "UTF-8"));
 			 gcmMessageBuilder.addData(ConstParam.MSG, URLEncoder.encode(msg, "UTF-8"));
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		}

        Message gcmMessage = gcmMessageBuilder.build();
        
        String regId = "";
        ArrayList<User_ItemDTO> list = UserList.getUserList();
        for(int i = 0 ; i < list.size(); i++)
        {
        	User_ItemDTO imsi = list.get(i);
        	if(phone.equals(imsi.getPhoneNumber()))
        	{
        		regId = imsi.getRegID();
        		System.out.println("���� ��� ã�� : "+imsi.getName());
        		System.out.println("���� �� ��ȣ : "+imsi.getPhoneNumber());
        	}
        }
        
        if(regId.equals(""))
        {
        	System.out.println("���� ��� ����");
        }
        
   		Result resultMessage = null;
   		try {
   			 resultMessage = send.send(gcmMessage, regId, RETRY);
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
           
   		String output = "GCM ���� �޽��� ��� => " + resultMessage.getErrorCodeName()
                   + "," + resultMessage.getCanonicalRegistrationId() + "," + resultMessage.getMessageId();
   		
   		System.out.println(output);
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
        ArrayList<User_ItemDTO> user_list = USER.UserList.getUserList();
        
        for(int i =0; i < user_list.size(); i++)
        {
        	list.add(user_list.get(i).getRegID());
        }
        
        // ��ü���� ����
        MulticastResult resultMessage;
		try {
			resultMessage = send.send(gcmMessage, list, RETRY);
			
			  String output = "GCM ���� �޽��� ��� => " + resultMessage.getMulticastId()
		                + "," + resultMessage.getRetryMulticastIds() + "," + resultMessage.getSuccess();
				
			  System.out.println(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
      
	}
}
