// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclChar extends ConstDeclPart {

    private String constName;
    private Character charValue;

    public ConstDeclChar (String constName, Character charValue) {
        this.constName=constName;
        this.charValue=charValue;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue=charValue;
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
        buffer.append("ConstDeclChar(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+charValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclChar]");
        return buffer.toString();
    }
}
