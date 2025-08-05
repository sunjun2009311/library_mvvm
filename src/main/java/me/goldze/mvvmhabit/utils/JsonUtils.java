package me.goldze.mvvmhabit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Jun
 * @date: 2022/2/18
 */
public class JsonUtils {

    private JsonUtils(){}

    private static Gson gson = new GsonBuilder()
            .serializeNulls()
            .setLenient() // 宽松模式，允许不规范的 JSON 格式
            .create();

    public static String toJson(Object object,boolean isShowNull){
        if(isShowNull){
            gson = new GsonBuilder().serializeNulls().create();
        }
        return gson.toJson(object);
    }

    public static String toJson(Object object){
        return toJson(object,false);
    }

    public static <T> T toObject(String json, Class<T> classOfT){
        return gson.fromJson(json,classOfT);
    }

    public static HashMap<String,Object> toHashMap(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<HashMap<String,Object>>(){}.getType());
    }

    // 将 JSON 字符串转换为泛型对象列表
    public static <T> List<T> toList(String json, Type type) {
        Type listType = new TypeToken<List<T>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static <T> List<T> toList(String json, Class<T> tlss) {
        Type listType = new TypeToken<List<T>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public static String toJson(String json){
        try {
            Object obj = gson.fromJson(json, Object.class);
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }
    }
}
