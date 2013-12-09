package kr.ents.customgeocoder.data;

public class LocationData {

	
	private double mLatitude;
	private double mLongitude;
	
	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}

	public LocationData(){
		
	}
	
	public LocationData(double latitude, double longitude) {
		mLatitude = latitude;
		mLongitude = longitude;
	}
	
	public LocationData(String latitude, String longtitude) {
		try{
			mLatitude = Double.parseDouble(latitude);
			mLongitude = Double.parseDouble(longtitude);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			mLatitude = 0.f;
			mLongitude = 0.f;
		}
	}
	

	public void setLocation(double latitude, double longitude){
		mLatitude = latitude;
		mLongitude = longitude;
	}
	
	public void setLocation(String latitude, String longtitude) {
		try{
			mLatitude = Double.parseDouble(latitude);
			mLongitude = Double.parseDouble(longtitude);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			mLatitude = 0.f;
			mLongitude = 0.f;
		}
	}
}
