package com.qinzi123.controller.micro;

import com.qinzi123.service.UserOrderService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import com.qinzi123.util.WeChatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;

/**
 * @title: UserOrderController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/16 0016 11:18
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
@RestController
@Api(value = "C端订单")
@Configurable
@EnableScheduling
public class UserOrderController {

    //微信订单支付有效时长20分钟
    private int orderValidityMinute = 20;

    @Resource
    private UserOrderService userOrderService;

    /**
     * 执行定时任务，暂定每隔30分钟.
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void reportCurrentTime() {
        List<LinkedHashMap> pendingPayList = userOrderService.getPendingPayList();
        if (pendingPayList.size() > 0) {
            pendingPayList.forEach(item -> {
                String now = DateUtils.getAccurateDate();
                String orderExpirationTime = DateUtils.minuteAfter(item.get("create_time").toString(), orderValidityMinute);
                try {
                    if (DateUtils.compareDate(orderExpirationTime, now)) {
                        //订单已失效
                        //更新状态
                        userOrderService.updateOrderStatusById(item);
                        //还原库存
                        int orderType = Integer.parseInt(item.get("order_type").toString());
                        int standardId = Integer.parseInt(item.get("standard_id").toString());
                        int num = Integer.parseInt(item.get("num").toString());
                        if (orderType == 0) {
                            //产品订单
                            userOrderService.updateProductInventoryByStandardId(standardId, num);
                        } else if (orderType == 1) {
                            //活动订单
                            userOrderService.updateActivityInventoryByStandardId(standardId, num);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @ApiOperation(value = "用户产品订单新增", notes = "用户产品订单新增")
    @RequestMapping(value = "/userOrder/data", method = RequestMethod.POST)
    private int addUserOrder(@RequestBody Map map) {
        return userOrderService.addUserOrder(map);
    }

    @ApiOperation(value = "用户活动订单新增", notes = "用户活动订单新增")
    @RequestMapping(value = "/userOrder/dataForActivity", method = RequestMethod.POST)
    private int addUserActivityOrder(@RequestBody Map map) {
        return userOrderService.addUserActivityOrder(map);
    }

    @ApiOperation(value = "用户免费产品订单新增", notes = "用户免费产品订单新增")
    @RequestMapping(value = "/userOrder/freeData", method = RequestMethod.POST)
    private int addUserFreeOrder(@RequestBody HashMap map) {
        return userOrderService.addUserFreeOrder(map);
    }

    @ApiOperation(value = "用户免费活动订单新增", notes = "用户免费活动订单新增")
    @RequestMapping(value = "/userOrder/freeDataForActivity", method = RequestMethod.POST)
    private int addUserFreeActivityOrder(@RequestBody HashMap map) {
        return userOrderService.addUserFreeActivityOrder(map);
    }

    @ApiOperation(value = "查询订单显示信息", notes = "查询订单显示信息")
    @RequestMapping(value = "/userOrder/getShowInfo", method = RequestMethod.POST)
    private Map getShowInfo(@RequestBody Map map) {
        return userOrderService.getShowInfo(map);
    }

    @ApiOperation(value = "预支付", notes = "预支付")
    @RequestMapping(value = "/userOrder/prePay", method = RequestMethod.POST)
    private Map prePay(@RequestBody Map map) {
        return userOrderService.prepay(map);
    }

    @ApiOperation(value = "查询收货地址列表", notes = "查询收货地址列表")
    @RequestMapping(value = "/userOrder/getReceiveAddress", method = RequestMethod.POST)
    private List<LinkedHashMap> getReceiveAddress(@RequestBody Map map) {
        return userOrderService.getReceiveAddress(map);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表")
    @RequestMapping(value = "/userOrder/getSelectedOrderList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSelectedOrderList(@RequestBody Map map) {
        return userOrderService.getSelectedOrderList(map);
    }

    @ApiOperation(value = "确认收货", notes = "确认收货")
    @RequestMapping(value = "/userOrder/confirmReceipt", method = RequestMethod.POST)
    private int confirmReceipt(@RequestBody Map map) throws Exception {
        return userOrderService.confirmReceipt(map);
    }

    @ApiOperation(value = "查询订单状态", notes = "查询订单状态")
    @RequestMapping(value = "/userOrder/checkOrderStatus", method = RequestMethod.POST)
    private int checkOrderStatus(@RequestBody Map map) {
        return userOrderService.checkOrderStatus(map);
    }

    @ApiOperation(value = "核销活动订单", notes = "核销活动订单")
    @RequestMapping(value = "/userOrder/writeOffUserOrder", method = RequestMethod.POST)
    private int writeOffUserOrder(@RequestBody Map map) throws Exception {
        return userOrderService.writeOffUserOrder(map);
    }

    @ApiOperation(value = "获取产品名称", notes = "获取产品名称")
    @RequestMapping(value = "/userOrder/getProductNameByOrderId", method = RequestMethod.POST)
    private Map getProductNameByOrderId(@RequestBody Map map) {
        return userOrderService.getProductNameByOrderId(map);
    }

    @ApiOperation(value = "通过openId获取核销人员信息", notes = "通过openId获取核销人员信息")
    @RequestMapping(value = "/userOrder/getWriteOffClerkByOpenId", method = RequestMethod.POST)
    private List<LinkedHashMap> getWriteOffClerkByOpenId(@RequestBody Map map) {
        return userOrderService.getWriteOffClerkByOpenId(map);
    }

    @ApiOperation(value = "校验核销活动与核销人员所属小店是否一致", notes = "校验核销活动与核销人员所属小店是否一致")
    @RequestMapping(value = "/userOrder/checkWhiteOffInfo", method = RequestMethod.POST)
    private Map<String, Object> checkWhiteOffInfo(@RequestBody Map map) throws Exception {
        return userOrderService.checkWhiteOffInfo(map);
    }

    @ApiOperation(value = "查询用户购买记录", notes = "查询用户购买记录")
    @RequestMapping(value = "/userOrder/getOrderListByUserIdAndProductId", method = RequestMethod.POST)
    private List<LinkedHashMap> getOrderListByUserIdAndProductId(@RequestBody Map map) {
        return userOrderService.getOrderListByUserIdAndProductId(map);
    }

    @ApiOperation(value = "刷新异常订单，超时自动收货", notes = "刷新异常订单，超时自动收货")
    @RequestMapping(value = "/userOrder/refreshOrder", method = RequestMethod.POST)
    private int refreshOrder() {
        return userOrderService.refreshOrder();
    }

    @ApiOperation(value = "添加核销人员二维码", notes = "添加核销人员二维码")
    @RequestMapping(value = "/userOrder/getAddWriteOffClerkQrCode/{shopId}", method = RequestMethod.GET)
    public void getAddWriteOffClerkQrCode(@PathVariable("shopId") String shopId,
                                          HttpServletResponse response) throws Exception {
        //获取AccessToken
        String page = "pages/writeOff/addClerk";
        String accessToken = Utils.getWxAccessToken();
        byte[] qrCodeBytes = null;
        Map<String, Object> paraMap = new HashMap();
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
        url += accessToken;
        //二维码携带参数 不超过32位 参数类型必须是字符串
        paraMap.put("scene", shopId);  //存入的参数
        paraMap.put("page", page);
        qrCodeBytes = WeChatUtil.getminiqrQr(url, paraMap);
        response.setContentType("image/jpg");
        // 写入response的输出流中
        OutputStream stream = response.getOutputStream();
        stream.write(qrCodeBytes);
        stream.flush();
        stream.close();
    }

    @ApiOperation(value = "获取活动核销二维码", notes = "获取活动核销二维码")
    @RequestMapping(value = "/userOrder/download-qrCode/{orderId}", method = RequestMethod.GET)
    public void downloadQrCode(@PathVariable("orderId") String orderId,
                               HttpServletResponse response) throws Exception {
        //获取AccessToken
        String page = "pages/writeOff/operation";
        String accessToken = Utils.getWxAccessToken();
        byte[] qrCodeBytes = null;
        Map<String, Object> paraMap = new HashMap();
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
        url += accessToken;
        //二维码携带参数 不超过32位 参数类型必须是字符串
        paraMap.put("scene", orderId);  //存入的参数
        paraMap.put("page", page);
        qrCodeBytes = WeChatUtil.getminiqrQr(url, paraMap);
        response.setContentType("image/jpg");
        // 写入response的输出流中
        OutputStream stream = response.getOutputStream();
        stream.write(qrCodeBytes);
        stream.flush();
        stream.close();
    }

    @ApiOperation(value = "签到码入口更新订单信息", notes = "签到码入口更新订单信息")
    @RequestMapping(value = "/userOrder/updateOrderByProductIdAndPhone", method = RequestMethod.POST)
    private Map<String, Object> updateOrderByProductIdAndPhone(@RequestBody Map map) {
        return userOrderService.updateOrderByProductIdAndPhone(map);
    }

    @ApiOperation(value = "获取买家信息回显", notes = "获取买家信息回显")
    @RequestMapping(value = "/userOrder/loadBuyerInfoByOrderId", method = RequestMethod.POST)
    private Map<String, Object> loadBuyerInfoByOrderId(@RequestBody Map map) {
        return userOrderService.loadBuyerInfoByOrderId(map);
    }

    @ApiOperation(value = "获取活动规格", notes = "获取活动规格")
    @RequestMapping(value = "/userOrder/loadStandardByOrderId", method = RequestMethod.POST)
    private Map<String, Object> loadStandardByOrderId(@RequestBody Map map) {
        return userOrderService.loadStandardByOrderId(map);
    }

    @ApiOperation(value = "获取买家收货地址", notes = "获取买家收货地址")
    @RequestMapping(value = "/userOrder/loadReceiveAddressByOrderId", method = RequestMethod.POST)
    private Map<String, Object> loadReceiveAddressByOrderId(@RequestBody Map map) {
        return userOrderService.loadReceiveAddressByOrderId(map);
    }

    @ApiOperation(value = "区分是否供应商", notes = "区分是否供应商")
    @RequestMapping(value = "/userOrder/differentiateSupplier", method = RequestMethod.POST)
    private Map<String, Object> differentiateSupplier(@RequestBody Map map) {
        return userOrderService.differentiateSupplier(map);
    }

}
