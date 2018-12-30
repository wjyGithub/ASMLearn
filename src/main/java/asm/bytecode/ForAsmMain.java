package asm.bytecode;


import asm.util.MyClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

/**
 * public class ForAsm {
 *
 *    public ForAsm() {
 *        super();
 *    }
 *    int sum = 0;
 *    for(int i=0; i<100; i++) {
 *      sum += i;
 *   }
 *   return sum;
 * }
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/29 23:58
 */
public class ForAsmMain {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        String fullName = ClassUtil.buildFullName("ForAsm");
        String fullNameType = fullName.replace(".","/");
        /**
         * ClassWriter参数:
         *  0:自己计算操作数栈和局部变量栈
         */
        ClassWriter cw = new ClassWriter(0);
        ByteCodeUtil byteCodeUtil = new ByteCodeUtil(cw,fullNameType);
        byteCodeUtil.createClass(null);
        byteCodeUtil.generatorConstructor(null);
        /**
         * public int spain() {
         *      int sum = 0;
         *      for(int i=0; i<100; i++) {
         *          sum += i;
         *      }
         *      return sum;
         * }
         */
        MethodVisitor spainMethod = cw.visitMethod(ACC_PUBLIC, "spain", "()I", null, null);
        spainMethod.visitCode();
        //局部变量表中索引为0的位置存放了this

        //下面两条指令: int sum = 0
        //将常量0读取到操作数栈中
        spainMethod.visitInsn(ICONST_0);
        //将操作数栈栈顶数据存入到局部变量表索引为1的位置,并弹出常量0
        spainMethod.visitVarInsn(ISTORE,1);

        //for循环的开始标记
        Label forLabel = new Label();
        //for循环的结束标记
        Label endLabel = new Label();

        //int i = 0;
        spainMethod.visitInsn(ICONST_0);
        spainMethod.visitVarInsn(ISTORE,2);

        //做了一个用于跳转的标记
        spainMethod.visitLabel(forLabel);
        //将局部变量表中索引为1的位置的数据加载到操作数栈中
        spainMethod.visitVarInsn(ILOAD,2);
        //将常量100放入操作数栈中
        spainMethod.visitVarInsn(BIPUSH,100);
        //取出操作数栈的前两个数据(之后这两个数据都会弹出操作数据),比较,即 i<100时,执行下面指令,否则跳转到endLabel位置
        spainMethod.visitJumpInsn(IF_ICMPGE,endLabel);
        //todo 做一些逻辑操作
        // 取出sum的数据，放入操作数栈
        spainMethod.visitVarInsn(ILOAD,1);
        // 出去i的数据,放入操作数栈
        spainMethod.visitVarInsn(ILOAD,2);
        // sum + i;
        spainMethod.visitInsn(IADD);
        //sum = sum + i;
        spainMethod.visitVarInsn(ISTORE,1);
        //将局部变量表中索引为1的数值加1,并重新存入到局部变量表中,即i++操作
        spainMethod.visitIincInsn(2,1);
        //跳转到for循环开头,继续判断i的值
        spainMethod.visitJumpInsn(GOTO,forLabel);
        spainMethod.visitLabel(endLabel);
        //要返回一个数据,只需要将该数据放入到操作数栈栈顶
        spainMethod.visitVarInsn(ILOAD,1);
        spainMethod.visitInsn(IRETURN);
        //计算操作数栈和局部变量表的大小,对于操作数栈,任何读取操作数的指令,都会将数据POP(弹出栈)
        spainMethod.visitMaxs(2,3);
        spainMethod.visitEnd();
        byte[] bytes = byteCodeUtil.writeToFile(null);

        MyClassLoader classLoader = new MyClassLoader();
        System.out.println(fullNameType.replace("/","."));
        Class<?> cls = classLoader.defineClassPublic(fullNameType.replace("/","."),bytes,0,bytes.length);
        Object o = cls.newInstance();
        Method spain = cls.getMethod("spain");
        Object ret = spain.invoke(o);
        System.out.println(ret);

    }


}
