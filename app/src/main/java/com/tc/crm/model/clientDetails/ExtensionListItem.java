package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class ExtensionListItem{

	@SerializedName("fromDate")
	public String fromDate;

	@SerializedName("approvedOn")
	public String approvedOn;

	@SerializedName("masterId")
	public int masterId;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("propertyCost")
	public int propertyCost;

	@SerializedName("noOfNights")
	public int noOfNights;

	@SerializedName("toDate")
	public String toDate;

	@SerializedName("extensionAmount")
	public int extensionAmount;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("recId")
	public int recId;

	@SerializedName("eStatus")
	public int eStatus;
}