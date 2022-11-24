package com.company.project;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {
    private final SharedPreferences preferences;

    public Storage(Context context){
        preferences = context.getSharedPreferences("Storage", Context.MODE_PRIVATE);
    }

    public void SaveLink(String link){
        preferences.edit().putString("link", link).apply();
    }

    public String getLink(){
        return preferences.getString("link", null);
    }
}
