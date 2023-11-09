// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class MultipleConstDeclPart extends ConstDeclPartList {

    private ConstDeclPartList ConstDeclPartList;
    private ConstDeclPart ConstDeclPart;

    public MultipleConstDeclPart (ConstDeclPartList ConstDeclPartList, ConstDeclPart ConstDeclPart) {
        this.ConstDeclPartList=ConstDeclPartList;
        if(ConstDeclPartList!=null) ConstDeclPartList.setParent(this);
        this.ConstDeclPart=ConstDeclPart;
        if(ConstDeclPart!=null) ConstDeclPart.setParent(this);
    }

    public ConstDeclPartList getConstDeclPartList() {
        return ConstDeclPartList;
    }

    public void setConstDeclPartList(ConstDeclPartList ConstDeclPartList) {
        this.ConstDeclPartList=ConstDeclPartList;
    }

    public ConstDeclPart getConstDeclPart() {
        return ConstDeclPart;
    }

    public void setConstDeclPart(ConstDeclPart ConstDeclPart) {
        this.ConstDeclPart=ConstDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclPartList!=null) ConstDeclPartList.accept(visitor);
        if(ConstDeclPart!=null) ConstDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclPartList!=null) ConstDeclPartList.traverseTopDown(visitor);
        if(ConstDeclPart!=null) ConstDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclPartList!=null) ConstDeclPartList.traverseBottomUp(visitor);
        if(ConstDeclPart!=null) ConstDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConstDeclPart(\n");

        if(ConstDeclPartList!=null)
            buffer.append(ConstDeclPartList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclPart!=null)
            buffer.append(ConstDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConstDeclPart]");
        return buffer.toString();
    }
}
