package cn.flyaudio.customproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author wydnn
 */
@Route(path ="/app/controlActivity")
public class ControlActivity extends AppCompatActivity {
    /**
     * 跳转按钮
     */
    private Button btn;
    /**
     * text文字提示
     */
    private TextView tv_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        initView();
        initEvent();
        initTips();

    }

    private void initTips() {

        tv_Text.setText("这是ControlDialogActivity");

    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ARouter.getInstance().build("/app/mainActivity")
                        .navigation();

            }
        });

    }

    /**
     * 初始化视图
     */
    private void initView() {

        btn = findViewById(R.id.btn);
        tv_Text = findViewById(R.id.tv_Text);

    }

}
