package asm.create.instruction;


import asm.util.ByteCodeUtil;
import asm.util.ClassUtil;
import asm.util.MyClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

/**
 * 创建对象(new指令用法)
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/31 18:27
 */
public class NewInstructionMain {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        String fullName = ClassUtil.buildFullName("NewDemo");
        String fullNameType = fullName.replace(".","/");

        String demoFullNameType = Demo.class.getName().replace(".","/");
        ClassWriter cw = new ClassWriter(0);

        ByteCodeUtil byteCodeUtil = new ByteCodeUtil(cw,fullNameType);
        byteCodeUtil.createClass(null);
        byteCodeUtil.generatorConstructor(null);
        /**
         * public void getDemo() {
         *  System.out.println(new Demo("demoName",3).getDemoName());
         * }
         */
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,"getDemo","()V",null,null);
        mv.visitCode();
        //将获取到的out对象放入到操作数栈中
        mv.visitFieldInsn(GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        //在堆内存里面开辟一个空间,并把内存地址放到操作数栈中(即this)
        mv.visitTypeInsn(NEW,demoFullNameType);
        //复制一份this,因为创建构造函数需要消耗一个this,之后如果需要保存到局部变量表中,那么就将第二个this放入到局部变量表中
        //如果不存到局部变量表中,则调用pop指令,弹出该对象
        mv.visitInsn(DUP);
        mv.visitLdcInsn("demoName");
        mv.visitInsn(ICONST_3);
        mv.visitMethodInsn(INVOKESTATIC,"java/lang/Integer","valueOf","(I)Ljava/lang/Integer;",false);
        mv.visitMethodInsn(INVOKESPECIAL,demoFullNameType,"<init>","(Ljava/lang/String;Ljava/lang/Integer;)V",false);
        mv.visitMethodInsn(INVOKEVIRTUAL,demoFullNameType,"getDemoName","()Ljava/lang/String;",false);
        mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(5,1);
        mv.visitEnd();
        byte[] bytes = byteCodeUtil.writeToFile(null);

        MyClassLoader classLoader = new MyClassLoader();
        Class<?> cls = classLoader.defineClassPublic(fullName,bytes,0,bytes.length);
        Object o = cls.newInstance();
        Method spain = cls.getMethod("getDemo");
        spain.invoke(o);
    }
}
