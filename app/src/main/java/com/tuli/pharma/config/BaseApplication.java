package com.tuli.pharma.config;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tuli.pharma.R;
//import com.tuli.pharma.database.SharedPreferenceManager;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

//import static com.tuli.pharma.utils.Constants.SP_MANAGER;

public class BaseApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/POPPINS-REGULAR.TTF")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
//        FirebaseAnalytics.getInstance(this);
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        //SP_MANAGER = new SharedPreferenceManager(this);
    }


    /**
     * @ get current app version
     * @return current app version
     */
    public String currentVersion(Context context)
    {
        String currentVersion = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            currentVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return currentVersion;
    }
}
