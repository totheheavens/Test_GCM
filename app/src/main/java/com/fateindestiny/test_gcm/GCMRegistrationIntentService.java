package com.fateindestiny.test_gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Create By
 *
 * @author KKM on 2015-10-15.
 */
public class GCMRegistrationIntentService extends IntentService {

    private static final String TAG = "GCMRegistrationIntentService";

    public GCMRegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = null;

        try {
            // Instance ID 생성
            InstanceID instanceID = InstanceID.getInstance(this);
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent tokenGeneratingIntent = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        tokenGeneratingIntent.putExtra(QuickstartPreferences.INSTANCE_ID_TOKEN, token);

        LocalBroadcastManager.getInstance(this).sendBroadcast(tokenGeneratingIntent);
    }
} // end of class GCMRegistrationIntentService
