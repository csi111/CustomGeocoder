package kr.ents.customgeocoder;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;

public class Geocoder {

	private Context mContext;
	private Locale mLocale;
	private Mode mMode;
	
	public Geocoder(Context context, Locale locale) {
		mContext = context;
		mLocale = locale;
		mMode = Mode.NO_ASYNC;
	}
	
	public Geocoder(Context context, Locale locale, Mode mode){
		mContext = context;
		mLocale = locale;
		mMode = mode;
	}
	
	public Geocoder(Context context){
		mContext = context;
		mLocale = Locale.getDefault();
		mMode = Mode.NO_ASYNC;
	}
	

	public List<Address> getFromLocation(double latitude, double longitude, int maxResults) {
		//TODO getFromLocation
		return null;
	}
	
	
	public List<Address> getFromLocationName(String locationName, int maxResults) {
		//TODO getFromLocationName
		return null;
	}
	
	
	/**
	 * @param mode
	 */
	public void setMode(Mode mode){
		mMode = mode;
	}
	
	public enum Mode{
		NO_ASYNC,
		ASYNC_TASK,
		ASYNC_HANDLER
	}
}
