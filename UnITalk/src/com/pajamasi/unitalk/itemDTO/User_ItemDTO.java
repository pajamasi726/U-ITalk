package com.pajamasi.unitalk.itemDTO;

public class User_ItemDTO {

	private String Name;
	private String PhoneNumber;
	private String RegID;
	
	public User_ItemDTO(String name , String phone, String id) {
		this.setName(name);
		this.setPhoneNumber(phone);
		this.setRegID(id);
	}
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getRegID() {
		return RegID;
	}
	public void setRegID(String regID) {
		RegID = regID;
	}
	
	@Override
	public String toString() {
		
		String str = "Name : "+Name+"\n";
		 	   str += "Phone : "+PhoneNumber+"\n";
		 	   str += "RegID : "+RegID+"\n";
		 	   
		 return str;
	}
	
}
