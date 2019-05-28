package cn.flyaudio.baselibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;

import cn.flyaudio.baselibrary.common.BaseApplication;
import cn.flyaudio.baselibrary.injection.component.ActivityComponent;
import cn.flyaudio.baselibrary.injection.component.DaggerActivityComponent;
import cn.flyaudio.baselibrary.injection.module.LifeCycleComponentModule;
import cn.flyaudio.baselibrary.presenter.BasePresenter;
import cn.flyaudio.baselibrary.presenter.view.BaseView;
import cn.flyaudio.baselibrary.utils.ToastUtils;
import cn.flyaudio.baselibrary.widgets.LoadingDialog;

/**
 * @className BaseMvpFragment
 * @createDate 2018/7/16 9:18
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc mvpFragment基础类
 * @param <T>
 *
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    /**
     * presenter
     */
    @Inject
    public T mPresenter;
    /**
     * activity注入器
     */
    protected ActivityComponent activityComponent;
    /**
     * loading对话框
     */
    protected LoadingDialog loadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityInjection();
        initInjection();
        loadingDialog = new LoadingDialog(getActivity());
    }

    /**
     * 初始化activity级别的注入器
     */
    private void initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(((BaseApplication) getActivity().getApplication()).getAppComponent())
                .lifeCycleComponentModule(new LifeCycleComponentModule(this))
                .build();
    }

    /**
     * 让子类记得实现注入
     */
    protected abstract void initInjection();

    /**
     * 显示Toast
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 显示异常
     * @param error 异常信息
     */
    @Override
    public void onError(String error) {
        ToastUtils.showShort(error);
    }

    /**
     * 显示loading框
     */
    @Override
    public void showLoading() {
        loadingDialog.showLoading();
    }

    /**
     * 隐藏loading框
     */
    @Override
    public void hideLoading() {
        loadingDialog.hideLoading();
    }


}
