package com.fateindestiny.test_gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Create By
 *
 * @author FateInDestiny on 2015-10-15.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
} // end of class MyInstanceIDListenerService
