package com.example.applymapping.actionunits;

import android.app.Activity;
import android.view.View;

import com.example.applymapping.MainActivity;
import com.example.applymapping.R;

public class Minification extends ActionUnitImpl {

    public static final String ACTION_NAME = "Minification";
    public static final String RESULT = "Works!";
    public static final Minification INNER_MINIFICATION = new Minification(null);

    public Minification(MainActivity activity) {
        super(activity);
    }

    public void callOnClick() {
        onClick(null);
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }

    @Override
    public void run(Activity activity) {
        updateOutputView(activity, R.id.txtOutput, RESULT);
    }

    @Override
    public void onClick(View v) {
        setOutput(RESULT);
    }
}
