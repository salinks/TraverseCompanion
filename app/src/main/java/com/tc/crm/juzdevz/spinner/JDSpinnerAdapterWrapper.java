package com.tc.crm.juzdevz.spinner;

import android.content.Context;
import android.widget.ListAdapter;


public class JDSpinnerAdapterWrapper extends JDSpinnerBaseAdapter {

    private final ListAdapter baseAdapter;

    JDSpinnerAdapterWrapper(
            Context context,
            ListAdapter toWrap,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment
    ) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment);
        baseAdapter = toWrap;
    }

    @Override public int getCount() {
        return baseAdapter.getCount() - 1;
    }

    @Override public Object getItem(int position) {
        return baseAdapter.getItem(position >= selectedIndex ? position + 1 : position);
    }

    @Override public Object getItemInDataset(int position) {
        return baseAdapter.getItem(position);
    }
}