// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class MultipleFormPars extends FormPars {

    private FormParsPart FormParsPart;
    private FormPars FormPars;

    public MultipleFormPars (FormParsPart FormParsPart, FormPars FormPars) {
        this.FormParsPart=FormParsPart;
        if(FormParsPart!=null) FormParsPart.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
    }

    public FormParsPart getFormParsPart() {
        return FormParsPart;
    }

    public void setFormParsPart(FormParsPart FormParsPart) {
        this.FormParsPart=FormParsPart;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsPart!=null) FormParsPart.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsPart!=null) FormParsPart.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsPart!=null) FormParsPart.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleFormPars(\n");

        if(FormParsPart!=null)
            buffer.append(FormParsPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleFormPars]");
        return buffer.toString();
    }
}
