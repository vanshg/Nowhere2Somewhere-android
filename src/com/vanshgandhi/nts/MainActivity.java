package com.vanshgandhi.nts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;

public class MainActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
	
	private Location loc, lastLoc;
	double lat, lon;
	Button button;
	EditText editText;
	String destination;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.editText1);
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener()
		{
		    public void onLocationChanged(Location location)
		    {
		      loc = location;
		      
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		};
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		lastLoc = locationManager.getLastKnownLocation(locationProvider);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        destination = editText.getText().toString();
		        System.out.println(destination);
		        sendText();
		    }
		});
		
		
		//locationClient = new LocationClient(this, this, this);
	}
	

	public void sendText()
	{
		//double lat = loc.getLatitude(), lon = loc.getLongitude();
		String message = lastLoc.getLatitude() + ", " + lastLoc.getLongitude() + "|" + destination;
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("message", message);
		clipboard.setPrimaryClip(clip);
		try
		{
			SmsManager.getDefault().sendTextMessage("+17076745678", null, message, null, null);
		}
		catch (Exception e)
		{
			AlertDialog.Builder alertDialogBuilder = new
			AlertDialog.Builder(this);
			AlertDialog dialog = alertDialogBuilder.create();
			dialog.setMessage(e.getMessage());
			dialog.show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onConnected(Bundle dataBundle)
	{
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

    }

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
