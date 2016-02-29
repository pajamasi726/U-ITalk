package com.pajamasi.unitalk;

import java.util.Vector;

import com.pajamasi.unitalk.firstTab.fragment.FirstTab_Fragment;
import com.pajamasi.unitalk.secondTab.fragment.SecondTab_Fragment;
import com.pajamasi.unitalk.thirdTab.fragment.ThirdTab_Fragment;

import android.annotation.SuppressLint;
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
		initFragmentList();
		initAdapter();
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
	
	private ViewPager 		 m_Pager;		// 프래그먼트 페이저
	private MainPagerAdapter m_Adapter;		// 프래그먼트 관리 어댑터
	private FragmentManager  m_FragManager; // 프래그먼트 매니저
	private Vector<Fragment> m_Fragments;	// 프래그먼트 배열
}
