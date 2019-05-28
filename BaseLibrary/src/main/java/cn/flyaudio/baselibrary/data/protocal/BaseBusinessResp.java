package cn.flyaudio.baselibrary.data.protocal;

/**
 * @author newtrekWang
 * @fileName BaseBusinessResp
 * @createDate 2019/1/9 9:29
 * @email 408030208@qq.com
 * @desc 基本的业务字段
 */
public class BaseBusinessResp {
    /**
     * 信息提示
     */
    private String msg;
    /**
     * 标志
     */
    private String  flag;

    public BaseBusinessResp(String msg, String flag) {
        this.msg = msg;
        this.flag = flag;
    }

    public BaseBusinessResp() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String  getFlag() {
        return flag;
    }

    public void setFlag(String  flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "BaseBusinessResp{" +
                "msg='" + msg + '\'' +
                ", flag=" + flag +
                '}';
    }
}
