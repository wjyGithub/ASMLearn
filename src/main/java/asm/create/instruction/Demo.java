package asm.create.instruction;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/31 18:29
 */
public class Demo {

    private String demoName;

    private Integer demeVersion;

    public Demo() {}

    public Demo(String demoName, Integer demeVersion) {
        this.demoName = demoName;
        this.demeVersion = demeVersion;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public Integer getDemeVersion() {
        return demeVersion;
    }

    public void setDemeVersion(Integer demeVersion) {
        this.demeVersion = demeVersion;
    }

}
