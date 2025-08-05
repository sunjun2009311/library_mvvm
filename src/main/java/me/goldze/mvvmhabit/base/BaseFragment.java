package me.goldze.mvvmhabit.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle4.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import me.goldze.mvvmhabit.R;
import me.goldze.mvvmhabit.base.BaseViewModel.ParameterField;
import me.goldze.mvvmhabit.bus.Messenger;

/**
 * 所有fragment的优化基类，支持单Activity多Fragment模式
 * @param <V> ViewDataBinding
 * @param <VM> viewModel
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseView {
    public static String REQUEST_KEY = "f_request_key";
    private int viewModelId;
    private View rootView;
    protected V binding;
    protected VM viewModel;
    protected FragmentManager fragmentManager;
    protected AppCompatActivity containerActivity;
    protected String requestKey;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        containerActivity = (AppCompatActivity) activity;
        if(getArguments()!=null){
            requestKey = getArguments().getString(REQUEST_KEY);
        }
        fragmentManager  = containerActivity.getSupportFragmentManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view;
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }

    public View getRootView(){
        return rootView;
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                //指定的viewmodel应该在泛型的最后一位
                int endModelIndex = ((ParameterizedType) type).getActualTypeArguments().length-1;
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[endModelIndex];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(getViewLifecycleOwner());
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(viewLifecycleOwner, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        viewModel.getUC().getStartActivityWithReqEvent().observe(viewLifecycleOwner, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                int rcode = (int) params.get("requestCode");
                Intent intent = new Intent(requireContext(),clz);
                intent.putExtras(bundle);
                startActivityForResult(intent, rcode);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(viewLifecycleOwner, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(viewLifecycleOwner, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(viewLifecycleOwner, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().onBackPressed();
            }
        });
        viewModel.getUC().getFinishWithResultEvent().observe(viewLifecycleOwner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer resultCode) {
                requireActivity().setResult(resultCode);
                requireActivity().finish();
            }
        });
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 官方摈弃的方法
     * @param clz
     * @param bundle
     * @param requestCode
     */
    @Deprecated
    public void startActivityForResult(Class<?> clz, Bundle bundle, final int requestCode) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    public void startContainerActivityForResult(String canonicalName, Bundle bundle,int requestCode) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * =====================================================================
     **/

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    @Override
    public void initParam() {
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initViewObservable() {
    }

    public boolean isBackPressed() {
        return false;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return new ViewModelProvider(fragment).get(cls);
    }

    /**
     * 获取父Activity的共享ViewModel
     * @param cls
     * @param <P>
     * @return
     */
    public <P extends ViewModel> P getParentViewModel(Class<P> cls){
        return new ViewModelProvider(requireActivity()).get(cls);
    }

    public void onBackPressed(){
        //多个fragment说明要回退
        if(fragmentManager.getBackStackEntryCount()>1){
            fragmentManager.popBackStack();
        }
    }
    /**
     * 返回上级视图，带上对应requestKey的参数resArgs
     * @param resArgs
     */
    public void onBackWithResult(Bundle resArgs){
        if(!TextUtils.isEmpty(requestKey)){
            fragmentManager.setFragmentResult(requestKey,resArgs);
            fragmentManager.popBackStack();
        }
    }
    public void replaceFragment(@NonNull Fragment fragment,Bundle args){
        if(args==null){
            args = new Bundle();
        }
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.fragment_right_enter,R.anim.fragment_right_exit)
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    /**
     * 在fragment中打开fragment
     * @param fragment
     * @param args
     */
    public void startFragment(@NonNull Fragment fragment,Bundle args){
        if(args==null){
            args = new Bundle();
        }
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container,fragment)
                .setReorderingAllowed(true)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void startFragmentForResult(@NonNull Fragment fragment, String requestKey, Bundle args,FragmentResultListener listener){
        if(args==null){
            args = new Bundle();
        }
        args.putString(REQUEST_KEY,requestKey);
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container,fragment)
                .setReorderingAllowed(true)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        if(listener!=null){
            fragmentManager.setFragmentResultListener(requestKey, this,listener);
        }
    }

    public void startFragmentForResult(@NonNull String fragmentRoutePath, String requestKey, Bundle args,FragmentResultListener listener){
        if(args==null){
            args = new Bundle();
        }
        args.putString(REQUEST_KEY,requestKey);
        BaseFragment fragment = (BaseFragment) ARouter.getInstance().build(fragmentRoutePath).navigation();
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container,fragment)
                .setReorderingAllowed(true)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        if(listener!=null){
            fragmentManager.setFragmentResultListener(requestKey, this,listener);
        }
    }

    public void startTop(Fragment fragment,Bundle args){
        if(args==null){
            args = new Bundle();
        }
        fragment.setArguments(args);
        clearFragmentStacks();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.fragment_right_enter,R.anim.fragment_right_exit)
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void clearFragmentStacks(){
        if(fragmentManager.getBackStackEntryCount()>0){
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(entry.getName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
