# MVVMHabit Wiki

欢迎访问 MVVMHabit 的 Wiki 页面！本项目是一个基于 Android Architecture Components 和 MVVM 模式的 Android 开发框架库，旨在提供一套完整的 Android 开发解决方案。

## 项目概述

MVVMHabit 是一个现代化的 Android 开发框架，它基于 Google 推荐的 MVVM（Model-View-ViewModel）架构模式，并整合了 Android Architecture Components。本项目在 [goldze/MVVMHabit](https://github.com/goldze/MVVMHabit) 的基础上进行了改进和优化，以提供更好的开发体验和更强的稳定性。

## 核心特性

- **MVVM架构**：采用标准的 MVVM 架构模式，实现界面与业务逻辑的分离
- **DataBinding集成**：深度集成 DataBinding，简化 UI 开发流程
- **网络请求封装**：基于 Retrofit + RxJava 的网络请求模块，支持 RESTful API
- **事件总线**：集成 RxBus 实现组件间通信
- **异常处理**：全局异常处理机制，提升应用稳定性
- **本地存储**：基于 MMKV 的高性能本地数据存储方案
- **组件化支持**：支持组件化开发模式

## 快速开始

### 集成方式

在项目根目录的 `build.gradle` 中添加 JitPack 仓库：

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在应用模块的 `build.gradle` 中添加依赖：

```gradle
dependencies {
    implementation 'com.github.sunjun2009311:library_mvvm:v1.1.22'
}
```

### 基础使用

1. 创建自定义 Application 并继承 BaseApplication：

```java
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化操作
    }
}
```

2. 创建 ViewModel 并继承 BaseViewModel：

```java
public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
```

3. 创建 Activity 并继承 BaseActivity：

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

## 架构设计

### MVVM 模式

MVVM（Model-View-ViewModel）是一种软件架构设计模式，常用于简化用户界面的开发。MVVM 的核心是 ViewModel，它负责连接 View 和 Model，实现了 View 和 Model 的解耦。

```
View (Activity/Fragment) 
    ↑↓
ViewModel (BaseViewModel)
    ↑↓
Model (Repository/DataSource)
```

### 核心组件

1. **BaseActivity/BaseFragment**：基础的 Activity 和 Fragment，负责界面展示和生命周期管理
2. **BaseViewModel**：基础的 ViewModel，负责业务逻辑处理和数据管理
3. **DataBinding**：实现 View 和 ViewModel 的数据绑定
4. **Repository**：数据仓库层，统一数据来源

## 进阶使用

### 网络请求

使用封装的网络请求模块：

```java
// 创建 API 接口
public interface ApiService {
    @GET("users")
    Observable<List<User>> getUsers();
}

// 在 ViewModel 中使用
public class UserViewModel extends BaseViewModel {
    public void loadUsers() {
        Observable<List<User>> observable = RetrofitClient.getInstance().create(ApiService.class).getUsers();
        // 处理网络请求
    }
}
```

### 事件总线

使用 RxBus 进行组件间通信：

```java
// 发送事件
RxBus.getDefault().post(new MessageEvent("Hello"));

// 接收事件
RxBus.getDefault().toObservable(MessageEvent.class)
    .subscribe(event -> {
        // 处理事件
    });
```

## 最佳实践

1. **ViewModel 设计**：每个页面对应一个 ViewModel，避免 ViewModel 过于臃肿
2. **数据绑定**：充分利用 DataBinding 简化 UI 更新逻辑
3. **生命周期管理**：合理使用 RxJava 的生命周期管理避免内存泄漏
4. **错误处理**：统一处理网络请求和业务逻辑中的异常情况

## 常见问题

### 构建问题

如果遇到构建问题，请检查：
1. Gradle 版本兼容性
2. 依赖库版本冲突
3. Proguard 混淆规则

### 运行时问题

如果遇到运行时问题，请检查：
1. DataBinding 是否正确配置
2. ViewModel 是否正确初始化
3. 网络权限是否添加

## 贡献指南

欢迎提交 Issue 和 Pull Request 来帮助改进项目。

## 许可证

本项目采用 MIT 许可证，详情请查看 [LICENSE](https://github.com/sunjun2009311/library_mvvm/blob/master/LICENSE) 文件。