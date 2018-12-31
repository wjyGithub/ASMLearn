package asm.util;


import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

/**
 * 字节码相关的操作工具类
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/30 0:12
 */
public class ByteCodeUtil {

    private ClassWriter cw;

    private String classNameType;

    public ByteCodeUtil(ClassWriter cw,String classNameType) {
        this.cw = cw;
        this.classNameType = classNameType;
    }

    /**
     * 创建类
     * public class Demo {
     *
     *     private String deveceName;
     *     private String deviceType;
     *     private Integer deviceSize;
     *     ....
     * }
     * @param typeNameMap
     */
    public void createClass(Map<String,Class<?>> typeNameMap) {
        cw.visit(V1_7, ACC_PUBLIC, classNameType, null, "java/lang/Object", null);
        if (typeNameMap != null && typeNameMap.size() != 0) {
            for (Map.Entry<String, Class<?>> entry : typeNameMap.entrySet()) {
                cw.visitField(ACC_PRIVATE, entry.getKey(), Type.getDescriptor(entry.getValue()), null, null).visitEnd();
            }
        }
    }

    /**
     * 生成构造函数
     * nameValueMap == null:
     * public Demo() {
     *     super();
     * }
     * 不空:
     * public Demo(String deveceName,String deviceType,Integer deviceSize...) {
     *      super.Object();
     *      this.deveceName = deveceName;
     *      this.deviceType = deviceType;
     *      this.deviceSize = deviceSize;
     *      ......
     * }
     * @param nameValueMap key:成员属性的名称,value:初始化的值
     */
    public void generatorConstructor(Map<String,Object> nameValueMap){
        MethodVisitor constructorMethod = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        constructorMethod.visitCode();
        constructorMethod.visitIntInsn(ALOAD,0);
        constructorMethod.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
        //带有参数的构造函数
        boolean hasParam = nameValueMap != null && nameValueMap.size() != 0;
        if(hasParam) {
            // todo 设置参数
        }
        constructorMethod.visitInsn(RETURN);
        //手动维护栈大小和局部变量表的大小
        constructorMethod.visitMaxs(1,1);
        if(hasParam) {
            // todo 重新计算栈大小和局部变量表的大小
        }
        constructorMethod.visitEnd();
    }

    /**
     * 将字节码数据写入到文件
     * @param pathName 要写入的文件路径,目前为空
     * @return
     */
    public byte[] writeToFile(String pathName) {
        colseClassWrite();
        byte[] bytes = null;
        if(StringUtils.isEmpty(pathName)) {
            File file = new File("F:\\out.class");
            try (
                    FileOutputStream fos = new FileOutputStream(file);
            ) {
                bytes = cw.toByteArray();
                fos.write(bytes);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public void colseClassWrite() {
        cw.visitEnd();
    }

}
