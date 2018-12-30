package asm.modify.add;

/**
 * 日志代码
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/28 23:55
 */
public class Log {

    public static final void beforeMethod() {
        System.out.println("方式二:方法开始运行...");
    }

    public static final void afterMethod() {
        System.out.println("方式二:方法运行结束...");
    }
}
