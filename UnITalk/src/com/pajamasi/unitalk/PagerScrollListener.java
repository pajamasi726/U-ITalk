package com.pajamasi.unitalk;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;

public class PagerScrollListener implements ViewPager.OnPageChangeListener{

	ActionBar m_Bar;
	
	public PagerScrollListener(ActionBar bar) {
		this.m_Bar = bar;
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onPageSelected(int position) {
		// 페이지가 이동된 페이지를 액션바에서 선택 해준다
		m_Bar.setSelectedNavigationItem(position);
	}

}
