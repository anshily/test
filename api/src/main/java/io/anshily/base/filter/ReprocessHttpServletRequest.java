package io.anshily.base.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author: xiehao
 * @Date: 2018-08-21
 */

public class ReprocessHttpServletRequest extends HttpServletRequestWrapper {
    private final static Logger logger = LoggerFactory.getLogger(ReprocessHttpServletRequest.class);
    private final byte[]              bodyBytes;
    private String                    bodyString;
    private Map<String, List<String>> map;
    private int readMap=0;
    private String urlQueryString;
    /**
     * @Author xiehao
     * @Date 2018/8/21 16:18
     * @Param
     * @Description 构造函数，获取转换后的request
     */
    public ReprocessHttpServletRequest(HttpServletRequest request) throws IOException{
        super(request);
        String requestStreamString = getRequestBodyString(request);
        bodyBytes = requestStreamString.getBytes(Charset.forName("UTF-8"));
        urlQueryString = request.getQueryString();
    }
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return super.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        try{
            Map<String,List<String>> nameVals = doParseParameter();
            List<String> list = nameVals.get(name);
            if(list != null && list.size()>0){
                return list.toArray(new String[]{});
            }
        }catch (UnsupportedEncodingException e){
            logger.error("获取reques中参数"+name+"对应的值失败",e);
        }
        return new String[]{};
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyBytes);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
    /**
     * @Author xiehao
     * @Date 2018/8/21 16:04
     * @Param
     * @Description
     */
    public String getRequestBodyString(final ServletRequest request){
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try{
            inputStream = cloneServletInputStream(request.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        }catch (IOException e){
            logger.error("request的ServletInputStream转换失败",e);
        }finally {
            if (inputStream != null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    logger.error("转换后inputStram关闭失败",e);
                }
            }
            if (bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    logger.error("转换后bufferedReader关闭失败",e);
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * @Author xiehao
     * @Date 2018/8/21 16:28
     * @Param
     * @Description 复制ServletInputStream到ByteArrayInputStream
     */
    public InputStream cloneServletInputStream(ServletInputStream servletInputStream){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while((len = servletInputStream.read(buffer))>-1){
                byteArrayOutputStream.write(buffer,0,len);
            }
        }catch (IOException e){
            logger.error("request的ServletInputStream读取失败",e);
        }
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return inputStream;
    }

    /**
     * @Author xiehao
     * @Date 2018/8/21 16:32
     * @Param
     * @Description 获取request中所有参数对应的名字和值
     */
    public Map<String, List<String>> doParseParameter() throws UnsupportedEncodingException {
        if (readMap == 0) {
            //这里把post里的参数和地址栏参数结合到一起,然后解析
            bodyString = new String(bodyBytes, getCharacterEncoding()) + "&" + urlQueryString;
            String[] nameVals = bodyString.split("&");
            map = new HashMap<String, List<String>>();
            for (String nameVal : nameVals) {
                String name = nameVal.split("=")[0];
                String val = nameVal.split("=")[1];
                if (map.containsKey(name)) {
                    List<String> vals = map.get(name);
                    vals.add(val);
                    map.put(name, vals);
                } else {
                    List<String> vals = new ArrayList<String>();
                    vals.add(val);
                    map.put(name, vals);
                }
            }
            readMap = 1;
        }
        return map;
    }

}
