package com.qinzi123.util;

import com.alibaba.fastjson.JSONObject;
import com.qinzi123.dto.TemplateData;
import com.qinzi123.dto.WxMssVo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: WeChatUtil
 * @package: com.qinzi123.util
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/15 0015 19:59
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public class WeChatUtil {

    public static String httpRequest(String requestUrl, String requestMethod, String output) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("utf-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @return java.lang.String
     * @Author: jie.yuan
     * @Description: 微信推送关注订阅消息
     * @Date: 2021/11/15 0015 16:35
     * @Param [openId]
     **/
    public static String pushFollowMessageToOneUser(String openId, String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + Utils.getWxAccessToken();
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);
        //订阅消息模板id
        wxMssVo.setTemplate_id("01EMtrNhQzLgkppeVZf0PtmoOk812HevdmwKDZQqkUE");
        wxMssVo.setPage("pages/message/messageController");

        Map<String, TemplateData> m = new HashMap<>(2);
        m.put("thing3", new TemplateData(name));
        m.put("time2", new TemplateData(DateUtils.getAccurateDate()));
        wxMssVo.setData(m);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
    }

    public static byte[] getminiqrQr(String url, Map<String, Object> paraMap) throws Exception {
        byte[] result;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");

        // 设置请求的参数
        JSONObject postData = new JSONObject();
        for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
            postData.put(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(new StringEntity(postData.toString(), "UTF-8"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toByteArray(entity);
        return result;
    }
}
