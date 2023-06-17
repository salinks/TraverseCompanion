package com.tc.crm.model.amenities;

import com.google.gson.annotations.SerializedName;

public class AmenitiesRequest{

	@SerializedName("amenityName")
	public String amenityName;

	@SerializedName("userId")
	public String userId;

	@SerializedName("aId")
	public String aId;
}