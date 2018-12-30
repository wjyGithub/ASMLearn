package asm.util;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/28 23:04
 */
public class MyClassLoader extends ClassLoader {

    public Class<?> defineClassPublic(String name, byte[] b, int off, int len) throws ClassFormatError {
        Class<?> clazz = defineClass(name, b, off, len);
        return clazz;
    }
}
