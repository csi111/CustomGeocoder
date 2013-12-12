package kr.ents.customgeocoder;

public interface GeocoderCallbackListener {
	public void onCallback(Object result);
	public void onError(int resultCode, String error);
	
}
