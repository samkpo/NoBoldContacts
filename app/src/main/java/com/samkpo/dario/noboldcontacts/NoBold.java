package com.samkpo.dario.noboldcontacts;

import android.content.res.XModuleResources;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Created by dario on 27/02/16.
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
        XposedBridge.log("Loaded app: " + lpparam.packageName);
        if (!lpparam.packageName.equals(APPS_TO_HACK[0]) && !lpparam.packageName.equals(APPS_TO_HACK[1]))
            return;

        XposedBridge.log("\n\n\n\nWe are in AOSP Dialer\n\n\n\n");

        XposedBridge.log("Version: " + (BuildConfig.DEBUG ? BuildConfig.VERSION_NAME + " - " + BuildConfig.VERSION_CODE
                : BuildConfig.VERSION_NAME));
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals(APPS_TO_HACK[0]) && !resparam.packageName.equals(APPS_TO_HACK[1]))
            return;

        //Get resources
        //XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);

        //Primary colors
        resparam.res.setReplacement(resparam.packageName, "string", "letter_tile_letter_font_family", "sans-serif-light");

        XposedBridge.log("Replacing data in contacts.");

    }

}
