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
 * @title: ProductController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/22 0022 11:27
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "产品信息")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private ShopService shopService;

    @Resource
    private ProductDao productDao;

    @ApiOperation(value = "添加产品信息", notes = "添加产品信息")
    @RequestMapping(value = "/product/addProduct", method = RequestMethod.POST)
    private int addProduct(@RequestBody Map map) {
        return productService.addProduct(map);
    }

    @ApiOperation(value = "添加活动信息", notes = "添加活动信息")
    @RequestMapping(value = "/product/addActivity", method = RequestMethod.POST)
    private int addActivity(@RequestBody Map map) {
        return productService.addActivity(map);
    }

    @ApiOperation(value = "获取产品已购数量", notes = "获取产品已购数量")
    @RequestMapping(value = "/product/getBuyCountById", method = RequestMethod.POST)
    private Map<String, Object> getBuyCountById(@RequestBody Map map) {
        return productService.getBuyCountById(map);
    }

    @ApiOperation(value = "修改产品信息", notes = "修改产品信息")
    @RequestMapping(value = "/product/updateProduct", method = RequestMethod.POST)
    private int updateProduct(@RequestBody Map map) {
        return productService.updateProduct(map);
    }

    @ApiOperation(value = "修改活动信息", notes = "修改活动信息")
    @RequestMapping(value = "/product/updateActivity", method = RequestMethod.POST)
    private int updateActivity(@RequestBody Map map) {
        return productService.updateActivity(map);
    }

    @ApiOperation(value = "删除产品", notes = "删除产品")
    @RequestMapping(value = "/product/deleteProductById", method = RequestMethod.POST)
    private int deleteProductById(@RequestBody Map map) {
        return productService.deleteProductById(map);
    }

    @ApiOperation(value = "商户提交上架产品申请", notes = "商户提交上架产品申请")
    @RequestMapping(value = "/product/upProduct", method = RequestMethod.POST)
    private int upProduct(@RequestBody Map map) {
        return productService.upProduct(map);
    }

    @ApiOperation(value = "商户提交上架活动申请", notes = "商户提交上架活动申请")
    @RequestMapping(value = "/product/upActivity", method = RequestMethod.POST)
    private int upActivity(@RequestBody Map map) {
        return productService.upActivity(map);
    }

    @ApiOperation(value = "产品下架", notes = "产品下架")
    @RequestMapping(value = "/product/downProduct", method = RequestMethod.POST)
    private int downProduct(@RequestBody Map map) {
        return productService.downProduct(map);
    }

    @ApiOperation(value = "活动下架", notes = "活动下架")
    @RequestMapping(value = "/product/downActivity", method = RequestMethod.POST)
    private int downActivity(@RequestBody Map map) {
        return productService.downActivity(map);
    }

    @RequestMapping(value = "/product/productList", method = RequestMethod.POST)
    private List<LinkedHashMap> listProduct(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return productService.listProduct(card, start, num);
    }

    @RequestMapping(value = "/product/activityList", method = RequestMethod.POST)
    private List<LinkedHashMap> listActivity(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return productService.listActivity(card, start, num);
    }


    @RequestMapping(value = "/product/getReleasedProductList", method = RequestMethod.POST)
    private List<LinkedHashMap> listReleasedProduct(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int index = Integer.parseInt(map.get("index").toString());
        List<LinkedHashMap> resultList = null;
        if (index == 0) {
            resultList = productService.listReleasedProduct(card, start, num);
        } else if (index == 1) {
            resultList = productService.listDistributionProduct(card, start, num);
        }
        return resultList;
    }

    @RequestMapping(value = "/product/getSearchReleasedProductList", method = RequestMethod.POST)
    private List<LinkedHashMap> listSearchReleasedProduct(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String searchValue = map.get("searchValue").toString();
        int index = Integer.parseInt(map.get("index").toString());
        List<LinkedHashMap> resultList = null;
        if (index == 0) {
            resultList = productService.listSearchReleasedProduct(card, start, num, searchValue);
        } else if (index == 1) {
            resultList = productService.listSearchDistributionProduct(card, start, num, searchValue);
        }
        return resultList;
    }

    @RequestMapping(value = "/product/getAllList", method = RequestMethod.POST)
    private List<LinkedHashMap> getAllListProduct(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int type = Integer.parseInt(map.get("type").toString());
        List<LinkedHashMap> allList = productService.getAllListProduct(type, start, num);
        allList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            if (productType == 1) {
                //活动
                List<LinkedHashMap> standardsList = productService.getStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(item);
                List<LinkedHashMap> standardsListOrderByDistributionMin = productDao.getStandardsListOrderByDistributionMin(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                item.put("distributionMin", standardsListOrderByDistributionMin.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(item);
                List<LinkedHashMap> standardsListOrderByDistributionMin = productDao.getProductStandardsListOrderByDistributionMin(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                item.put("distributionMin", standardsListOrderByDistributionMin.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }

            item.put("company_name", productService.getCompanyNameByCompanyId(item));
        });
        return allList;
    }

    @RequestMapping(value = "/product/getExclusiveAllList", method = RequestMethod.POST)
    private List<LinkedHashMap> getExclusiveAllList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int type = Integer.parseInt(map.get("type").toString());
        int shopId = Integer.parseInt(map.get("shopId").toString());
        List<LinkedHashMap> allList = productService.getExclusiveAllList(shopId, type, start, num);
        allList.forEach(item -> {
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
        return allList;
    }

    @RequestMapping(value = "/product/getSearchList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSearchList(@RequestBody Map map) {
        List<LinkedHashMap> allList = productService.getSearchList(map);
        allList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            if (productType == 1) {
                //活动
                List<LinkedHashMap> standardsList = productService.getStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(item);
                List<LinkedHashMap> standardsListOrderByDistributionMin = productDao.getStandardsListOrderByDistributionMin(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                item.put("distributionMin", standardsListOrderByDistributionMin.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(item);
                List<LinkedHashMap> standardsListOrderByDistributionMin = productDao.getProductStandardsListOrderByDistributionMin(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                item.put("distributionMin", standardsListOrderByDistributionMin.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }
            item.put("company_name", productService.getCompanyNameByCompanyId(item));
        });
        return allList;
    }

    @RequestMapping(value = "/product/getExclusiveSearchList", method = RequestMethod.POST)
    private List<LinkedHashMap> getExclusiveSearchList(@RequestBody Map map) {
        List<LinkedHashMap> allList = productService.getExclusiveSearchList(map);
        allList.forEach(item -> {
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
        return allList;
    }

    @ApiOperation(value = "精选联盟产品列表", notes = "精选联盟产品列表")
    @RequestMapping(value = "/product/getAllianceProductList", method = RequestMethod.POST)
    private List<LinkedHashMap> getAllianceProductList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int index = Integer.parseInt(map.get("index").toString());
        int card = Integer.parseInt(map.get("card").toString());
        List<LinkedHashMap> resultList = null;
        if (index == 0) {
            resultList = productService.getAllAllianceProductList(card, start, num);
        } else if (index == 1) {
            resultList = productService.getAllianceProductListOrderByBuyCount(card, start, num);
        }
        return resultList;
    }

    @ApiOperation(value = "搜索精选联盟产品列表", notes = "搜索精选联盟产品列表")
    @RequestMapping(value = "/product/getSearchAllianceProductList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSearchAllianceProductList(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String searchValue = map.get("searchValue").toString();
        int index = Integer.parseInt(map.get("index").toString());
        List<LinkedHashMap> resultList = null;
        if (index == 0) {
            resultList = productService.getSearchAllianceProductList(card, start, num, searchValue);
        } else if (index == 1) {
            resultList = productService.getSearchAllianceProductListOrderByBuyCount(card, start, num, searchValue);
        }
        return resultList;
    }

    @ApiOperation(value = "添加分销记录", notes = "添加分销记录")
    @RequestMapping(value = "/product/addDistributionRecord", method = RequestMethod.POST)
    private int addDistributionRecord(@RequestBody Map map) {
        return productService.addDistributionRecord(map);
    }

    @RequestMapping(value = "/product/getOtherImagesById", method = RequestMethod.POST)
    private List<LinkedHashMap> getOtherImagesById(@RequestBody Map map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        return productService.getOtherImagesById(productId);
    }

    @RequestMapping(value = "/product/getReleaseShopInfoByShopId", method = RequestMethod.POST)
    private Map<String, Object> getReleaseShopInfoByShopId(@RequestBody Map map) {
        return productService.getReleaseShopInfoByShopId(map);
    }

    @RequestMapping(value = "/product/getReleaseCompanyInfoByCompanyId", method = RequestMethod.POST)
    private List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(@RequestBody Map map) {
        return productService.getReleaseCompanyInfoByCompanyId(map);
    }

    @RequestMapping(value = "/product/getInventoryByStandardId", method = RequestMethod.POST)
    private int getInventoryByStandardId(@RequestBody Map map) {
        return productService.getInventoryByStandardId(map);
    }

    @RequestMapping(value = "/product/getSelectedOrderList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSelectedOrderList(@RequestBody Map map) {
        return productService.getSelectedOrderList(map);
    }

    @RequestMapping(value = "/product/getSelectedOrderListByUserId", method = RequestMethod.POST)
    private List<LinkedHashMap> getSelectedOrderListByUserId(@RequestBody Map map) {
        return productService.getSelectedOrderListByUserId(map);
    }

    @RequestMapping(value = "/product/getOrderCount", method = RequestMethod.POST)
    private Map<String, Object> getOrderCount(@RequestBody Map map) {
        return productService.getOrderCount(map);
    }

    @RequestMapping(value = "/product/loadOrderCountByUserId", method = RequestMethod.POST)
    private Map<String, Object> loadOrderCountByUserId(@RequestBody Map map) {
        return productService.loadOrderCountByUserId(map);
    }

    @RequestMapping(value = "/product/getProductStandardListByProductId", method = RequestMethod.POST)
    private List<LinkedHashMap> getProductStandardListByProductId(@RequestBody Map map) {
        return productService.getProductStandardListByProductId(map);
    }

    @RequestMapping(value = "/product/getStandardListByProductId", method = RequestMethod.POST)
    private List<LinkedHashMap> getStandardListByProductId(@RequestBody Map map) {
        return productService.getStandardListByProductId(map);
    }

    @RequestMapping(value = "/product/getOrderCountByproductId", method = RequestMethod.POST)
    private int getOrderCountByproductId(@RequestBody Map map) {
        return productService.getOrderCountByproductId(map);
    }

    @RequestMapping(value = "/product/loadProductInfoById", method = RequestMethod.POST)
    private Map loadProductInfoById(@RequestBody Map map) {
        return productService.loadProductInfoById(map);
    }

    @ApiOperation(value = "b端获取产品二维码，指向c端产品详情页", notes = "b端获取产品二维码，指向c端产品详情页")
    @RequestMapping(value = "/product/bEndGenerateActivityQrCode/{productId}-{card}", method = RequestMethod.GET)
    public void bEndGenerateActivityQrCode(@PathVariable("productId") String productId,
                                           @PathVariable("card") String card,
                                           HttpServletResponse response) throws Exception {
        //获取AccessToken
        String page = "pages/product/oneProduct";
        Map<String, Object> shopParamsMap = new HashedMap();
        shopParamsMap.put("card", card);
        List<LinkedHashMap> shopList = shopService.getShopInfoByCardId(shopParamsMap);
        Map<String, Object> shopInfo = shopList.get(0);
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
        //获取小店id
        int issuerShopId = productService.getIssuerShopIdByProductId(Integer.parseInt(productId));
        int myShopId = productService.getMyShopIdByCard(Integer.parseInt(card));
        String scene = null;
        //字段qr=1 代表由扫码进入
        if (issuerShopId == myShopId) {
            //非分销商品
            scene = "i=" + productId + "&disShopId=-1" + "&qr=1";
        } else {
            //分销商品
            scene = "i=" + productId + "&disShopId=" + myShopId + "&qr=1";
        }
        //二维码携带参数 不超过32位 参数类型必须是字符串  这里尤其要注意
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

    /*
     * @Author: jie.yuan
     * @Description:
     * @Date: 2022/4/12 0012 15:27
     * @Param [productId, userId, enterFromShop, response]
     * enterFromShop(从小店进入) ---> 0：是；1：否
     * @return void
     **/
    @ApiOperation(value = "成长GO海报获取二维码，指向产品详情页", notes = "成长GO海报获取二维码，指向产品详情页")
    @RequestMapping(value = "/product/generateActivityQrCode/{productId}-{userId}-{enterFromShop}", method = RequestMethod.GET)
    public void downloadQrCode(@PathVariable("productId") String productId,
                               @PathVariable("userId") String userId,
                               @PathVariable("enterFromShop") String enterFromShop,
                               HttpServletResponse response) throws Exception {
        //获取AccessToken
        String page = "pages/product/oneProduct";
        Map<String, Object> issuerShopInfo = productService.getIssuerShopInfoByProductId(Integer.parseInt(productId));
        int type = Integer.parseInt(issuerShopInfo.get("type").toString());
        String accessToken = "";
        if (type == 0) {
            accessToken = Utils.getWxChengZhangGoAccessToken();
        } else if (type == 1) {
            String appId = issuerShopInfo.get("app_id").toString();
            String appSecret = issuerShopInfo.get("app_secret").toString();
            accessToken = Utils.getIndependentAppletAccessToken(appId, appSecret);
        }

        byte[] qrCodeBytes = null;
        Map<String, Object> paraMap = new HashMap();
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
        url += accessToken;
        String phone = productService.getPhoneByUserId(userId);
        //产品id+分享者手机号码
        String scene = "i=" + productId + "&p=" + phone + "&f=" + enterFromShop;
        //二维码携带参数 不超过32位 参数类型必须是字符串
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

    @ApiOperation(value = "获取所有已上架活动", notes = "获取所有已上架活动")
    @RequestMapping(value = "/product/getAllActivityList", method = RequestMethod.POST)
    private List<LinkedHashMap> getAllActivityList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return productService.getAllActivityList(start, num);
    }

    @ApiOperation(value = "搜索已上架活动", notes = "搜索已上架活动")
    @RequestMapping(value = "/product/getSearchActivityList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSearchActivityList(@RequestBody Map map) {
        return productService.getSearchActivityList(map);
    }

    @ApiOperation(value = "获取独立小程序推荐活动产品列表", notes = "获取独立小程序推荐活动产品列表")
    @RequestMapping(value = "/product/getRecommendProductListByShopId", method = RequestMethod.POST)
    private List<LinkedHashMap> getRecommendProductListByShopId(@RequestBody Map map) {
        List<LinkedHashMap> allList = productService.getRecommendProductListByShopId(map);
        allList.forEach(item -> {
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
        return allList;
    }

    @ApiOperation(value = "获取推荐活动产品列表", notes = "获取推荐活动产品列表")
    @RequestMapping(value = "/product/getRecommendProductList", method = RequestMethod.POST)
    private List<LinkedHashMap> getRecommendProductList(@RequestBody Map map) {
        List<LinkedHashMap> allList = productService.getRecommendProductList(map);
        allList.forEach(item -> {
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
        return allList;
    }

    @ApiOperation(value = "生成活动签到二维码", notes = "生成活动签到二维码")
    @RequestMapping(value = "/prorduct/getActivitySignQrCode/{productId}", method = RequestMethod.GET)
    public void getActivitySignQrCode(@PathVariable("productId") String productId,
                                      HttpServletResponse response) throws Exception {
        //获取AccessToken
        String page = "pages/activitySign/sign";
        String accessToken = Utils.getWxChengZhangGoAccessToken();
        byte[] qrCodeBytes = null;
        Map<String, Object> paraMap = new HashMap();
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
        url += accessToken;
        //二维码携带参数 不超过32位 参数类型必须是字符串
        paraMap.put("scene", productId);  //存入的参数
        paraMap.put("page", page);
        qrCodeBytes = WeChatUtil.getminiqrQr(url, paraMap);
        response.setContentType("image/jpg");
        // 写入response的输出流中
        OutputStream stream = response.getOutputStream();
        stream.write(qrCodeBytes);
        stream.flush();
        stream.close();
    }

    @ApiOperation(value = "根据产品id查询活动图片", notes = "根据产品id查询活动图片")
    @RequestMapping(value = "/product/getActivityPictureByProductId", method = RequestMethod.POST)
    private List<LinkedHashMap> getActivityPictureByProductId(@RequestBody Map map) {
        return productService.getActivityPictureByProductId(map);
    }

    @ApiOperation(value = "保存活动图片", notes = "保存活动图片")
    @RequestMapping(value = "/product/saveActivityPictureById", method = RequestMethod.POST)
    private int saveActivityPictureById(@RequestBody Map map) {
        return productService.saveActivityPictureById(map);
    }

    @ApiOperation(value = "删除分销记录", notes = "删除分销记录")
    @RequestMapping(value = "/product/deleteDistributionRecord", method = RequestMethod.POST)
    private int deleteDistributionRecord(@RequestBody Map map) {
        return productService.deleteDistributionRecord(map);
    }

    @ApiOperation(value = "查询上架状态", notes = "查询上架状态")
    @RequestMapping(value = "/product/checkProductStatus", method = RequestMethod.POST)
    private int checkProductStatus(@RequestBody Map map) {
        return productService.checkProductStatus(map);
    }

}
