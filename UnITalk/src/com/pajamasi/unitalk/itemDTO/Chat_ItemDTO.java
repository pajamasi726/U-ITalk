package com.pajamasi.unitalk.itemDTO;

public class Chat_ItemDTO {
	
	private String Name;
	private String Phone;
	private String Comment;
	private int    Count;
	
	public Chat_ItemDTO(String path, String phone, String comment, int count)
	{
		this.Name = path;
		this.Phone = phone;
		this.Comment = comment;
		this.Count = count;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String m_Path) {
		this.Name = m_Path;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String m_Phone) {
		this.Phone = m_Phone;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String m_Comment) {
		this.Comment = m_Comment;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int m_Count) {
		this.Count = m_Count;
	}

}
