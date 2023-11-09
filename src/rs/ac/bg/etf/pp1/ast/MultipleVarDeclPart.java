// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class MultipleVarDeclPart extends VarDeclPartList {

    private VarDeclPartList VarDeclPartList;
    private VarDeclPart VarDeclPart;

    public MultipleVarDeclPart (VarDeclPartList VarDeclPartList, VarDeclPart VarDeclPart) {
        this.VarDeclPartList=VarDeclPartList;
        if(VarDeclPartList!=null) VarDeclPartList.setParent(this);
        this.VarDeclPart=VarDeclPart;
        if(VarDeclPart!=null) VarDeclPart.setParent(this);
    }

    public VarDeclPartList getVarDeclPartList() {
        return VarDeclPartList;
    }

    public void setVarDeclPartList(VarDeclPartList VarDeclPartList) {
        this.VarDeclPartList=VarDeclPartList;
    }

    public VarDeclPart getVarDeclPart() {
        return VarDeclPart;
    }

    public void setVarDeclPart(VarDeclPart VarDeclPart) {
        this.VarDeclPart=VarDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclPartList!=null) VarDeclPartList.accept(visitor);
        if(VarDeclPart!=null) VarDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclPartList!=null) VarDeclPartList.traverseTopDown(visitor);
        if(VarDeclPart!=null) VarDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclPartList!=null) VarDeclPartList.traverseBottomUp(visitor);
        if(VarDeclPart!=null) VarDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleVarDeclPart(\n");

        if(VarDeclPartList!=null)
            buffer.append(VarDeclPartList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclPart!=null)
            buffer.append(VarDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleVarDeclPart]");
        return buffer.toString();
    }
}
