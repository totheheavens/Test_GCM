package com.fateindestiny.test_gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Create By
 *
 * @author KKM on 2015-10-15.
 */
public class FIDGCMListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(MainActivity.LOG_TAG, this.getClass().getName() + " :: onMessageReceived :: from=" + from + " :: data=" + data.toString());

        String message = data.getString("message");
        int meesageCode = data.getInt("messageCode");

        Log.d(MainActivity.LOG_TAG, this.getClass().getName() + " :: onMessageReceived :: meesageCode=" + meesageCode);
        Intent intent = new Intent(QuickstartPreferences.GCM_MESSAGE_RECEIVE);
        intent.putExtra(QuickstartPreferences.GCM_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
} // end of class FIDGCMListenerService