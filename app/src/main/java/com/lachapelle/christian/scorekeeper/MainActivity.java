package com.lachapelle.christian.scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Hashtable<Integer, String> ids = new Hashtable<Integer, String>();
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Populate Hashtable to be used by Shared Storage
        populateHashtable();

        // Setup buttons and OnClick listeners
        Button btnHPT = (Button) findViewById(R.id.btnHomePT);
        btnHPT.setOnClickListener(view -> updateScore(btnHPT));

        Button btnAPT = (Button) findViewById(R.id.btnAwayPT);
        btnAPT.setOnClickListener(view -> updateScore(btnAPT));

        Button btnANT = (Button) findViewById(R.id.btnAwayNT);
        btnANT.setOnClickListener(view -> updateScore(btnANT));

        Button btnHPC = (Button) findViewById(R.id.btnHomePC);
        btnHPC.setOnClickListener(view -> updateScore(btnHPC));

        Button btnHNC = (Button) findViewById(R.id.btnHomeNC);
        btnHNC.setOnClickListener(view -> updateScore(btnHNC));

        Button btnHNT = (Button) findViewById(R.id.btnHomeNT);
        btnHNT.setOnClickListener(view -> updateScore(btnHNT));

        Button btnHNP = (Button) findViewById(R.id.btnHomeNP);
        btnHNP.setOnClickListener(view -> updateScore(btnHNP));

        Button btnHND = (Button) findViewById(R.id.btnHomeND);
        btnHND.setOnClickListener(view -> updateScore(btnHND));

        Button btnAPC = (Button) findViewById(R.id.btnAwayPC);
        btnAPC.setOnClickListener(view -> updateScore(btnAPC));

        Button btnANC = (Button) findViewById(R.id.btnAwayNC);
        btnANC.setOnClickListener(view -> updateScore(btnANC));

        Button btnHPP = (Button) findViewById(R.id.btnHomePP);
        btnHPP.setOnClickListener(view -> updateScore(btnHPP));

        Button btnAPP = (Button) findViewById(R.id.btnAwayPP);
        btnAPP.setOnClickListener(view -> updateScore(btnAPP));

        Button btnANP = (Button) findViewById(R.id.btnAwayNP);
        btnANP.setOnClickListener(view -> updateScore(btnANP));

        Button btnHPD = (Button) findViewById(R.id.btnHomePD);
        btnHPD.setOnClickListener(view -> updateScore(btnHPD));

        Button btnAPD = (Button) findViewById(R.id.btnAwayPD);
        btnAPD.setOnClickListener(view -> updateScore(btnAPD));

        Button btnAND = (Button) findViewById(R.id.btnAwayND);
        btnAND.setOnClickListener(view -> updateScore(btnAND));

        Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(view -> resetScores());

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                Toast toast = Toast.makeText(getApplicationContext(),"Created by: Christian Lachapelle\n" +
                                                                          "Student #: A00230066\n" +
                                                                          "Course Code: IOT-1009", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(getApplicationContext(), Activity_settings.class);
                startActivity(settingsIntent);
                return true;
            case R.id.reset:
                resetScores();
                return true;
        }

        return super.onOptionsItemSelected((item));
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromLocal(false);
    }
    @Override
    public void onStop(){
        saveToLocal();
        super.onStop();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        loadFromLocal(true);
    }


    // Populate Hashtable to to be used to save/load from the shared storage
    private void populateHashtable(){
        ids.put(R.id.edtHomeTeamName, "edt");
        ids.put(R.id.edtAwayTeamName, "edt");
        ids.put(R.id.txtAwayCScore, "txt");
        ids.put(R.id.txtAwayFScore, "txt");
        ids.put( R.id.txtAwayTScore, "txt");
        ids.put(R.id.txtAwayDScore, "txt");
        ids.put(R.id.txtAwayPScore, "txt");
        ids.put(R.id.txtHomeTScore, "txt");
        ids.put(R.id.txtHomeCScore, "txt");
        ids.put(R.id.txtHomeDScore, "txt");
        ids.put(R.id.txtHomeFScore, "txt");
        ids.put(R.id.txtHomePScore, "txt");
    }

    // Save settings from the shared storage
    public void saveToLocal(){
        SharedPreferences.Editor editor = prefs.edit();
        if (prefs.getBoolean("save_values_pref", false)) {
            EditText et;
            TextView tv;
            Enumeration<Integer> e = ids.keys();
            while (e.hasMoreElements()) {
                int key = e.nextElement();
                String keyString = new Integer(key).toString();
                if (ids.get(key).equals("edt")) {
                    et = (EditText) findViewById(key);
                    editor.putString(keyString, et.getText().toString());
                } else if (ids.get(key).equals("txt")) {
                    tv = (TextView) findViewById(key);
                    editor.putString(keyString, tv.getText().toString());
                }
            }
        }
        else{
            editor.clear();
            editor.putBoolean("save_values_pref", false);
        }
        editor.apply();
    }

    // Load settings from the shared storage
    private void loadFromLocal(boolean resume){
        if (prefs.getBoolean("save_values_pref", false) || resume) {
            EditText et;
            TextView tv;
            Enumeration<Integer> e = ids.keys();
            while (e.hasMoreElements()) {
                int key = e.nextElement();
                String keyString = new Integer(key).toString();

                if (ids.get(key).equals("edt")) {
                    et = (EditText) findViewById(key);
                    et.setText(prefs.getString(keyString, ""));
                } else if (ids.get(key).equals("txt")) {
                    tv = (TextView) findViewById(key);
                    tv.setText(prefs.getString(keyString, "0"));
                }
            }
        }
    }

    private void updateScore(View view){
        switch (view.getId()) {
            case (R.id.btnHomePT):
                updateScoreSheet(R.id.txtHomeTScore, +5, false);
                updateFinalScore(R.id.txtHomeFScore, +5);
                break;
            case (R.id.btnAwayPT):
                updateScoreSheet(R.id.txtAwayTScore, +5, false);
                updateFinalScore(R.id.txtAwayFScore, +5);
                break;
            case (R.id.btnAwayNT):
                if (updateScoreSheet(R.id.txtAwayTScore, null, true)) updateFinalScore(R.id.txtAwayFScore, -5);
                break;
            case (R.id.btnHomePC):
                updateScoreSheet(R.id.txtHomeCScore, -2, false);
                updateFinalScore(R.id.txtHomeFScore, -2);
                break;
            case (R.id.btnHomeNC):
                if (updateScoreSheet(R.id.txtHomeCScore, null, true)) updateFinalScore(R.id.txtHomeFScore, +2);
                break;
            case (R.id.btnHomeNT):
                if (updateScoreSheet(R.id.txtHomeTScore, null, true)) updateFinalScore(R.id.txtHomeFScore, -5);
                break;
            case (R.id.btnHomeNP):
                if (updateScoreSheet(R.id.txtHomePScore, null, true)) updateFinalScore(R.id.txtHomeFScore, +3);
                break;
            case (R.id.btnHomeND):
                if (updateScoreSheet(R.id.txtHomeDScore, null, true)) updateFinalScore(R.id.txtHomeFScore, +3);
                break;
            case (R.id.btnAwayPC):
                updateScoreSheet(R.id.txtAwayCScore, -2, false);
                updateFinalScore(R.id.txtAwayFScore, -2);
                break;
            case (R.id.btnAwayNC):
                if (updateScoreSheet(R.id.txtAwayCScore, null, true)) updateFinalScore(R.id.txtAwayFScore, +2);
                break;
            case (R.id.btnHomePP):
                updateScoreSheet(R.id.txtHomePScore, -3, false);
                updateFinalScore(R.id.txtHomeFScore, -3);
                break;
            case (R.id.btnAwayPP):
                updateScoreSheet(R.id.txtAwayPScore, -3, false);
                updateFinalScore(R.id.txtAwayFScore, -3);
                break;
            case (R.id.btnAwayNP):
                if (updateScoreSheet(R.id.txtAwayPScore, null, true)) updateFinalScore(R.id.txtAwayFScore, +3);
                break;
            case (R.id.btnHomePD):
                updateScoreSheet(R.id.txtHomeDScore, -3, false);
                updateFinalScore(R.id.txtHomeFScore, -3);
                break;
            case (R.id.btnAwayPD):
                updateScoreSheet(R.id.txtAwayDScore, -3, false);
                updateFinalScore(R.id.txtAwayFScore, -3);
                break;
            case (R.id.btnAwayND):
                if (updateScoreSheet(R.id.txtAwayDScore, null, true)) updateFinalScore(R.id.txtAwayFScore, +3);
                break;
        }
    }

    // Update score sheet values
    private Boolean updateScoreSheet(int id, Integer score, Boolean reverse){
        TextView textView;
        textView = (TextView) findViewById(id);
        if (reverse) {
            if (textView.getText().toString().equals("0")) {
                return false;
            } else if (textView.getText().toString().length() == 1 || textView.getText().toString().length() == 2) {
                textView.setText("0");
                return true;
            } else {
                textView.setText(textView.getText().toString().substring(0, textView.getText().toString().lastIndexOf(',')));
                return true;
            }
        }else{
            if (textView.getText().toString().equals("0")){
                textView.setText(Integer.toString(score));
            } else {
                textView.setText(textView.getText() + ", " + Integer.toString(score));
            }
        }
        return null;
    }

    // Update final score
    private void updateFinalScore(int id, int score){
        TextView finalScore;
        finalScore = (TextView) findViewById(id);
        finalScore.setText(String.valueOf(Integer.parseInt(finalScore.getText().toString()) + score));
    }

    private void resetScores(){
        finish();
        startActivity(getIntent());

    }
}