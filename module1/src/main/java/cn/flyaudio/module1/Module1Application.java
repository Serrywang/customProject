package cn.flyaudio.module1;

import android.app.Application;
import android.support.annotation.NonNull;

import com.xiaojinzi.component.anno.ModuleAppAnno;
import com.xiaojinzi.component.application.IComponentApplication;

import cn.flyaudio.baselibrary.utils.ToastUtils;

/**
 * Created by SerryWang
 * on 2019/5/28
 * @author wydnn
 */
@ModuleAppAnno()
public class Module1Application implements IComponentApplication {

    @Override
    public void onCreate(@NonNull Application app) {

        ToastUtils.showShort("Module1模块的初始化");

    }

    @Override
    public void onDestory() {

    }

}
