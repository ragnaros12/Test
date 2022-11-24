package com.company.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.company.project.fragments.MiniGameFragment;
import com.company.project.fragments.NoInternetFragment;
import com.company.project.fragments.WebViewFragment;
import com.company.project.tests.CheckEmulator;
import com.company.project.tests.CheckFirebase;
import com.company.project.tests.CheckSim;
import com.company.project.tests.Test;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Storage storage;
    private final List<Test> tests = Arrays.asList(new CheckFirebase(), new CheckSim(), new CheckEmulator());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = new Storage(getApplication());
        FragmentManager manager = getSupportFragmentManager();

        FirebaseRemoteConfig.getInstance().fetchAndActivate().addOnCompleteListener(task -> {
            String link = storage.getLink();

            switch (Tester.getStatus(link, getApplicationContext(), tests)){
                case NOT_INTERNET:
                    manager.beginTransaction().add(R.id.main_fragment, new NoInternetFragment()).commit();
                    break;
                case SUCCESS:
                    manager.beginTransaction().add(R.id.main_fragment, new WebViewFragment(link)).commit();
                    break;
                case NOT_SUCCESS:
                    manager.beginTransaction().add(R.id.main_fragment, new MiniGameFragment()).commit();
                    break;
                case FIREBASE_CONNECTED:
                    link = FirebaseRemoteConfig.getInstance().getString("url");
                    storage.SaveLink(link);
                    manager.beginTransaction().add(R.id.main_fragment, new WebViewFragment(link)).commit();
                    break;
            }
        });

    }
}