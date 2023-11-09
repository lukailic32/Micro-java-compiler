// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclNumber extends ConstDeclPart {

    private String constName;
    private Integer numValue;

    public ConstDeclNumber (String constName, Integer numValue) {
        this.constName=constName;
        this.numValue=numValue;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Integer getNumValue() {
        return numValue;
    }

    public void setNumValue(Integer numValue) {
        this.numValue=numValue;
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
        buffer.append("ConstDeclNumber(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+numValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclNumber]");
        return buffer.toString();
    }
}
