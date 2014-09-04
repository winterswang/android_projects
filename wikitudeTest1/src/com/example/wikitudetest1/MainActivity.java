package com.example.wikitudetest1;

import java.io.IOException;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.ArchitectConfig;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	/**
	 * holds the Wikitude SDK AR-View, this is where camera, markers, compass, 3D models etc. are rendered
	 */
	protected ArchitectView					architectView;
	
	/**
	 * sensor accuracy listener in case you want to display calibration hints
	 */
	protected SensorAccuracyChangeListener	sensorAccuracyListener;
	
	/**
	 * last known location of the user, used internally for content-loading after user location was fetched
	 */
	protected Location 						lastKnownLocaton;

	/**
	 * sample location strategy, you may implement a more sophisticated approach too
	 */
	//protected ILocationProvider				locationProvider;
	
	/**
	 * location listener receives location updates and must forward them to the architectView
	 */
	protected LocationListener 				locationListener;
	
	/**
	 * urlListener handling "document.location= 'architectsdk://...' " calls in JavaScript"
	 */
	protected ArchitectUrlListener 			urlListener;
	protected static final String WIKITUDE_SDK_KEY = "tW3Ht861dC8lNC2/xnahrP7DRI7nqfXDGZfUvdeIGJsc64QLICf0leLIO50svo8me8yrGBcglCEHwAQEhqs8eOceTQ0iXJhF6Glf016W/tDsCSXMetFz4vdZIHzOdNOOBJEKuIEPYZgyffutNigYtdWpPzS33L1OQLoVkdQ3z/NTYWx0ZWRfX21xcPVHf8nkO6IcVarrd+BPN3s78E/Ac39sjGX90LKiVK47DeMMovn5X5X3vXIcwrxe3WRZqL6sczpdObKvll+Qwvpbrnej1ausee5uUCCXFC6oevbcnuWyqgYXzctjCjrJlcdfzL1EgPi9W4i87bnT1uf8IYISiPwBlK7vNvLpBIfryzyn9t4EKMGr5X0GTgXA11G5BJW2wkgukMSt7zp8fE1XurT/C97tTtN9P/w5lyWnpjm/1Qt8n409QxB09A9ZW2x9vcOoTzyCKQyTXduWyhW4hv6gXHZUU5A88PJBGtFpX63f40VURgi+6hMNy2Q4I57Ck0d+NTJHQnPbm2NoHKiAkFr5LS2A91lnhF3BI9Qx8YPpjStPNslW93NJPXOstmSFDLep9MpNx+tLzPMRBj5HLjSTPCzWZSQSuXSj31hc/nwoaVl+0MvQzNC1YQdS1QcjeQx5Ln6nFaW+eCmRHOOAZZQJUJeaVvLSMll+zaYwZ+WrIYDCMx/nCwjtQ7Y+Voj2sivGIxPNrcDUuWul6q2D3WJmUwjquibs2M97lLljwojpgLfRf8hWynpvnfliTgk2OZUT96+vcwLW3FHkCLFteuRQ2g==";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
		final ArchitectConfig config = new ArchitectConfig(WIKITUDE_SDK_KEY);
		architectView.onCreate(config);
		// set accuracy listener if implemented, you may e.g. show calibration prompt for compass using this listener
		this.sensorAccuracyListener = this.getSensorAccuracyListener();
		
		// set urlListener, any calls made in JS like "document.location = 'architectsdk://foo?bar=123'" is forwarded to this listener, use this to interact between JS and native Android activity/fragment
		this.urlListener = this.getUrlListener();  
		
		// register valid urlListener in architectView, ensure this is set before content is loaded to not miss any event
		if ( this.urlListener !=null ) {
			this.architectView.registerUrlListener( this.getUrlListener() );
		}
		
		// listener passed over to locationProvider, any location update is handled here
		/*this.locationListener = new LocationListener() {

			@Override
			public void onStatusChanged( String provider, int status, Bundle extras ) {
			}

			@Override
			public void onProviderEnabled( String provider ) {
			}

			@Override
			public void onProviderDisabled( String provider ) {
			}

			@Override
			public void onLocationChanged( final Location location ) {
				// forward location updates fired by LocationProvider to architectView, you can set lat/lon from any location-strategy
				if (location!=null) {
				// sore last location as member, in case it is needed somewhere (in e.g. your adjusted project)
				AbstractArchitectCamActivity.this.lastKnownLocaton = location;
				if ( AbstractArchitectCamActivity.this.architectView != null ) {
					// check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
					if ( location.hasAltitude() && location.hasAccuracy() && location.getAccuracy()<7) {
						AbstractArchitectCamActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() );
					} else {
						AbstractArchitectCamActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000 );
					}
				}
				}
			}
		};
		*/
		// locationProvider used to fetch user position
		//this.locationProvider = getLocationProvider( this.locationListener );
		
	}
	@Override
	protected void onPostCreate( final Bundle savedInstanceState ) {
		super.onPostCreate( savedInstanceState );
		
		if ( this.architectView != null ) {
			
			// call mandatory live-cycle method of architectView
			this.architectView.onPostCreate();
			
			try {
				// load content via url in architectView, ensure '<script src="architect://architect.js"></script>' is part of this HTML file, have a look at wikitude.com's developer section for API references
				//this.architectView.load("http://test.heay.cn/imageOnTarget/index.html");
				this.architectView.load("imageOnTarget/index.html");
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// call mandatory live-cycle method of architectView
		if ( this.architectView != null ) {
			this.architectView.onResume();
			
			// register accuracy listener in architectView, if set
			if (this.sensorAccuracyListener!=null) {
				this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
			}
		}

		// tell locationProvider to resume, usually location is then (again) fetched, so the GPS indicator appears in status bar
//		if ( this.locationProvider != null ) {
//			this.locationProvider.onResume();
//		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		// call mandatory live-cycle method of architectView
		if ( this.architectView != null ) {
			this.architectView.onPause();
			
			// unregister accuracy listener in architectView, if set
			if ( this.sensorAccuracyListener != null ) {
				this.architectView.unregisterSensorAccuracyChangeListener( this.sensorAccuracyListener );
			}
		}
		
		// tell locationProvider to pause, usually location is then no longer fetched, so the GPS indicator disappears in status bar
//		if ( this.locationProvider != null ) {
//			this.locationProvider.onPause();
//		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// call mandatory live-cycle method of architectView
		if ( this.architectView != null ) {
			this.architectView.onDestroy();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if ( this.architectView != null ) {
			this.architectView.onLowMemory();
		}
	}

	public ArchitectUrlListener getUrlListener() {
		return new ArchitectUrlListener() {

			@Override
			public boolean urlWasInvoked(String uriString) {
				// by default: no action applied when url was invoked
				return false;
			}
		};
	}

	public SensorAccuracyChangeListener getSensorAccuracyListener() {
		return new SensorAccuracyChangeListener() {
			@Override
			public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
//				if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && SampleCamActivity.this != null && !SampleCamActivity.this.isFinishing() && System.currentTimeMillis() - SampleCamActivity.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
//					Toast.makeText( SampleCamActivity.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG ).show();
//					SampleCamActivity.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
//				}
			}
		};
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
