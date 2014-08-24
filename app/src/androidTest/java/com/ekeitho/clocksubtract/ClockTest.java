package com.ekeitho.clocksubtract;

import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

/**
 * Test Case for ClockSubtract2.0
 */
public class ClockTest extends ActivityInstrumentationTestCase2<MainActivity> {

    FragmentActivity mainActivity = null;
    TextView welcome_view = null;

    public ClockTest()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        welcome_view = (TextView) mainActivity.findViewById(R.id.welcome_text);

    }

    public void testBeginningScreen() {

        //will write a lot more test later
        //practicing with travis-ci & android
        assertEquals("Welcome to Clockout", welcome_view.getText().toString());

    }

}
