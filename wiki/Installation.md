# 安装指南

本指南将帮助您在项目中集成和使用 MVVMHabit 框架。

## 环境要求

在开始之前，请确保您的开发环境满足以下要求：

- Android Studio 3.0 或更高版本
- Android 5.0 (API level 21) 或更高版本
- JDK 8 或更高版本
- Gradle 7.5 或更高版本

## 集成步骤

### 1. 添加 JitPack 仓库

在项目根目录的 `build.gradle` 文件中添加 JitPack 仓库：

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }  // 添加这一行
    }
}
```

### 2. 添加依赖

在应用模块的 `build.gradle` 文件中添加 MVVMHabit 依赖：

```gradle
dependencies {
    implementation 'com.github.sunjun2009311:library_mvvm:v1.1.22'
}
```

### 3. 启用 DataBinding

在应用模块的 `build.gradle` 文件中启用 DataBinding：

```gradle
android {
    ...
    buildFeatures {
        dataBinding true
    }
}
```

### 4. 初始化框架

创建自定义 Application 类并继承 BaseApplication：

```java
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 在这里可以进行其他初始化操作
    }
}
```

然后在 `AndroidManifest.xml` 中注册：

```xml
<application
    android:name=".MyApplication"
    ...>
    ...
</application>
```

## 配置 Proguard

如果使用了代码混淆，请在 `proguard-rules.pro` 中添加以下规则：

```proguard
# MVVMHabit
-keep class me.goldze.mvvmhabit.** {*;}
-dontwarn me.goldze.mvvmhabit.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

# RxJava
-dontwarn org.reactivestreams.**
-keep class org.reactivestreams.** { *; }

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
```

## 验证安装

完成上述步骤后，您可以创建一个简单的页面来验证框架是否正确集成：

```java
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
```

如果项目可以正常编译和运行，说明框架已成功集成。

## 常见问题

### 1. 依赖下载失败

如果遇到依赖下载失败的问题，请检查：

1. 网络连接是否正常
2. JitPack 仓库地址是否正确
3. 依赖版本号是否正确

### 2. DataBinding 相关错误

如果遇到 DataBinding 相关错误，请检查：

1. 是否在 `build.gradle` 中启用了 DataBinding
2. 布局文件是否以 `<layout>` 标签开始
3. BR 类是否正确生成

### 3. 混淆导致的问题

如果在开启混淆后出现问题，请检查：

1. Proguard 规则是否完整
2. 是否遗漏了某些需要保留的类