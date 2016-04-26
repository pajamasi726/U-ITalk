package USER;

import java.util.ArrayList;

import DTO.User_ItemDTO;

public class UserList 
{
	private static ArrayList<User_ItemDTO> list;
	
	static
	{
		if(list == null)
		{
			list = new ArrayList<User_ItemDTO>(1);
		}
	}
	
	public static ArrayList<User_ItemDTO> getUserList()
	{
		if(list == null)
		{
			list = new ArrayList<User_ItemDTO>(1);
		}
		
		return list;
	}
}
