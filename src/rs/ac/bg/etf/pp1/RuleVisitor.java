package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

import org.apache.log4j.Logger;

public class RuleVisitor extends VisitorAdaptor {
	
	int printCallCount = 0;
	int varDeclCount = 0;
	
	Logger log = Logger.getLogger(getClass());

    public void visit(StatementPrint StatementPrint) { 
    	printCallCount++;
    	log.info("Prepoznata naredba print!");
    }
    
    public void visit(OneVarDeclPart OneVarDeclPart) {
    	varDeclCount++;
    	log.info("Prepoznata deklaracija promenljivih!");	
    }

    public void visit(MultipleVarDeclPart MultipleVarDeclPart) {
    	varDeclCount++;
    	log.info("Prepoznata deklaracija promenljivih!");	
    }

}
