package com.tasteofdata.land.util;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wwj on 2014/6/24.
 */
public class JsonUtil {
    private static JsonConfig jsonConfig = new JsonConfig();
    static {
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
    }

    public static String object2Json(Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj,jsonConfig);
        return jsonObject.toString();
    }

    private static class JsonDateValueProcessor implements JsonValueProcessor {
        private String pattern = "yyyy-MM-dd HH:mm:ss";

        public JsonDateValueProcessor() {
            super();
        }

        public JsonDateValueProcessor(String pattern) {
            super();
            this.pattern = pattern;
        }

        @Override
        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        @Override
        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
            return process(value);
        }
        /**
         * process
         * @param value
         * @return
         */
        private Object process(Object value) {
            try {
                if (value instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    return sdf.format((Date) value);
                }
                return value == null ? "" : value.toString();
            } catch (Exception e) {
                return "";
            }
        }
    }
}
