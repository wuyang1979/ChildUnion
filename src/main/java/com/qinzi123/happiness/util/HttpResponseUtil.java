/**
 * <p><owner>赖相旭</owner></p>
 * <p><createdate>2014-10-13</createdate></p>
 * <p>文件名称: JsonUtil.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2001-2020</p>
 * <p>公司名称: 深圳市中兴通讯股份有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-10-13</p>
 * <p>完成日期：2014-10-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * <p>评审记录1: // 评审历史记录，包括评审日期、评审人及评审内容</p>
 * <pre>
 *    评审日期：
 *    版 本 号：
 *    评 审 人：
 *    评审内容：
 * </pre>
 * <p>评审记录2：…</p>
 *
 * @version 1.0
 * @author Administrator
 */

package com.qinzi123.happiness.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: HttpResponseUtil
 * @package: com.qinzi123.happiness.util
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class HttpResponseUtil {
    public static Map<String, Object> genSuccessfulMsg(List<Map<String, Object>> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 0);
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> genSuccessfulMsg(long data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 0);
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> genFailureMsg(String errorMsg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", -1);
        map.put("errorMsg", errorMsg);
        return map;
    }

    public static Map<String, Object> genSuccessfulTolMsg(List<Map<String, Object>> data, Object totalRecords) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 0);
        map.put("data", data);
        map.put("totalRecords", totalRecords);
        return map;
    }
}

