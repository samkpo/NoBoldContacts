package com.samkpo.dario.noboldcontacts;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
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

        //XposedBridge.log("\n\n\n\nWe are in AOSP Dialer\n\n\n\n");

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
        //XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);

        //Primary colors
        resparam.res.setReplacement(resparam.packageName, "string", "letter_tile_letter_font_family", "sans-serif-thin");

        XposedBridge.log("Replacing contacts typeface.");
    }

}
