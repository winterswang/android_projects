package com.example.fragment;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.example.criminalintent.R;
import com.example.model.Crime;
import com.example.model.CrimeLab;

public class CrimeFragment extends Fragment {
	
	public static final String EXTRA_CRIME_ID = "com.example.android.criminalintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final int REQUEST_DATE = 0;

	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);		
	}
	
	public void updateDate(){
		mDateButton.setText(mCrime.getmDate().toString());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_fragment, parent, false);
		
		mTitleField = (EditText)v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getmTitle());
		mTitleField.addTextChangedListener(new MyTextWatcher());
		
		mDateButton = (Button)v.findViewById(R.id.crime_date);
		//mDateButton.setText(mCrime.getmDate().toString());
		updateDate();
		//mDateButton.setEnabled(false);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				//DatePickerFragment dialog = new DatePickerFragment();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.ismSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mCrime.setmSolved(isChecked);
			}			
		});
		
		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(resultCode != Activity.RESULT_OK) return;
		Log.i("resultCode:", ""+resultCode);
		Log.i("RESULT_OK:", ""+Activity.RESULT_OK);		
		//if(resultCode == REQUEST_DATE){
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);		
		    mCrime.setmDate(date);
		    updateDate();
		    //mDateButton.setText(mCrime.getmDate().toString());
		//}
	}
	
	public static CrimeFragment newInstance(UUID crimeId){ 
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		CrimeFragment fragment = new CrimeFragment();
		
		fragment.setArguments(args);
		return fragment;
	}
	
	private class MyTextWatcher implements TextWatcher{
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			mCrime.setmTitle(s.toString());
		}		
	}
}
