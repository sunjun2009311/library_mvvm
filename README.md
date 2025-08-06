# MVVMHabit - Android MVVM Framework

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![](https://jitpack.io/v/sunjun2009311/library_mvvm.svg)](https://jitpack.io/#sunjun2009311/library_mvvm)

## 简介

MVVMHabit 是一个基于 Android Architecture Components 和 MVVM 模式的 Android 开发框架。它提供了一系列基础类和工具，帮助开发者快速搭建稳定、可维护的 Android 应用。

## 特性

- **MVVM架构**: 基于 Google 推荐的 Android Architecture Components 实现
- **DataBinding**: 支持数据绑定，减少视图操作代码
- **网络请求**: 封装 Retrofit + RxJava，简化网络请求处理
- **事件总线**: 集成 RxBus，实现组件间通信
- **异常处理**: 全局异常捕获和处理机制
- **工具类库**: 提供丰富的工具类，提高开发效率

## 集成方式

### Step 1. 添加 JitPack 仓库到项目根目录的 build.gradle:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2. 添加依赖:

```gradle
dependencies {
    implementation 'com.github.sunjun2009311:library_mvvm:1.1.0'
}
```

## 使用说明

### 1. 初始化

在 Application 中初始化框架:

```java
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 其他初始化代码
    }
}
```

### 2. 创建 ViewModel

```java
public class MyViewModel extends BaseViewModel {
    public MyViewModel(@NonNull Application application) {
        super(application);
    }
    
    // 业务逻辑代码
}
```

### 3. 在 Activity/Fragment 中使用

```java
public class MainActivity extends BaseActivity<ActivityMainBinding, MyViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    
    @Override
    public void initData() {
        // 初始化数据
    }
}
```

## 混淆规则

框架已经内置了混淆规则，无需额外添加。

## 许可证

```
Copyright 2025 sunjun2009311

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```