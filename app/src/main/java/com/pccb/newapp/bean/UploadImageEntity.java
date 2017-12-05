package com.pccb.newapp.bean;

import java.util.List;

/**
 * 上传图片
 * @author cgc
 * @created 2017-11-06
 */
public class UploadImageEntity {


    /*
    * *
     * code :
     * data : {"serverRoot":"/media"}
     * message :
     * rows : [{"createTime":"2015-10-26 21:34:53","fileExt":"png","fileName":"201510262134539562878.png","filePath":"/2015/10/26/201510262134539562878.png","fileSize":128033222656,"fileType":"picture","id":9,"inputName":"uploadFIle1","objectId":"876081fb653c21d8a6ca54123de6d77b54d0242e","originalName":"2015-07-01-03-03-11.png","status":"enable","thumbnail":"/2015/10/26/201510262134539562878_thum.png"}]
     * success : true
     * total : 1
     */

    private String code;
    private DataEntity data;
    private String message;
    private boolean success;
    private int total;
    private List<RowsEntity> rows;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public int getTotal() {
        return total;
    }

    public List<RowsEntity> getRows() {
        return rows;
    }

    public static class DataEntity {
        /**
         * serverRoot : /media
         */

        private String serverRoot;

        public void setServerRoot(String serverRoot) {
            this.serverRoot = serverRoot;
        }

        public String getServerRoot() {
            return serverRoot;
        }
    }

    public static class RowsEntity {
        /**
         * createTime : 2015-10-26 21:34:53
         * fileExt : png
         * fileName : 201510262134539562878.png
         * filePath : /2015/10/26/201510262134539562878.png
         * fileSize : 128033222656
         * fileType : picture
         * id : 9
         * inputName : uploadFIle1
         * objectId : 876081fb653c21d8a6ca54123de6d77b54d0242e
         * originalName : 2015-07-01-03-03-11.png
         * status : enable
         * thumbnail : /2015/10/26/201510262134539562878_thum.png
         */

        private String createTime;
        private String fileExt;
        private String fileName;
        private String filePath;
        private long fileSize;
        private String fileType;
        private int id;
        private String inputName;
        private String objectId;
        private String originalName;
        private String status;
        private String thumbnail;

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setFileExt(String fileExt) {
            this.fileExt = fileExt;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setInputName(String inputName) {
            this.inputName = inputName;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getFileExt() {
            return fileExt;
        }

        public String getFileName() {
            return fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public long getFileSize() {
            return fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public int getId() {
            return id;
        }

        public String getInputName() {
            return inputName;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getOriginalName() {
            return originalName;
        }

        public String getStatus() {
            return status;
        }

        public String getThumbnail() {
            return thumbnail;
        }
    }
}
