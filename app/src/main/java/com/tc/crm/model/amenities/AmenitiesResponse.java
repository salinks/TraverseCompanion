package com.tc.crm.model.amenities;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AmenitiesResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;

	@SerializedName("Amenities")
	public ArrayList<AmenitiesItem> amenities;
}