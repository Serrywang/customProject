package cn.flyaudio.module2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;



/**
 * @author wydnn
 */
@Route(path = "/test2/MainActivity")
public class MainActivity extends AppCompatActivity {

    private Button bt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_activity_main);
       //ToastUtils.showShort("test2Activity");
        initView();
        initEvent();
    }

    private void initEvent() {

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ARouter.getInstance().build("/ok/mainActivity")
                        .navigation();

            }
        });

    }

    /**
     * 初始化视图
     */
    private void initView() {

        bt_search = findViewById(R.id.bt_search);

    }
}
