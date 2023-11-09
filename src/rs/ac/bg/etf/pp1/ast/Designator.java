// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private DesName DesName;
    private ExprOpt ExprOpt;

    public Designator (DesName DesName, ExprOpt ExprOpt) {
        this.DesName=DesName;
        if(DesName!=null) DesName.setParent(this);
        this.ExprOpt=ExprOpt;
        if(ExprOpt!=null) ExprOpt.setParent(this);
    }

    public DesName getDesName() {
        return DesName;
    }

    public void setDesName(DesName DesName) {
        this.DesName=DesName;
    }

    public ExprOpt getExprOpt() {
        return ExprOpt;
    }

    public void setExprOpt(ExprOpt ExprOpt) {
        this.ExprOpt=ExprOpt;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesName!=null) DesName.accept(visitor);
        if(ExprOpt!=null) ExprOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesName!=null) DesName.traverseTopDown(visitor);
        if(ExprOpt!=null) ExprOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesName!=null) DesName.traverseBottomUp(visitor);
        if(ExprOpt!=null) ExprOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator(\n");

        if(DesName!=null)
            buffer.append(DesName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprOpt!=null)
            buffer.append(ExprOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator]");
        return buffer.toString();
    }
}
