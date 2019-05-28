package cn.flyaudio.Utils;

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
public class AppApplication implements IComponentApplication {

    @Override
    public void onCreate(@NonNull Application app) {

        ToastUtils.showShort("app模块组件实例化");

    }
    @Override
    public void onDestory() {

    }

}
