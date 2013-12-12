package kr.ents.customgeocoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.ents.customgeocoder.Geocoder.Mode;
import kr.ents.customgeocoder.config.Config;
import kr.ents.customgeocoder.config.Constants;
import kr.ents.customgeocoder.data.AddressComponents;
import kr.ents.customgeocoder.data.GeocoderData;
import kr.ents.customgeocoder.data.LocationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.text.TextUtils;

public class GeocoderWorker<T> {
	
	private final String ADDRESS = "address";
	private final String LAT_LNG = "latlng";
	private final String SENSOR = "sensor";
	private final String LANGUAGE = "language";
	
	
	private Context mContext;
	private String 	mUrl;
	private Locale	mLocale;
	private Mode	mMode;
	private int 	mMaxResult;
	

	private GetLocationThread xThread;
	
	private GeocoderCallbackListener mListener;
	
	public GeocoderWorker(Context context){
		this(context, Mode.ASYNC_HANDLER, Locale.getDefault());
		
	}
	
	public GeocoderWorker(Context context, Mode mode, Locale locale){
		mLocale = locale;
		mMode = mode;
		mMaxResult = 1;
	}
	
	public Mode getMode(){
		cancel();
		return mMode;
	}
		
	public List<T> execute(T data)
	{	//TODO Execute get geocoder data
		setUrl(data);
		List<T> addrs = (List<T>) new ArrayList<Address>();
		switch(mMode)
		{
			case NO_ASYNC:
				addrs = getAddressList();
				if(mListener != null)
					mListener.onCallback(addrs);
				break;
			case ASYNC_HANDLER:
				xThread = new GetLocationThread();
				xThread.start();
				
				try{
					xThread.join();
					addrs = xThread.getResult();
				}
				catch(InterruptedException e){
					mListener.onError(999, e.getMessage());
					addrs = null;
				}		
				break;
		}
		return (List<T>) addrs;
	
	}
		
	public void cancel()
	{
		//TODO Thread Running cancel && Network Cancel
		if(xThread != null && xThread.isAlive()){
			xThread.quit();
		}
	}
	
	
	public void setUrl(T data)
	{
		StringBuffer strBuffer = new StringBuffer();
		if(data instanceof LocationData)
		{
			strBuffer.append(Constants.PREFIX_URL)
			.append(GeocoderUtil.setParam(LAT_LNG, 
					((LocationData) data).getLatitude()+","
					+((LocationData) data).getLongitude(), true))
			.append(GeocoderUtil.setParam(SENSOR, 
					String.valueOf(GeocoderUtil.getGPSEnabled(mContext)), false))
			.append(GeocoderUtil.setParam(LANGUAGE, mLocale.toString(), false));
		}
		else if(data instanceof String)
		{
			try {
				strBuffer.append(Constants.PREFIX_URL)
				.append(GeocoderUtil.setParam(ADDRESS, 
						URLEncoder.encode(data.toString(), "UTF-8"), true))
				.append(GeocoderUtil.setParam(SENSOR, 
						String.valueOf(GeocoderUtil.getGPSEnabled(mContext)), false))
				.append(GeocoderUtil.setParam(LANGUAGE, mLocale.toString(), false));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				mListener.onError(Config.ENCODING_ERROR, e.getMessage());
			}
		}
		mUrl = strBuffer.toString();
	}
	
	
	/**
	 * correspond Google Map Geocoder API
	 * return address datajson_  
	 * @return string
	 */
	private String connect()
	{
		URL url;
		StringBuilder strBuilder = new StringBuilder();
		
		try {
			url = new URL(mUrl);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			if(conn!=null) {
				conn.setConnectTimeout(10000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				int statusCode = conn.getResponseCode();
				
				if(statusCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line = null;
					while((line=br.readLine())!=null) {
						strBuilder.append(line+"\n");
					}
					br.close();
					conn.disconnect();
					
					return strBuilder.toString();
					
				}
				else {
					//TODO Failed correspond Network
					mListener.onError(HttpURLConnection.HTTP_NOT_FOUND, "Failed correspond network");
					return null;
				}
			}
			else {
				//TODO Failed correspond Network
				return null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			mListener.onError(HttpURLConnection.HTTP_BAD_REQUEST,e.getMessage());
			return null;
		}catch (ProtocolException e) {
			e.printStackTrace();
			mListener.onError(HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
			return null;
		}catch (IOException e) {
			e.printStackTrace();
			mListener.onError(Config.HTTP_ERROR_ETC, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Geocode Data Parsing
	 * Json Parser Type
	 * @param _json
	 */
	private ArrayList<GeocoderData> parse(String _json)
	{
		try 
		{
			ArrayList<GeocoderData> DataList = new ArrayList<GeocoderData>();
			JSONObject json = new JSONObject(_json);
			String mStatus = json.getString(Constants.TAG_STATUS);					//Status Code
			JSONArray resultDatas = json.getJSONArray(Constants.TAG_RESULTS);		//Result json data
			
			if(mStatus.equals(Constants.STAUTS_OK))
			{
				for(int i = 0 ; i < resultDatas.length() ; i++)
				{
					GeocoderData geocoderData = new GeocoderData();
					
					JSONObject resultJson = resultDatas.getJSONObject(i);
					JSONArray  addCompJsonArray = resultJson.getJSONArray(Constants.TAG_ADDRESS_COMPONENTS);
					String fmtAdd = resultJson.getString(Constants.TAG_FORMATTED_ADDRESS);
					JSONArray typesJsonArray = resultJson.optJSONArray(Constants.TAG_ADDRESS_COMPONENTS_TYPES);
					JSONObject geometryJson = resultJson.getJSONObject(Constants.TAG_GEOMETRY);
					
					for(int addressIdx = 0 ; addressIdx < addCompJsonArray.length(); addressIdx++)
					{
						AddressComponents data = new AddressComponents();	//addressComponents Data
						
						JSONObject addressJson = addCompJsonArray.getJSONObject(addressIdx); 
						JSONArray addTypesJsonArray = addressJson.optJSONArray(Constants.TAG_ADDRESS_COMPONENTS_TYPES);
						
						
						data.setLong_name(addressJson.getString(Constants.TAG_ADDRESS_COMPONENTS_LONG_NAME));
						data.setShort_name(addressJson.getString(Constants.TAG_ADDRESS_COMPONENTS_SHORT_NAME));

						for(int typesIdx = 0; typesIdx < addTypesJsonArray.length() ; typesIdx++)
						{
							data.addListType(addTypesJsonArray.getString(typesIdx));
						}

						geocoderData.addComponentData(data);
					}
					for(int typesIdx = 0; typesIdx < typesJsonArray.length() ; typesIdx++)
					{
						geocoderData.addTypes(typesJsonArray.getString(typesIdx));	//types 
					}
					LocationData location  = new LocationData();
					LocationData northeast = new LocationData();
					LocationData southwest = new LocationData();

					JSONObject locationJson = geometryJson.getJSONObject(Constants.TAG_GEOMETRY_LOCATION);
					JSONObject viewPortsJson = geometryJson.getJSONObject(Constants.TAG_GEOMETRY_VIEWPORT);
					JSONObject northeastJson = viewPortsJson.getJSONObject(Constants.TAG_GEOMETRY_VIEWPORT_NORTHEAST);
					JSONObject southwestJson = viewPortsJson.getJSONObject(Constants.TAG_GEOMETRY_VIEWPORT_SOUTHWEST);
					
					//Viewports notheast
					northeast.setLocation(northeastJson.getString(Constants.TAG_LATITUDE), northeastJson.getString(Constants.TAG_LONGITUDE));
					//Viewports southwest
					southwest.setLocation(southwestJson.getString(Constants.TAG_LATITUDE), southwestJson.getString(Constants.TAG_LONGITUDE));
					//Location
					location.setLocation(locationJson.getString(Constants.TAG_LATITUDE), locationJson.getString(Constants.TAG_LONGITUDE));
					
					//Location Type
					geocoderData.setLocationType(geometryJson.getString(Constants.TAG_GEOMETRY_LOCATION_TYPE));
					
					geocoderData.putViewportData(Constants.TAG_GEOMETRY_VIEWPORT_NORTHEAST, northeast); //Viewports northeast
					geocoderData.putViewportData(Constants.TAG_GEOMETRY_VIEWPORT_SOUTHWEST, southwest); //Viewports southwest
					//set Location
					geocoderData.setLocation(location);
					//formatted_address
					geocoderData.setFormatted_address(fmtAdd);	
					
					DataList.add(geocoderData);
				}
			}
			
			return DataList;
		}
		catch(JSONException e)
		{
			mListener.onError(Config.PARSER_ERORR, e.getMessage());
			return null;
		}
		catch(Exception e)
		{
			mListener.onError(Config.BASIC_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * return AddressList from Location
	 * @return List<Address>
	 */
	private List<T> getAddressList()
	{
		
		String jsonData;
		ArrayList<GeocoderData> geocodeDataList = null;
		List<Address> addrs = new ArrayList<Address>();
		
		if(!TextUtils.isEmpty(mUrl))
		{
			jsonData = connect();
			if(!TextUtils.isEmpty(jsonData))
				geocodeDataList = parse(jsonData);
		}
		
		if(geocodeDataList != null & !geocodeDataList.isEmpty()){
			
			for(int idx = 0; idx < (mMaxResult < geocodeDataList.size() ? mMaxResult : geocodeDataList.size()); idx++){
				Address addr = new Address(mLocale);
				GeocoderData data = geocodeDataList.get(idx);
				addr.setAddressLine(0, data.getFormatted_address());
				addr.setCountryName(data.getAddressLongname(Constants.TYPE_COUNTRY));
				addr.setCountryCode(data.getAddressShortname(Constants.TYPE_COUNTRY));
				if(data.getAddressComponentsDatas().size() > 1){
					addr.setFeatureName(data.getAddressComponentsDatas().get(0).getLong_name());
					addr.setThoroughfare(data.getAddressComponentsDatas().get(1).getLong_name());
				}
				addr.setAdminArea(data.getAddressLongname(Constants.TYPE_ADMINISTRATIVE_AREA_LEVEL_1));
				addr.setPostalCode(data.getAddressLongname(Constants.TYPE_POSTAL_CODE));
				addr.setLatitude(data.getLocation().getLatitude());
				addr.setLongitude(data.getLocation().getLongitude());
				
				addrs.add(addr);
			}
		}
			
		return (List<T>) addrs;
	}
	
	private class GetLocationThread extends Thread {
		
		private volatile boolean mQuit = false;
		
		private List<T> resultData;
		
		public void quit() {
			mQuit = true;
			interrupt();
		}
		  
		@Override
		public void run() {
			
			if(mQuit){
				return;
			}

			resultData = getAddressList(); 
			if(mListener != null){
				mListener.onCallback(resultData);
			}
			
		}
		public List<T> getResult()
		{
			return resultData;
		}
	}
	
	/**
	 * Set max result data size
	 * @param result
	 */
	protected void setMaxResult(int result)
	{
		cancel();
		mMaxResult = result;
	}
	
	/**
	 * Set callback Listener
	 * @param listener
	 */
	protected void setLocationListener(GeocoderCallbackListener listener)
	{
		cancel();
		this.mListener = listener;
	}
	
		
}
