package cn.flyaudio.module1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

//import cn.flyaudio.baselibrary.utils.ToastUtils;

/**
 * @author wydnn
 */
@Route(path = "/ok/mainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.moudle1_activity_main);//佈局問題，你和上一個同名了，

      //  ToastUtils.showShort("Module1模块");

        //然后呢，在这个界面你想做什么操作，我只想从app模块跳转到module1模块

        //你找到app 跳转代码

        //这边可以收到了，你看到了嘛没跳转过去，就是界面没
        //看到沒？什麽原因導致的


    }

}
