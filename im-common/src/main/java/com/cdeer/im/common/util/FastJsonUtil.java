package com.cdeer.im.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * FastJson工具类
 */
public class FastJsonUtil {

    private static Logger log = Logger.getLogger(FastJsonUtil.class.getName());

    public static <T> List<T> json2LinkedList(String jsonString, Class<T> clazz) throws IOException {
        return JSON.parseArray(jsonString, clazz);
    }

    /**
     * json转成list
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> json2List(String jsonString, Class<T> clazz) throws IOException {
        return JSON.parseArray(jsonString, clazz);
    }

    /**
     * json转成对象
     *
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T json2Object(String jsonString, Class<T> clazz) throws IOException {
        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        return JSON.parseObject(jsonString, clazz);
    }

    /**
     * 对象转换成json
     *
     * @param ob
     * @return
     */
    public static String object2string(Object ob) {
        String str = "";
        try {
            str = JSON.toJSONString(ob);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * 对象转换成对象
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T object2Object(Object obj, Class<T> clazz) {
        String str = JSON.toJSONString(obj, SerializerFeature.UseSingleQuotes);
        return JSON.parseObject(str, clazz);
    }


}
