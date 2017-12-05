package com.pccb.newapp.net.http.util;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.global.PccbApplication;
import com.pccb.newapp.util.Base64;
import com.pccb.newapp.util.SharedPreferencesTool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 当前类注释：HttpHelper
 * <p>
 * Author :LeonWang <p>
 * Created  2017/4/1.17:43 <p>
 * Description:
 * <p>
 * E-mail:lijiawangjun@gmail.com
 */

public class HttpHelper {

    /**
     * 请求状态码
     */
    //成功
    public static final String EXECUTE_SUCCESS = "EXECUTE_SUCCESS";
    //    交易处理中
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    //    系统内部错误
    public static final String EXECUTE_PROCESSING = "EXECUTE_PROCESSING";
    //    服务不存在
    public static final String SERVICE_NOT_FOUND_ERROR = "SERVICE_NOT_FOUND_ERROR";
    //    参数错误
    public static final String PARAMETER_ERROR = "PARAMETER_ERROR";
    //    参数格式错误
    public static final String PARAM_FORMAT_ERROR = "PARAM_FORMAT_ERROR";
    //    认证(签名)错误
    public static final String UNAUTHENTICATED = "UNAUTHENTICATED";
    //    未授权的服务
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    //    商户请求号不唯一
    public static final String REQUEST_NO_NOT_UNIQUE = "REQUEST_NO_NOT_UNIQUE";
    //    对象字段重复
    public static final String FIELD_NOT_UNIQUE = "FIELD_NOT_UNIQUE";
    //    重定向服务需设置redirectUrl
    public static final String REDIRECT_URL_NOT_EXIST = "REDIRECT_URL_NOT_EXIST";
    //    合作伙伴没有注册
    public static final String PARTNER_NOT_REGISTER = "PARTNER_NOT_REGISTER";
    //    商户没有配置产品
    public static final String PARTNER_NOT_PRODUCT = "PARTNER_NOT_PRODUCT";
    //    不支持的请求协议
    public static final String UNSUPPORTED_SECHEME = "UNSUPPORTED_SECHEME";

    //充值、付款短信验证码错误
    public static final String PAY_SMS_CAPTCH_ERROR = "PAY_SMS_CAPTCH_ERROR";
    //支付超出限额
    public static final String BANK_CARD_LIMIT_EXCEED = "BANK_CARD_LIMIT_EXCEED";

    //扫码登录 二维码失效
    public static final String QRCODE_HAS_EXPIRED = "QRCODE_HAS_EXPIRED";
    //已经开通银行存管
    public static final String BANK_DEPOSITION_EXIST = "BANKDEPOSITION_EXIST";
    //余额不足
    public static final String NO_ENOUGH_BALANCE = "NO_ENOUGH_BALANCE";

    private final String SECRET_KEY = "secret_key";
    private final String PARTNER_ID = "partner_id";

    private String securityKey = "anonymouanonymou";
    private String partnerId = "anonymous";

    private HttpHelper() {
    }

    private static volatile HttpHelper mInstance = null;

    public static HttpHelper getInstance() {
        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpHelper();
                }
            }
        }
        return mInstance;
    }


    /**
     * 普通秘钥 Map参数集合
     *
     * @param map
     * @return
     */
    public Map<String, String> getCommomParams(Map<String, String> map) {

        // 生成 orderNo
        String orderNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 保存安全码
        DefaultParameterSigner.setSecurityKey(getSecretKey());

        Map<String, String> params = new HashMap<>();
        //协议格式
        params.put("protocol", "HTTP_FORM_JSON");
        //服务版本
        params.put("version", "1.0");
        //商户ID
        params.put("partnerId", getPartnerId());
        Logger.d("-----------partnerId------------------>" + getPartnerId());
        //签名方式
        params.put("signType", "MD5");
        //请求号
//        params.put("requestNo", orderNo);
        params.put("orderNo", orderNo);
        //交易订单号
        params.put("merchOrderNo", orderNo);
        //客户端
        params.put("appClient", true + "");

        //遍历map
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        Logger.d("------------签名-----------" + Signer.getSigner().sign(params));
        params.put("sign", Signer.getSigner().sign(params));
        return params;
    }


    /**
     * ASE加密
     *
     * @param plainText 明文
     * @return 密文
     */
    public String AESEncrypt(String plainText) {
        try {
            // 使用商户安全码前16字节作为加密秘钥
            byte[] secretKeyBytes = getSecretKey().substring(0, 16).getBytes("UTF-8");
            // 明文数据
            byte[] plainBytes = plainText.getBytes("UTF-8");
            SecretKey key = new SecretKeySpec(secretKeyBytes, "AES");
            // 获取Cipher对象较为耗费资源,如追求性能,请对cipher做缓存方案;
            // 通过算法/模式/填充获取cipher对象时,采用默认AES,表示: AES/ECB/PKCS1Padding
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(plainBytes);
            // 转换为base64(utf-8)字符串作为密文
            return Base64.encode(cipherBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败: " + e.getMessage());
        }
    }

    /**
     * 获取登录与未登录状态下的securityKey
     *
     * @return
     */
    private String getSecretKey() {
        String secretKey =
                SharedPreferencesTool.getString(PccbApplication.getInstance().getContext(), SECRET_KEY, "");
        if (TextUtils.isEmpty(secretKey)) {
            return securityKey;
        } else {
            return secretKey;
        }

    }


    /**
     * 获取登录与未登录状态下的partnerId
     *
     * @return
     */
    private String getPartnerId() {
        String accessKey =
                SharedPreferencesTool.getString(PccbApplication.getInstance().getContext(), PARTNER_ID, "");
        if (TextUtils.isEmpty(accessKey)) {
            return partnerId;
        } else {
            return accessKey;
        }

    }


}
