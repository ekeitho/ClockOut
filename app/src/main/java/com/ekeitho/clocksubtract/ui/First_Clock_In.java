package com.ekeitho.clocksubtract.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekeitho.clocksubtract.R;
import com.neopixl.pixlui.components.button.Button;

import org.joda.time.DateTime;

import java.util.Date;

/**
 *  First fragment for when the user gets into work or first clock in.
 */
public class First_Clock_In extends Fragment {

    private ActivityCommunicator activityCommunicator;

    public First_Clock_In() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_clock_in, container, false);
        Button button = (Button) view.findViewById(R.id.first_clock_in_button);

        /* using for radial picker time clocks preset times */
        DateTime now = DateTime.now();
        Date date = now.toDate();
        activityCommunicator.setChosenDate(date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCommunicator.listenerClocks("First", 0);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityCommunicator = (ActivityCommunicator) getActivity();
    }
}
