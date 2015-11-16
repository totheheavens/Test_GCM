package com.fateindestiny.test_gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Create By
 *
 * @author FateInDestiny on 2015-10-15.
 */
public class FIDGCMListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        int meesageCode = data.getInt("messageCode");

        Intent intent = new Intent(QuickstartPreferences.GCM_MESSAGE_RECEIVE);
        intent.putExtra(QuickstartPreferences.GCM_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
} // end of class FIDGCMListenerService
