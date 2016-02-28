package com.samkpo.dario.noboldcontacts;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by dario on 27/02/16.
 */
public class NoBold implements IXposedHookLoadPackage{//},IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;
    private static String APP_TO_HACK = "com.android.dialer";

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(APP_TO_HACK))
            return;

        XposedBridge.log("We are in AOSP Dialer");

        XposedBridge.log("Version: " + (BuildConfig.DEBUG ? BuildConfig.VERSION_NAME + " - " + BuildConfig.VERSION_CODE
                : BuildConfig.VERSION_NAME));
    }

    /*@Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals(APP_TO_HACK))
            return;

        //Get resources
        //XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);

        //Primary colors
        //String[] _primaryColors = modRes.getStringArray(R.array.primary_colors_to_replace);
        //for(String pc : _primaryColors)
        //    resparam.res.setReplacement(APP_TO_HACK, "color", pc, modRes.getColor(R.color.colorPrimary));

    }*/
}
