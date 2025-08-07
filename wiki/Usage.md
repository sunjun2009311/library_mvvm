# 使用指南

本指南将详细介绍如何使用 MVVMHabit 框架进行 Android 应用开发。

## 基础概念

### MVVM 架构模式

MVVMHabit 基于 MVVM（Model-View-ViewModel）架构模式：

- **Model**：负责数据获取和业务逻辑处理
- **View**：负责界面展示，通常是 Activity 或 Fragment
- **ViewModel**：连接 View 和 Model，处理 UI 相关的业务逻辑

### 核心组件

1. **BaseActivity/BaseFragment**：基础的页面组件
2. **BaseViewModel**：基础的 ViewModel 组件
3. **DataBinding**：数据绑定机制
4. **网络请求模块**：基于 Retrofit + RxJava 的网络请求封装

## 创建页面

### 1. 创建 ViewModel

首先创建一个继承自 BaseViewModel 的 ViewModel：

```java
public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    
    // 添加业务逻辑方法
    public void loadData() {
        // 加载数据的逻辑
    }
}
```

### 2. 创建布局文件

创建一个使用 DataBinding 的布局文件：

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="viewModel"
            type="com.yourpackage.MainViewModel" />
    </data>
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        
        <!-- UI 组件 -->
        
    </LinearLayout>
</layout>
```

### 3. 创建 Activity

创建一个继承自 BaseActivity 的 Activity：

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
    
    @Override
    public void initData() {
        super.initData();
        // 初始化数据
    }
}
```

## 网络请求

### 1. 定义 API 接口

```java
public interface ApiService {
    @GET("users")
    Observable<List<User>> getUsers();
    
    @POST("users")
    Observable<User> createUser(@Body User user);
}
```

### 2. 使用网络请求

在 ViewModel 中使用网络请求：

```java
public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    
    public void loadUsers() {
        Observable<List<User>> observable = RetrofitClient.getInstance()
            .create(ApiService.class)
            .getUsers();
            
        observable.subscribe(new ApiDisposableObserver<List<User>>() {
            @Override
            public void onResult(List<User> users) {
                // 处理成功结果
            }
            
            @Override
            public void onError(int code, String message) {
                // 处理错误
            }
        });
    }
}
```

## 数据绑定

### 基础数据绑定

在布局文件中绑定数据：

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="user"
            type="com.yourpackage.User" />
    </data>
    
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{user.name}" />
</layout>
```

### 事件绑定

绑定点击事件：

```xml
<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="点击"
    android:onClick="@{() -> viewModel.onButtonClick()}" />
```

在 ViewModel 中处理点击事件：

```java
public class MainViewModel extends BaseViewModel {
    public void onButtonClick() {
        // 处理按钮点击
    }
}
```

## 事件总线

使用 RxBus 进行组件间通信：

### 发送事件

```java
RxBus.getDefault().post(new MessageEvent("Hello"));
```

### 接收事件

```java
// 在 ViewModel 或 Activity 中
Disposable disposable = RxBus.getDefault()
    .toObservable(MessageEvent.class)
    .subscribe(event -> {
        // 处理接收到的事件
    });
    
// 记得在适当的时候取消订阅
disposable.dispose();
```

## 本地存储

使用 MMKV 进行本地数据存储：

```java
// 保存数据
MMKV mmkv = MMKV.defaultMMKV();
mmkv.putString("key", "value");

// 读取数据
String value = mmkv.getString("key", "");
```

## 生命周期管理

框架已经自动处理了生命周期管理，确保在页面销毁时取消网络请求和事件订阅，避免内存泄漏。

## 最佳实践

### 1. ViewModel 设计原则

- 每个页面对应一个 ViewModel
- ViewModel 不应该持有 View 的引用
- 业务逻辑应该放在 ViewModel 中
- 数据状态通过 LiveData 或 ObservableField 暴露给 View

### 2. 网络请求处理

- 使用封装的 ApiDisposableObserver 处理网络请求结果
- 统一处理网络错误和异常情况
- 在请求开始时显示加载状态，结束时隐藏

### 3. 数据绑定技巧

- 合理使用 ObservableField 来实现数据的双向绑定
- 避免在布局文件中编写复杂的表达式
- 使用 BindingAdapter 扩展 DataBinding 功能

### 4. 内存管理

- 及时取消网络请求和事件订阅
- 避免在 ViewModel 中持有 Activity/Fragment 的引用
- 合理使用 WeakReference

## 常见问题

### 1. DataBinding 相关问题

如果遇到 DataBinding 相关问题，请检查：

1. 布局文件是否以 `<layout>` 标签开始
2. 是否正确配置了 DataBinding
3. BR 类是否正确生成

### 2. 网络请求问题

如果网络请求无法正常工作，请检查：

1. 网络权限是否添加
2. API 接口定义是否正确
3. BaseUrl 是否正确配置

### 3. 事件总线问题

如果 RxBus 无法正常工作，请检查：

1. 是否正确发送和接收事件
2. 是否在适当的时候取消订阅
3. 事件类型是否匹配