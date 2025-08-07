# MVVMHabit

[![jitpack](https://jitpack.io/v/sunjun2009311/library_mvvm.svg)](https://jitpack.io/#sunjun2009311/library_mvvm)

## 项目介绍

MVVMHabit是一个基于Android Architecture Components和MVVM模式的Android开发框架库，旨在提供一套完整的Android开发解决方案。

> 本项目基于 [goldze/MVVMHabit](https://github.com/goldze/MVVMHabit) 开发，在其基础上进行了改进和优化，以适应更广泛的使用场景和解决特定问题。

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
    implementation 'com.github.sunjun2009311:library_mvvm:v1.1.22'
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

## 项目改进

相比于原始的 [goldze/MVVMHabit](https://github.com/goldze/MVVMHabit) 项目，本项目进行了以下改进：

1. **构建系统优化**：
   - 修复了 Gradle 版本兼容性问题
   - 优化了构建配置以提高构建稳定性
   - 解决了 AAR 打包问题，确保 classes.jar 正确包含

2. **资源处理优化**：
   - 修复了资源引用问题
   - 优化了资源处理流程
   - 解决了构建过程中的资源链接错误

3. **依赖管理改进**：
   - 修复了依赖库解析问题
   - 优化了构建任务配置
   - 提升了整体构建性能

4. **版本维护**：
   - 提供了持续的版本更新和维护
   - 集成了自动化的构建和发布流程

## 版本更新日志

### v1.1.22
- 修复Gradle版本兼容性问题
- 优化构建配置以提高构建稳定性
- 修复资源引用问题
- 修复AAR打包问题，确保classes.jar正确包含
- 优化资源处理流程
- 修复构建过程中的资源链接错误
- 修复依赖库解析问题
- 优化构建任务配置
- 提升构建性能

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