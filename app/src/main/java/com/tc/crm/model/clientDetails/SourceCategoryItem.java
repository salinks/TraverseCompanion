package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class SourceCategoryItem{

	@SerializedName("scStatus")
	public int scStatus;

	@SerializedName("inputRequired")
	public int inputRequired;

	@SerializedName("hasValues")
	public int hasValues;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("scId")
	public int scId;

	@SerializedName("scName")
	public String scName;
}