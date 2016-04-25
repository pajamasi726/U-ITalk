package com.pajamasi.unitalk;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.DB.DBMark;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.Util;
import com.pajamasi.unitalk.fragment.FirstTab_Fragment;
import com.pajamasi.unitalk.fragment.SecondTab_Fragment;
import com.pajamasi.unitalk.fragment.ThirdTab_Fragment;
import com.pajamasi.unitalk.gcm.GCMRegister;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setPhoneNumber();
		initDBManager();
		
		init();
		initActionBar();
		initFragmentList();
		initAdapter();
		addListener();
		
		
		
		// ������ �Ǿ� ���� ���� ��� ���̾ƿ� ǥ��
		setJoinLayout();
		
		/*
		Thread tht = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				// �������� ���� 
				nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.REGISTER_PHONE));
				
				// ���� ����� ���ؼ�, ����ȣ�� ID�� �ѱ��.
				nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_PHONENUM, Const.PHONE_NUM));
				nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_ID, 		Const.RegID));
				
				new HttpClient().sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
			}
		});
		
		tht.start();
		*/
		
	}
	


	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btn_join :
				
				// NAME �޾ƿ���
				String name = m_edtID.getText().toString();
				Const.NAME = name;
				
				// ���� �ϱ� 
				new GCMRegister(this).setRegister(m_DBManager);
				setInVisibleJoinLayout();
			break;
		}
	}
	
	
	private void setPhoneNumber()
	{
		String phoneNumber 	= Util.getPhoneNumber(this);
		Const.PHONE_NUM 	= phoneNumber;
	}
	
	
	/** ȸ�� ���� ���� �Ǵ��ϱ� */
	private void setJoinLayout() {
		boolean b = m_DBManager.m_Member.select_RegID();
		
		if(b)
		{
			// ȸ�� ������ �Ǿ� �����Ƿ�, ����â ����
			setInVisibleJoinLayout();
		}
		else
		{
			// ȸ�� ������ �ʿ� �ϹǷ�, ����â ���̱�
			setVisibleJoinLayout();
		}
	}
	
	
	/** ȸ�� ���� â ���ֱ� */
	private void setInVisibleJoinLayout()
	{
		m_Bar.show();
		m_Pager.setVisibility(View.VISIBLE);
		m_JoinLayout.setVisibility(View.GONE);
	}
	
	/** ȸ�� ���� â ��Ÿ���� */
	private void setVisibleJoinLayout()
	{
		m_Bar.hide();
		m_JoinLayout.setVisibility(View.VISIBLE);
		m_Pager.setVisibility(View.GONE);
	}
	
	
	/** ������ ���̽� �Ŵ��� �ε� */
	private void initDBManager()
	{
		m_DBManager = new DBManager(this, DBMark.DB_NAME, null, DBMark.DB_VERSION);
		
		// DB �Ŵ��� ����
		if(m_DBManager != null)
		{
			m_DBManager.openDB();
		}
	}
	
	
	
	/** ������ �߰�*/
	private void addListener()
	{
		m_Scroll_listener 	= new PagerScrollListener(m_Bar);
		m_Pager.setOnPageChangeListener(m_Scroll_listener);
	}
	
	/** �׼ǹ� ���� */
	private void initActionBar()
	{
		m_Bar = getActionBar();
		m_Actionbar_listener  = new ActionBarListener(m_Pager);
	
		
		// ���� ����
		m_Bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		m_Bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        Tab t1 = m_Bar.newTab();
        t1.setIcon(R.drawable.icon_person);
        t1.setTabListener(m_Actionbar_listener);
        
        
        Tab t2 = m_Bar.newTab();
        t2.setIcon(R.drawable.icon_chatting);
        t2.setTabListener(m_Actionbar_listener);
        
        Tab t3 = m_Bar.newTab();
        t3.setIcon(R.drawable.icon_setup);
        t3.setTabListener(m_Actionbar_listener);
        
        
        m_Bar.addTab(t1);
        m_Bar.addTab(t2);
        m_Bar.addTab(t3);
	}
	
	/** ���ҽ� �ε� */
	private void init()
	{
		m_Pager	      =	(ViewPager)	findViewById(R.id.pager);
		m_JoinLayout  = (RelativeLayout)findViewById(R.id.joinLayout);
		m_FragManager = this.getSupportFragmentManager();
		m_edtID		  = (EditText)findViewById(R.id.edt_Id);
	}
	
	/** �����׸�Ʈ ������ ����Ʈ ���� */
	private void initFragmentList()
	{
		m_Fragments = new Vector<Fragment>(1);
		
		Fragment first  = new Fragment().instantiate(this, FirstTab_Fragment.class.getName());
		Fragment second = new Fragment().instantiate(this, SecondTab_Fragment.class.getName()); 
		Fragment third	= new Fragment().instantiate(this, ThirdTab_Fragment.class.getName());
		
		m_Fragments.add(first);
		m_Fragments.add(second);
		m_Fragments.add(third);
	}
	
	/** �������� ����� ���� */
	private void initAdapter()
	{
		m_Adapter = new MainPagerAdapter(m_FragManager, m_Fragments);
		m_Pager.setAdapter(m_Adapter);
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(m_DBManager != null)
		{
			m_DBManager.closeDB();
		}
	}
	
	
	/** ���� ���� */
	// ��� ��ü
	private ViewPager 		 m_Pager;		// �����׸�Ʈ ������
	private MainPagerAdapter m_Adapter;		// �����׸�Ʈ ���� �����
	private FragmentManager  m_FragManager; // �����׸�Ʈ �Ŵ���
	private Vector<Fragment> m_Fragments;	// �����׸�Ʈ �迭
	private ActionBar 		 m_Bar;			// �׼ǹ�
	
	private EditText		 m_edtID;
	
	// ������
	private ActionBarListener 	m_Actionbar_listener;	// �׼ǹ� ���� ������
	private PagerScrollListener m_Scroll_listener;  	// ������ ��ũ�� ������
	
	// ȸ������ ���̾ƿ�
	private RelativeLayout 	m_JoinLayout;
	
	// DB �Ŵ���
	private DBManager 		m_DBManager;
}
