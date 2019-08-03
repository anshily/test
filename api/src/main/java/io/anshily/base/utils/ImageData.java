package io.anshily.base.utils;

public class ImageData {

    //图片返回的格式信息
    private String ContentType;
    //图片返回数据的字节数组
    private byte[] data;

    public String getContentType() {
        return ContentType;
    }
    public void setContentType(String contentType) {
        ContentType = contentType;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "ImageData [ContentType=" + ContentType + ", data长度="
                + data.length + "]";
    }

}
//        原文：https://blog.csdn.net/weixin_36751895/article/details/79654843
