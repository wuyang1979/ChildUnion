package com.qinzi123.controller.web;

import com.qinzi123.dto.EnterpriseInfo;
import com.qinzi123.dto.LoginUser;
import com.qinzi123.service.EntityService;
import com.qinzi123.util.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @title: EntityController
 * @package: com.qinzi123.controller.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@RequestMapping(value = "/manage")
public class EntityController {

    @Autowired
    EntityService entityService;

    private static final String City_Saas = "card_info";
    private static final String SAAS_KEY = "city";
    private static final String PRODUCT = "wx_product";
    private static final String ACTIVITY_BASE = "activity_base";
    private static final String ACTIVITY_BASE_RESERVE = "activity_base_reserve";
    private static final String ENTERPRISE_INFO = "enterprise_info";
    private static final String ENTERPRISE_CONSULTING = "enterprise_consulting";
    private static final String ENTERPRISE_COMMENT = "enterprise_comment";
    private static final String BUSS_LEAGUE_INFO = "buss_league_info";
    private static final String PRODUCT_INFO = "product_info";
    private static final String C_END_ORDER = "c_end_order";

    @RequestMapping(value = "/{tableName}/addEntity", method = RequestMethod.POST)
    public int addService(@PathVariable("tableName") String tableName,
                          @RequestBody Map<String, Object> map) {
        if (PRODUCT.equals(tableName)) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("channel_price", map.get("channel_price"));
            paramsMap.put("company", map.get("company"));
            paramsMap.put("currency", map.get("currency"));
            paramsMap.put("deadline_time", map.get("deadline_time"));
            paramsMap.put("detail", map.get("detail"));
            paramsMap.put("limit_stock", map.get("limit_stock"));
            paramsMap.put("main_image", map.get("main_image"));
            paramsMap.put("name", map.get("name"));
            paramsMap.put("price", map.get("price"));
            paramsMap.put("status", map.get("status"));
            paramsMap.put("stock", map.get("stock"));
            int rows = entityService.addService(tableName, paramsMap);
            int productId = -1;
            if (map.get("name") != null) {
                productId = entityService.getProductId(map.get("name").toString());
            }
            if (map.get("other_image") != null) {
                if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                    Map<String, Object> picMap = new HashMap<>();
                    picMap.put("productId", productId);
                    picMap.put("other_image", map.get("other_image"));
                    entityService.addOtherImages(picMap);
                }
            }
            return rows;
        } else if (ACTIVITY_BASE.equals(tableName)) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("card_id", map.get("card_id") == null ? -1 : map.get("card_id"));
            paramsMap.put("city", map.get("city"));
            paramsMap.put("longitude", map.get("longitude"));
            paramsMap.put("latitude", map.get("latitude"));
            paramsMap.put("create_time", DateUtils.getDateTime());
            paramsMap.put("district", map.get("district"));
            paramsMap.put("introduce", map.get("introduce"));
            paramsMap.put("level", map.get("level"));
            paramsMap.put("main_image", map.get("main_image"));
            paramsMap.put("name", map.get("name"));
            paramsMap.put("official_account_name", map.get("official_account_name"));
            paramsMap.put("open_time", map.get("open_time"));
            paramsMap.put("price", map.get("price"));
            paramsMap.put("leaguetype", map.get("leaguetype"));
            paramsMap.put("phone", map.get("phone"));
            String typeStr = map.get("topic_type_id").toString();
            String topic_type_id = null;
            if (typeStr.contains(",")) {
                String[] typeArr = typeStr.split(",");
                topic_type_id = StringUtils.join(typeArr, "/");
            } else {
                topic_type_id = typeStr;
            }
            paramsMap.put("topic_type_id", topic_type_id);
            paramsMap.put("traffic", map.get("traffic"));
            paramsMap.put("workaddress", map.get("workaddress"));
            int rows = entityService.addBaseService(paramsMap);
            int baseId = -1;
            if (map.get("name") != null) {
                baseId = entityService.getBaseId(map.get("name").toString());
            }
            if (map.get("other_image") != null) {
                if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                    Map<String, Object> picMap = new HashMap<>();
                    picMap.put("baseId", baseId);
                    picMap.put("other_image", map.get("other_image"));
                    entityService.addOtherImagesToBase(picMap);
                }
            }

            return rows;
        } else if (ENTERPRISE_INFO.equals(tableName)) {
            EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
            enterpriseInfo.setName(map.get("name").toString());
            enterpriseInfo.setCardId(Integer.parseInt(map.get("card_id").toString()));
            enterpriseInfo.setCreateTime(DateUtils.getNow());
            enterpriseInfo.setPhone(map.get("phone") != null ? map.get("phone").toString() : "");
            String typeStr = map.get("type").toString();
            String type;
            if (typeStr.contains(",")) {
                String[] typeArr = typeStr.split(",");
                type = StringUtils.join(typeArr, "/");
            } else {
                type = typeStr;
            }
            enterpriseInfo.setType(type);
            enterpriseInfo.setIsHot(Integer.parseInt(map.get("isHot").toString()));
            enterpriseInfo.setTitle(map.get("title").toString());
            enterpriseInfo.setMainImage(map.get("main_image").toString());
            enterpriseInfo.setScore(map.get("score") != null ? map.get("score").toString() : "");
            enterpriseInfo.setIntroduce(map.get("introduce") != null ? map.get("introduce").toString() : "");
            int rows = entityService.addEnterpriseService(enterpriseInfo);
            int enterpriseId = enterpriseInfo.getId();
            if (map.get("other_image") != null) {
                if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                    Map<String, Object> picMap = new HashMap<>();
                    picMap.put("enterpriseId", enterpriseId);
                    picMap.put("other_image", map.get("other_image"));
                    entityService.addOtherImagesToEnterprise(picMap);
                }
            }

            return rows;
        } else if (BUSS_LEAGUE_INFO.equals(tableName)) {
            return entityService.addEstablishmentService(map);
        } else if (PRODUCT_INFO.equals(tableName)) {
            return entityService.addProductInfoService(map);
        } else if (C_END_ORDER.equals(tableName)) {
            return entityService.addCEndOrderService(map);
        } else {
            return entityService.addService(tableName, map);
        }
    }

    @RequestMapping(value = "/{tableName}/updateEntity", method = RequestMethod.POST)
    public int updateService(@PathVariable("tableName") String tableName, @RequestBody Map<String, Object> map) {
        if (ACTIVITY_BASE.equals(tableName)) {
            return entityService.updateBaseService(map);
        } else if (PRODUCT.equals(tableName)) {
            return entityService.updateProductService(map);
        } else if (ENTERPRISE_INFO.equals(tableName)) {
            return entityService.updateEnterpriseService(map);
        } else if (BUSS_LEAGUE_INFO.equals(tableName)) {
            return entityService.updateEstablishmentService(map);
        } else if (PRODUCT_INFO.equals(tableName)) {
            return entityService.updateProductInfoService(map);
        }else if(C_END_ORDER.equals(tableName)){
            return entityService.updateCEndOrderService(map);
        }
        else {
            return entityService.updateService(tableName, map);
        }
    }

    @RequestMapping(value = "/{tableName}/deleteEntity", method = RequestMethod.POST)
    public int deleteService(@PathVariable("tableName") String tableName, @RequestBody List<Map<String, Object>> list) {
        if (ACTIVITY_BASE.equals(tableName)) {
            return entityService.deleteBaseService(list);
        } else if (ACTIVITY_BASE_RESERVE.equals(tableName)) {
            return entityService.deleteBaseReserveService(list);
        } else if (ENTERPRISE_INFO.equals(tableName)) {
            return entityService.deleteEnterpriseService(list);
        } else if (ENTERPRISE_CONSULTING.equals(tableName)) {
            return entityService.deleteEnterpriseConsultingService(list);
        } else if (ENTERPRISE_COMMENT.equals(tableName)) {
            return entityService.deleteEnterpriseCommentService(list);
        } else if (BUSS_LEAGUE_INFO.equals(tableName)) {
            return entityService.deleteEstablishmentService(list);
        } else if (PRODUCT_INFO.equals(tableName)) {
            return entityService.deleteProductInfoService(list);
        } else if (C_END_ORDER.equals(tableName)) {
            return entityService.deleteCEndOrderService(list);
        } else {
            return entityService.deleteService(tableName, list);
        }
    }

    @RequestMapping(value = "/{tableName}/findEntity", method = RequestMethod.POST)
    public List<LinkedHashMap> showService(@PathVariable("tableName") String tableName, @RequestBody Map<String, Object> map) {
        return entityService.showService(tableName, map);
    }

    @RequestMapping(value = "/{tableName}/showEntitys", method = RequestMethod.POST)
    public List<LinkedHashMap> showAllService(@PathVariable("tableName") String tableName, HttpSession httpSession) {
        if (City_Saas.equals(tableName)) {
            String user = httpSession.getAttribute(LoginFilter.SESSION_KEY).toString();
            LoginUser loginUser = LoginUser.getLoginUser(user);
            if (loginUser != LoginUser.DEFAULT) {
                return entityService.showSaaSService(tableName, String.format(" %s = %d", SAAS_KEY, loginUser.getCity()));
            }
        }
//        if (ACTIVITY_BASE.equals(tableName)) {
//            List<LinkedHashMap> resultList = entityService.showBaseService(tableName);
//            resultList.forEach(item -> {
//                item.put("card_id", item.get("realname"));
//            });
//            return resultList;
//        }
        return entityService.showAllService(tableName);
    }

    @RequestMapping(value = "/showSpecialService", method = RequestMethod.POST)
    public List<LinkedHashMap> showSpecialService(@RequestBody Map<String, Object> map) {
        return entityService.showSpecialService(map);
    }

    @RequestMapping(value = "/base/pictureList", method = RequestMethod.POST)
    private List<LinkedHashMap> pictureList(@RequestBody Map map) {
        int baseId = Integer.parseInt(map.get("id").toString());
        return entityService.pictureList(baseId);
    }

    @RequestMapping(value = "/campaign/pictureList", method = RequestMethod.POST)
    private List<LinkedHashMap> campaignPictureList(@RequestBody Map map) {
        int productId = Integer.parseInt(map.get("id").toString());
        return entityService.campaignPictureList(productId);
    }

    @RequestMapping(value = "/product/pictureList", method = RequestMethod.POST)
    private List<LinkedHashMap> productPictureList(@RequestBody Map map) {
        int productId = Integer.parseInt(map.get("id").toString());
        return entityService.productPictureList(productId);
    }

    @RequestMapping(value = "/enterprise/pictureList", method = RequestMethod.POST)
    private List<LinkedHashMap> enterprisePictureList(@RequestBody Map map) {
        int enterpriseId = Integer.parseInt(map.get("id").toString());
        return entityService.enterprisePictureList(enterpriseId);
    }

    @RequestMapping(value = "/luckDraw/getLuckDrawMemberList", method = RequestMethod.POST)
    public List<LinkedHashMap> getLuckDrawMemberList(HttpSession httpSession) {
        return entityService.getLuckDrawMemberList();
    }

    @RequestMapping(value = "/luckDraw/getOutsideLuckDrawMemberList", method = RequestMethod.POST)
    public List<LinkedHashMap> getOutsideLuckDrawMemberList(HttpSession httpSession) {
        return entityService.getOutsideLuckDrawMemberList();
    }

    @ApiOperation(value = "清空抽奖人员", notes = "清空抽奖人员")
    @RequestMapping(value = "/luckDraw/clearAllJoiner", method = RequestMethod.POST)
    private int clearAllJoiner(@RequestBody Map map) {
        return entityService.clearAllJoiner();
    }
}