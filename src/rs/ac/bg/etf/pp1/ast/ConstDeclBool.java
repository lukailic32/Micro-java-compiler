// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclBool extends ConstDeclPart {

    private String constName;
    private Boolean boolValue;

    public ConstDeclBool (String constName, Boolean boolValue) {
        this.constName=constName;
        this.boolValue=boolValue;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue=boolValue;
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
        buffer.append("ConstDeclBool(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+boolValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclBool]");
        return buffer.toString();
    }
}
