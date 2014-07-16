package com.pocketdigi.fragment;

import com.pocketdigi.slidingmenudemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//@EFragment(R.layout.fragment_a)
public class FragmentA extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.fragment_a, container,
				false);
		return newsLayout;
	}	
}
