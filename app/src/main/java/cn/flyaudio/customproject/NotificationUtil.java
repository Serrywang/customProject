package cn.flyaudio.customproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import cn.flyaudio.Utils.MyApplication;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by SerryWang
 * on 2019/5/23
 * @author wydnn
 */
public class NotificationUtil {
    /**
     * 通知的Manager
     */
    private static NotificationManager notificationManager;

    private static int NOTIFICATION_ID_ONE = 1;

    private static int NOTIFICATION_ID_TWO = 2;

    private static final String TAG = "NotificationUtil";
    /**
     * 收到推送消息，显示是否下载通知条
     */
    public static void showOtaPushNotification(){
        // 获得notificationManager对象
        notificationManager = (NotificationManager) MyApplication.getMyApplication().getSystemService(NOTIFICATION_SERVICE);
        Log.e(TAG, "showOtaPushNotification: >>>" + notificationManager);
        // 创建NotificationCompat.Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyApplication.getMyApplication());
        // 对Builder的Notification设置一些属性
        mBuilder.setContentTitle("标题")
                .setContentText("内容")
                .setContentIntent(getPendingIntent())
                .setTicker("通知到来")
                .setWhen(System.currentTimeMillis())
                .setPriority(1)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_loading);

        Notification notification = mBuilder.build();
        // notification1
        notificationManager.notify(NOTIFICATION_ID_ONE,notification);

        // notification2
        NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(MyApplication.getMyApplication());
        // 对Builder的Notification设置一些属性
        mBuilder1.setContentTitle("第二个Notification")
                .setContentText("第二个Content")
                .setContentIntent(getPendingIntent())
                .setTicker("我是第二个ticker")
                .setWhen(System.currentTimeMillis())
                .setPriority(0)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.gender_nor_bg);
        Notification notification1 = mBuilder1.build();
        notificationManager.notify(NOTIFICATION_ID_TWO,notification1);

    }

    /**
     * 返回PendingIntent
     * @return
     */
    public static PendingIntent getPendingIntent(){

        Intent intent = new Intent(MyApplication.getMyApplication(),MainActivity.class);
        // 跳转到Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getMyApplication(),0,intent,0);

        return pendingIntent;

    }

    /**
     * 取消Notification
     */
    public static void cancelNotification(){
        /**
         * 清除所有的Notification通知
         */
        if(notificationManager != null){

            notificationManager.cancelAll();

        }

    }

}
