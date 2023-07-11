package com.tc.crm.model.clientList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ClientListResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("Filters")
	public Filters filters;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;

	@SerializedName("Clients")
	public List<ClientsItem> clients;
}