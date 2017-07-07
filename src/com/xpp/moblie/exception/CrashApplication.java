package com.xpp.moblie.exception;

import android.app.Application;  

public class CrashApplication extends Application {  
    public void onCreate() {  
        super.onCreate();  
        CrashHandler crashHandler = CrashHandler.getInstance();  
        crashHandler.init(getApplicationContext());  
    }  
} 
