// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:42:40


package rs.ac.bg.etf.pp1.ast;

public class StatementForeach extends Statement {

    private ForeachDes ForeachDes;
    private Foreach Foreach;
    private ForeachStart ForeachStart;
    private Statement Statement;
    private ForeachEnd ForeachEnd;

    public StatementForeach (ForeachDes ForeachDes, Foreach Foreach, ForeachStart ForeachStart, Statement Statement, ForeachEnd ForeachEnd) {
        this.ForeachDes=ForeachDes;
        if(ForeachDes!=null) ForeachDes.setParent(this);
        this.Foreach=Foreach;
        if(Foreach!=null) Foreach.setParent(this);
        this.ForeachStart=ForeachStart;
        if(ForeachStart!=null) ForeachStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ForeachEnd=ForeachEnd;
        if(ForeachEnd!=null) ForeachEnd.setParent(this);
    }

    public ForeachDes getForeachDes() {
        return ForeachDes;
    }

    public void setForeachDes(ForeachDes ForeachDes) {
        this.ForeachDes=ForeachDes;
    }

    public Foreach getForeach() {
        return Foreach;
    }

    public void setForeach(Foreach Foreach) {
        this.Foreach=Foreach;
    }

    public ForeachStart getForeachStart() {
        return ForeachStart;
    }

    public void setForeachStart(ForeachStart ForeachStart) {
        this.ForeachStart=ForeachStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ForeachEnd getForeachEnd() {
        return ForeachEnd;
    }

    public void setForeachEnd(ForeachEnd ForeachEnd) {
        this.ForeachEnd=ForeachEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForeachDes!=null) ForeachDes.accept(visitor);
        if(Foreach!=null) Foreach.accept(visitor);
        if(ForeachStart!=null) ForeachStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ForeachEnd!=null) ForeachEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachDes!=null) ForeachDes.traverseTopDown(visitor);
        if(Foreach!=null) Foreach.traverseTopDown(visitor);
        if(ForeachStart!=null) ForeachStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ForeachEnd!=null) ForeachEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachDes!=null) ForeachDes.traverseBottomUp(visitor);
        if(Foreach!=null) Foreach.traverseBottomUp(visitor);
        if(ForeachStart!=null) ForeachStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ForeachEnd!=null) ForeachEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementForeach(\n");

        if(ForeachDes!=null)
            buffer.append(ForeachDes.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Foreach!=null)
            buffer.append(Foreach.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachStart!=null)
            buffer.append(ForeachStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachEnd!=null)
            buffer.append(ForeachEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementForeach]");
        return buffer.toString();
    }
}
