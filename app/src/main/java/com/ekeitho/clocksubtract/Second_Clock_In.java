package com.ekeitho.clocksubtract;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.neopixl.pixlui.components.button.Button;

/**
 * Third fragment for when user clocks back in from lunch or break.
 */
public class Second_Clock_In extends Fragment {

    private ActivityCommunicator activityCommunicator;

    public Second_Clock_In() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_clock_in, container, false);

        Button button = (Button) view.findViewById(R.id.second_clock_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCommunicator.listenerClocks("Third", 2);
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
