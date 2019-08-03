package io.anshily.base.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * ZIP工具包(支持中文)
 * 依赖：ant-1.9.6.jar
 */
@SuppressWarnings("unused")
public class CHZipUtils {
 
    /**使用GBK编码可以避免压缩中文文件名乱码*/
	private static final String CHINESE_CHARSET = "UTF-8";
    /**文件读取缓冲区大小*/
    private static final int CACHE_SIZE = 1024;
 
    
    
    public static void unZipFiles(String zipPath,String descDir)throws IOException{  
        unZipFiles(new File(zipPath), descDir);  
    }  
    /** 
     * 解压文件到指定目录 
     * @param zipFile 
     * @param descDir 
     * @author isea533 
     */  
    @SuppressWarnings("rawtypes")  
    public static void unZipFiles(File zipFile,String descDir)throws IOException{  
        File pathFile = new File(descDir);  
        if(!pathFile.exists()){  
            pathFile.mkdirs();  
        }  
        @SuppressWarnings("resource")
		ZipFile zip = new ZipFile(zipFile);  
        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;  
            //判断路径是否存在,不存在则创建文件路径  
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
            if(!file.exists()){  
                file.mkdirs();  
            }  
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
            if(new File(outPath).isDirectory()){  
                continue;  
            }  
            //输出文件路径信息  
            System.out.println(outPath);  
              
            OutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while((len=in.read(buf1))>0){  
                out.write(buf1,0,len);  
            }  
            in.close();  
            out.close();  
            }  
        System.out.println("******************解压完毕********************");  
    }  
   
    
//    
//    /**
//     * 压缩文件夹
//     * @param sourceDIR 文件夹名称（包含路径）
//     * @param targetZipFile 生成zip文件名
//     * @author liuxiangwei
//     */
//    public staticByhang void zipDIR(String sourceDIR, String targetZipFile) {
//      try {
//        FileOutputStream target = new FileOutputStream(targetZipFile);
//        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(target));
//        int BUFFER_SIZE = 1024;
//        byte buff[] = new byte[BUFFER_SIZE];
//        File dir = new File(sourceDIR);
//        if (!dir.isDirectory()) {
//          throw new IllegalArgumentException(sourceDIR+" is not a directory!");
//        }
//        File files[] = dir.listFiles();
//        for (int i = 0; i < files.length; i++) {
//          FileInputStream fi = new FileInputStream(files[i]);
//          BufferedInputStream origin = new BufferedInputStream(fi);
//          ZipEntry entry = new ZipEntry(files[i].getName());
//          out.putNextEntry(entry);
//          int count;
//          while ((count = origin.read(buff)) != -1) {
//            out.write(buff, 0, count);
//          }
//          origin.close();
//        }
//        out.close();
//      } catch (IOException e) {
//        
//      }
//    }
    
    
    static final int BUFFER = 8192;     
    
    private File zipFile;     
      
     public CHZipUtils(String pathName){
    	 
    	 zipFile = new File(pathName);  
     }
    
    
    public void compress(String... pathName) {   
        ZipOutputStream out = null;     
        try {    
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);     
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,     
                    new CRC32());     
            out = new ZipOutputStream(cos);     
            String basedir = "";   
            for (int i=0;i<pathName.length;i++){  
                compress(new File(pathName[i]), out, basedir);     
            }  
            out.close();    
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }   
    }     
    public void compress(String srcPathName) {     
        File file = new File(srcPathName);     
        if (!file.exists())     
            throw new RuntimeException(srcPathName + "不存在！");     
        try {     
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);     
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,     
                    new CRC32());     
            ZipOutputStream out = new ZipOutputStream(cos);     
            String basedir = "";     
            compress(file, out, basedir);     
            out.close();     
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }     
    }     
    
    private void compress(File file, ZipOutputStream out, String basedir) {     
        /* 判断是目录还是文件 */    
        if (file.isDirectory()) {     
            System.out.println("压缩：" + basedir + file.getName());     
            this.compressDirectory(file, out, basedir);     
        } else {     
            System.out.println("压缩：" + basedir + file.getName());     
            this.compressFile(file, out, basedir);     
        }     
    }     
    
    /** 压缩一个目录 */    
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {     
        if (!dir.exists())     
            return;     
    
        File[] files = dir.listFiles();     
        for (int i = 0; i < files.length; i++) {     
            /* 递归 */    
            compress(files[i], out, basedir + dir.getName() + "/");     
        }     
    }     
    
    /** 压缩一个文件 */    
    private void compressFile(File file, ZipOutputStream out, String basedir) {     
        if (!file.exists()) {     
            return;     
        }     
        try {     
            BufferedInputStream bis = new BufferedInputStream(     
                    new FileInputStream(file));     
            ZipEntry entry = new ZipEntry(basedir + file.getName());     
            out.putNextEntry(entry);     
            int count;     
            byte data[] = new byte[BUFFER];     
            while ((count = bis.read(data, 0, BUFFER)) != -1) {     
                out.write(data, 0, count);     
            }     
            bis.close();     
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }     
    } 
    
    
    
 
    public static void main(String[] args) throws Exception {
//      String sourceFolder = "D:/test/1.txt";
//      String sourceFolder = "D:/test/中文名.txt";
        String sourceFolder = "D:/test/cms";
        String zipFilePath = "/Users/qingyun/Documents/apache-tomcat-7.0.59/webapps/ar/upload/ar/scence";
        String destDir = "/Users/qingyun/Documents/apache-tomcat-7.0.59/webapps/ar/upload/ar/scence.zip";
     //   CHZipUtils.unZipFiles(zipFilePath, destDir);
        CHZipUtils util = new CHZipUtils(destDir);
        util.compress(zipFilePath);
        System.out.println("********执行成功**********");
    }
 
}