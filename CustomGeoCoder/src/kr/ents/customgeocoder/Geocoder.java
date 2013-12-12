package kr.ents.customgeocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.ents.customgeocoder.data.LocationData;
import android.content.Context;
import android.location.Address;

public class Geocoder {

	private Context mContext;
	private Locale mLocale;
	private Mode mMode;
	
	private GeocoderCallbackListener mListener;

	private GeocoderWorker mWorker;
	
	public Geocoder(Context context) {
		this(context, Locale.getDefault(), Mode.ASYNC_HANDLER);
	}
	
	public Geocoder(Context context, Locale locale){
		this(context, locale, Mode.ASYNC_HANDLER);
	}
	
	public Geocoder(Context context, Locale locale, Mode mode){
		mContext = context;
		mLocale = locale;
		mMode = mode;
		mWorker = new GeocoderWorker(mContext);
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
		if(addrs == null){
			mWorker = new GeocoderWorker(mContext, mMode, mLocale);
			mWorker.setMaxResult(maxResults);
			mWorker.setLocationListener(mListener);
			addrs = mWorker.execute(new LocationData(latitude, longitude));
			
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
		if(addrs == null){
			mWorker = new GeocoderWorker(mContext, mMode, mLocale);
			mWorker.setMaxResult(maxResults);
			mWorker.setLocationListener(mListener);
			addrs = mWorker.execute(locationName);
			
		}
		
		return addrs;
	}
	
	public void clear(){
		
		mWorker.cancel();
		mWorker = null;
		mLocale = null;
		mListener = null;
		mMode = null;
		System.gc();
	}
	
	public void setCallbackListener(GeocoderCallbackListener listener){
		mListener = listener;
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
