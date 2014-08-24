package com.ekeitho.clocksubtract;

import java.util.Date;
import java.util.HashMap;

public interface ActivityCommunicator {

    public HashMap<String, Date> getMap();
    public void switchFragment(int index);
    public void listenerClocks(final String order, final int index);
    public void passIntToActivity(int hours_worked);
    public void calculate();
}

