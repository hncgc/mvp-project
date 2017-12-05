package com.pccb.newapp.net.http.util;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * ${name} ${description}
 *
 * @author yanjun
 * @since 0.0.1
 */

public class DefaultParameterSigner implements IParameterSigner {

    private static String securityKey;

    public static String getSecurityKey() {
        return securityKey;
    }

    public static void setSecurityKey(String securityKey) {
        DefaultParameterSigner.securityKey = securityKey;
    }

    @Override
    public String sign(Map<String, String> params) {
        try {
            return genSign(params, getSecurityKey(), "utf-8");
        } catch (Exception e) {
            Log.e("DefaultParameterSigner", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 生成签名
     *
     * @param dataMap
     * @param securityCheckKey
     * @param charset
     * @return
     * @throws Exception
     */
    private static String genSign(Map<String, String> dataMap, String securityCheckKey, String charset)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        List<Map.Entry<String, String>> sortedKes = getSortedKeySet(dataMap);
        for (Map.Entry<String, String> entry : sortedKes) {
            if (entry.getValue() == null || entry.getKey().equals("sign")) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(securityCheckKey);
        byte[] toDigest;
        String digest = null;
        try {
            String str = sb.toString();
//            str = "captchCode=0000&customerIp=127.0.0.1&mobileNo=15601942580&partnerId=test&password=vcyr69XIfGO6iXmeiR2GIw==&requestNo=RN20170417114534kbkIc&service=customerRegister";
            Log.d("TAG","-----------签名的串---------->"+str);
            toDigest = str.getBytes(charset);
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(toDigest);
            digest = new String(Hex.encodeHex(md.digest()));

            Logger.d("----------MD5----签名-------------"+digest);
        } catch (Exception e) {
            throw new Exception("签名失败", e);
        }
        return digest;
    }

    private static List<Map.Entry<String, String>> getSortedKeySet(Map<String, String> map) {
        ArrayList<Map.Entry<String, String>> lst = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(lst, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                    Map.Entry<String, String> o2) {
                return (o1.getKey().compareTo(o2.getKey()));
            }
        });

        return lst;
    }

}
