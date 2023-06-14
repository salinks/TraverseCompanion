package com.tc.crm.ui.contents.sourceCategory;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.sourceCategory.SourceCategoryResponse;

public interface SourceCategoryView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

    void onSourceCategoryResponse(SourceCategoryResponse body);
}
