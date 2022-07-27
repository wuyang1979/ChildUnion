package com.qinzi123.controller.micro;

import com.alibaba.fastjson.JSONObject;
import com.qinzi123.service.IndexService;
import com.qinzi123.service.UserOrderService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: IndexController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/8 0008 14:10
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "首页")
public class IndexController {

    @Resource
    private IndexService indexService;

    @Resource
    private UserOrderService userOrderService;

    @ApiOperation(value = "获取手机号", notes = "获取手机号")
    @RequestMapping(value = "/index/getPhoneNumber", method = RequestMethod.POST)
    private Object getPhoneNumber(@RequestBody Map map) {
        String encryptedData = null, session_key = null, iv = null;
        if (map.get("encryptedData") != null && map.get("sessionId") != null && map.get("iv") != null) {
            encryptedData = map.get("encryptedData").toString();
            session_key = map.get("sessionId").toString();
            iv = map.get("iv").toString();
        } else {
            return null;
        }

        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);

        // 加密秘钥
        byte[] keyByte = Base64.decode(session_key);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "添加申请分销合伙人订单", notes = "添加申请分销合伙人订单")
    @RequestMapping(value = "/index/addDistributionPartnerOrder", method = RequestMethod.POST)
    private int addDistributionPartnerOrder(@RequestBody Map map) {
        return indexService.addDistributionPartnerOrder(map);
    }

    @ApiOperation(value = "预支付", notes = "预支付")
    @RequestMapping(value = "/index/joinDistributionPartnerPrePay", method = RequestMethod.POST)
    private Map joinDistributionPartnerPrePay(@RequestBody Map map) {
        return userOrderService.joinDistributionPartnerPrePay(map);
    }

    @ApiOperation(value = "更新用户手机号", notes = "更新用户手机号")
    @RequestMapping(value = "/index/updatePhoneByCard", method = RequestMethod.POST)
    private int updatePhoneByCard(@RequestBody Map map) {
        return indexService.updatePhoneByCard(map);
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @RequestMapping(value = "/index/getUserInfoById", method = RequestMethod.POST)
    private List<LinkedHashMap> getUserInfoById(@RequestBody Map map) {
        String id = map.get("id").toString();
        return indexService.getUserInfoById(id);
    }

    @ApiOperation(value = "获取用户证书数量", notes = "获取用户证书数量")
    @RequestMapping(value = "/index/getCertificateNum", method = RequestMethod.POST)
    private Map getCertificateNum(@RequestBody Map map) {
        return indexService.getCertificateNum(map);
    }

    @ApiOperation(value = "注册用户信息", notes = "注册用户信息")
    @RequestMapping(value = "/index/registerNewUser", method = RequestMethod.POST)
    private Map<String, Object> registerNewUser(@RequestBody Map map) {
        return indexService.registerNewUser(map);
    }

    @ApiOperation(value = "获取b端在亲子云商开通的小店列表", notes = "获取b端在亲子云商开通的小店列表")
    @RequestMapping(value = "/index/getDistributionPartnerListByUserId", method = RequestMethod.POST)
    private List<LinkedHashMap> getDistributionPartnerListByUserId(@RequestBody Map map) {
        return indexService.getDistributionPartnerListByUserId(map);
    }

    @ApiOperation(value = "获取b端在亲子云商开通的小店列表", notes = "获取b端在亲子云商开通的小店列表")
    @RequestMapping(value = "/index/getShopListByUserId2", method = RequestMethod.POST)
    private List<LinkedHashMap> getShopListByUserId2(@RequestBody Map map) {
        return indexService.getShopListByUserId2(map);
    }

    @ApiOperation(value = "根据userId获取分销小店列表", notes = "根据userId获取分销小店列表")
    @RequestMapping(value = "/index/getDistributionShopByUserId", method = RequestMethod.POST)
    private List<LinkedHashMap> getDistributionShopByUserId(@RequestBody Map map) {
        return indexService.getDistributionShopByUserId(map);
    }

    @ApiOperation(value = "测试", notes = "测试")
    @RequestMapping(value = "/index/test", method = RequestMethod.POST)
    private int test() {
        return indexService.test();
    }
}
