package com.company.project.tests;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class CheckFirebase implements Test {
    @Override
    public boolean check(Context context) {
        try {
            FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
            return config.getString("url").equals("");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Ошибка работы с firebase", Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
