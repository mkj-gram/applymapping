package com.example.applymapping.actionunits;

import android.app.Activity;
import android.view.View;

import com.example.applymapping.MainActivity;
import com.example.applymapping.R;

public class FunctionalInterface extends ActionUnitImpl implements FuncInf {

    private String message = "NotGenerated";
    public static final String ACTION_NAME = "FunctionalInterface";
    public static final String RESULT = "Generated";

    public FunctionalInterface(MainActivity activity) {
        super(activity);
    }

    @Override
    public void onClick(View v) {
        activity.perform(this, new TempAction(RESULT));
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }

    @Override
    public void run(Activity activity) {
        updateOutputView(activity, R.id.txtOutput, message);
    }


    @Override
    public ActionUnit generate(TempAction action) {
        message = action.message;
        return this;
    }
}
