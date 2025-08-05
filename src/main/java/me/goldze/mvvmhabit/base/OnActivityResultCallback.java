package me.goldze.mvvmhabit.base;

import android.content.Intent;

/**
 * @author: Jun
 * @date: 2022/7/5
 */
public interface OnActivityResultCallback {
    void onActivityResult(Intent data,int resultCode);
}
