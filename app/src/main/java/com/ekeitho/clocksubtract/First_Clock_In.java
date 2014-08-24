package com.ekeitho.clocksubtract;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.neopixl.pixlui.components.button.Button;

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
