// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class DescriptionVar extends DescriptionList {

    private DescriptionList DescriptionList;
    private VarDecl VarDecl;

    public DescriptionVar (DescriptionList DescriptionList, VarDecl VarDecl) {
        this.DescriptionList=DescriptionList;
        if(DescriptionList!=null) DescriptionList.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public DescriptionList getDescriptionList() {
        return DescriptionList;
    }

    public void setDescriptionList(DescriptionList DescriptionList) {
        this.DescriptionList=DescriptionList;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DescriptionList!=null) DescriptionList.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DescriptionList!=null) DescriptionList.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DescriptionList!=null) DescriptionList.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DescriptionVar(\n");

        if(DescriptionList!=null)
            buffer.append(DescriptionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DescriptionVar]");
        return buffer.toString();
    }
}
