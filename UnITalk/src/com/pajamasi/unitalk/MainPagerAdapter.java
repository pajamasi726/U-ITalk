package com.pajamasi.unitalk;

import java.util.Vector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter{
	
	private Vector<Fragment> m_Fragments;	// �����׸�Ʈ �迭
	
	public MainPagerAdapter(FragmentManager fm, Vector<Fragment> fragments) {
		super(fm);
		this.m_Fragments = fragments;
	}

	// View���� ���ϴ� �������� ����
	@Override
	public Fragment getItem(int position) {
		return m_Fragments.get(position);
	}

	// �������� ������ ����
	@Override
	public int getCount() {
		return m_Fragments.size();
	}
}
