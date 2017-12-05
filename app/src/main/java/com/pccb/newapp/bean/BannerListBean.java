package com.pccb.newapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 首页横幅广告
 * @author cgc
 * @created 2017-04-19
 */
public class BannerListBean implements Serializable {
    private static final long serialVersionUID = 1234368828010083000L;
    /*
   "sign": "94eff903883eaf7452ecc90b8eca4633",
   "protocol": "HTTP_FORM_JSON",
   "appClient": false,
   "requestNo": "20170419115212748",
   "merchOrderNo": "20170419115212748",
   "signType": "MD5",
   "partnerId": "13966750772",
   "service": "bannerList",
   "resultCode": "EXECUTE_SUCCESS",
   "resultMessage": "成功",
   "success": true,
   "version": "1.0",
   "banners": [
     {
       "image": "http:\/\/newone01.pccb.com\/media\/\/app\/2017\/04\/06\/201704061450148072438.jpg",
       "title": "dfdfdfdfd",
       "thumbnail": "http:\/\/newone01.pccb.com\/media\/\/app\/2017\/04\/06\/201704061450148072438.jpg",
       "comments": "dfdfd",
       "link": "http:\/\/www.baidu.com"
     }
   ]

     */
    private boolean appClient;
    private String merchOrderNo;
    private String partnerId;
    private String protocol;
    private String requestNo;
    private String resultCode;
    private String resultMessage;
    //具体错误
    private String resultDetail = "";
    private String service;
    private String sign;
    private String signType;
    private boolean success;
    private String version;
    private List<BannersEntity> banners;

    public boolean isAppClient() {
        return appClient;
    }

    public void setAppClient(boolean appClient) {
        this.appClient = appClient;
    }

    public String getMerchOrderNo() {
        return merchOrderNo;
    }

    public void setMerchOrderNo(String merchOrderNo) {
        this.merchOrderNo = merchOrderNo;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(String resultDetail) {
        this.resultDetail = resultDetail;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<BannersEntity> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersEntity> banners) {
        this.banners = banners;
    }

    @Override
    public String toString() {
        return "BannerListBean{" +
                "appClient=" + appClient +
                ", merchOrderNo='" + merchOrderNo + '\'' +
                ", partnerId='" + partnerId + '\'' +
                ", protocol='" + protocol + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", resultDetail='" + resultDetail + '\'' +
                ", service='" + service + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", success=" + success +
                ", version='" + version + '\'' +
                ", banners=" + banners +
                '}';
    }

    public static class BannersEntity  implements Serializable {
        private static final long serialVersionUID = 1234368828010083001L;
        /*
            title
            thumbnail 缩略图地址 字符串
            image 图片地址 字符串
            link 内容链接 字符串
            comments 备注 字符串

        "image": "http:\/\/newone01.pccb.com\/media\/\/app\/2017\/04\/06\/201704061450148072438.jpg",
       "title": "dfdfdfdfd",
       "thumbnail": "http:\/\/newone01.pccb.com\/media\/\/app\/2017\/04\/06\/201704061450148072438.jpg",
       "comments": "dfdfd",
       "link": "http:\/\/www.baidu.com"

         */
        //标题 字符串
        private String title;
        //缩略图地址 字符串
        private String thumbnail;
        //图片地址 字符串
        private String image;
        //内容链接 字符串
        private String link;
        //备注 字符串
        private String comments;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        @Override
        public String toString() {
            return "RowsEntity{" +
                    "title='" + title + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", image='" + image + '\'' +
                    ", link='" + link + '\'' +
                    ", comments='" + comments + '\'' +
                    '}';
        }
    }

}
