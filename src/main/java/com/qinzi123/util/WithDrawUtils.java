package com.qinzi123.util;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.happiness.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @title: WithDrawUtils
 * @package: com.qinzi123.util
 * @projectName: trunk
 * @description: 提现工具类
 * @author: jie.yuan
 * @date: 2022/5/5 0005 15:45
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WithDrawUtils {

    private static String mch_appid = "wx2f3e800fce3fd438";//亲子云商appid
    private static String mch_cheng_zhang_go_appid = "wx830f64ad127bbcb1";//成长GOappid
    private static String apiKey = "Qinzi01234567890Qinzi01234567890";//API秘钥：成长GO和亲子云商一致
    private static String mchid = "1255306301";//成长GO商户号
    private static String apiclient_cert_path = "apiclient_cert_cheng_zhang_go.p12";//成长GO支付证书路径

    //生成订单号日期时间+随机字符
    public static String getOrderNumber() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        return date.format(new Date()) + RandomStringUtils.randomNumeric(4);
    }

    public static Map<String, String> fillRequest(String independentMchid, String independentApiKey, Map<String, String> reqData) throws Exception {
        reqData.put("mch_appid", mch_appid);
        if (StringUtils.isEmpty(independentMchid)) {
            reqData.put("mchid", mchid);
        } else {
            reqData.put("mchid", independentMchid);
        }
        reqData.put("nonce_str", WXPayUtil.generateNonceStr().toUpperCase());
        if (StringUtils.isEmpty(independentApiKey)) {
            reqData.put("sign", WXPayUtil.generateSignature(reqData, apiKey, WXPayConstants.SignType.MD5));
        } else {
            reqData.put("sign", WXPayUtil.generateSignature(reqData, independentApiKey, WXPayConstants.SignType.MD5));
        }
        return reqData;
    }

    public static Map<String, String> fillRequestForChengZhangGo(String independentMchid, String independentApiKey, Map<String, String> reqData) throws Exception {
        reqData.put("mch_appid", mch_cheng_zhang_go_appid);
        if (StringUtils.isEmpty(independentMchid)) {
            reqData.put("mchid", mchid);
        } else {
            reqData.put("mchid", independentMchid);
        }
        reqData.put("nonce_str", WXPayUtil.generateNonceStr().toUpperCase());
        if (StringUtils.isEmpty(independentApiKey)) {
            reqData.put("sign", WXPayUtil.generateSignature(reqData, apiKey, WXPayConstants.SignType.MD5));
        } else {
            reqData.put("sign", WXPayUtil.generateSignature(reqData, independentApiKey, WXPayConstants.SignType.MD5));
        }
        return reqData;
    }

    public String getMd5ByString(String str) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes());
            byte[] hash = mdTemp.digest();
            for (byte b : hash) {
                if ((0xff & b) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & b)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & b));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static String getRestInstance(String independentMchid, String apiclientCertPath, String url, String data) throws Exception {
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(url);
        char[] password;
        if (StringUtils.isEmpty(independentMchid)) {
            //商户号
            password = mchid.toCharArray();
        } else {
            //独立小程序商户号
            password = independentMchid.toCharArray();
        }
        ClassPathResource classPathResource;
        if (StringUtils.isEmpty(apiclientCertPath)) {
            classPathResource = new ClassPathResource(apiclient_cert_path);
        } else {
            classPathResource = new ClassPathResource(apiclientCertPath);
        }
        InputStream certStream = classPathResource.getInputStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);
        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), (TrustManager[]) null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(8000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.connect();

        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(data.getBytes(UTF8));
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuilder stringBuffer = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        String resp = stringBuffer.toString();
        try {
            bufferedReader.close();
        } catch (IOException ignored) {
        }

        try {
            inputStream.close();
        } catch (IOException ignored) {
        }

        try {
            outputStream.close();
        } catch (IOException ignored) {
        }

        if (certStream != null) {
            try {
                certStream.close();
            } catch (IOException ignored) {
            }
        }
        return resp;
    }

    public static String getRestInstance1(String url, String data) throws Exception {
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(url);
        char[] password = mchid.toCharArray();
        ClassPathResource classPathResource = new ClassPathResource(apiclient_cert_path);
        InputStream certStream = classPathResource.getInputStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);
        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), (TrustManager[]) null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(8000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.connect();

        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(data.getBytes(UTF8));
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuilder stringBuffer = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        String resp = stringBuffer.toString();
        try {
            bufferedReader.close();
        } catch (IOException ignored) {
        }

        try {
            inputStream.close();
        } catch (IOException ignored) {
        }

        try {
            outputStream.close();
        } catch (IOException ignored) {
        }

        if (certStream != null) {
            try {
                certStream.close();
            } catch (IOException ignored) {
            }
        }
        return resp;
    }
}

