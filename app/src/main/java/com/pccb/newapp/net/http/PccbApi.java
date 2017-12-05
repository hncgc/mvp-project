package com.pccb.newapp.net.http;

import com.pccb.newapp.bean.UploadImageEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 当前类注释：API接口
 * <p>
 * Author :LeonWang <p>
 * Created  2017/4/5.12:38 <p>
 * Description:
 * <p>
 * E-mail:lijiawangjun@gmail.com
 */

public interface PccbApi {

    @FormUrlEncoded
    @POST("gateway.html")
    Observable<String> getRequestData(@FieldMap Map<String, String> map);

    /**
     * 上传文件
     */
    @Multipart
    @POST("ofile/upload.html")
    Observable<UploadImageEntity> upLoadUserIcon(@Part MultipartBody.Part file,
                                                 @Part("accessKey") RequestBody accessKey,
                                                 @Part("timestamp") RequestBody timestamp,
                                                 @Part("signType") RequestBody signType,
                                                 @Part("appClient") RequestBody appClient,
                                                 @Part("sign") RequestBody sign);
}
