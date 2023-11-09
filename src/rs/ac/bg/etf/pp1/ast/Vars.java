// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class Vars extends VarDecl {

    private Type Type;
    private VarDeclPartList VarDeclPartList;

    public Vars (Type Type, VarDeclPartList VarDeclPartList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclPartList=VarDeclPartList;
        if(VarDeclPartList!=null) VarDeclPartList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclPartList getVarDeclPartList() {
        return VarDeclPartList;
    }

    public void setVarDeclPartList(VarDeclPartList VarDeclPartList) {
        this.VarDeclPartList=VarDeclPartList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclPartList!=null) VarDeclPartList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclPartList!=null) VarDeclPartList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclPartList!=null) VarDeclPartList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Vars(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclPartList!=null)
            buffer.append(VarDeclPartList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Vars]");
        return buffer.toString();
    }
}
