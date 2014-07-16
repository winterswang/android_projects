package com.example.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.criminalintent.R;

public class DatePickerFragment extends DialogFragment {

	public static final String EXTRA_DATE = "com.example.android.criminalintent.date";
	
	private Date mDate;
	
	public static DatePickerFragment newInstance(Date date){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public void sendResult(int resultCode){
		if(getTargetFragment() == null){
			Log.i("target fragment", "is null");
			return ;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		Log.i("sendResut",mDate.toString());
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date1,null);
		
		DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener(){

			@Override
			public void onDateChanged(DatePicker view, int year, int month,
					int day) {
				// TODO Auto-generated method stub
				mDate = new GregorianCalendar(year,month,day).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
			
		});
		
		return new AlertDialog.Builder(getActivity()).setView(v)
				.setTitle(R.string.date_picker_title)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						sendResult(Activity.RESULT_OK);
					}
				}).create();
				//.setPositiveButton(android.R.string.ok,null).create();
	}
}
