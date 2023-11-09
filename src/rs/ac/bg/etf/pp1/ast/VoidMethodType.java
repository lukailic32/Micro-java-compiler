// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class VoidMethodType extends MethodType {

    private String methodName;

    public VoidMethodType (String methodName) {
        this.methodName=methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName=methodName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VoidMethodType(\n");

        buffer.append(" "+tab+methodName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VoidMethodType]");
        return buffer.toString();
    }
}
