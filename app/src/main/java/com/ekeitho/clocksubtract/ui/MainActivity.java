package com.ekeitho.clocksubtract.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.ekeitho.clocksubtract.R;
import com.ekeitho.clocksubtract.service.ScheduleClient;
import com.neopixl.pixlui.components.textview.TextView;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends FragmentActivity implements ActivityCommunicator {

    private int gcyear, gcmonth, gcday, frag_num = 0, flag = 0, hours;
    private TextView view, sub_view;
    private SimpleDateFormat formatter;
    private Animation animation_fade;
    private ViewPager viewPager;
    private HashMap<String, Date> past_clocks = new HashMap<String, Date>();
    private ScheduleClient scheduleClient;
    private SharedPreferences prefs;

    @Override
    public HashMap<String, Date> getMap() {
        return past_clocks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* get shared preference for settings values */
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        /* Create a new service client and bind our activity to this service */
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        /* set up */
        formatter = new SimpleDateFormat("MMM d, h:mm a");
        view = (TextView) findViewById(R.id.welcome_text);
        view.setText(getString(R.string.welcome) + "\n" + prefs.getString("name_text", ""));
        sub_view = (TextView) findViewById(R.id.past_choices_textview);

        /* sets up the swipe fragment views */
        viewPager = (ViewPager) findViewById(R.id.pager);
        MultipleViewFragments multipleViewFragments = new MultipleViewFragments(getSupportFragmentManager());
        viewPager.setAdapter(multipleViewFragments);

        /* animation set up */
        animation_fade =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        /* fade in the title screen */
        view.startAnimation(animation_fade);

        /* set up for dates to be used */
        Calendar calendar = Calendar.getInstance();
        gcyear = calendar.get(Calendar.YEAR);
        gcmonth = calendar.get(Calendar.MONTH);
        gcday = calendar.get(Calendar.DAY_OF_MONTH);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        view.setText(savedInstanceState.getString("Title"));
        sub_view.setText(savedInstanceState.getString("Past_Picks"));
        past_clocks = (HashMap<String, Date>) savedInstanceState.getSerializable("Date_Map");
        hours = savedInstanceState.getInt("HOURS");
        flag = savedInstanceState.getInt("FLAG");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title", view.getText().toString());
        outState.putString("Past_Picks", sub_view.getText().toString());
        outState.putSerializable("Date_Map", past_clocks);
        outState.putInt("HOURS", hours);
        outState.putInt("FLAG", flag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addDates(Date date, String key) {
        past_clocks.put(key, date);
    }


    private void setPastTimes(int index) {

        StringBuilder builder = new StringBuilder();
        /* retrieves from the map and sorts it out for appropriate view: first, second, third */
        ArrayList<String> list = new ArrayList<String>(past_clocks.keySet());
        Collections.sort(list);

        for (String value : list) {
            builder.append(value + ": " + formatter.format(past_clocks.get(value)) + "\n");
        }
        sub_view.setText(builder.toString());

    }

    @Override
    public void listenerClocks(final String order, final int index) {

        if (flag < index) {
            Log.v("Index", "Index is " + index + " and flag is " + flag);
            Toast.makeText(getApplicationContext(),
                    "Swipe left to choose clock in correct order.", Toast.LENGTH_SHORT).show();
        } else {
            flag++;

            /* using for radial picker time clocks preset times */
            DateTime now = DateTime.now();

            RadialTimePickerDialog timePickerDialog = RadialTimePickerDialog
                    .newInstance(new RadialTimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(RadialPickerLayout radialPickerLayout,
                                              int hours, int minutes) {
                            Date date = new Date(gcyear, gcmonth, gcday, hours, minutes, 0);
                            passDateStrings(order + " time set is\n", date);
                            //used this method to add to an arrayList
                            //for efficiency and to be able to use this method for different amounts
                            //of clocks without repetition usage
                            addDates(date, order);
                            setPastTimes(index);

                            switch (index + 1) {
                                case 1:
                                    switchFragment(1);
                                    break;
                                case 2:
                                    switchFragment(2);
                                    break;
                                case 3:
                                    flag = 0;
                                    switchFragment(3);
                                    break;
                            }
                        }
                    }, now.getHourOfDay(), now.getMinuteOfHour(), false);
            timePickerDialog.show(getSupportFragmentManager(), "ClockOutput");
        }
    }


    public void passDateStrings(String someValue, Date date) {
        view.setText(someValue + formatter.format(date));
        view.startAnimation(animation_fade);
    }

    @Override
    public void switchFragment(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    public void passIntToActivity(int hours_worked) {
        hours = hours_worked;
    }

    /* algorithm for finding the time need to clock out */
    @Override
    public Date calculate() {
        // numbers based on the Date() times
        // 60 000 = one minutes
        // 3 600 000 = one hour

        Date date1 = past_clocks.get("First");
        Date date2 = past_clocks.get("Second");
        Date date3 = past_clocks.get("Third");


        int hour_date_time = 3600000;
        int minute_date_time = 60000;

        //find minute difference
        int diff_min = 60 + date2.getMinutes() - date1.getMinutes();
        //find hour difference
        int diff_hour = ((diff_min / 60) - 1) +
                date2.getHours() - date1.getHours();
        //this occurs when overlaps from a 12 hour period
        if (diff_min > 59) {
            diff_min -= 60;
        }

        //subtracts user input of hours needed to work to what they've already currently worked
        int sub = hours * hour_date_time -
                (diff_hour * hour_date_time + diff_min * minute_date_time);

        //finds from the subtraction of how many hours needed to work
        int hours_left = sub / hour_date_time;
        int minutes_left = (sub - (hours_left * hour_date_time)) / minute_date_time;


        Date final_date = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                date3.getHours() + hours_left,
                date3.getMinutes() + minutes_left);


        scheduleClient.setAlarmForNotification(final_date);
        view.setText("You need to clock out at\n " + formatter.format(final_date));
        view.startAnimation(animation_fade);

        //empty list for next iteration if necessary
        past_clocks.clear();
        sub_view.setText(null);

        return final_date;
    }

    @Override
    protected void onDestroy() {
        scheduleClient.doUnbindService();
        super.onDestroy();
    }
}
