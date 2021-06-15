package com.s20.databasedemo_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MyActivity {
    Context con;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    MyActivity(Context context){
        this.con = context;
    }


    public void call(String dial){
        Log.d("call","call"+dial);
        dial="tel:"+dial;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(dial));
        con.startActivity(callIntent);
    }
}