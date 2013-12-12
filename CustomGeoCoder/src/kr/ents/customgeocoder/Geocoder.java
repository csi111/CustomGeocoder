package kr.ents.customgeocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.ents.customgeocoder.data.LocationData;
import android.content.Context;
import android.location.Address;
import android.util.Log;

public class Geocoder {

	private Context mContext;
	private Locale mLocale;
	private Mode mMode;
	


	public Geocoder(Context context) {
		this(context, Locale.getDefault(), Mode.NO_ASYNC);
	}
	
	public Geocoder(Context context, Locale locale){
		this(context, locale, Mode.NO_ASYNC);
	}
	
	public Geocoder(Context context, Locale locale, Mode mode){
		mContext = context;
		mLocale = locale;
		mMode = mode;
	}
	
	
	public List<Address> getFromLocation(double latitude, double longitude, int maxResults) {
		//TODO getFromLocation
		
		android.location.Geocoder geocoder = new android.location.Geocoder(mContext);
		List<Address> addrs = new ArrayList<Address>();
		try{
			addrs = geocoder.getFromLocation(latitude, longitude, maxResults);
			addrs = null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			addrs = null;
		}
		addrs = null;
		if(addrs == null){
			Log.d("TEST" , "get Address");
			GeocoderWorker worker = new GeocoderWorker(mContext, mMode, mLocale);
			worker.setMaxResult(maxResults);
			addrs = worker.execute(new LocationData(latitude, longitude));
			
		}
			
		
		return addrs;
	}
	
	
	public List<Address> getFromLocationName(String locationName, int maxResults) {
		//TODO getFromLocationName
		android.location.Geocoder geocoder = new android.location.Geocoder(mContext);
		List<Address> addrs = new ArrayList<Address>();
		try{
			addrs = geocoder.getFromLocationName(locationName, maxResults);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			addrs = null;
		}
		addrs = null;
		if(addrs == null){
			Log.d("TEST" , "get Address");
			GeocoderWorker worker = new GeocoderWorker(mContext, mMode, mLocale);
			worker.setMaxResult(maxResults);
			addrs = worker.execute(locationName);
			
		}
		
		return addrs;
	}
	
	/**
	 * @param mode
	 */
	public void setMode(Mode mode){
		mMode = mode;
	}
	
	public static enum Mode{
		NO_ASYNC,
		ASYNC_HANDLER
	}
}
