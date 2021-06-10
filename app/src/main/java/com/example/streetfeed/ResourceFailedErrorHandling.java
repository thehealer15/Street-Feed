package com.example.streetfeed;

import android.app.Application;
import android.os.StrictMode;

public class ResourceFailedErrorHandling extends Application
{
    public ResourceFailedErrorHandling()
    {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }
}
