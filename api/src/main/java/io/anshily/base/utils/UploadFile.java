package io.anshily.base.utils;

import io.anshily.base.core.Constants;
import net.sf.json.JSONArray;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadFile {

    public static String uploadBase64(String data) {
        if (data == null) {
            return null;
        } else if (data.length() <= 0) {
            return null;
        } else {
            JSONArray pathArr = new JSONArray();
            if (data.contains("[") || data.contains("]")) {
                JSONArray arr = JSONArray.fromObject(data);
                for (int i = 0; i < arr.size(); i++) {
                    pathArr.add(uploadSingleBase64(arr.get(i).toString()));
                }
                System.out.println(pathArr.toString());
                return pathArr.toString();
            } else {
                pathArr.add(uploadSingleBase64(data));
                return pathArr.toString();
            }
        }
    }

    protected static String getDate() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(currentTime);
    }

    public static String uploadSingleBase64(String data) {
        if (data == null) {
            return null;
        } else if (data.length() <= 0) {
            return null;
        } else {
            try {
                //判断data是否是一个已经存在的路径
                if (data.contains("uploads") && data.contains("http")) {
                    String[] strArr = data.split("uploads");
                    return "uploads/" + strArr[1];
                }
                if (data.contains("uploads") && !data.contains("http")) {
                    return data;
                }
                BASE64Decoder decoder = new BASE64Decoder();
                String[] dataArr = data.split(",");
                String fileType = ".png";
                if (dataArr[0].contains("video")) {
                    fileType = ".mp4";
                }
                if (dataArr[0].contains("android")) {
                    fileType = ".apk";
                }
                if (dataArr[0].contains("sheet")) {
                    fileType = ".xlsx";
                }
                // 解密
                byte[] b = decoder.decodeBuffer(dataArr[1]);
                // 处理数据
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                // 生成文件名
                String randomName = UUID.randomUUID().toString() + fileType;
                String imageName = getDate() + "/" + randomName;
                //服务器真正存放的路径
                String fileName = Constants.PATH_IMAGE_PATH + imageName;
//                String fileName = "192.16w8.0.229:8090" + imageName;
                // 保存图片到本地
                File file = new File(fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(fileName);
                out.write(b);
                out.flush();
                out.close();
                //返回可以保存的路径
                System.out.println(Constants.PATH_IMAGE_PATH_DB + imageName);
                return Constants.PATH_IMAGE_PATH_DB + imageName;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
