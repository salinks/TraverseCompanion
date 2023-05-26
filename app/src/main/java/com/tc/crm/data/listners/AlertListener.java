package com.tc.crm.data.listners;

import androidx.fragment.app.Fragment;


public interface AlertListener {
    void onPositiveClick(Fragment alertFragment);

    void onNegativeClick(Fragment alertFragment);
}
