package com.tc.crm.model.clientDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdditionalCostItem{

	@SerializedName("approvedOn")
	public String approvedOn;

	@SerializedName("totalAmount")
	public int totalAmount;

	@SerializedName("entryRemarks")
	public String entryRemarks;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("createdBy")
	public int createdBy;

	@SerializedName("approvedBy")
	public int approvedBy;

	@SerializedName("aStatus")
	public int aStatus;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("items")
	public List<ItemsItem> items;

	@SerializedName("recId")
	public int recId;
}