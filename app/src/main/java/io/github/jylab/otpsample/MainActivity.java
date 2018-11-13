/**
 *
 *  Created by jylee on 13/11/2018
 *  Copyright © 2018 jylee. All rights reserved.
 *
 */


package io.github.jylab.otpsample;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import io.github.jylab.otplib.OTP;
import static io.github.jylab.otpsample.SampleInfo.getSampleID;
import static io.github.jylab.otpsample.SampleInfo.getSampleUUID;



public class MainActivity extends AppCompatActivity {
    Context mContext = this;
    private  String UUID;
    private String ID;
    private String OtpNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ID= getSampleID();
        UUID = getSampleUUID();
        //UUID = getDevicesUUID(mContext);
    }


    private void requestOTP(View view){
        OTP otp = new OTP(ID, UUID);
        otp.configServer("http://192.168.0.7:8080/OTP.jsp"); // optional
        OtpNumber = otp.doProcess();


    }

    private void logIn(View view){

    }


    // not to be used func. I will use getSampleUUID() func
    private String getDevicesUUID(Context mContext){
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String android_id=Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);;
        return android_id ;
    }
}
