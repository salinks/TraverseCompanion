package com.tc.crm.model.sourceCategory;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SourceCategoryResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;

	@SerializedName("SourceCategory")
	public ArrayList<SourceCategoryItem> sourceCategory;
}