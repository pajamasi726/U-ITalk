package com.pajamasi.unitalk;

import java.util.Vector;

import com.pajamasi.unitalk.firstTab.fragment.FirstTab_Fragment;
import com.pajamasi.unitalk.secondTab.fragment.SecondTab_Fragment;
import com.pajamasi.unitalk.thirdTab.fragment.ThirdTab_Fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		init();
		initActionBar();
		initFragmentList();
		initAdapter();
		addListener();
		
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
		m_Pager	=	(ViewPager)	findViewById(R.id.pager);
		m_FragManager = this.getSupportFragmentManager();
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
	
	
	/** ���� ���� */
	// ��� ��ü
	private ViewPager 		 m_Pager;		// �����׸�Ʈ ������
	private MainPagerAdapter m_Adapter;		// �����׸�Ʈ ���� �����
	private FragmentManager  m_FragManager; // �����׸�Ʈ �Ŵ���
	private Vector<Fragment> m_Fragments;	// �����׸�Ʈ �迭
	private ActionBar 		 m_Bar;			// �׼ǹ�
	
	// ������
	private ActionBarListener 	m_Actionbar_listener;	// �׼ǹ� ���� ������
	private PagerScrollListener m_Scroll_listener;  	// ������ ��ũ�� ������
}
