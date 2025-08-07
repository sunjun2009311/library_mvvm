# MVVMHabit

[![jitpack](https://jitpack.io/v/sunjun2009311/library_mvvm.svg)](https://jitpack.io/#sunjun2009311/library_mvvm)

## 项目介绍

MVVMHabit是一个基于Android Architecture Components和MVVM模式的Android开发框架库，旨在提供一套完整的Android开发解决方案。

## 特性

- 基于MVVM模式的架构设计
- 集成DataBinding，简化UI开发
- 封装了网络请求模块，基于Retrofit+RxJava
- 提供常用的工具类库
- 集成事件总线RxBus
- 全局异常处理机制
- 基于MMKV的本地数据存储方案

## 集成步骤

### 1. 在项目根目录的build.gradle中添加:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. 在应用模块的build.gradle中添加依赖:

```gradle
dependencies {
    // 当前最新版本
    implementation 'com.github.sunjun2009311:library_mvvm:v1.1.11'
}
```

### 3. 在Application中初始化:

```java
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化操作
    }
}
```

## 使用说明

详细的使用文档请参考 [Wiki](https://github.com/sunjun2009311/library_mvvm/wiki)

## 版本更新日志

### v1.1.11
- 修复Kotlin版本兼容性问题
- 优化构建配置

### v1.1.10
- 修复JitPack插件兼容性问题
- 优化构建配置

### v1.1.9
- 修复JitPack构建问题
- 优化构建配置

### v1.1.8
- 修复task配置冲突问题
- 更新版本号

### v1.1.7
- 更新Kotlin版本至1.8.0
- 增强AAPT2配置

## 许可证

```
Copyright (c) 2025 MVVMHabit

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```