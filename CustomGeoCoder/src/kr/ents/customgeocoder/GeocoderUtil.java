package kr.ents.customgeocoder;

import java.util.Locale;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GeocoderUtil {

	
	/**
	 * check Network status
	 * @param context
	 * @return
	 */
	private boolean getNetworkStatus(Context context)
	{
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] allNetworkInfo = conManager.getAllNetworkInfo();
		NetworkInfo currNetworkInfo;
		boolean anythingConnected = false;
		for (int i = 0; i < allNetworkInfo.length; i++) {
			currNetworkInfo = allNetworkInfo[i];
			if (currNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
				anythingConnected = true;
		}
		
		return anythingConnected;
	}
	
	/**
	 * Device Default Language
	 * @return
	 */
	public static String getDefaultLanguage()
	{
		return Locale.getDefault().toString();
	}
	
	/**
	 * check GPS usable 
	 * @param cont
	 * @return
	 */
	public static boolean getGPSEnabled(Context context)
	{
		LocationManager myLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean isGpsEnabled = myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		return isGpsEnabled;
	}
}
