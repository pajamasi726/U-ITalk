package com.pajamasi.unitalk;

import java.util.Vector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter{
	
	private Vector<Fragment> m_Fragments;	// 프래그먼트 배열
	
	public MainPagerAdapter(FragmentManager fm, Vector<Fragment> fragments) {
		super(fm);
		this.m_Fragments = fragments;
	}

	// View에서 원하는 페이지를 리턴
	@Override
	public Fragment getItem(int position) {
		return m_Fragments.get(position);
	}

	// 페이지의 갯수를 리턴
	@Override
	public int getCount() {
		return m_Fragments.size();
	}
}
