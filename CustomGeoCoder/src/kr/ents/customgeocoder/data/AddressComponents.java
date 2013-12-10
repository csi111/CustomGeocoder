package kr.ents.customgeocoder.data;

import java.util.ArrayList;
import java.util.List;

public class AddressComponents {

	private String mLong_name;
	private String mShort_name;
	private List<String> mTypes;
	
	/**
	 * Add Type
	 * @param type
	 */
	public void addListType(String type)
	{
		if(mTypes == null)
		{
			mTypes = new ArrayList<String>();
		}
		mTypes.add(type);
	}
	
	/**
	 * return Long_name if types contains type
	 * @param type
	 * @return
	 */
	public String getLong_name(String type)
	{
		if(mTypes.contains(type)){
			return mLong_name;
		}
		else{
			return null;
		}
	}
	
	public String getShort_name(String type)
	{
		if(mTypes.contains(type)){
			return mShort_name;
		}
		else{
			return null;
		}
	}

	public String getLong_name() {
		return mLong_name;
	}

	public void setLong_name(String long_name) {
		this.mLong_name = long_name;
	}

	public String getShort_name() {
		return mShort_name;
	}

	public void setShort_name(String short_name) {
		this.mShort_name = short_name;
	}

	public List<String> getTypes() {
		return mTypes;
	}

	public void setTypes(List<String> types) {
		this.mTypes = types;
	}
	
	public boolean hasTypes(String type)
	{
		return mTypes.contains(type) ? true : false;
	}
	

}
