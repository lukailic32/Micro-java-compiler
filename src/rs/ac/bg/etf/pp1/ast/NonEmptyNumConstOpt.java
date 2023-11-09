// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class NonEmptyNumConstOpt extends NumConstOpt {

    private Integer value;

    public NonEmptyNumConstOpt (Integer value) {
        this.value=value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value=value;
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
        buffer.append("NonEmptyNumConstOpt(\n");

        buffer.append(" "+tab+value);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyNumConstOpt]");
        return buffer.toString();
    }
}
