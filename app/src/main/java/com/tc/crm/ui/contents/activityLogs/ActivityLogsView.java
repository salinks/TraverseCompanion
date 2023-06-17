package com.tc.crm.ui.contents.activityLogs;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.activityLogs.ActivityLogsResponse;
import com.tc.crm.model.amenities.AmenitiesResponse;

public interface ActivityLogsView {


    void onError(String msg);

    void onCommonResult(CommonResult body);


    void onActivityLogsResponse(ActivityLogsResponse body);
}
