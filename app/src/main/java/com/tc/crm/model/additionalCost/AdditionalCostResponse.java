package com.tc.crm.model.additionalCost;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdditionalCostResponse{

	@SerializedName("AdditionalCosts")
	public ArrayList<AdditionalCostsItem> additionalCosts;

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;
}