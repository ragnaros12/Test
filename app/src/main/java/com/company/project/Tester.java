package com.company.project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.company.project.tests.Test;

import java.util.List;

public class Tester {
    enum StatusPhone{
        SUCCESS,
        NOT_INTERNET,
        NOT_SUCCESS,
        FIREBASE_CONNECTED
    }

    public static StatusPhone getStatus(String link, Context context, List<Test> checks){
        if(link != null) {
            NetworkInfo networkInfo = ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnectedOrConnecting())
                return StatusPhone.SUCCESS;
            return StatusPhone.NOT_INTERNET;
        }

        for (Test check : checks){
            if(check.check(context)){
                return StatusPhone.NOT_SUCCESS;
            }
        }

        return StatusPhone.FIREBASE_CONNECTED;
    }
}
