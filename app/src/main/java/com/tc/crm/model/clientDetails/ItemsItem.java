package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("masterId")
	public int masterId;

	@SerializedName("cName")
	public String cName;

	@SerializedName("itemCost")
	public int itemCost;

	@SerializedName("cItemId")
	public int cItemId;

	@SerializedName("recId")
	public int recId;
}