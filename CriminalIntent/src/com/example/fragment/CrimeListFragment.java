package com.example.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.criminalintent.CrimePageActivity;
import com.example.criminalintent.R;
import com.example.model.Crime;
import com.example.model.CrimeLab;

public class CrimeListFragment extends ListFragment {

	private ArrayList<Crime> mCrimes;
	private static final String TAG = "CrimeListFragment";
	private static final int REQUEST_CRIME = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		Log.d(TAG, c.getmTitle()+" was clicked");
		
		//start crimeActivity
	    //Intent i = new Intent(getActivity(),CrimeActivity.class);
		Intent i = new Intent(getActivity(),CrimePageActivity.class);
	    i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getmId());
		startActivity(i);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_CRIME){
			//Handle result
			getActivity().setResult(Activity.RESULT_OK,null);
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime>{
		public CrimeAdapter(ArrayList<Crime> crimes){
			super(getActivity(),0,crimes);
		}
		@Override
		public View getView(int position,View convertView,ViewGroup parent){
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			Crime c = getItem(position);
			TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
		    titleTextView.setText(c.getmTitle());
		    TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
		    dateTextView.setText(c.getmDate().toString());
		    CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
		    solvedCheckBox.setChecked(c.ismSolved());
		    
		    return convertView;
		}
	}
}
