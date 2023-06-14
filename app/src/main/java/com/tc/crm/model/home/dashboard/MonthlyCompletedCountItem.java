package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class MonthlyCompletedCountItem{

	@SerializedName("total")
	public int total;

	@SerializedName("month")
	public int month;
}