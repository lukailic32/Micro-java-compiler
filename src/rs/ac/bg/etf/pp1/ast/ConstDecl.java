// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ConstDeclPartList ConstDeclPartList;

    public ConstDecl (Type Type, ConstDeclPartList ConstDeclPartList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstDeclPartList=ConstDeclPartList;
        if(ConstDeclPartList!=null) ConstDeclPartList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstDeclPartList getConstDeclPartList() {
        return ConstDeclPartList;
    }

    public void setConstDeclPartList(ConstDeclPartList ConstDeclPartList) {
        this.ConstDeclPartList=ConstDeclPartList;
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
        if(Type!=null) Type.accept(visitor);
        if(ConstDeclPartList!=null) ConstDeclPartList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstDeclPartList!=null) ConstDeclPartList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstDeclPartList!=null) ConstDeclPartList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclPartList!=null)
            buffer.append(ConstDeclPartList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
