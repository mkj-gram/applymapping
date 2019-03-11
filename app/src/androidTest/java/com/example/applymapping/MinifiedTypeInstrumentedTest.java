package com.example.applymapping;

import android.app.Activity;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.applymapping.actionunits.ActionUnit;
import com.example.applymapping.actionunits.ActionUnitImpl;
import com.example.applymapping.actionunits.Minification;
import com.example.applymapping.actionunits.TempAction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class MinifiedTypeInstrumentedTest {

    public static final String IMPLEMENTED_INTERFACE_RESULT = "MinifiedTypeInstrumentedTest";
    public static final String FUNCTIONAL_INTERFACE_IMPL = "Anonymously";
    public static final String FUNCTIONAL_INTERFACE_MESSAGE = "42";

    Minification field;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void minificationTypeTest() {
        onView(withId(R.id.btnTestMinification)).perform(click());
        // This is the baseline result.
        String baseline = getOutputText();
        assertThat(baseline).isEqualTo(Minification.ACTION_NAME + " " + Minification.RESULT);

        MainActivity mainActivity = activityRule.getActivity();

        field = getAsArray(new Minification(mainActivity))[0];
        mainActivity.runOnUiThread(() -> {
            mainActivity.performActionUnit(field);
            assertThat(getOutputText()).isEqualTo(baseline);

            MinificationExtended extended = new MinificationExtended(mainActivity);
            mainActivity.performActionUnit(extended);
            assertThat(getOutputText()).isEqualTo(baseline);

            extended.setOutput(IMPLEMENTED_INTERFACE_RESULT);
            assertThat(getOutputText()).isEqualTo(Minification.ACTION_NAME + " " + IMPLEMENTED_INTERFACE_RESULT + "Extended" + extended.getCount());

            MinificationExtended2 extended2 = new MinificationExtended2(mainActivity);
            extended2.setOutput(IMPLEMENTED_INTERFACE_RESULT);
            assertThat(getOutputText()).isEqualTo(Minification.ACTION_NAME + " " + IMPLEMENTED_INTERFACE_RESULT + "Extended100Extended1");

            mainActivity.performActionUnit(new OtherActionUnit());
            assertThat(getOutputText()).isEqualTo(IMPLEMENTED_INTERFACE_RESULT + " " + IMPLEMENTED_INTERFACE_RESULT);

            performOnList(Arrays.asList(getAsArray(new Minification(mainActivity))));
            assertThat(getOutputText()).isEqualTo(baseline);

            performOnListLambda(Arrays.asList(getAsArray(new Minification(mainActivity))));
            assertThat(getOutputText()).isEqualTo(baseline);

            // Fails in 3.4.
            performOnList(Arrays.asList(getAsArray(new Minification(mainActivity))), mainActivity);
            assertThat(getOutputText()).isEqualTo(baseline);

            mainActivity.perform(action -> new ActionUnit() {
                @Override
                public String getActionName() {
                    return FUNCTIONAL_INTERFACE_IMPL;
                }

                @Override
                public void run(Activity activity) {
                    ActionUnit.super.updateOutputView(activity, R.id.txtOutput, action.message);
                }
            }, new TempAction(FUNCTIONAL_INTERFACE_MESSAGE));
            assertThat(getOutputText()).isEqualTo(FUNCTIONAL_INTERFACE_IMPL + " " + FUNCTIONAL_INTERFACE_MESSAGE);
        });
    }

    private String getOutputText() {
        return ((TextView)activityRule.getActivity().findViewById(R.id.txtOutput)).getText().toString();
    }

    private Minification[] getAsArray(Minification arg) {
        return new Minification[] { arg };
    }

    private void performOnList(List<Minification> minifications) {
        for (Minification min : minifications) {
            min.callOnClick();
        }
    }

    private void performOnListLambda(List<Minification> minifications) {
        minifications.forEach(x -> x.callOnClick());
    }

    private void performOnList(List<? extends ActionUnitImpl> actionUnits, Activity activity) {
        actionUnits.forEach(a -> a.run(activity));
    }
}

class MinificationExtended extends Minification {

    protected int count = 0;

    public MinificationExtended(MainActivity activity) {
        super(activity);
    }

    public int getCount() {
        return count;
    }

    @Override
    public void setOutput(String text) {
        count++;
        super.setOutput(text + "Extended" + count);
    }
}

class MinificationExtended2 extends MinificationExtended {

    public MinificationExtended2(MainActivity activity) {
        super(activity);
    }

    @Override
    public void setOutput(String text) {
        super.setOutput(text + "Extended100");
    }
}

class OtherActionUnit implements ActionUnit {

    @Override
    public String getActionName() {
        return MinifiedTypeInstrumentedTest.IMPLEMENTED_INTERFACE_RESULT;
    }

    @Override
    public void run(Activity activity) {
        updateOutputView(activity, R.id.txtOutput, MinifiedTypeInstrumentedTest.IMPLEMENTED_INTERFACE_RESULT);
    }
}