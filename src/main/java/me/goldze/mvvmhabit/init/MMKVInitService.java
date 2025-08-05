package me.goldze.mvvmhabit.init;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.goldze.mvvmhabit.BuildConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.KVUtils;

/**
 * @author: Jun
 * @date: 2021/9/15
 */
@Route(path = ServiceConstant.INIT_MMKV)
public class MMKVInitService implements InitService{

    @Override
    public void init(Context context) {
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        KLog.i("--MMKV初始化--");
        KVUtils.getInstance().init(context);
    }
}
