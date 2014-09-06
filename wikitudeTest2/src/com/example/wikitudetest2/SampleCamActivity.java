package com.example.wikitudetest2;

import android.hardware.SensorManager;
import android.location.LocationListener;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;

public class SampleCamActivity extends AbstractArchitectCamActivity {

	/**
	 * extras key for activity title, usually static and set in Manifest.xml
	 */
	protected static final String EXTRAS_KEY_ACTIVITY_TITLE_STRING = "test";
	
	/**
	 * extras key for architect-url to load, usually already known upfront, can be relative folder to assets (myWorld.html --> assets/myWorld.html is loaded) or web-url ("http://myserver.com/myWorld.html"). Note that argument passing is only possible via web-url 
	 */
	//protected static final String EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL = "imageOnTarget/index.html";
	protected static final String EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL = "locationPoi/index.html";
	
	protected static final String WIKITUDE_SDK_KEY = "h9G3av/CDMHrc3b7TIenvDf128JKUjX5Mer6Kf+5XIbJEbKtTxLxunnPZuR/Jsk8nMj/tAweQ8L20M89Al4tldBLmgfguqIsBHjzfXKTMWLaM+5L9+InQtaOr3Br8V2xR15WLt3StnPEI8aXXQQdcw/ypouzUYHHjV+mhRkWlgVTYWx0ZWRfX/Bomui7owfLhJqiy9Ep7iICExdq8f7UuyKBcwFjhRxaybbbxNaL0hR0XTYH4IkFOgyCht9A4jY1JsxtjNJKjHncQ8CDG7YKXyHJIjVbBs9XBgbAS2AGFmtB+dXFrHFDz1oJWumOmHo3CejZTGnx99foRUGOTGnMwAzUztbMlu+IigC26OQXaYnNBFr/UaNHK1gGw9NHJARg03iLvBPI/LqRZZx8s84xgGy77oLR4rQuB1LH1W4GyXFLrI0q90Uy4mdYNghfrFXBZGnPL77CIFGj713RGk0QhxDnec4Esl/v7Y4Giep8GsGgCHSfw3AVLdAUr1y6JlCdeO2MJ9euict+zfotsl0ziZhJ6EqYg8XkGBh5ooDEltMAwcgCih/EtY7Xie2ndh/zYRKfy/axgU0T5B/Nkn5Wgsj7vIg1yjJ94f7RKiTEivk2mrBR3gOF9y1p7mi7LDv5Xa7rcHoVVG2JMbYOUaInwlWLBYBCBg9zn3tmBX4ZxVU=";
	/**
	 * last time the calibration toast was shown, this avoids too many toast shown when compass needs calibration
	 */
	private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();

	@Override
	public String getARchitectWorldPath() {
		return this.EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL;
	}

	@Override
	public String getActivityTitle() {
		return (getIntent().getExtras() != null && getIntent().getExtras().get(
				EXTRAS_KEY_ACTIVITY_TITLE_STRING) != null) ? getIntent()
				.getExtras().getString(EXTRAS_KEY_ACTIVITY_TITLE_STRING)
				: "Test-World";
	}

	@Override
	public int getContentViewId() {
		return R.layout.wikitude;
	}

	@Override
	public int getArchitectViewId() {
		return R.id.architectView;
	}
	
	@Override
	public String getWikitudeSDKLicenseKey() {
		return this.WIKITUDE_SDK_KEY;
	}
	
	@Override
	public SensorAccuracyChangeListener getSensorAccuracyListener() {
		return new SensorAccuracyChangeListener() {
			@Override
			public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
				if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && SampleCamActivity.this != null && !SampleCamActivity.this.isFinishing() && System.currentTimeMillis() - SampleCamActivity.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
					//Toast.makeText( SampleCamActivity.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG ).show();
					SampleCamActivity.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
				}
			}
		};
	}

	@Override
	public ArchitectUrlListener getUrlListener() {
		return new ArchitectUrlListener() {

			@Override
			public boolean urlWasInvoked(String uriString) {
				// by default: no action applied when url was invoked
				return false;
			}
		};
	}

	@Override
	public ILocationProvider getLocationProvider(final LocationListener locationListener) {
		return new LocationProvider(this, locationListener);
	}
	
	@Override
	public float getInitialCullingDistanceMeters() {
		// you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
		return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
	}

}
