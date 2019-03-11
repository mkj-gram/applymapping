package com.example.applymapping;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.applymapping.actionunits.Minification;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class MinifiedNameInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void minificationNameTest() {
        onView(withId(R.id.btnTestMinification)).perform(click());
        // This is the baseline result.
        String baseline = getOutputText();
        assertThat(baseline).isEqualTo(
                Minification.INNER_MINIFICATION.ACTION_NAME
                        + " " + Minification.INNER_MINIFICATION.RESULT);
        MainActivity mainActivity = activityRule.getActivity();
        mainActivity.runOnUiThread(() -> {
            mainActivity.performActionUnit(new Minification(mainActivity));
            assertThat(getOutputText()).isEqualTo(baseline);
            new Minification(mainActivity).callOnClick();
            assertThat(getOutputText()).isEqualTo(baseline);
        });
    }

    private String getOutputText() {
        return ((TextView)activityRule.getActivity().findViewById(R.id.txtOutput)).getText().toString();
    }
}
