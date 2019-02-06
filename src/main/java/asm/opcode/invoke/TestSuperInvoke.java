package asm.opcode.invoke;

/**
 * 用于测试调用父类方法
 * @author jianyuan.wei@hand-china.com
 * @date 2019/2/6 21:45
 */
public class TestSuperInvoke {

    public TestSuperInvoke(){

    }

    public void publicSuperTest(){
        System.out.println("父类的public方法");
    }

    protected void protectedSuperTest(){
        System.out.println("父类的protected方法");
    }

    private void privateSuperTest(){
        System.out.println("父类的private方法");
    }
}
