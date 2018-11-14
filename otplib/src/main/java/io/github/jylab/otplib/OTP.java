/**
 *
 *  Created by jylee on 13/11/2018
 *  Copyright © 2018 jylee. All rights reserved.
 *
 */

package io.github.jylab.otplib;

import java.net.URLEncoder;
import java.util.HashMap;
import io.github.jylab.otplib.server.AsyncOTPRequest;
import io.github.jylab.otplib.server.Config;
import io.github.jylab.otplib.util.Utility;

import static io.github.jylab.otplib.server.Config.SERVER_URL;
import static io.github.jylab.otplib.util.Utility.byteArrayToHex;
import static io.github.jylab.otplib.util.Utility.getSha256;


public class OTP {
    private String id="";
    private String uuid="";
    private String OtpNumber="";

    // Types of 'optProperty': idSha256, uuidSha256, uniqInfo, ...
    private HashMap<String, String> optProperty =  new HashMap<String, String>();


    public OTP(String id, String uuid) {
        this.id = id;
        this.uuid = uuid;
        Utility.Log("id :"+id);
        Utility.Log("uuid :"+uuid);

        try {
            addProperty("idSha256", getSha256(id));
            addProperty("uuidSha256", getSha256(uuid));
            Utility.Log("idSha256 :"+getSha256(id));
            Utility.Log("uuidSha256 :"+getSha256(id));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Optional
    public void configServer(String svrURL) {
        SERVER_URL = svrURL;
        Utility.Log(SERVER_URL);
    }


    public String doProcess() {
        Utility.Log("server url :"+Config.SERVER);

        makeUniqInfo();
        Utility.Log("uniqInfo :"+optProperty.get("uniqInfo"));

        String strUniqInfo = optProperty.get("uniqInfo");


        AsyncOTPRequest req = new AsyncOTPRequest();
        try {
            OtpNumber = req.execute(Config.SERVER+"?data="+URLEncoder.encode(strUniqInfo)).get();
            addProperty("OtpNumber", OtpNumber);
            Utility.Log("OtpNumber :"+Config.SERVER);
        }catch (Exception e){
            e.printStackTrace();
        }

        return OtpNumber;

    }

    private void makeUniqInfo(){
        byte[] idBytes= new byte[16];
        byte[] uuidBytes = new byte[16];
        byte[] UniqInfo = new byte[16];

        byte [] tmp ;
        try {
            idBytes = optProperty.get("idSha256").getBytes("UTF-8");
            uuidBytes = optProperty.get("uuidSha256").getBytes("UTF-8");

        }catch (Exception e){
            e.printStackTrace();
        }

        // It will be edited
        for(int i=0; i<16; i++){
            UniqInfo[i] = (byte)(idBytes[i] ^ uuidBytes[i]);
        }


        addProperty("UniqInfo", byteArrayToHex(UniqInfo) );

    }

    protected void addProperty(String key, String value)
    {
        if(key != null)
            this.optProperty.put(key, value);
    }

}
