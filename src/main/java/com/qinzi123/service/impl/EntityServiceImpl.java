package com.qinzi123.service.impl;

import com.qinzi123.dao.CardDao;
import com.qinzi123.dao.EntityDao;
import com.qinzi123.dao.TableConfigDao;
import com.qinzi123.dto.*;
import com.qinzi123.service.EntityService;
import com.qinzi123.service.PushMiniProgramService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @title: EntityServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class EntityServiceImpl implements EntityService {

    @Autowired
    public EntityDao entityDao;

    @Autowired
    public TableConfigDao tableConfigDao;

    @Autowired
    PushMiniProgramService pushService;

    @Autowired
    CardDao cardDao;

    @Autowired
    EntityService entityService;

    private HashMap<String, List<String>> tableKeyMap = new HashMap<String, List<String>>();
    private HashMap<String, HashMap<String, Column>> tableColumnMap = new HashMap<String, HashMap<String, Column>>();

    // 获取表的主键,作为修改和删除的依据
    private List<String> getKeyByTable(String tableName) {
        if (tableKeyMap.size() == 0) {
            List<TableConfig> list = tableConfigDao.findTableConfig();
            for (TableConfig tableConfig : list) {
                List<String> keyList = Arrays.asList(tableConfig.getKeyList().split(","));
                tableKeyMap.put(tableConfig.getTableName(), keyList);
            }
        }
        return tableKeyMap.get(tableName);
    }

    private HashMap<String, Column> getColumnByTable(String tableName) {
        if (tableColumnMap.size() == 0) {
            List<HashMap> list = tableConfigDao.findTableColumn();
            for (HashMap map : list) {
                String table = map.get("table_name").toString();
                String columnName = map.get("column_name").toString();
                HashMap<String, Column> tableMap = tableColumnMap.get(table) == null ? new HashMap<String, Column>() : tableColumnMap.get(table);
                tableMap.put(columnName, new Column(columnName, map.get("table_name").toString()));
                tableColumnMap.put(table, tableMap);
            }
        }
        return tableColumnMap.get(tableName);
    }

    interface Loop {
        void run(String key, String value);
    }

    // 迭代前端传入的数据
    private void iterator(Map<String, Object> map, Loop loop) {
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            loop.run(entry.getKey(), entry.getValue().toString());
        }
    }

    // 插入数据
    @Override
    public int addService(String tableName, Map<String, Object> map) {
        List<String> keyColumns = new LinkedList<String>();
        List<String> valueColumns = new LinkedList<String>();
        List<String> keys = getKeyByTable(tableName);
        iterator(map, new Loop() {
            @Override
            public void run(String key, String value) {
                if (keys.contains(key)) {
                    return; // 目前所有主键都是自动生成,这里屏蔽
                }
                keyColumns.add(key);
                valueColumns.add(Utils.fillColumn(value));
            }
        });
        return entityDao.addEntity(tableName, Utils.join(keyColumns, ","), Utils.join(valueColumns, ","));
    }

    // 插入活动基地数据
    @Override
    public int addBaseService(Map<String, Object> map) {
        return entityDao.addBaseService(map);
    }

    // 插入活动基地数据
    @Override
    public int addEnterpriseService(EnterpriseInfo enterpriseInfo) {
        return entityDao.addEnterpriseService(enterpriseInfo);
    }

    @Override
    public int getProductId(String name) {
        return entityDao.getProductId(name);
    }

    @Override
    public int getBaseId(String name) {
        return entityDao.getBaseId(name);
    }

    // 插入其他图片到产品表
    @Override
    public int addOtherImages(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("productId", productId);
                paramsMap.put("url", s);
                entityDao.addOtherImages(paramsMap);
            }
        } else {
            paramsMap.put("productId", productId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImages(paramsMap);
        }
        return productId;
    }

    // 插入其他图片到基地表
    @Override
    public int addOtherImagesToBase(Map<String, Object> map) {
        int baseId = Integer.parseInt(map.get("baseId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("baseId", baseId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToBase(paramsMap);
            }
        } else {
            paramsMap.put("baseId", baseId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToBase(paramsMap);
        }
        return baseId;
    }

    // 插入其他图片到基地表
    @Override
    public int addOtherImagesToEnterprise(Map<String, Object> map) {
        int enterpriseId = Integer.parseInt(map.get("enterpriseId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("enterpriseId", enterpriseId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToEnterprise(paramsMap);
            }
        } else {
            paramsMap.put("enterpriseId", enterpriseId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToEnterprise(paramsMap);
        }
        return enterpriseId;
    }

    // 插入其他图片到产品图片表
    @Override
    public int addOtherImagesToProductInfo(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("productId", productId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToProductInfo(paramsMap);
            }
        } else {
            paramsMap.put("productId", productId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToProductInfo(paramsMap);
        }
        return productId;
    }

    // 新增机构信息
    @Override
    public int addEstablishmentService(Map<String, Object> map) {
        map.put("submittime", DateUtils.getNow());
        map.put("updatetime", DateUtils.getNow());
        return entityDao.addEstablishmentService(map);
    }

    // 新增产品信息
    @Override
    public int addProductInfoService(Map<String, Object> map) {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setStatus(Integer.parseInt(map.get("status").toString()));
//        productInfo.setIsHot(Integer.parseInt(map.get("is_hot").toString()));
//        productInfo.setCardId(map.get("card_id").toString());
//        productInfo.setName(map.get("name").toString());
//        if (map.get("address") != null) {
//            productInfo.setAddress(map.get("address").toString());
//        } else {
//            productInfo.setAddress("");
//        }
//        if (map.get("address_name") != null) {
//            productInfo.setAddressName(map.get("address_name").toString());
//        } else {
//            productInfo.setAddressName("");
//        }
//        if (map.get("longitude") != null) {
//            productInfo.setLongitude(map.get("longitude").toString());
//        } else {
//            productInfo.setLongitude("");
//        }
//        if (map.get("latitude") != null) {
//            productInfo.setLatitude(map.get("latitude").toString());
//        } else {
//            productInfo.setLatitude("");
//        }
//        int product_type = Integer.parseInt(map.get("product_type").toString());
//        productInfo.setProductType(product_type);
//        productInfo.setMainImage(map.get("main_image").toString());
//        if (map.get("original_price") != null) {
//            productInfo.setOriginalPrice(map.get("original_price").toString());
//        } else {
//            productInfo.setOriginalPrice("");
//        }
//        if (map.get("present_price") != null) {
//            productInfo.setPresentPrice(map.get("present_price").toString());
//        } else {
//            productInfo.setPresentPrice("");
//        }
//        productInfo.setInventory(map.get("inventory").toString());
//        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeat_purchase").toString()));
//        productInfo.setOnceMaxPurchaseCount(Integer.parseInt(map.get("once_max_purchase_count").toString()));
//        productInfo.setPhone(map.get("phone").toString());
//        if (map.get("introduce") != null) {
//            productInfo.setIntroduce(map.get("introduce").toString());
//        } else {
//            productInfo.setIntroduce("");
//        }
//        if (map.get("vedio_path") != null) {
//            productInfo.setVideoPath(map.get("vedio_path").toString());
//        } else {
//            productInfo.setVideoPath("");
//        }
//        if (map.get("instruction") != null) {
//            productInfo.setInstruction(map.get("instruction").toString());
//        } else {
//            productInfo.setInstruction("");
//        }
//        productInfo.setBuyCount(map.get("buy_count").toString());
//        productInfo.setCreateTime(DateUtils.getNow());
//        productInfo.setDeadlineTime(map.get("deadline_time").toString());
//        int rows;
//        if (product_type == 0) {
//            productInfo.setType(Integer.parseInt(map.get("type").toString()));
//            productInfo.setProductStyle(Integer.parseInt(map.get("product_style").toString()));
//            rows = entityDao.addProductInfoService(productInfo);
//        } else {
//            rows = entityDao.addActivityInfoService(productInfo);
//        }
//        int productId = productInfo.getId();
//
//        if (map.get("other_image") != null) {
//            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
//                Map<String, Object> picMap = new HashMap<>();
//                picMap.put("productId", productId);
//                picMap.put("other_image", map.get("other_image"));
//                entityService.addOtherImagesToProductInfo(picMap);
//            }
//        }

//        return rows;
        return 0;
    }

    @Override
    public int addCEndOrderService(Map<String, Object> map) {
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("createTime", DateUtils.getAccurateDate());
        if (map.get("open_id") == null) {
            map.put("open_id", "");
        }
        return entityDao.addCEndOrderService(map);
    }

    // 插入其他图片到活动表
    @Override
    public int addOtherImagesToProduct(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("productId", productId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToProduct(paramsMap);
            }
        } else {
            paramsMap.put("productId", productId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToProduct(paramsMap);
        }
        return productId;
    }

    @Override
    public int deleteBasePicList(Map<String, Object> map) {
        return entityDao.deleteBasePicList(map);
    }

    @Override
    public int deleteEnterprisePicList(Map<String, Object> map) {
        return entityDao.deleteEnterprisePicList(map);
    }

    @Override
    public int deleteProductInfoPicList(Map<String, Object> map) {
        return entityDao.deleteProductInfoPicList(map);
    }

    @Override
    public int deleteProductPicList(Map<String, Object> map) {
        return entityDao.deleteProductPicList(map);
    }

    private void loadKeyColumns(String tableName, Map<String, Object> map, List<String> keyColumns, List<String> keys, List<String> updateColumns) {
        HashMap<String, Column> columnMap = getColumnByTable(tableName);
        iterator(map, new Loop() {
            @Override
            public void run(String key, String value) {
                Column column = columnMap.get(key);
                // 类型判断和处理,临时写一下,以后再考虑系统处理
                if (column == null || value == null) {
                    return;
                }
                if (column.getDataType().equals("int") && value.length() == 0) {
                    return;
                }
                String oneSetting = String.format(Utils.EQUAL_SQL, key, Utils.fillColumn(value));
                if (keys.contains(key)) {
                    keyColumns.add(oneSetting);
                } else {
                    if (updateColumns != null) {
                        updateColumns.add(oneSetting);
                    }
                }
            }
        });
    }

    public void callMessageUser(String tableName, Map<String, Object> map) {
        int verify = Integer.parseInt(map.get("verify").toString());
        if (verify == 1 || verify == 2) {
            String message = String.format("审批%s通过", verify == 2 ? "不" : "");
            CardInfo cardInfo = cardDao.getCardInfoBeanById(Integer.parseInt(map.get("card_id").toString()));
            if (tableName.equalsIgnoreCase("card_message")) {
                map.put("last", cardDao.getLast(Integer.parseInt(map.get("id").toString())));
                CardMessage cardMessage = ServiceHelper.convertMap2CardMessage(map, cardInfo,
                        message);
                pushService.pushMessage2OneUser(cardMessage);
            } else {
                CardMessageReply cardMessageReply = ServiceHelper.convertMap2CardMessageReply(
                        map, message);
                pushService.pushMessageReply2OneUser(cardMessageReply);
            }
        }
    }

    // 修改数据
    @Override
    public int updateService(String tableName, Map<String, Object> map) {
        List<String> updateColumns = new LinkedList<String>();
        List<String> keyColumns = new LinkedList<String>();
        loadKeyColumns(tableName, map, keyColumns, getKeyByTable(tableName), updateColumns);
        if (tableName.equalsIgnoreCase("card_message") || tableName.equalsIgnoreCase("card_message_reply")) {
            callMessageUser(tableName, map);
        }
        return entityDao.updateEntity(tableName, Utils.join(updateColumns, ","), Utils.join(keyColumns, Utils.AND));
    }

    // 修改基地数据
    @Override
    public int updateBaseService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("baseId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteBasePicList(picMap);
                entityService.addOtherImagesToBase(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("baseId", map.get("id"));
                entityService.deleteBasePicList(picMap);
            }
        }
        String typeStr = map.get("topic_type_id").toString();
        String topic_type_id = null;
        if (typeStr.contains(",")) {
            String[] typeArr = typeStr.split(",");
            topic_type_id = StringUtils.join(typeArr, "/");
        } else {
            topic_type_id = typeStr;
        }
        map.put("topic_type_id", topic_type_id);
        return entityDao.updateBaseEntity(map);
    }

    // 修改活动数据
    @Override
    public int updateProductService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteProductPicList(picMap);
                entityService.addOtherImagesToProduct(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                entityService.deleteProductPicList(picMap);
            }
        }
        return entityDao.updateProductEntity(map);
    }

    // 修改企业服务数据
    @Override
    public int updateEnterpriseService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("enterpriseId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteEnterprisePicList(picMap);
                entityService.addOtherImagesToEnterprise(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("enterpriseId", map.get("id"));
                entityService.deleteEnterprisePicList(picMap);
            }
        }
        String typeStr = map.get("type").toString();
        String type = null;
        if (typeStr.contains(",")) {
            String[] typeArr = typeStr.split(",");
            type = StringUtils.join(typeArr, "/");
        } else {
            type = typeStr;
        }
        map.put("type", type);
        return entityDao.updateEnterpriseEntity(map);
    }

    // 修改机构数据
    @Override
    public int updateEstablishmentService(Map<String, Object> map) {
        return entityDao.updateEstablishmentEntity(map);
    }

    // 修改产品信息
    @Override
    public int updateProductInfoService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteProductInfoPicList(picMap);
                entityService.addOtherImagesToProductInfo(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                entityService.deleteProductInfoPicList(picMap);
            }
        }
        return entityDao.updateProductInfoService(map);
    }

    @Override
    public int updateCEndOrderService(Map<String, Object> map) {
        return entityDao.updateCEndOrderService(map);
    }

    // 删除数据
    @Override
    public int deleteService(String tableName, List<Map<String, Object>> list) {
        List<String> allRecordKeys = new LinkedList<String>();
        for (Map<String, Object> map : list) {
            List<String> keyColumns = new LinkedList<String>();
            loadKeyColumns(tableName, map, keyColumns, getKeyByTable(tableName), null);
            allRecordKeys.add(String.format("(%s)", Utils.join(keyColumns, Utils.AND)));
        }
        return entityDao.deleteEntity(tableName, Utils.join(allRecordKeys, Utils.OR));
    }

    // 删除基地数据
    @Override
    public int deleteBaseService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteBaseEntity(map);
            rows++;
        }
        return rows;
    }

    // 删除基地数据
    @Override
    public int deleteBaseReserveService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteBaseReserveEntity(map);
            rows++;
        }
        return rows;
    }

    // 删除企业服务数据
    @Override
    public int deleteEnterpriseService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEnterpriseService(map);
            rows++;
        }
        return rows;
    }

    // 删除企业服务数据
    @Override
    public int deleteEnterpriseConsultingService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEnterpriseConsultingService(map);
            rows++;
        }
        return rows;
    }

    // 删除企业服务数据
    @Override
    public int deleteEnterpriseCommentService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEnterpriseCommentService(map);
            rows++;
        }
        return rows;
    }

    // 删除机构数据
    @Override
    public int deleteEstablishmentService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEstablishmentService(map);
            rows++;
        }
        return rows;
    }

    // 删除产品数据
    @Override
    public int deleteProductInfoService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteProductInfoService(map);
            rows++;
        }
        return rows;
    }

    // 删除成长GO订单数据
    @Override
    public int deleteCEndOrderService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteCEndOrderService(map);
            rows++;
        }
        return rows;
    }

    // 根据key查找数据
    @Override
    public List<LinkedHashMap> showService(String tableName, Map<String, Object> map) {
        String queryTableName = tableName;
        List<String> keyColumns = new LinkedList<String>();
        loadKeyColumns(queryTableName, map, keyColumns, getKeyByTable(queryTableName), null);
        return entityDao.findEntityByKey(queryTableName, Utils.join(keyColumns, Utils.AND));
    }

    // 查询所有数据，目前分页在客户端做，将来可以考虑服务端做
    @Override
    public List<LinkedHashMap> showAllService(String tableName) {
        return entityDao.findEntitys(tableName);
    }

    // 查询基地数据，目前分页在客户端做，将来可以考虑服务端做
    @Override
    public List<LinkedHashMap> showBaseService(String tableName) {
        return entityDao.findBaseEntity(tableName);
    }

    @Override
    public List<LinkedHashMap> showSpecialService(Map<String, Object> map) {
        String tableName = map.get("model").toString();
        String key = map.get("key").toString();
        String value = map.get("value").toString();
        String selectList = Utils.join(new String[]{key, value}, ",");
        return entityDao.findSpecialEntitys(selectList, tableName);
    }

    @Override
    public List<LinkedHashMap> showSaaSService(String tableName, String condition) {
        return entityDao.findEntityByKey(tableName, condition);
    }

    @Override
    public List<LinkedHashMap> pictureList(int baseId) {
        return entityDao.pictureList(baseId);
    }

    @Override
    public List<LinkedHashMap> campaignPictureList(int productId) {
        return entityDao.campaignPictureList(productId);
    }

    @Override
    public List<LinkedHashMap> productPictureList(int productId) {
        return entityDao.productPictureList(productId);
    }

    @Override
    public List<LinkedHashMap> enterprisePictureList(int enterpriseId) {
        return entityDao.enterprisePictureList(enterpriseId);
    }

    @Override
    public List<LinkedHashMap> getLuckDrawMemberList() {
        return entityDao.getLuckDrawMemberList();
    }

    @Override
    public List<LinkedHashMap> getOutsideLuckDrawMemberList() {
        return entityDao.getOutsideLuckDrawMemberList();
    }

    @Override
    public int clearAllJoiner() {
        return entityDao.clearAllJoiner();
    }
}
