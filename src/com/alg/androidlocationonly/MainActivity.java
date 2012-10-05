package com.alg.androidlocationonly;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
	
	private TextView longitudeLabel;
	private TextView latitudeLabel;
	private TextView errorLabel;
	
	private LocationManager lm;
	private LocationListener ll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        longitudeLabel = (TextView)findViewById(R.id.longitude_label);
        latitudeLabel = (TextView)findViewById(R.id.latitude_label);
        errorLabel = (TextView)findViewById(R.id.error_label);
        
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        ll = new mylocationlistener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	if(lm==null)
    	{
    		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    	}
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	lm.removeUpdates(ll);
    	lm = null;
    }
    
    private class mylocationlistener implements LocationListener {
    	public void onLocationChanged(Location location) {
    		//if(location!=null) {
    			//Log.d("LOCATION CHANGED", location.getLatitude()+"");
    			//Log.d("LOCATION CHANGED", location.getLongitude()+"");
    			//latitudeLabel.setText(getString(R.string.latitude_string) + " " + location.getLatitude());
    			//longitudeLabel.setText(getString(R.string.longitude_string) + " " + location.getLongitude());
    			//Toast.makeText(MainActivity.this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
    		//}
    	}
    	public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
    
    public void lastLocationButton_Click(View button)
    {
    	Criteria criteria = new Criteria();
    	criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	
    	String provider = lm.getBestProvider(criteria, true);
    	Location location = lm.getLastKnownLocation(provider);
    	
    	if (location!=null)
    	{
    		errorLabel.setText("");
    		latitudeLabel.setText(getString(R.string.latitude_string) + " " + location.getLatitude());
    		longitudeLabel.setText(getString(R.string.longitude_string) + " " + location.getLongitude());
    	}
    	else
    	{
    		errorLabel.setText(getString(R.string.error_gps_no_location_string));
    	}
    }
}
