# library-mvvm

MVVM架构核心库，提供基础框架支持。

## 使用方法

在项目的根build.gradle中添加JitPack仓库：

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

添加依赖：

```gradle
implementation 'com.github.sunjun2009311:library_mvvm:v1.1.1'
```

## 功能特性

- 基于MVVM架构模式
- 集成DataBinding和LiveData
- 支持RxJava2
- 网络请求封装
- 图片加载库集成
- 常用工具类和组件