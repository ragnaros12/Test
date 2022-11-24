package com.company.project.tests;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CheckSim implements Test{
    @Override
    public boolean check(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT;
    }
}
