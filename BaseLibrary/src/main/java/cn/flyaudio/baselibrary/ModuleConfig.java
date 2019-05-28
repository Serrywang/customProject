package cn.flyaudio.baselibrary;

/**
 * Created by SerryWang
 * on 2019/5/28
 * @author wydnn
 */
public class ModuleConfig {


    public static class App{

        public static final String NAME = "app";
        /**
         * 路径必须是host/path
         */
        public static final String CONTROLACTIVITYPATH = "app/ControlPath";

        public static final String MAINACTIVITYPATH = "app/MainActivityPath";

    }

    /**
     * module1的一些常量属性
     */
    public static class Module1{

        public static final String NAME = "module1";
        public static final String PATH = "ModuleOnePath";

    }

}
