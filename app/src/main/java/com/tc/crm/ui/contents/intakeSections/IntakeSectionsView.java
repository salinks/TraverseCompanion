package com.tc.crm.ui.contents.intakeSections;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.intakeSections.IntakeSectionResponse;

public interface IntakeSectionsView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

    void onIntakeSectionResponse(IntakeSectionResponse body);
}
