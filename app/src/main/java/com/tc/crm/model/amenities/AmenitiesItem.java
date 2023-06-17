package com.tc.crm.model.amenities;

import com.google.gson.annotations.SerializedName;

public class AmenitiesItem{

	@SerializedName("amenityName")
	public String amenityName;

	@SerializedName("aStatus")
	public int aStatus;

	@SerializedName("aId")
	public int aId;

	@SerializedName("createdOn")
	public String createdOn;


	public String actionName;
}