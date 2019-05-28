package cn.flyaudio.customproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaojinzi.component.anno.RouterAnno;
import com.xiaojinzi.component.impl.Router;

import cn.flyaudio.baselibrary.IRouter;
import cn.flyaudio.baselibrary.ModuleConfig;

/**
 * @author wydnn
 */

/**
 * 模块名以及路径
 * @author wydnn
 */
@RouterAnno(

        host = ModuleConfig.App.NAME,
        path = ModuleConfig.App.MAINACTIVITYPATH

)
public class MainActivity extends AppCompatActivity {

    private Button btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();

    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {

        // 通知
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Router.withApi(IRouter.class).toControlActivity(MainActivity.this);

            }

        });

    }
    /**
     * 初始化视图
     */
    private void initView() {

        btnNotification = findViewById(R.id.btn_notification);

    }
}
