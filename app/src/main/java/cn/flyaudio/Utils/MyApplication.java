package cn.flyaudio.Utils;


import com.xiaojinzi.component.ComponentConfig;
import com.xiaojinzi.component.impl.application.ModuleManager;

import cn.flyaudio.baselibrary.BuildConfig;
import cn.flyaudio.baselibrary.ModuleConfig;
import cn.flyaudio.baselibrary.common.BaseApplication;

/**
 * Created by SerryWang
 * on 2019/5/23
 * @author wydnn
 */
public class MyApplication extends BaseApplication {

    public static MyApplication getMyApplication() {

        return myApplication;

    }

    public static void setMyApplication(MyApplication myApplication) {

        MyApplication.myApplication = myApplication;

    }

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        // 初始化ComponentConfig
        ComponentConfig.init(this,true);
        // 框架还带有检查重复的路由和重复的拦截器等功能，debug的时候开启它
        if(BuildConfig.DEBUG){

            ModuleManager.getInstance().check();

        }
        // 装载各个业务组件
        ModuleManager.getInstance().registerArr(

                ModuleConfig.App.NAME,ModuleConfig.Module1.NAME

        );
    }

}
