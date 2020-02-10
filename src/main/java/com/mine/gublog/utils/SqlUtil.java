package com.mine.gublog.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * sql工具类
 */
public class SqlUtil {
    
    private static String equal="equal";
    private static String like="like";
    private static String sort="sort";

    private SqlUtil() {
    }

    private static class SqlUtilHandler {
        private static final SqlUtil INSTANCE = new SqlUtil();
    }

    public static SqlUtil getInstance() {
        return SqlUtilHandler.INSTANCE;
    }

    /**
     * 通用解析参数生成sql查询条件
     *
     * @param map {"cond":JSON.stringify([{"field":"","value":""}])}
     * @return
     */
    public String parseMap(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isAppend = false;
        if (map != null) {
            if (map.containsKey(equal)) {
                StringBuilder equalSqlStr = new StringBuilder();
                for (Object object : JSONArray.parseArray(map.get(equal))) {
                    JSONObject jsonObject = (JSONObject) object;
                    if (jsonObject.containsKey("field") && jsonObject.containsKey("value")) {
                        String valueStr = jsonObject.getString("value");
                        if (valueStr == null) {
                            continue;
                        }
                        String fieldStr = jsonObject.getString("field");
                        equalSqlStr.append(" and ").append(fieldStr).append(" = '").append(valueStr).append("' ");
                        isAppend = true;
                    }
                }
                if (isAppend) {
                    stringBuilder.append(equalSqlStr);
                    isAppend = false;
                }
            }
            if (map.containsKey(like)) {
                StringBuilder likeSqlStr = new StringBuilder();
                for (Object object : JSONArray.parseArray(map.get(like))) {
                    JSONObject jsonObject = (JSONObject) object;
                    if (jsonObject.containsKey("field") && jsonObject.containsKey("value")) {
                        String valueStr = jsonObject.getString("value");
                        if (valueStr == null) {
                            continue;
                        }
                        String fieldStr = jsonObject.getString("field");
                        likeSqlStr.append(" and ").append(fieldStr).append(" like '").append(valueStr).append("' ");
                        isAppend = true;
                    }
                }
                if (isAppend) {
                    stringBuilder.append(likeSqlStr);
                    isAppend = false;
                }
            }
            if (map.containsKey(sort)) {
                StringBuilder sortSqlStr = new StringBuilder(" order by ");
                for (Object object : JSONArray.parseArray(map.get(sort))) {
                    JSONObject jsonObject = (JSONObject) object;
                    if (jsonObject.containsKey("field") && jsonObject.containsKey("order")) {
                        String orderStr = jsonObject.getString("order");
                        if (orderStr == null) {
                            continue;
                        }
                        String fieldStr = jsonObject.getString("field");
                        sortSqlStr.append(fieldStr).append(" ").append(orderStr).append(",");
                        isAppend = true;
                    }
                }
                if (isAppend) {
                    stringBuilder.append(sortSqlStr.substring(0, sortSqlStr.length() - 1));
                    isAppend = false;
                }
            }
        }
        return stringBuilder.toString();
    }
}
