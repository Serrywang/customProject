package cn.flyaudio.baselibrary.bus;

/**
 * @author newtrekWang
 * @fileName BusEvent
 * @createDate 2019/1/10 11:55
 * @email 408030208@qq.com
 * @desc 通用事件总线传递的数据
 */
public final class BusEvent {
    /**
     * code，区分类型
     */
    public int eventCode;
    /**
     * msg 消息
     */
    public String msg;
    /**
     * 业务数据1
     */
    public Object param1;
    /**
     * 业务数据2
     */
    public Object param2;
}
