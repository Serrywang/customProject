package cn.flyaudio.baselibrary.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * @author newtrekWang
 * @fileName DateAndTimePickerDialogFragment
 * @createDate 2018/11/8 14:34
 * @email 408030208@qq.com
 * @desc 日期和时间选择器，使用系统的日期或时间选择控件，支持仅日期选择，仅时间选择和日期加时间选择
 */
public class DateAndTimePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener {
    /**
     * 只选日期
     */
    public static final int MODE_ONLY_PICK_DATE = 111;
    /**
     * 只选时间
     */
    public static final int MODE_ONLY_PICK_TIME = 222;
    /**
     * 不仅选日期还要选时间
     */
    public static final int MODE_PICK_DATE_AND_TIME = 333;

    /**
     * 年
     */
    private int year ;
    /**
     * 月
     */
    private int month;
    /**
     * 日
     */
    private int dayOfMonth;
    /**
     * 时
     */
    private int hour;
    /**
     * 分
     */
    private int minute;
    /**
     * 选择模式
     */
    private int mode ;


    /**
     * 选择回调接口
     */
    private PickDateAndTimeCallback pickDateAndTimeCallback;

    public DateAndTimePickerDialogFragment(){
        setBuilder(new Builder());
    }

    public void setBuilder(Builder buidler){
        this.year = buidler.year;
        this.month = buidler.month;
        this.dayOfMonth = buidler.dayOfMonth;
        this.hour = buidler.hour;
        this.minute = buidler.minute;
        this.mode = buidler.mode;
        this.pickDateAndTimeCallback = buidler.pickDateAndTimeCallback;
    }

    /**
     * 返回Dialog实例给Fragment
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog pickDialog = null;
        if (mode == MODE_ONLY_PICK_DATE || mode == MODE_PICK_DATE_AND_TIME){
            pickDialog = new DatePickerDialog(getActivity(),this,year,month,dayOfMonth);
        }else{
            pickDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
        }
        return pickDialog;
    }

    /**
     * 防止android5.0以下重复显示timePicker
     */
    private boolean alreadySet = false;

    /**
     * 日期选择器选择回调
     * @param view datePicker控件
     * @param year 年
     * @param month 月
     * @param dayOfMonth 日
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        if (mode == MODE_ONLY_PICK_DATE){
            if (pickDateAndTimeCallback != null){
                pickDateAndTimeCallback.onPickDateAndTimeCallback(year,month,dayOfMonth,hour,minute);
            }
        }else if(mode == MODE_PICK_DATE_AND_TIME && Build.VERSION.SDK_INT >= 21 ){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
            timePickerDialog.show();
        }else if(mode == MODE_PICK_DATE_AND_TIME && Build.VERSION.SDK_INT < 21){
            if (!alreadySet){
                alreadySet = true;
            }else{
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
                timePickerDialog.show();
                alreadySet = false;
            }

        }
    }

    /**
     * 时间选择器回调
     * @param view timePicker控件
     * @param hourOfDay 小时
     * @param minute 分钟
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
        if (pickDateAndTimeCallback != null){
            if (mode == MODE_ONLY_PICK_TIME){
              pickDateAndTimeCallback.onPickDateAndTimeCallback(year,month,dayOfMonth,hourOfDay,minute);
            }else if(mode == MODE_PICK_DATE_AND_TIME){
                pickDateAndTimeCallback.onPickDateAndTimeCallback(year,month,dayOfMonth,hourOfDay,minute);
            }
        }
    }

    /**
     * @className DateAndTimePickerDialogFragment
     * @createDate 2018/11/8 15:14
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 构建者模式构建DateAndTimePickerDialogFragment
     *
     */
    public static class  Builder {
        /**
         * 年
         */
        private int year ;
        /**
         * 月
         */
        private int month;
        /**
         * 日
         */
        private int dayOfMonth;
        /**
         * 时
         */
        private int hour;
        /**
         * 分
         */
        private int minute;
        /**
         * 选择模式
         */
        private int mode ;
        /**
         * 选择回调接口
         */
        private PickDateAndTimeCallback pickDateAndTimeCallback;


        public Builder(){

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            this.year = year;
            this.month = month;
            this.dayOfMonth = day;
            this.hour = hour;
            this.minute = minute;
            this.mode = MODE_PICK_DATE_AND_TIME;
            this.pickDateAndTimeCallback = null;

        }

        /**
         * 设置年
         * @param year
         * @return
         */
        public Builder setYear(final int year){
            this.year = year;
            return this;
        }

        /**
         * 设置月
         * @param month
         * @return
         */
        public Builder setMonth(final int month){
            this.month = month;
            return this;
        }

        /**
         * 设置日
         * @param dayOfMonth
         * @return
         */
        public Builder setDayOfMonth(final int dayOfMonth){

            this.dayOfMonth = dayOfMonth;
            return this;

        }

        /**
         * 设置小时，统一为24小时制
         * @param hour 小时，统一为24小时制
         * @return
         */
        public Builder setHour(final int hour){

            this.hour = hour;
            return this;

        }

        /**
         * 设置分钟
         * @param minute
         * @return
         */
        public Builder setMinute(final int minute){

            this.minute = minute;
            return this;

        }

        /**
         * 设置选择模式
         * @param mode
         * @return
         */
        public Builder setMode(final int mode){

            if (mode == MODE_ONLY_PICK_DATE || mode == MODE_ONLY_PICK_TIME || mode == MODE_PICK_DATE_AND_TIME){

                this.mode = mode;

            }else {

                throw new UnsupportedOperationException("please set right mode!");

            }
            return this;
        }

        /**
         * 设置选择回调
         * @param pickCallback
         * @return
         */
        public Builder setPickCallback(PickDateAndTimeCallback pickCallback){

            this.pickDateAndTimeCallback = pickCallback;
            return this;

        }

        /**
         * 构建DateAndTimePickerDialogFragment
         * @return DateAndTimePickerDialogFragment实例
         */
        public DateAndTimePickerDialogFragment build(){

            DateAndTimePickerDialogFragment fragment = new DateAndTimePickerDialogFragment();
            fragment.setBuilder(this);
            return fragment;

        }
    }
    /**
     * @className DateAndTimePickerDialogFragment
     * @createDate 2018/11/8 15:47
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 选择结果回调
     *
     */
    public interface PickDateAndTimeCallback{
        /**
         * 选择器回调结果
         * @param year
         * @param month
         * @param dayOfMonth
         * @param hour
         * @param minute
         */
        void onPickDateAndTimeCallback(int year, int month, int dayOfMonth, int hour, int minute);

    }

}
