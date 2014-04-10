package com.pocketdigi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.pocketdigi.slidingmenudemo.MainActivity;
import com.pocketdigi.slidingmenudemo.R;

public class MenuFragment extends Fragment {
	
	Button btn_fragmentA,btn_fragmentB;
	MainActivity mainActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.fragment_menu, container,false);
		mainActivity=(MainActivity)getActivity();
		btn_fragmentA = (Button) newsLayout.findViewById(R.id.btn_fragmentA);
		btn_fragmentB = (Button) newsLayout.findViewById(R.id.btn_fragmentB);	
		
		btn_fragmentA.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mainActivity.showFragmentA();
			}});
		
		btn_fragmentB.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mainActivity.showFragmentB();
			}});		
		return newsLayout;
		
	}	
}
