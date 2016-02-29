package com.samkpo.dario.noboldcontacts;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends Activity {
    //Tag, for debugging porpuses
    private static String TAG = SettingsActivity.class.getSimpleName();
    //Shared preferences
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Get app preferences
        mPreferences = getSharedPreferences("user_settings", MODE_WORLD_READABLE);

        //Get string list
        final String _typefaces[] = getResources().getStringArray(R.array.typefaces_avaialables_names);
        final String _typefacesValues[] = getResources().getStringArray(R.array.typefaces_available);

        //Get current value of typeface
        String _currentTypeface = mPreferences.getString(getString(R.string.prefs_typeface), getString(R.string.default_typeface));
        int position = Arrays.asList(_typefacesValues).indexOf(_currentTypeface);
        Log.v(TAG, "Current typeface: " + position + ": " + _currentTypeface + ": " + _typefaces[position]);

        //Configure spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, _typefaces);
        Spinner _spinner = (Spinner)findViewById(R.id.typeface_spinner);
        _spinner.setAdapter(adapter);
        _spinner.setSelection(position);
        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "item: " + (String) parent.getItemAtPosition(position));
                Log.v(TAG, "item value: " + _typefacesValues[position]);

                //Save the pref
                mPreferences.edit().putString(getString(R.string.prefs_typeface), _typefacesValues[position]).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Show launcher icon
        boolean m_showIcon = mPreferences.getBoolean(getString(R.string.show_launcher_icon), false);

        //Configure show icon
        CheckBox _showIcon = (CheckBox)findViewById(R.id.pref_show_app_icon);
        _showIcon.setChecked(m_showIcon);
        _showIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PackageManager packageManager = getPackageManager();
                int state = b ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                ComponentName aliasName = new ComponentName(SettingsActivity.this, "com.samkpo.dario.noboldcontacts.SettingsActivity-Alias");
                packageManager.setComponentEnabledSetting(aliasName, state, PackageManager.DONT_KILL_APP);

                mPreferences.edit().putBoolean(getString(R.string.show_launcher_icon), b).apply();
            }
        });
    }

}
