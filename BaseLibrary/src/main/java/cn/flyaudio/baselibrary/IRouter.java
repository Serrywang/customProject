package cn.flyaudio.baselibrary;

import android.content.Context;

import com.xiaojinzi.component.anno.router.HostAndPathAnno;
import com.xiaojinzi.component.anno.router.RouterApiAnno;

/**
 * Created by SerryWang
 * on 2019/5/28
 * @author wydnn
 */
@RouterApiAnno()
public interface IRouter {

    @HostAndPathAnno(ModuleConfig.App.CONTROLACTIVITYPATH)
    void toControlActivity(Context context);

}
