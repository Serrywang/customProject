package cn.flyaudio.baselibrary.utils;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * @className TimerUtils
 * @createDate 2018/11/9 15:48
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 计时工具
 *
 */
public final class TimerUtils {
    /**
     * 防止实例化
     */
    private TimerUtils(){throw new UnsupportedOperationException("u don't touch me!");}

    /**
     * 延时操作
     * @param delayMillis 延时时间，单位为毫秒
     * @param timerDelayCallback 延时操作回调接口
     * @return 操作取消器
     */
    public static TimerCancelable delay(long delayMillis,@NonNull final TimerDelayCallback timerDelayCallback){
        // 校验参数
        if (delayMillis < 0){
            timerDelayCallback.onError(new Throwable("delayMillis is wrong !"));
            return null;
        }
        timerDelayCallback.onStart();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                timerDelayCallback.onComplete();
            }
        };
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,delayMillis);
        return new TimerCancelable(handler,runnable);
    }

    /**
     * 计时操作
     * @param intervalMillis 间隔时间，单位为毫秒
     * @param count 间隔个数
     * @param timerIntervalCallback 计时回调
     * @return 操作取消器
     */
    public static TimerCancelable interval(final long intervalMillis, final int count, @NonNull final TimerIntervalCallback timerIntervalCallback){
        // 校验参数
        if (intervalMillis < 0){
            timerIntervalCallback.onError(new Throwable("delayMillis is wrong !"));
            return null;
        }
        if (count < 0){
            timerIntervalCallback.onError(new Throwable("count is wrong !"));
            return null;
        }
        final int[] countTemp = {0};
        timerIntervalCallback.onStart();

        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ++countTemp[0];
                timerIntervalCallback.onNext(countTemp[0]);
                if (countTemp[0]<count){
                    handler.postDelayed(this,intervalMillis);
                }
            }
        };
        handler.post(runnable);
        return new TimerCancelable(handler,runnable);
    }
    /**
     * @className TimerUtils
     * @createDate 2018/11/9 15:53
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 延时操作回调接口
     *
     */
    public interface TimerDelayCallback{
        /**
         * 开始延时
         */
        void onStart();

        /**
         * 结束延时
         */
        void onComplete();

        /**
         * 出现异常
         * @param throwable
         */
        void onError(Throwable throwable);
    }
    /**
     * @className TimerUtils
     * @createDate 2018/11/9 15:53
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 计时操作回调接口
     *
     */
    public interface TimerIntervalCallback{
        /**
         * 开始计时
         */
        void onStart();

        /**
         * 已计个数
         * @param count
         */
        void onNext(int count);

        /**
         * 出现异常
         * @param throwable
         */
        void onError(Throwable throwable);
    }
    /**
     * @className TimerUtils
     * @createDate 2018/11/9 15:55
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 操作取消器
     *
     */
    public static final class TimerCancelable {
        private Handler handler;
        private Runnable runnable;

        public TimerCancelable(Handler handler, Runnable runnable){
            this.handler = handler;
            this.runnable = runnable;
        }
        /**
         * 取消任务
         */
        public void cancel(){
           if (handler!=null){
               handler.removeCallbacks(runnable);
           }
        }

    }


}
