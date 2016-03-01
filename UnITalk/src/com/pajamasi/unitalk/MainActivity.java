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
	
	/** 리스너 추가*/
	private void addListener()
	{
		m_Scroll_listener 	= new PagerScrollListener(m_Bar);
		m_Pager.setOnPageChangeListener(m_Scroll_listener);
	}
	
	/** 액션바 지정 */
	private void initActionBar()
	{
		m_Bar = getActionBar();
		m_Actionbar_listener  = new ActionBarListener(m_Pager);
	
		
		// 색상 지정
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
	
	/** 리소스 로딩 */
	private void init()
	{
		m_Pager	=	(ViewPager)	findViewById(R.id.pager);
		m_FragManager = this.getSupportFragmentManager();
	}
	
	/** 프래그먼트 페이지 리스트 생성 */
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
	
	/** 페이저에 어댑터 지정 */
	private void initAdapter()
	{
		m_Adapter = new MainPagerAdapter(m_FragManager, m_Fragments);
		m_Pager.setAdapter(m_Adapter);
	}
	
	
	/** 전역 변수 */
	// 사용 객체
	private ViewPager 		 m_Pager;		// 프래그먼트 페이저
	private MainPagerAdapter m_Adapter;		// 프래그먼트 관리 어댑터
	private FragmentManager  m_FragManager; // 프래그먼트 매니저
	private Vector<Fragment> m_Fragments;	// 프래그먼트 배열
	private ActionBar 		 m_Bar;			// 액션바
	
	// 리스너
	private ActionBarListener 	m_Actionbar_listener;	// 액션바 선택 리스너
	private PagerScrollListener m_Scroll_listener;  	// 페이지 스크롤 리스너
}
