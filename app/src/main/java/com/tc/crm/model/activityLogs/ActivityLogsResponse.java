package com.tc.crm.model.activityLogs;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ActivityLogsResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;

	@SerializedName("activityLogs")
	public ArrayList<ActivityLogsItem> activityLogs;
}