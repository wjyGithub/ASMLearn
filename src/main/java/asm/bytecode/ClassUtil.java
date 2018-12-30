package asm.bytecode;

/**
 * 类操作工具类
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/30 20:44
 */
public class ClassUtil {

    /**
     * 构建全限定名,即包名+类名:asm.bytecode.Demo
     * @param className
     * @return
     */
    public static String buildFullName(String className) {
        String packagePath = ClassUtil.class.getName();
        return packagePath + "." + className;
    }

    /**
     * 构建类的路径名,即asm/bytecode/Demo
     */
    public static String buildFullNameType(String className) {
        return buildFullName(className).replace(".","/");
    }

}
