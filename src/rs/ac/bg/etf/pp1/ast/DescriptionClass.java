// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class DescriptionClass extends DescriptionList {

    private DescriptionList DescriptionList;
    private ClassDecl ClassDecl;

    public DescriptionClass (DescriptionList DescriptionList, ClassDecl ClassDecl) {
        this.DescriptionList=DescriptionList;
        if(DescriptionList!=null) DescriptionList.setParent(this);
        this.ClassDecl=ClassDecl;
        if(ClassDecl!=null) ClassDecl.setParent(this);
    }

    public DescriptionList getDescriptionList() {
        return DescriptionList;
    }

    public void setDescriptionList(DescriptionList DescriptionList) {
        this.DescriptionList=DescriptionList;
    }

    public ClassDecl getClassDecl() {
        return ClassDecl;
    }

    public void setClassDecl(ClassDecl ClassDecl) {
        this.ClassDecl=ClassDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DescriptionList!=null) DescriptionList.accept(visitor);
        if(ClassDecl!=null) ClassDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DescriptionList!=null) DescriptionList.traverseTopDown(visitor);
        if(ClassDecl!=null) ClassDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DescriptionList!=null) DescriptionList.traverseBottomUp(visitor);
        if(ClassDecl!=null) ClassDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DescriptionClass(\n");

        if(DescriptionList!=null)
            buffer.append(DescriptionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassDecl!=null)
            buffer.append(ClassDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DescriptionClass]");
        return buffer.toString();
    }
}
