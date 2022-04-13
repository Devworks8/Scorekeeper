package com.lachapelle.christian.scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Activity_settings extends AppCompatActivity {
    private SharedPreferences prefs;
    private boolean save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = prefs.edit();
        Switch swtSave = (Switch) findViewById(R.id.swtPersistance);
        save = prefs.getBoolean("save_values_pref", false);
        swtSave.setChecked(save);
        swtSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("save_values_pref", b);
                editor.apply();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Switch sw = (Switch) findViewById(R.id.swtPersistance);
        boolean save = prefs.getBoolean("save_values_pref", true);
        sw.setChecked(save);
    }
}