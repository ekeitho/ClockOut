package com.ekeitho.clocksubtract.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.ekeitho.clocksubtract.R;
import com.neopixl.pixlui.components.edittext.EditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to ask the user how many hours he'll be working.
 * Base on the result, it'll send to MainActivity and will process the users
 * remaining hours based on the response of this class.
 */
public class Fragment_Pick extends Fragment {

    private ActivityCommunicator activityCommunicator;

    public Fragment_Pick() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pick, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.frag_pick);


            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                    /* this will get the current view when
                       the user is in edit mode or able to input */
                        InputMethodManager imm = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    /* then will close the keyboard by this method */
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        if( activityCommunicator.getMap().size() != 3) {
                            Toast.makeText(getActivity(),
                                    "Make sure to do all clock-ins/out.", Toast.LENGTH_SHORT).show();
                        } else {
                    /* passes information to the MainActivity based on the
                       user's response of hours needed to work */
                            activityCommunicator
                                    .passIntToActivity(Integer.parseInt(editText.getText().toString()));
                    /* then signals the callback in the activity to calculate when the user
                       needs to clock out */
                            activityCommunicator
                                    .calculate();
                    /* switches back to the first fragment after completion */
                            activityCommunicator
                                    .switchFragment(0);

                    /* sets the hint back to the edit text for next time */
                            editText.setText(null);

                            return true;
                        }
                    }
                    return false;
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
