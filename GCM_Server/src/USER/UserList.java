package USER;

import java.util.ArrayList;

import DTO.User;

public class UserList 
{
	private static ArrayList<User> list;
	
	static
	{
		if(list == null)
		{
			list = new ArrayList<User>(1);
		}
	}
	
	public static ArrayList<User> getUserList()
	{
		if(list == null)
		{
			list = new ArrayList<User>(1);
		}
		
		return list;
	}
}
