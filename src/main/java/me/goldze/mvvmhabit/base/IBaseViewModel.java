package me.goldze.mvvmhabit.base;


import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public interface IBaseViewModel extends DefaultLifecycleObserver {

    @Override
    default void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        onCreate();
    }

    @Override
    default void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        onStart();
    }

    @Override
    default void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        onResume();
    }

    @Override
    default void onPause(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onPause(owner);
        onPause();
    }

    @Override
    default void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        onStop();
    }

    @Override
    default void onDestroy(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onDestroy(owner);
        onDestroy();
    }

    void onCreate();


    void onDestroy();


    void onStart();


    void onStop();


    void onResume();


    void onPause();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();
}
