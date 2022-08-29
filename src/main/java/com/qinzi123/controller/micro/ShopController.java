package com.qinzi123.controller.micro;

import com.qinzi123.dao.ProductDao;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.ProductService;
import com.qinzi123.service.ShopService;
import com.qinzi123.util.Utils;
import com.qinzi123.util.WeChatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * @title: ShopController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/3/22 0022 13:50
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "小店信息")
public class ShopController {

    @Resource
    private ShopService shopService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductDao productDao;

    @ApiOperation(value = "获取小店信息", notes = "获取小店信息")
    @RequestMapping(value = "/shop/getShopInfoByCardId", method = RequestMethod.POST)
    private List<LinkedHashMap> getShopInfoByCardId(@RequestBody Map map) {
        return shopService.getShopInfoByCardId(map);
    }

    @ApiOperation(value = "检查小店名称是否重名", notes = "检查小店名称是否重名")
    @RequestMapping(value = "/shop/checkShopName", method = RequestMethod.POST)
    private List<LinkedHashMap> checkShopName(@RequestBody Map map) {
        return shopService.checkShopName(map);
    }

    @ApiOperation(value = "检查同公司下是否已有人开通小店", notes = "检查同公司下是否已有人开通小店")
    @RequestMapping(value = "/shop/checkCompanyShop", method = RequestMethod.POST)
    private List<LinkedHashMap> checkCompanyShop(@RequestBody Map map) {
        return shopService.checkCompanyShop(map);
    }

    @ApiOperation(value = "添加小店信息", notes = "添加小店信息")
    @RequestMapping(value = "/shop/addShop", method = RequestMethod.POST)
    private int addShop(@RequestBody Map map) {
        return shopService.addShop(map);
    }

    @ApiOperation(value = "更新小店信息", notes = "更新小店信息")
    @RequestMapping(value = "/shop/updateShopInfoById", method = RequestMethod.POST)
    private int updateShopInfoById(@RequestBody Map map) {
        return shopService.updateShopInfoById(map);
    }

    @ApiOperation(value = "获取小店信息", notes = "获取小店信息")
    @RequestMapping(value = "/shop/getShopInfo", method = RequestMethod.POST)
    private List<LinkedHashMap> getShopInfo(@RequestBody Map map) {
        return shopService.getShopInfo(map);
    }

    @ApiOperation(value = "新增核销人员", notes = "新增核销人员")
    @RequestMapping(value = "/shop/addWriteOffClerk", method = RequestMethod.POST)
    private Map<String, Object> addWriteOffClerk(@RequestBody Map map) throws Exception {
        return shopService.addWriteOffClerk(map);
    }

    @ApiOperation(value = "通过小店id查询所有核销人员", notes = "通过小店id查询所有核销人员")
    @RequestMapping(value = "/shop/getAllWriteOffClerkListByShopId", method = RequestMethod.POST)
    private List<LinkedHashMap> getAllWriteOffClerkListByCompanyId(@RequestBody Map map) {
        return shopService.getAllWriteOffClerkListByCompanyId(map);
    }

    @ApiOperation(value = "通过id查询成长GO小店展示信息", notes = "通过id查询成长GO小店展示信息")
    @RequestMapping(value = "/shop/getShopShowInfoById", method = RequestMethod.POST)
    private Map<String, Object> getShopShowInfoById(@RequestBody Map map) {
        return shopService.getShopShowInfoById(map);
    }

    @ApiOperation(value = "查询小店类型", notes = "查询小店类型")
    @RequestMapping(value = "/shop/getShopTypeByCardId", method = RequestMethod.POST)
    private Map<String, Object> getShopTypeByCardId(@RequestBody Map map) {
        return shopService.getShopTypeByCardId(map);
    }

    @ApiOperation(value = "b端获取小店二维码，指向c端小店详情页", notes = "b端获取小店二维码，指向c端小店详情页")
    @RequestMapping(value = "/shop/generateShopQrCode/{shopId}", method = RequestMethod.GET)
    public void downloadQrCode(@PathVariable("shopId") String shopId,
                               HttpServletResponse response) throws Exception {
        //获取AccessToken
        String page = "pages/shop/myShop";
        Map<String, Object> shopParamsMap = new HashedMap();
        shopParamsMap.put("shopId", shopId);
        List<LinkedHashMap> shopInfoList = shopService.getShopInfo(shopParamsMap);
        Map<String, Object> shopInfo = shopInfoList.get(0);
        int shopType = Integer.parseInt(shopInfo.get("type").toString());
        String accessToken = "";
        if (shopType == 0) {
            accessToken = Utils.getWxChengZhangGoAccessToken();
        } else if (shopType == 1) {
            String appId = shopInfo.get("app_id").toString();
            String appSecret = shopInfo.get("app_secret").toString();
            accessToken = Utils.getIndependentAppletAccessToken(appId, appSecret);
        }
        if (StringUtils.isEmpty(accessToken)) {
            throw new GlobalProcessException("accessToken获取出错");
        }
        byte[] qrCodeBytes;
        Map<String, Object> paraMap = new HashMap();
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
        url += accessToken;
        //二维码携带参数 不超过32位 参数类型必须是字符串
        String scene = "shopId=" + shopId;
        paraMap.put("scene", scene);  //存入的参数
        paraMap.put("page", page);
        qrCodeBytes = WeChatUtil.getminiqrQr(url, paraMap);
        response.setContentType("image/jpg");
        // 写入response的输出流中
        OutputStream stream = response.getOutputStream();
        stream.write(qrCodeBytes);
        stream.flush();
        stream.close();
    }

    @ApiOperation(value = "获取小店下所有上架商品", notes = "获取小店下所有上架商品")
    @RequestMapping(value = "/shop/loadAllProductByShopId", method = RequestMethod.POST)
    private List<LinkedHashMap> loadAllProductByShopId(@RequestBody Map map) {
        int shopId = Integer.parseInt(map.get("shopId").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        List<LinkedHashMap> productList = shopService.listProuct(shopId, start, num);
        productList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            if (productType == 1) {
                //活动
                List<LinkedHashMap> standardsList = productService.getStandardsList(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsList.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsList.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }
            item.put("company_name", productService.getCompanyNameByCompanyId(item));
        });
        return productList;
    }

    @ApiOperation(value = "通过产品id查询对应公司id", notes = "通过产品id查询对应公司id")
    @RequestMapping(value = "/shop/getCompanyIdInnerShopByProductId", method = RequestMethod.POST)
    private Map<String, Object> getCompanyIdInnerShopByProductId(@RequestBody Map map) {
        return shopService.getCompanyIdInnerShopByProductId(map);
    }


    @ApiOperation(value = "通过产品id查询群二维码", notes = "通过产品id查询群二维码")
    @RequestMapping(value = "/shop/getQrCodeByProductId", method = RequestMethod.POST)
    private Map<String, Object> getQrCodeByProductId(@RequestBody Map map) {
        return shopService.getQrCodeByProductId(map);
    }

    @ApiOperation(value = "自增小店访问次数", notes = "自增小店访问次数")
    @RequestMapping(value = "/shop/addVisitCountByShopId", method = RequestMethod.POST)
    private int addVisitCountByShopId(@RequestBody Map map) {
        return shopService.addVisitCountByShopId(map);
    }
}
