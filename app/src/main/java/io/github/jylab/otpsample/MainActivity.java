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
import android.widget.EditText;
import android.widget.Toast;

import io.github.jylab.otplib.OTP;
import io.github.jylab.otplib.util.Utility;

import static io.github.jylab.otpsample.SampleInfo.getSampleID;
import static io.github.jylab.otpsample.SampleInfo.getSampleUUID;



public class MainActivity extends AppCompatActivity {
    Context mContext = this;
    private  String UUID="";
    private String ID="";
    private String OtpNumber=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ID= getSampleID();
        UUID = getSampleUUID();
        //UUID = getDevicesUUID(mContext);
    }


    public void requestOTP(View view){
        EditText idBox = findViewById(R.id.editText1);
        ID = idBox.getText().toString();
        if(ID.equals("")){
            Toast.makeText(this,"Please Input Your ID",Toast.LENGTH_SHORT).show();
            return;
        }

        OTP otp = new OTP(ID, UUID);
        otp.configServer("http://192.168.0.8:8080/OneTimePasswd/otpPage.jsp"); // optional
        OtpNumber = otp.doProcess();
        Utility.Log("OtpNumber: "+ OtpNumber);

        if(OtpNumber == null ) {
            Toast.makeText(this,"This isn't a registered device.",Toast.LENGTH_SHORT).show();
        }else{
            EditText otpBox = findViewById(R.id.editText2);
            otpBox.setText(OtpNumber);
        }


    }

    public void logIn(View view){

    }


    public void userRegist(View view){

    }


    // not to be used func. I will use getSampleUUID() func
    private String getDevicesUUID(Context mContext){
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String android_id=Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);;
        return android_id ;
    }
}
