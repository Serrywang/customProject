package cn.flyaudio.baselibrary;

import android.content.Context;

import com.xiaojinzi.component.anno.router.HostAnno;
import com.xiaojinzi.component.anno.router.PathAnno;
import com.xiaojinzi.component.anno.router.RouterApiAnno;

/**
 * Created by SerryWang
 * on 2019/5/28
 * @author wydnn
 */
@RouterApiAnno()
@HostAnno(ModuleConfig.App.NAME)
public interface IRouter {

    @PathAnno(ModuleConfig.App.CONTROLACTIVITYPATH)
    void toControlActivity(Context context);

}
