package com.ekeitho.clocksubtract;

import android.test.ActivityInstrumentationTestCase2;

import com.ekeitho.clocksubtract.ui.MainActivity;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Test Case for ClockSubtract2.0
 */
public class ClockTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mainActivity = null;
    TextView welcome_view = null;
    Date date = null;

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



    public void testBeginningScreen() throws Throwable {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                welcome_view.requestFocus();
            }
        });

        //will write a lot more test later
        //practicing with travis-ci & android
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                date = addSuccessiveDates(7,32,8,45,9,52,8);
                //should equal 4:39 PM
                //hours are in 24hr format
                assertEquals(date.getHours(), 16);
                assertEquals(date.getMinutes(), 39);

                date = addSuccessiveDates(7,0,12,0,1,0,8);
                assertEquals(date.getHours(), 4);
                assertEquals(date.getMinutes(), 0);

            }
        });


    }

    private Date addSuccessiveDates(int hour1, int min1, int hour2, int min2, int hour3, int min3,
                                    int hours_worked) {
        /* set up the dates */
        Date date1 = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                hour1,
                min1);
        Date date2 = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                hour2,
                min2);
        Date date3 = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                hour3,
                min3);

        /* add them to past_times hash map */
        mainActivity.addDates(date1, "First");
        mainActivity.addDates(date2, "Second");
        mainActivity.addDates(date3, "Third");
        mainActivity.passIntToActivity(hours_worked);

        /* calculate from the given dates */
        return mainActivity.calculate();
    }

}
