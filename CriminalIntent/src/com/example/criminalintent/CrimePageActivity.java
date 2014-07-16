package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.fragment.CrimeFragment;
import com.example.model.Crime;
import com.example.model.CrimeLab;

public class CrimePageActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm){

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				Crime crime = mCrimes.get(arg0);
				return CrimeFragment.newInstance(crime.getmId());
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCrimes.size();
			}			
		});
		
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		for(int i = 0; i < mCrimes.size(); i++){
			if(mCrimes.get(i).getmId().equals(crimeId)){
				mViewPager.setCurrentItem(i);
				setTitle(mCrimes.get(i).getmTitle());
				break;
			}
		}
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Crime crime = mCrimes.get(arg0);
				if(crime.getmTitle() != null){
					setTitle(crime.getmTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
