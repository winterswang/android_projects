package com.example.criminalintent;

import android.support.v4.app.Fragment;

import com.example.fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

}
