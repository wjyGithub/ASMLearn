package asm.modify.access;


import asm.util.MyClassLoader;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ASM6;

/**
 * 修改成员属性的权限修饰符
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/28 22:47
 */
public class ModifyFieldAccessMain {

    public static class ModifyAccessVisitor extends ClassVisitor {

        public ModifyAccessVisitor(int i, ClassVisitor classVisitor) {
            super(i, classVisitor);
        }

        /**
         * 读取到成员属性时,触发该方法
         * @param access 字段的权限修饰符
         * @param name 字段名
         * @param descriptor 字段的描述符
         * @param signature 签名
         * @param value 数值
         * @return
         */
        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            //只修改成员属性name的权限,该修改只在内存中修改,如果不写入到class文件中,是不会对原class有任何影响
            if(StringUtils.equals(name,"name")) {
                return super.visitField(ACC_PUBLIC,name,descriptor,signature,value);
            }
            return super.visitField(access, name, descriptor, signature, value);
        }
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, NoSuchFieldException {

        String fullName = Demo.class.getName();
        String fullNameType = fullName.replace(".","/");
        ClassReader cr = new ClassReader(fullNameType);
        ClassWriter cw = new ClassWriter(0);
        ModifyAccessVisitor mv = new ModifyAccessVisitor(ASM6,cw);
        cr.accept(mv,ClassReader.SKIP_DEBUG);
        byte[] bytes = cw.toByteArray();

        MyClassLoader classLoader = new MyClassLoader();
        Class<?> cls = classLoader.defineClassPublic(fullName, bytes, 0, bytes.length);

        //创建实例对象
        Object o = cls.newInstance();
        Field name = cls.getField("name");
        name.set(o,"kobin");
        Object nameValue = name.get(o);
        System.out.println("name:" + nameValue);

        /**未将成员属性age修改为public,所以调用会报错*/
        Field age = cls.getField("age");
        age.set(o,21);
        Object ageValue = age.get(o);
        System.out.println("age:"+ageValue);

    }
}
