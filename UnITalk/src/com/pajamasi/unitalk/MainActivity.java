package com.pajamasi.unitalk;

import java.util.Vector;

import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.DB.DBMark;
import com.pajamasi.unitalk.firstTab.fragment.FirstTab_Fragment;
import com.pajamasi.unitalk.secondTab.fragment.SecondTab_Fragment;
import com.pajamasi.unitalk.thirdTab.fragment.ThirdTab_Fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	
	private String reg = "APA91bGfqZOTsQalZoDg2Z6jzwxWlr51_yfJjSfZrp5GVmZ9E-PbV25Zj0SuhL9HeC3E3jJnsuD8e4LWJDTtAtEX0snv_-16Y-nt-50PsaRBRlAnyvyuFwKZAy5QIeswRw6ljkzLHGez";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		initActionBar();
		initFragmentList();
		initAdapter();
		addListener();
		
		initDBManager();
		
		// 가입이 되어 있지 않은 경우 레이아웃 표시
		setJoinLayout();
		
	}
	


	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btn_join :
				
				// 임시 가입 처리
				if(m_DBManager.insertRegID(reg));
					setInVisibleJoinLayout();
			break;
		}
	}
	
	/** 회원 가입 여부 판단하기 */
	private void setJoinLayout() {
		boolean b = m_DBManager.select_RegID();
		if(b)
		{
			// 회원 가입이 되어 있으므로, 가입창 삭제
			setInVisibleJoinLayout();
		}
		else
		{
			// 회원 가입이 필요 하므로, 가입창 보이기
			setVisibleJoinLayout();
		}
	}
	
	
	/** 회원 가입 창 없애기 */
	private void setInVisibleJoinLayout()
	{
		m_Bar.show();
		m_Pager.setVisibility(View.VISIBLE);
		m_JoinLayout.setVisibility(View.GONE);
	}
	
	/** 회원 가입 창 나타내기 */
	private void setVisibleJoinLayout()
	{
		m_Bar.hide();
		m_JoinLayout.setVisibility(View.VISIBLE);
		m_Pager.setVisibility(View.GONE);
	}
	
	
	/** 데이터 베이스 매니저 로딩 */
	private void initDBManager()
	{
		m_DBManager = new DBManager(this, DBMark.DB_NAME, null, DBMark.DB_VERSION);
		
		// DB 매니저 열기
		if(m_DBManager != null)
		{
			m_DBManager.openDB();
		}
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
		m_JoinLayout = (RelativeLayout)findViewById(R.id.joinLayout);
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
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(m_DBManager != null)
		{
			m_DBManager.closeDB();
		}
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
	
	// 회원가입 레이아웃
	private RelativeLayout 	m_JoinLayout;
	
	// DB 매니저
	private DBManager 		m_DBManager;
}
