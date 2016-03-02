/*
 * Copyright (C) 2016 Aguilera Dario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.samkpo.dario.noboldcontacts;

import android.content.res.XModuleResources;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Created by dario on 27/02/16.
 *
 * TODO Search the ContactsCommon library activity and there make the replacement
 */
public class NoBold implements IXposedHookZygoteInit,IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;
    private static String APPS_TO_HACK[] = {
            "com.android.dialer",
            "com.android.contacts"
    };

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        boolean r_return = true;
        for(String s : APPS_TO_HACK){
            if(lpparam.packageName.equals(s)) {
                r_return = false;
                break;
            }
        }
        if(r_return) return;

        XposedBridge.log("Loaded app: " + lpparam.packageName);
        XposedBridge.log("Version: " + (BuildConfig.DEBUG ? BuildConfig.VERSION_NAME + " - " + BuildConfig.VERSION_CODE
                : BuildConfig.VERSION_NAME));
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        boolean r_return = true;
        for(String s : APPS_TO_HACK){
            if(resparam.packageName.equals(s)) {
                r_return = false;
                break;
            }
        }
        if(r_return) return;
        XposedBridge.log("Loaded app: " + resparam.packageName);

        //Get resources
        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);

        //Preferences
        //SharedPreferences pref = getSharedPreferences("user_settings", MODE_WORLD_READABLE);
        XSharedPreferences pref = new XSharedPreferences("com.samkpo.dario.noboldcontacts", "user_settings");

        //Get selected leter
        XposedBridge.log("NoBold typeface key: " + modRes.getString(R.string.prefs_typeface));
        String _selectedTypeface = pref.getString(modRes.getString(R.string.prefs_typeface), "sans-serif-light");

        //letter
        resparam.res.setReplacement(resparam.packageName, "string", "letter_tile_letter_font_family", _selectedTypeface);
        XposedBridge.log("Replacing contacts typeface.");
    }

}
