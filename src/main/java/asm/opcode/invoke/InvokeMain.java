package asm.opcode.invoke;


/**
 * 方法调用指令
 * @author jianyuan.wei@hand-china.com
 * @date 2019/2/6 21:20
 */
public class InvokeMain extends TestSuperInvoke implements ITestInvoke{


    @Override
    public void interfaceMethod() {
        System.out.println("接口方法");
    }

    public void invokeSuperMethod(){
        //通过invokespecial指令调用
        protectedSuperTest();
        publicSuperTest();
    }

    private void privateMethod(){
        System.out.println("private的调用指令");
    }

    public void publicMethod(){
        System.out.println("public的调用指令");
    }

    protected void protectedMethod(){
        System.out.println("protected的调用指令");
    }

    public static final void staticMethod(){
        System.out.println("static的调用指令");
    }



    public static void main(String[] args) {
        /**
         * 总结:
         * invokevirtual:该指令主要用于调用对象的实例方法,包括public方法和protected方法,
         * 当通过对象进行实例方法的调用时,
         * 如果声明为本类，实例化为本类: 通过invokevirtual进行public方法和protected方法的调用
         * 如果声明为父类,实例化为子类：调用父类方法时,依旧通过invokevirtual指令进行调用
         * 如果声明为接口,并通过子类实现：调用接口方法时,通过invokeinterface指令进行调用
         *
         * invokespecial:该指令主要用于调用构造函数、私有方法以及父类方法
         * 其中:父类方法的调用不能通过之类的实例化对象进行调用，只能通过子类内部的成员方法进行直接调用
         *
         * invokestatic: 该指令主要用于调用类方法,即static修饰的方法
         * invokeinterface: 该指令主要用于当声明为接口,并通过子类实现时，调用接口方法,通过该指令调用
         */

        //调用静态方法,通过invokestatic指令调用
        staticMethod();

        //调用构造函数,通过invokespecial指令调用
        InvokeMain invokeMain = new InvokeMain();
        //调用private方法,通过invokespecial指令调用
        invokeMain.privateMethod();
        //调用public方法/protected方法,通过invokevirtual指令调用
        invokeMain.publicMethod();
        invokeMain.protectedMethod();

        //通过invokevirtual调用实现的接口方法
        invokeMain.interfaceMethod();

        //调用父类的protected方法,通过invokevirtual指令调用
        invokeMain.protectedSuperTest();
        //调用父类的public方法,通过invokevirtual指令调用
        invokeMain.publicSuperTest();

        invokeMain.invokeSuperMethod();

        //todo 父类声明,子类引用
        TestSuperInvoke testSuperInvoke = new InvokeMain();
        //通过invokevirtual指令调用
        testSuperInvoke.protectedSuperTest();
        //通过invokevirtual指令调用
        testSuperInvoke.publicSuperTest();

        //todo 接口声明，子类引用
        ITestInvoke iTestInvoke = new InvokeMain();
        //通过invokeinterface指令调用接口方法
        iTestInvoke.interfaceMethod();

    }



}
