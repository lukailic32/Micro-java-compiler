// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class DescriptionConst extends DescriptionList {

    private DescriptionList DescriptionList;
    private ConstDecl ConstDecl;

    public DescriptionConst (DescriptionList DescriptionList, ConstDecl ConstDecl) {
        this.DescriptionList=DescriptionList;
        if(DescriptionList!=null) DescriptionList.setParent(this);
        this.ConstDecl=ConstDecl;
        if(ConstDecl!=null) ConstDecl.setParent(this);
    }

    public DescriptionList getDescriptionList() {
        return DescriptionList;
    }

    public void setDescriptionList(DescriptionList DescriptionList) {
        this.DescriptionList=DescriptionList;
    }

    public ConstDecl getConstDecl() {
        return ConstDecl;
    }

    public void setConstDecl(ConstDecl ConstDecl) {
        this.ConstDecl=ConstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DescriptionList!=null) DescriptionList.accept(visitor);
        if(ConstDecl!=null) ConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DescriptionList!=null) DescriptionList.traverseTopDown(visitor);
        if(ConstDecl!=null) ConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DescriptionList!=null) DescriptionList.traverseBottomUp(visitor);
        if(ConstDecl!=null) ConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DescriptionConst(\n");

        if(DescriptionList!=null)
            buffer.append(DescriptionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecl!=null)
            buffer.append(ConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DescriptionConst]");
        return buffer.toString();
    }
}
