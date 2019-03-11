package com.example.applymapping.actionunits;

import android.app.Activity;
import android.widget.TextView;

public interface ActionUnit {

    String getActionName();

    void run(Activity activity);

    default void updateOutputView(Activity activity, int id, String result) {
        ((TextView)activity.findViewById(id)).setText(getActionName() + " " + result);
    }
}
