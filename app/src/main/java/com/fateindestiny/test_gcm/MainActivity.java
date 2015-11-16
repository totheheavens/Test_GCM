package com.fateindestiny.test_gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends AppCompatActivity {


    public static final String LOG_TAG = "FateInDestiny";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private Button mBtnGetInstanceId;
    private TextView mTxtInstanceId;
    private TextView mTxtReceiveMessage;

    /**
     * GCM 관련 Broadcast Receiver
     * GCM 에서 등록 및 받은 Push를 처리하는 Receiver
     */
    private BroadcastReceiver mRegistrationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)) {
                // GCM 등록 성공 및 Instance ID 생성 성공
                Toast.makeText(MainActivity.this, "GCM Registration Complete", Toast.LENGTH_SHORT).show();
                String token = intent.getStringExtra(QuickstartPreferences.INSTANCE_ID_TOKEN);
                mTxtInstanceId.setText(token);
            } else if (action.equals(QuickstartPreferences.GCM_MESSAGE_RECEIVE)) {
                // GCM 메시지 Recevie
                String message = intent.getStringExtra(QuickstartPreferences.GCM_MESSAGE);
                mTxtReceiveMessage.setText(message);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mBtnGetInstanceId = (Button) findViewById(R.id.main_btn_getinstanceid);
        mTxtInstanceId = (TextView) findViewById(R.id.main_txt_instanceid);
        mTxtReceiveMessage = (TextView) findViewById(R.id.main_txt_receive_message);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mBtnGetInstanceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstanceIdToken();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // GCM 관련 Broadcast Receiver 등록
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.GCM_MESSAGE_RECEIVE));
    }

    @Override
    protected void onPause() {
        // Broadcast Receiver 등록 해제
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Google Play Service를 사용 가능 여부 체크 함수
     *
     * @return 사용 가능 여부 Flag 값
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * GCM의 InstanceID를 가져오는 Service를 실행하는 함수
     */
    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }
    }
}
