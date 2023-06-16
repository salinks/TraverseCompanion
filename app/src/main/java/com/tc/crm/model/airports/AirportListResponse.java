package com.tc.crm.model.airports;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AirportListResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("AirportList")
	public ArrayList<AirportItem> airport;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;
}