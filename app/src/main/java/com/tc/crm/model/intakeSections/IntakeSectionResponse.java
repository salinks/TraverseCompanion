package com.tc.crm.model.intakeSections;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IntakeSectionResponse{

	@SerializedName("IntakeSections")
	public ArrayList<IntakeSectionsItem> intakeSections;

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;
}