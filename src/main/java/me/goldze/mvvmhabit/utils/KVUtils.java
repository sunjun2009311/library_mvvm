package me.goldze.mvvmhabit.utils;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;


import com.tencent.mmkv.MMKV;

import java.lang.reflect.Type;
import java.util.List;

/**
 * MMKV的简单封装，数据存储
 * @author: Jun
 * @date: 2021/9/15
 */
public class KVUtils {
    private volatile static KVUtils instance;
    private Context context;
    private MMKV kv;

    public static KVUtils getInstance() {
        if (instance == null) {
            synchronized (KVUtils.class) {
                if (instance == null) {
                    instance = new KVUtils();
                }
            }
        }
        return instance;
    }

    private KVUtils() {}

    private KVUtils(Context context) {
        init(context);
    }

    public void init(Context context){
        this.context = context;
        String rootDir = MMKV.initialize(context);
        kv = getKV();
        Log.i("","mmkv init,root dir "+rootDir);
    }

    public MMKV getKV() {
        if(kv==null){
            kv = MMKV.defaultMMKV();
        }
        return kv;
    }

    public void putString(String key,String value){
        getKV().encode(key,value);
    }

    public void putBoolean(String key,boolean value){
        getKV().encode(key,value);
    }

    public void putInt(String key,int value){
        getKV().encode(key,value);
    }

    public void putFloat(String key,float value){
        getKV().encode(key,value);
    }

    public void putLong(String key,long value){
        getKV().encode(key,value);
    }

    public void putDouble(String key,double value){
        getKV().encode(key,value);
    }

    public void putByte(String key,byte[] value){
        getKV().encode(key, value);
    }

    public void putParcelableVal(String key, Parcelable parcelableObj){
        getKV().encode(key,parcelableObj);
    }

    public void putObject(String key,Object obj){
        String jsonStr = JsonUtils.toJson(obj);
        putString(key,jsonStr);
    }

    public <T> void putList(String key, List<T> list){
        putObject(key,list);
    }

    public String getString(String key){
        return getString(key,"");
    }

    public String getString(String key,String def){
        return getKV().decodeString(key,def);
    }

    public boolean getBoolean(String key){
        return getBoolean(key,false);
    }

    public boolean getBoolean(String key,boolean defValue){
        return getKV().decodeBool(key,defValue);
    }

    public int getInt(String key){
        return getInt(key, -1);
    }

    public int getInt(String key,int defValue){
        return getKV().decodeInt(key, defValue);
    }

    public float getFloat(String key){
        return getFloat(key, -1f);
    }

    public float getFloat(String key,float defValue){
        return getKV().decodeFloat(key, defValue);
    }

    public long getLong(String key){
        return getLong(key, -1);
    }

    public long getLong(String key,long defValue){
        return getKV().decodeLong(key, defValue);
    }

    public double getDouble(String key){
        return getDouble(key, -1);
    }

    public double getDouble(String key,double defValue){
        return getKV().decodeDouble(key, defValue);
    }

    public byte[] getByteArray(String key){
        return getKV().decodeBytes(key);
    }

    public <T extends Parcelable> T getParcelableObj(String key,Class<T> tClass){
        return getKV().decodeParcelable(key,tClass);
    }

    public <T> T getObject(String key,Class<T> clazz){
        String text = getString(key);
        if(!StringUtils.isEmpty(text)){
            return JsonUtils.toObject(text,clazz);
        }
        return null;
    }

    public<T> List<T> getList(String key, Type typeOf){
        String text = getString(key);
        if(!StringUtils.isEmpty(text)){
            return JsonUtils.toList(text,typeOf);
        }
        return null;
    }


    public boolean containsKey(String key){
        return getKV().containsKey(key);
    }

    public void remove(String key){
        getKV().removeValueForKey(key);
    }

    public void remove(String[] keys){
        getKV().removeValuesForKeys(keys);
    }
 }
