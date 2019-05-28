package cn.flyaudio.baselibrary.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

import cn.flyaudio.baselibrary.R;


/**
 * @author newtrekWang
 * @fileName ValidateButton
 * @createDate 2018/10/10 10:05
 * @email 408030208@qq.com
 * @desc 获取验证码按钮，带倒计时
 */
public class ValidateButton extends android.support.v7.widget.AppCompatButton{

    private Handler mHandler;
    /**
     * 默认60s
     */
    private int mCount  = 60;
    private CountDownRunnable countDownRunnable;
    public static String forward,end;
    public ValidateButton(Context context) {
        this(context,null);

        init();

    }

    public ValidateButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);

        init();

    }

    public ValidateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    /**
     * 初始化
     */
    private void init() {

        forward = getResources().getString(R.string.tv_repeat_forward);

        end = getResources().getString(R.string.tv_repeat_end);

        // 设置使能
        setEnabled(true);
        // 默认显示获取验证码,设置文本
        setText(R.string.get_validation_code);
        //
        setBackgroundColor(ContextCompat.getColor(getContext(),R.color.common_white));
        // 设置文本颜色
        setTextColor(ContextCompat.getColor(getContext(),R.color.tv_getValidate));
        //设置字体样式
        setTextSize(13);
        //设置背景
        setBackground(R.drawable.validatebuttonstyle);
       // setBackground();
        mHandler = new Handler();

        countDownRunnable = new CountDownRunnable(this);

    }

    private void setBackground(int validatebuttonstyle) {
        /**
         * 设置背景视图
         */
        Drawable drawable = getResources().getDrawable(validatebuttonstyle);
        setBackground(drawable);

    }

    /**
     * 重置按钮
     */
    public void reset() {

        removeRunnable();
        setText(R.string.get_validation_code);
        setEnabled(true);
        setBackgroundColor(ContextCompat.getColor(getContext(),R.color.common_white));
        setTextColor(ContextCompat.getColor(getContext(),R.color.tv_getValidate));
        mCount = 60 ;

    }

    /**
     * 开始倒计时
     */
    public void startCount(){

        mHandler.postDelayed(countDownRunnable,0);

    }

    public int getCount() {

        return mCount;
    }

    public void setCount(int mCount) {

        this.mCount = mCount;

    }

    public Handler getValidateHandler() {

        return mHandler;

    }

    /**
     * 取消倒计时
     */
    public void removeRunnable(){

        mHandler.removeCallbacks(countDownRunnable);

    }

    /**
     * 倒计时更新任务
     */
    static class CountDownRunnable implements Runnable{

        private WeakReference<ValidateButton> weakReference;
        public CountDownRunnable(ValidateButton validateButton){

            this.weakReference = new WeakReference<>(validateButton);

        }
        @Override
        public void run() {

            ValidateButton validateButton = weakReference.get();
            if (validateButton == null){
                return;
            }
            //getResource()

            validateButton.setText(forward+validateButton.getCount()+end);
            validateButton.setTextColor(Color.parseColor("#999999"));
            validateButton.setBackgroundColor(ContextCompat.getColor(validateButton.getContext(),R.color.common_disable));
            validateButton.setEnabled(false);

            if (validateButton.getCount() > 0){
                //每次循环一秒进行
                validateButton.getValidateHandler().postDelayed(this,1000);
                validateButton.setCount(validateButton.getCount()-1);

            }else {

                validateButton.reset();

            }

        }

    }
}
