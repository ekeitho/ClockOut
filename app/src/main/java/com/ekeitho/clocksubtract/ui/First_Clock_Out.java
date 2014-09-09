package com.ekeitho.clocksubtract.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekeitho.clocksubtract.R;
import com.neopixl.pixlui.components.button.Button;

/**
 * Second fragment for when the user clocks out for lunch or break.
 */
public class First_Clock_Out extends Fragment {

    private ActivityCommunicator activityCommunicator;

    public First_Clock_Out() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_clock_out, container, false);

        Button button = (Button) view.findViewById(R.id.first_clock_out_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCommunicator.listenerClocks("Second", 1);
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
