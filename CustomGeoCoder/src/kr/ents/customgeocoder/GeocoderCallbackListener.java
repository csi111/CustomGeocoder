package kr.ents.customgeocoder;

public interface GeocoderCallbackListener<T> {
	public void onCallback(T result);
	public void onError(String error);
	
}
