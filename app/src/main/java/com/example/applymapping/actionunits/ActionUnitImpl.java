package com.example.applymapping.actionunits;

import android.view.View;

import com.example.applymapping.MainActivity;
import com.example.applymapping.R;

public abstract class ActionUnitImpl implements ActionUnit, View.OnClickListener {

    protected final MainActivity activity;

    public ActionUnitImpl(MainActivity activity) {
        this.activity = activity;
    }

    public void setOutput(String text) {
        updateOutputView(activity, R.id.txtOutput, text);
    }
}
