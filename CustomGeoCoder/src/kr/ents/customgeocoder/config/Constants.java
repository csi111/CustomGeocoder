package kr.ents.customgeocoder.config;

public class Constants {
	
	//API Address
	public static final String PREFIX_URL = "http://maps.googleapis.com/maps/api/geocode/json";
	
	//JSON TAG
	public static final String TAG_STATUS								= "status";
	public static final String TAG_RESULTS 								= "results";
	public static final String TAG_ADDRESS_COMPONENTS					= "address_components";
	public static final String TAG_ADDRESS_COMPONENTS_LONG_NAME			= "long_name";
	public static final String TAG_ADDRESS_COMPONENTS_SHORT_NAME 		= "short_name";
	public static final String TAG_ADDRESS_COMPONENTS_TYPES 			= "types";
	public static final String TAG_FORMATTED_ADDRESS 					= "formatted_address";
	public static final String TAG_GEOMETRY 							= "geometry";
	public static final String TAG_GEOMETRY_LOCATION 					= "location";
	public static final String TAG_GEOMETRY_LOCATION_TYPE 				= "location_type";
	public static final String TAG_GEOMETRY_VIEWPORT 					= "viewport";
	public static final String TAG_GEOMETRY_VIEWPORT_NORTHEAST 			= "northeast";
	public static final String TAG_GEOMETRY_VIEWPORT_SOUTHWEST 			= "southwest";
	public static final String TAG_LATITUDE 							= "lat";
	public static final String TAG_LONGITUDE 							= "lng";
	
	//Status Codes
	public static final String STAUTS_OK								= "OK";
	public static final String STATUS_ZERO_RESULTS						= "ZERO_RESULTS";
	public static final String STATUS_OVER_QUERY_LIMIT					= "OVER_QUERY_LIMIT";
	public static final String STAUTS_REQUEST_DENIED					= "REQUEST_DENIED";
	public static final String STATUS_INVALID_REQUEST					= "INVALID_REQUEST";
	public static final String STAUTS_UNKNOWN_ERROR						= "UNKNOWN_ERROR";
	
	
	//Address Component Types
	public static final String TYPE_STREET_ADDRESS 						= "street_address";
	public static final String TYPE_ROUTE 								= "route";
	public static final String TYPE_INTERSECTION 						= "intersection";
	public static final String TYPE_POLITICAL 							= "political";
	public static final String TYPE_COUNTRY 							= "country";
	public static final String TYPE_ADMINISTRATIVE_AREA_LEVEL_1 		= "administrative_area_level_1";
	public static final String TYPE_ADMINISTRATIVE_AREA_LEVEL_2 		= "administrative_area_level_2";
	public static final String TYPE_ADMINISTRATIVE_AREA_LEVEL_3 		= "administrative_area_level_3";
	public static final String TYPE_COLLOQUIAL_AREA 					= "colloquial_area";
	public static final String TYPE_LOCALITY 							= "locality";
	public static final String TYPE_SUBLOCALITY 						= "sublocality";
	public static final String TYPE_NEIGHBORHOOD 						= "neighborhood";
	public static final String TYPE_PREMISE 							= "premise";
	public static final String TYPE_SUBPREMISE 							= "subpremise";
	public static final String TYPE_POSTAL_CODE 						= "postal_code";
	public static final String TYPE_NATURAL_FEATURE 					= "natural_feature";
	public static final String TYPE_AIRPORT 							= "airport";
	public static final String TYPE_PARK 								= "park";
	public static final String TYPE_POINT_OF_INTEREST 					= "point_of_interest";
	
	
	
}
