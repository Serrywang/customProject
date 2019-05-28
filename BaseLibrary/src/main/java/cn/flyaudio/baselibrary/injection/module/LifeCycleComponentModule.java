package cn.flyaudio.baselibrary.injection.module;

import com.trello.rxlifecycle2.LifecycleProvider;

import dagger.Module;
import dagger.Provides;

/**
 * @className LifeCycleComponentModule
 * @createDate 2018/7/16 9:09
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc lifeCycle module
 */

/**
 * 提供lifecycleProvider
 * @author wydnn
 */
@Module
public class LifeCycleComponentModule {
    /**
     * lifeCycle实例
     */
    private LifecycleProvider lifecycleProvider;

    /**
     * 注入实例
     * @param lifecycleProvider
     */
    public LifeCycleComponentModule(LifecycleProvider lifecycleProvider){

        this.lifecycleProvider = lifecycleProvider;

    }

    /**
     * 提供lifeCycle实例
     * @return lifeCycle实例
     */
    @Provides
    public LifecycleProvider providesLifeCycle(){

        return this.lifecycleProvider;

    }
}
