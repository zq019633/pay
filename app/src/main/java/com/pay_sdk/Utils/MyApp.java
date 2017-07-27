package com.pay_sdk.Utils;

import android.app.Application;
import android.content.Context;


/**
 * Created by zhouqiang on 2017/6/6.
 */

public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static Context getContextObject() {
        return context;
    }

}
