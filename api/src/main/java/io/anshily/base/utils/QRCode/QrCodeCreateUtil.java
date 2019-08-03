package io.anshily.base.utils.QRCode;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.anshily.base.core.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

/**
 * 二维码生成和读的工具类
 *
 */
public class QrCodeCreateUtil {

    /**
     * 生成包含字符串信息的二维码图片

     * @param content 二维码携带信
     * @throws IOException
     */
    public static String createQrCode(String content) throws WriterException, IOException{
        //保存图片的类型
        String fileType = ".jpg";
        //保存图片的大小
        int qrCodeSize=1200;
        //
        String imageFormat="JPG";
        // 生成文件名
        String randomName = UUID.randomUUID().toString() + fileType;
        String imageName =  getDate() + "/" + randomName;
        //服务器真正存放的路径
        String fileName = Constants.PATH_IMAGE_PATH + imageName;
        File file=new File(fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        OutputStream outputStream=new FileOutputStream(file);
        //设置二维码纠错级别ＭＡＰ
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth-200, matrixWidth-200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++){
            for (int j = 0; j < matrixWidth; j++){
                if (byteMatrix.get(i, j)){
                    graphics.fillRect(i-100, j-100, 1, 1);
                }
            }
        }
        ImageIO.write(image, imageFormat, outputStream);
        return Constants.PATH_IMAGE_PATH_DB + imageName;
    }

    /**
     * 读二维码并输出携带的信息
     */
    public static void readQrCode(InputStream inputStream) throws IOException{
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null ;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            e.printStackTrace();
        }
        System.out.println(result.getText());
    }
    /**
     * 测试代码
     * @throws WriterException
     */
    public static void main(String[] args) throws IOException, WriterException {
        //生成一个二维码的图片
        QrCodeCreateUtil.createQrCode("WE1231238239128sASDASDSADSDWEWWREWRERWSDFDFSDSDF123123123123213123");

    }
    protected static String getDate(){
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(currentTime);
    }
}