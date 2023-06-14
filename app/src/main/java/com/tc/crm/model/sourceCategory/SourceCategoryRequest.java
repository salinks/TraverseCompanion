package com.tc.crm.model.sourceCategory;

import com.google.gson.annotations.SerializedName;

public class SourceCategoryRequest{

	@SerializedName("inputRequired")
	public String inputRequired;

	@SerializedName("hasValues")
	public String hasValues;

	@SerializedName("userId")
	public String userId;

	@SerializedName("scName")
	public String scName;
	@SerializedName("scId")

	public String scId;
}