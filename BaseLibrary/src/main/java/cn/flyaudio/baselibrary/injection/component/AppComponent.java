package cn.flyaudio.baselibrary.injection.component;

import javax.inject.Singleton;

import cn.flyaudio.baselibrary.common.BaseApplication;
import cn.flyaudio.baselibrary.injection.module.AppModule;
import dagger.Component;

/**
 * @className AppComponent
 * @createDate 2018/7/16 9:11
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc app层级注射器
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    /**
     * 暴露application context
     * @return
     */
    BaseApplication baseApplication();

}
