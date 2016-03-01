package com.pajamasi.unitalk;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

@SuppressLint("NewApi")
public class ActionBarListener implements ActionBar.TabListener{

	private ViewPager m_Pager;
	
	public ActionBarListener(ViewPager pager) {
		this.m_Pager = pager;
	}
	
	// Tab이 선택이 되었을때
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		
		switch(position)
		{
			case 0 :
				m_Pager.setCurrentItem(0);
			break;
			
			case 1 :
				m_Pager.setCurrentItem(1);
			break;
			
			case 2 :
				m_Pager.setCurrentItem(2);
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

}
