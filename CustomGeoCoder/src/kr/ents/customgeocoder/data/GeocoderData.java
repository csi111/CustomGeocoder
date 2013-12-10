package kr.ents.customgeocoder.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeocoderData {
	private List<AddressComponents> addressComponentsDatas; 
	private String formatted_address;
	private LocationData location;
	private String locationType;
	private Map<String, LocationData> viewports;
	private List<String> types;
	
	public GeocoderData()
	{
		addressComponentsDatas = new ArrayList<AddressComponents>();
		viewports = new HashMap<String, LocationData>();
		types = new ArrayList<String>();
	}
	
	/**
	 * add addressComponent 
	 * @param data
	 */
	public void addComponentData(AddressComponents data)
	{
		if(addressComponentsDatas == null)
		{
			addressComponentsDatas = new ArrayList<AddressComponents>();
		}
		addressComponentsDatas.add(data);
	}
	/**
	 * get AddressComponent longnanme as same type
	 * @param type
	 * @return
	 */
	public String getAddressLongname(String type){
		for(AddressComponents addrData : addressComponentsDatas){
			if(addrData.hasTypes(type)) {
				return addrData.getLong_name();
			}
		}
		return null;	
	}
	/**
	 * get AddressComponent shortnanme as same type
	 * @param type
	 * @return
	 */
	public String getAddressShortname(String type){
		for(AddressComponents addrData : addressComponentsDatas){
			if(addrData.hasTypes(type)) {
				return addrData.getShort_name();
			}
		}
		return null;	
	}
	
	/**
	 * add viewport
	 * @param tag
	 * @param data
	 */
	public void putViewportData(String tag, LocationData data)
	{
		if(viewports == null)
		{
			viewports = new HashMap<String, LocationData>();
		}
		viewports.put(tag, data);
	}
	
	/**
	 * add address type
	 * @param type
	 */
	public void addTypes(String type)
	{
		if(types == null)
		{
			types = new ArrayList<String>();
		}
		
		types.add(type);
	}

	/**
	 * @return the addressComponentsDatas
	 */
	public List<AddressComponents> getAddressComponentsDatas() {
		return addressComponentsDatas;
	}

	/**
	 * @param addressComponentsDatas the addressComponentsDatas to set
	 */
	public void setAddressComponentsDatas(
			List<AddressComponents> addressComponentsDatas) {
		this.addressComponentsDatas = addressComponentsDatas;
	}

	/**
	 * @return the formatted_address
	 */
	public String getFormatted_address() {
		return formatted_address;
	}

	/**
	 * @param formatted_address the formatted_address to set
	 */
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	/**
	 * @return the location
	 */
	public LocationData getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationData location) {
		this.location = location;
	}

	/**
	 * @return the locationType
	 */
	public String getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return the viewports
	 */
	public Map<String, LocationData> getViewports() {
		return viewports;
	}

	/**
	 * @param viewports the viewports to set
	 */
	public void setViewports(Map<String, LocationData> viewports) {
		this.viewports = viewports;
	}

	/**
	 * @return the types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	
	
}
