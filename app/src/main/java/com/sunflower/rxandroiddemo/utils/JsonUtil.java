package com.sunflower.rxandroiddemo.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JSON转化工具类
 * 
 * @author zhangyb@ifenguo.com
 * @createDate 2015年3月10日
 * 
 */
public class JsonUtil {

    private static Gson gson = null;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(JsonObject.class, new JsonDeserializer<Object>() {

            @Override
            public Object deserialize(JsonElement jsonElement, Type type,
                    JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return jsonObject;
            }
        });

        gson = builder.disableHtmlEscaping().create();
    }

    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return gson.toJson(object);
    }

    public static <T> T fromJson(String content, Class<T> clazz) {
        if (content == null || "".equals(content) || clazz == null) {
            return null;
        }
        try {
            return gson.fromJson(content, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJson(String content, TypeToken<T> token) {
        if (content == null || "".equals(content) || token == null) {
            return null;
        }
        try {
            return gson.fromJson(content, token.getType());
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 把json转成对应的类型。适合用于自定义数据类型，如ArrayList<Foo>等
     * 
     * @param content json
     * @param type 自定义类型的token。使用方法如下 Type listType = new TypeToken<ArrayList<Foo>>(){}.getType();
     * @param <T>
     * @return 对应类型的对象
     */
    public static <T> T fromJson(String content, Type type) {
        if (!TextUtils.isEmpty(content) && type != null) {
            try {
                return gson.fromJson(content, type);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Map<String, Object> toMap(Object obj) {
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, Map.class);
    }

    public static <T> T fromObject(Object obj, Class<T> clazz) {
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, clazz);
    }

    public static <T> T fromObject(Object obj, TypeToken<T> token) {
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, token.getType());
    }

    public static Map<String, Object> getMap(Map<String, Object> map, String key) {
        if (map == null || key == null) {
            return null;
        }
        Object value = map.get(key);
        if (value instanceof Map) {
            return (Map) value;
        }
        return null;
    }

    public static Long getLong(Map<String, Object> map, String key) {
        if (map == null || key == null) {
            return null;
        }
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<Long> getLongList(Map<String, Object> map, String key) {
        if (map == null || key == null) {
            return Collections.EMPTY_LIST;
        }
        Object value = map.get(key);
        if (value == null) {
            return Collections.EMPTY_LIST;
        }
        if (value instanceof List) {
            List<Object> list = (List) value;
            List<Long> longValues = new ArrayList<Long>();
            for (Object i : list) {
                if (i instanceof Number) {
                    longValues.add(((Number) i).longValue());
                }
            }
            return longValues;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 从json中搜索，根据键的名字，返回值。
     * 
     * @param json
     * @param name json中的键名
     * @return Object
     */
    public static Object findObject(String json, String name) {

        Object object = null;

        if (TextUtils.isEmpty(json) || TextUtils.isEmpty(name)) {
            return null;
        }

        try {
            JSONObject jsonobject = new JSONObject(json);
            if (!jsonobject.has(name)) {
                return null;
            } else {
                object = jsonobject.get(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
