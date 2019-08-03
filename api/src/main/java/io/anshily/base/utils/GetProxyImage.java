package io.anshily.base.utils;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class GetProxyImage {
    /**
     * 根据传入的url将结果以byte[]的数据返回
     * @param url 需要请求的url
     * @return
     * @throws Exception
     */
    public static ImageData getImage(String url)throws Exception{

        //当传入的url返回不为空的时候，读取数据
        System.out.println("enter deImage, url= "+ url);
        ImageData back=null;
        InputStream input;
//        if(!StringUtils.isNullOrEmpty(url))
        if (!url.isEmpty()){
            try{
                back =new ImageData();
                String ContentType=null;
                //设置请求的头信息
                URL urlInfo = new URL(url);
                URLConnection connection = urlInfo.openConnection();
                //设置头信息
                connection.addRequestProperty("Host", urlInfo.getHost());
                connection.addRequestProperty("Connection", "keep-alive");
                //强制要求缓存服务器在返回缓存的版本之前将请求提交到源头服务器进行验证。
                connection.addRequestProperty("Cache-Control", "no-cache");
                //使用url的Host来标记来源
                connection.addRequestProperty("Referer", "http://"+urlInfo.getHost());
                //表示用户不愿意目标站点追踪用户个人信息。
                connection.addRequestProperty("DNT", "1");
                //强制要求缓存服务器在返回缓存的版本之前将请求提交到源头服务器进行验证。
                connection.addRequestProperty("Pragma", "no-cache");
                connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
                connection.connect();
                //获取头信息，图片的返回格式
                Map<String, List<String>> map = connection.getHeaderFields();
                List<String> type=map.get("Content-Type");
                ContentType=type.get(0);
                //获取请求回来的信息
                input = connection.getInputStream();

                //解决使用inputStream.available()，读取图片不完整问题
                //此方法在内部缓冲输入，因此不需要使用BufferedInputStream。
                byte[]data = IOUtils.toByteArray(input);

                System.out.println(input);

                input.close();
                //设置返回信息
                back.setContentType(ContentType);
                back.setData(data);

            }catch(Exception e){
                throw new Exception("读取Url数据失败："+url,e);
            }
        }
        return back;
    }
}

//        原文：https://blog.csdn.net/weixin_36751895/article/details/79654843
//        版权声明：本文为博主原创文章，转载请附上博文链接！
