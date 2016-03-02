package com.pajamasi.unitalk.DB;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * DB SQL, VERSION, NAME, TABLE �� DB���� ���� Ŭ����
 * @author PAJAMASI
 *
 */
public class DBMark {
	
	/** DB Version*/
	public static final int DB_VERSION = 1;
	
	/** DB �̸� */
	public static final String DB_NAME = "UNITALK.db";
	
	
	// ���
	/** ȸ������ ���� ���̺� �̸� */
	public static final String TABLE_NAME_MEMBER = "T_MEMBER";
	/** ȸ������ ���� �÷� */
	public static final String COLUMN_NAME_REGID = "C_REGID";
	/** ȸ������ ���̺� ���� SQL */
	public static final String SQL_CREATE_MEMBER = "CREATE TABLE "+TABLE_NAME_MEMBER+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			                                       + COLUMN_NAME_REGID+" TEXT);";
	/** ȸ������ ���� SQL */
	public static final String SQL_SELECT_REGID  = "SELECT "+COLUMN_NAME_REGID+" from "+TABLE_NAME_MEMBER+" where _id = 1";
	/** ȸ�� ���� �Է� */
	public static final String SQL_INSERT_REGID  = "INSERT INTO "+TABLE_NAME_MEMBER+" ("+COLUMN_NAME_REGID+") VALUES (?);";
			
			
	
}
