package DTO;

public class User {

	private String PhoneNumber;
	private String RegID;
	
	
	public User(String phone, String id) {
		this.setPhoneNumber(phone);
		this.setRegID(id);
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
	
	
}
