package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;


public class SemanticAnalyzer extends VisitorAdaptor{
	
	
	Obj currentMethod = null;
	Struct currentType = null;
	
	boolean mainMethod = false;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;

	private int methodParams = 0;
	private List<List<Struct>> actParsLists = new ArrayList<List<Struct>>();
	
	int foreachDepthCounter = 0;
	int whileDepthCounter = 0;
	
	// 0 - WHILE			1 - FOREACH
	List<Integer> loops = new ArrayList<Integer>();
	
	
	private String relOp = "";
	
	Logger log = Logger.getLogger(getClass());
	
	public static final Struct boolType = new Struct(Struct.Bool);
	
	int printCallCount = 0;
	int varDeclCount = 0;
	
	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	
	
	///----------PROGRAM----------///
	
	public void visit(ProgName progName){
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	public void visit(Program program) {
		if (mainMethod == false){
			report_error("GRESKA: NIJE PRONADJENA MAIN METODA", null);
		}
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	
	
	
	///----------VAR_DECL----------///

	public void visit(VarDeclPartArray varDeclPartArray){
		if (Tab.currentScope.findSymbol(varDeclPartArray.getVarName()) != null){
			report_error("GRESKA: Simbol " + varDeclPartArray.getVarName() + "je vec deklarisan", null);
			return;
		}
		report_info("Deklarisana promenljiva " + varDeclPartArray.getVarName(), varDeclPartArray);
		varDeclPartArray.obj = 
				Tab.insert(Obj.Var, varDeclPartArray.getVarName(), new Struct(Struct.Array, currentType));
	}

	public void visit(SingleVarDeclPart singleVarDeclPart){
		if (Tab.currentScope.findSymbol(singleVarDeclPart.getVarName()) != null){
			report_error("GRESKA: Simbol " + singleVarDeclPart.getVarName() + "je vec deklarisan", null);
			return;
		}
		report_info("Deklarisana promenljiva " + singleVarDeclPart.getVarName(), singleVarDeclPart);
		singleVarDeclPart.obj = Tab.insert(Obj.Var, singleVarDeclPart.getVarName(), currentType);
	}
	
	 public void visit(OneVarDeclPart OneVarDeclPart) {
	    	varDeclCount++;
	    	log.info("Prepoznata deklaracija promenljivih!");	
	 }
	 
	 public void visit(MultipleVarDeclPart MultipleVarDeclPart) {
	    	varDeclCount++;
	    	log.info("Prepoznata deklaracija promenljivih!");	
	 }
		
	
	///----------TYPE----------///

	public void visit(Type type){
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj){
			report_error("GRESKA: Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Tab.noType;
			return;
		} else {
			if ( typeNode.getKind() == Obj.Type){
				type.struct = typeNode.getType();
			} else {
				report_error("GRESKA: Rec " + type.getTypeName() + " ne predstavlja tip", type);
				return;
			}
		}
		currentType = type.struct;
	}
	
	
	
	///----------CONST----------///

	public void visit(ConstDeclNumber constDeclNumber){
		if (Tab.currentScope.findSymbol(constDeclNumber.getConstName()) != null){
			report_error("GRESKA: Simbol " + constDeclNumber.getConstName() + "je vec deklarisan", null);
			return;
		}
		if (!currentType.compatibleWith(Tab.intType)){
			report_error("GRESKA: Konstanti " + constDeclNumber.getConstName() + 
					" se ne dodeljuje vrednost odgovarajuceg tipa", null);
			return;
		}
		constDeclNumber.obj = Tab.insert(Obj.Con, constDeclNumber.getConstName(), currentType);
		constDeclNumber.obj.setAdr(constDeclNumber.getNumValue());
		report_info("Deklarisana konstanta " + constDeclNumber.getConstName(), constDeclNumber);
	}
	
	public void visit(ConstDeclChar constDeclChar) {
		if (Tab.currentScope.findSymbol(constDeclChar.getConstName()) != null){
			report_error("GRESKA: Simbol " + constDeclChar.getConstName() + "je vec deklarisan", null);
			return;
		}
		if (!currentType.compatibleWith(Tab.charType)){
			report_error("GRESKA: Konstanti " + constDeclChar.getConstName() + 
					" se ne dodeljuje vrednost odgovarajuceg tipa", null);
			return;
		}
		constDeclChar.obj = Tab.insert(Obj.Con, constDeclChar.getConstName(), currentType);
		constDeclChar.obj.setAdr(constDeclChar.getCharValue());
		report_info("Deklarisana konstanta " + constDeclChar.getConstName(), constDeclChar);
	}
	
	public void visit(ConstDeclBool constDeclBool){
		if (Tab.currentScope.findSymbol(constDeclBool.getConstName()) != null){
			report_error("GRESKA: Simbol " + constDeclBool.getConstName() + "je vec deklarisan", null);
			return;
		}
		if (!currentType.compatibleWith(boolType)){
			report_error("GRESKA: Konstanti " + constDeclBool.getConstName() + 
					" se ne dodeljuje vrednost odgovarajuceg tipa", null);
			return;
		}
		constDeclBool.obj = Tab.insert(Obj.Con, constDeclBool.getConstName(), currentType);
		if (constDeclBool.getBoolValue() == true)
			constDeclBool.obj.setAdr(1);
		else constDeclBool.obj.setAdr(0);
		report_info("Deklarisana konstanta " + constDeclBool.getConstName(), constDeclBool);
	}
	
	
   	
	///----------METH_DECL----------///

	public void visit(OtherMethodType otherMethodType){
		if (Tab.currentScope.findSymbol(otherMethodType.getMethodName()) != null){
			report_error("Ime metode " + otherMethodType.getMethodName() + " je vec deklarisano", null);
			return;
		}
		currentMethod = Tab.insert(Obj.Meth, otherMethodType.getMethodName(), otherMethodType.getType().struct);
		otherMethodType.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + otherMethodType.getMethodName(), otherMethodType);
	}
	
	public void visit(VoidMethodType voidMethodType){
		if (Tab.currentScope.findSymbol(voidMethodType.getMethodName()) != null){
			report_error("Ime metode " + voidMethodType.getMethodName() + " je vec deklarisano", null);
			return;
		}
		currentMethod = Tab.insert(Obj.Meth, voidMethodType.getMethodName(), Tab.noType);
		voidMethodType.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + voidMethodType.getMethodName(), voidMethodType);
	}
	
	public void visit(MethodDecl methodDecl){
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("GRESKA: Semanticka greska na liniji " + methodDecl.getLine() 
					+ ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
		}
		if ("main".equals(currentMethod.getName())){
			mainMethod = true;
		}
		
		
		currentMethod.setLevel(methodParams);
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		//resetovanje flagova
		methodParams = 0;
		returnFound = false;
		currentMethod = null;
	}
	
	
	
	///----------FORM_PARS----------///

	public void visit(FormParsPartArray formParsPartArray){
		if (Tab.currentScope.findSymbol(formParsPartArray.getParamName()) != null){
			report_error("GRESKA: Na liniji : " + formParsPartArray.getLine() + 
					" Ime formalnog parametra " + formParsPartArray.getParamName() + " je vec deklarisano", null);    		
			return;
		}
		methodParams++;
		Tab.insert(Obj.Var, formParsPartArray.getParamName(), new Struct(Struct.Array, currentType));
		
	}
	
	public void visit(SingleFormParsPart singleFormParsPart){
		if (Tab.currentScope.findSymbol(singleFormParsPart.getParamName()) != null){
			report_error("GRESKA: Na liniji : " + singleFormParsPart.getLine() + 
					" Ime formalnog parametra " + singleFormParsPart.getParamName() + "je vec deklarisano", null);
		}
		methodParams++;
		Tab.insert(Obj.Var, singleFormParsPart.getParamName(), currentType);
	}
	
	
	
	///----------DESIGNATOR----------///

	public void visit(DesignatorStatementDesignator designatorStatementDesignator){
		//proveriti
		Struct src = designatorStatementDesignator.getExpr().struct;
		Struct dst = designatorStatementDesignator.getDesignator().obj.getType();
		if (!(designatorStatementDesignator.getDesignator().obj.getKind() == Obj.Var ||
				designatorStatementDesignator.getDesignator().obj.getKind() == Obj.Elem)){
			report_error("GRESKA: Na liniji " + designatorStatementDesignator.getLine() + 
					": Samo promenljivoj i elementu niza mozemo dodeliti vrednost", null);
			return;
		}
		if (designatorStatementDesignator.getDesignator().obj.getType().getKind() == Struct.Array){
			dst = designatorStatementDesignator.getDesignator().obj.getType().getElemType();
		}
		if (designatorStatementDesignator.getExpr().struct.getKind() == Struct.Array){
			src = designatorStatementDesignator.getExpr().struct.getElemType();
		}
		if (dst.assignableTo(src)){
			report_info("Uspesna dodela vrednosti", designatorStatementDesignator);
		}
		else {
			report_error("GRESKA: Na liniji " + designatorStatementDesignator.getLine() + 
					": Leva i desna vrednost nisu istog tipa", designatorStatementDesignator);
		}
	}
	
	public void visit(DesignatorFuncCall designatorFuncCall){
		if (designatorFuncCall.getCallingFuncName().getDesignator().obj.getKind() != Obj.Meth){
			report_error("GRESKA: Na liniji " + designatorFuncCall.getLine() + 
					": Ne postoji funkcija sa imenom " + 
					designatorFuncCall.getCallingFuncName().getDesignator().getDesName().getName(), null);
			return;
		}
		
		List<Struct> lastActPars = actParsLists.remove(actParsLists.size() - 1);
		int actParsNumber = lastActPars.size();
		int formParsNumber = designatorFuncCall.getCallingFuncName().obj.getLevel();
		if (actParsNumber != formParsNumber) {
			report_error("GRESKA: Na liniji : " + designatorFuncCall.getLine() + 
					" Nije prosledjen odgovarajuci broj parametara ", null);
			return;
		}
		
		// popunicemo listu stvarnim parametrima funkcije
		List<Obj> formalPars = new ArrayList<Obj>();
		int i = 0;
		for (Iterator<Obj> formalParsIter = 
				designatorFuncCall.getCallingFuncName().obj.getLocalSymbols().iterator();
				i < designatorFuncCall.getCallingFuncName().obj.getLevel() && formalParsIter.hasNext();){
			formalPars.add(formalParsIter.next());
			i++;
		}
		
		//provera da li su parametri na istim mestima istog tipa
		for (int j = 0; j < formParsNumber; j++){
			if (!lastActPars.get(j).assignableTo(formalPars.get(j).getType())){
				report_error("GRESKA: Na liniji : " + designatorFuncCall.getLine() + 
						"Parametri na poziciji " + j + " nisu istog tipa", null);
			}
		}
		
	}
	
	public void visit(DesignatorStatementInc designatorStatementInc){
		if (designatorStatementInc.getDesignator().obj.getKind() != Obj.Var &&
				designatorStatementInc.getDesignator().obj.getKind() != Obj.Elem){
			report_error("GRESKA: Na liniji " + designatorStatementInc.getLine() + 
					": Simbol ne predstavlja promenljivu ili element niza", null);
			return;
		}
		if (designatorStatementInc.getDesignator().obj.getType().getKind() != Struct.Int){
			report_error("GRESKA: Na liniji " + designatorStatementInc.getLine() + 
					": Promenljiva nije tipa int", null);
		}
		
	}
	
	public void visit(DesignatorStatementDec designatorStatementDec){
		if (designatorStatementDec.getDesignator().obj.getKind() != Obj.Var &&
				designatorStatementDec.getDesignator().obj.getKind() != Obj.Elem){
			report_error("GRESKA: Na liniji " + designatorStatementDec.getLine() + 
					": Simbol ne predstavlja promenljivu ili element niza", null);
			return;
		}
		if (designatorStatementDec.getDesignator().obj.getType().getKind() != Struct.Int){
			report_error("GRESKA: Na liniji " + designatorStatementDec.getLine() + 
					": Promenljiva nije tipa int", null);
		}
		
	}
	
	public void visit(DesName desName){
		desName.obj = Tab.find(desName.getName());
	}
	
	public void visit(Designator designator){
		Obj des = Tab.find(designator.getDesName().getName());
		if (des == Tab.noObj){
			report_error("GRESKA: Na liniji : " + designator.getLine() + 
					" Promenljiva nije deklarisana", null);
			designator.obj = Tab.noObj;
			return;
		}
		
		Struct exp = designator.getExprOpt().struct;
		
		if (exp != Tab.noType){
			if (des.getType().getKind() != Struct.Array){
				report_error("GRESKA: Na liniji : " + designator.getLine() +
						"Promenljiva ne predstavlja niz", null);
				designator.obj = Tab.noObj;
				return;
			}
			// PRIPAZI
			if (exp.getKind() == Struct.Array){
				exp = exp.getElemType();
			}
			if (exp.getKind() != Struct.Int){
				report_error("GRESKA: Na liniji : " + designator.getLine() +
						" Indeks niza mora biti tipa int", null);
				designator.obj = Tab.noObj;
				return;
			}
			designator.obj = new Obj(Obj.Elem, des.getName(), des.getType().getElemType());
			return;
		} 
		designator.obj = des;
		
	}
	
	public void visit(NonEmptyExprOpt nonEmptyExprOpt){
		nonEmptyExprOpt.struct = nonEmptyExprOpt.getExpr().struct;
	}
	
	public void visit(NoExprOpt noExprOpt){
		noExprOpt.struct = Tab.noType;
	}
	
	
	
	///----------STATEMENT----------///

	public void visit(StatementRead statementRead){
		//MOZDA TREBA PROVERAVATI DA LI JE NIZ U PITANJU PA KORISTITI
		//statementRead.getDesignator().obj.getType().getElemType();
		if (statementRead.getDesignator().obj.getKind() != Obj.Elem && 
				statementRead.getDesignator().obj.getKind() != Obj.Var){
			report_error("GRESKA: Na liniji : " + statementRead.getLine() + 
					" U zagradama mora biti promenljiva ili element niza", null);
			return;
		}
		if (statementRead.getDesignator().obj.getType() != Tab.charType && 
				statementRead.getDesignator().obj.getType() != Tab.intType &&
				statementRead.getDesignator().obj.getType() != boolType) {
			report_error("GRESKA: Na liniji : " + statementRead.getLine() +
					" U zagradama mora biti int, char ili bool", null);
			return;
		}
	}
	
	public void visit(NonEmptyNumConstOpt nonEmptyNumConstOpt){
		nonEmptyNumConstOpt.struct = Tab.intType; 
	}
	
	public void visit(NoNumConstOpt noNumConstOpt){
		noNumConstOpt.struct = Tab.noType;
	}
	
	public void visit(StatementPrint statementPrint){
		if (statementPrint.getExpr().struct.getKind() != Struct.Int &&
				statementPrint.getExpr().struct.getKind() != Struct.Bool &&
				statementPrint.getExpr().struct.getKind() != Struct.Char){
			report_error("GRESKA: Na liniji : " + statementPrint.getLine() +
					"U zagradama mora biti int, char ili bool", null);
			return;
		}
		//numConsOpt sam stavio da bude Struct
		
		printCallCount++;
    	log.info("Prepoznata naredba print!");
	}
	/* NEMA POTREBE PROVERAVATI - AKO JE RETURN BEZ EXPR PROVERICEMO SAMO DA LI JE METODA NOTYPE
	public void visit(StatementReturn statementReturn){
		returnFound = true;
		//PROVERITI
		if (currentMethod.getType() != Tab.noType){
			report_error("GRESKA: Na liniji : " + statementReturn.getLine() + 
					"Funkcija nij", null);
		}
		//NASTAVI
	}*/
	
	public void visit(StatementReturnExpr statementReturnExpr){
		returnFound = true;
		//Mozda proveriti da li je currMeth VOID ali mislim da nema potrebe
		if (statementReturnExpr.getExpr().struct.getKind() != currentMethod.getType().getKind()){
			report_error("GRESKA: Na liniji : " + statementReturnExpr.getLine() + 
					" Tip povratne vrednosti u returnu i funkciji se ne poklapaju", null);
		}
	}

	public void visit(StatementIf statementIf){
		//ovde nas jedino condition zanima da li je tipa bool
		//mislim da je ovo suvisno
		/*if (statementIf.getCondition().struct.getKind() != Struct.Bool){
			report_error("GRESKA: Na liniji : " + statementIf.getLine() + 
					"Condition mora biti tipa bool", null);
		}*/
	}
	
	//da znamo da smo u while petlji i kojoj petlji po redu
	public void visit(WhileNonT whileNonT){
		whileDepthCounter++;
		loops.add(0);
		//Tab.openScope();
	}
	
	public void visit(StatementWhile statementWhile){
		whileDepthCounter--;
		loops.remove(loops.size()-1); //napustili smo poslednje dodatu while petlju
		//Tab.chainLocalSymbols(statementWhile.getWhile().obj);  MORAO BIH DA STAVIM DA JE WHILE OBJ u mjparseru
		//Tab.closeScope();
		//ovde nas jedino condition zanima da li je tipa bool
				//mislim da je ovo suvisno
			/*	if (statementWhile.getCondition().struct.getKind() != Struct.Bool){
					report_error("GRESKA: Na liniji : " + statementWhile.getLine() + 
							"Condition mora biti tipa bool", null);
				}*/
	}
	
	public void visit(StatementBreak statementBreak){
		if (loops.size() == 0){
			report_error("GRESKA: Na liniji : " + statementBreak.getLine() + 
					" Naredba break se sme koristiti samo unutar while ili foreach petlje", null);
		}
	}
	
	public void visit(StatementContinue statementContinue){
		if (loops.size() == 0) {
			report_error("GRESKA: Na liniji : " + statementContinue.getLine() + 
					" Naredba continue se sme koristit samo unutar while ili foreach petlje", null);
		}
	}
	
	public void visit(ForeachNonT foreachNonT){
		foreachDepthCounter++;
		loops.add(1);
		//Tab.openScope();
	}
	
	public void visit(StatementForeach statementForeach){
		//napustili smo poslednje dodatu foreach petlju
		foreachDepthCounter--;
		loops.remove(loops.size()-1);
		//Designator mora biti niz
		if (statementForeach.getForeachDes().getDesignator().obj.getType().getKind() != Struct.Array){
			report_error("GRESKA: Na liniji : " +  statementForeach.getLine() + 
					" Simbol sa leve strane mora predstavljati niz", null);
			return;
		}
		Obj ident = Tab.find(statementForeach.getForeachStart().getVarName());
		//IDENT mora biti globalna ili lokalna promenljiva
		if (ident.getKind() != Obj.Var){
			report_error("GRESKA: Na liniji : " + statementForeach.getLine() + 
					" Prvi parametar u zagradi mora biti deklarisana lokalna ili globalna promenljiva", null);
			return;
		}
		//IDENT mora biti istog tipa kao i elementi niza nad kojim se poziva funkcija
		if (ident.getType().getKind() != statementForeach.getForeachDes().getDesignator().obj.getType().getElemType().getKind()){
			report_error("GRESKA: Na liniji : " + statementForeach.getLine() + 
					"Prvi parametar u zagradi mora biti istog tipa kao i elementi niza " + 
					statementForeach.getForeachDes().getDesignator().obj.getName(), null);
		}
		//Tab.chainLocalSymbols(statementForeach.getForeach().obj);
		//Tab.closeScope();
	}
	
	public void visit(StatementFindAny statementFindAny){
		//Desni design mora biti niz
		if (statementFindAny.getDesignator1().obj.getType().getKind() != Struct.Array){
			report_error("GRESKA: Na liniji : " +  statementFindAny.getLine() + 
					" Simbol sa desne strane jednakosti mora predstavljati niz", null);
			return;
		}
		//Levi design mora biti promenljiva bool
		if ((statementFindAny.getDesignator().obj.getKind() != Obj.Var) ||
				(statementFindAny.getDesignator().obj.getType().getKind() != Struct.Bool)){
			report_error("GRESKA: Na liniji : " + statementFindAny.getLine() + 
					" Simbol sa leve strane mora biti promenljiva tipa bool", null);
			return;
		}
		//Expr mora biti istog tipa kao i elementi niza
		if (statementFindAny.getDesignator1().obj.getType().getElemType().getKind() != 
				statementFindAny.getExpr().struct.getKind()){
			report_error("GRESKA: Na liniji : " + statementFindAny.getLine() + 
					" Expr u zagradi mora biti istog tipa kao i elemnti niza", null);
			return;
		}
		
		// mozda treba ovom design da dodelimo nekako novu vrednost ili tako nesto
	}
	
	public void visit(StatementFindAndReplace statementFindAndReplace){
		//designovi moraju biti nizovi i njihovi elemnti moraju biti istog tipa
		if ((statementFindAndReplace.getDesignator1().obj.getType().getKind() != Struct.Array) ||
				(statementFindAndReplace.getDesignator().obj.getType().getKind() != Struct.Array) ||
				(statementFindAndReplace.getDesignator().obj.getType().getElemType().getKind() != 
				statementFindAndReplace.getDesignator1().obj.getType().getElemType().getKind())){
			report_error("GRESKA: Na liniji : " +  statementFindAndReplace.getLine() + 
					" Simboli moraju predstavljati nizove sa elementima istog tipa", null);
			return;
		}
		// ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi nizova
		Obj ident = Tab.find(statementFindAndReplace.getVarName());
		if (ident.getKind() != Obj.Var) {
			report_error("GRESKA: Na liniji : " + statementFindAndReplace.getLine() + 
					" Simbol nakon zareza u zagadi mora biti lokalna ili globalna promenljiva", null);
			return;
		}
		if (ident.getType() != statementFindAndReplace.getDesignator().obj.getType().getElemType()){
			report_error("GRESKA: Na liniji : " + statementFindAndReplace.getLine() +
					" Simbol nakon zareza u zagradi mora biti istog tipa kao i elementi nizova ", null);
		}
		// oba expr moraju biti istog tipa kao i elementi niza
		if ((statementFindAndReplace.getExpr().struct.getKind() != 
				statementFindAndReplace.getDesignator().obj.getType().getElemType().getKind()) ||
				(statementFindAndReplace.getExpr2().struct.getKind() !=
				statementFindAndReplace.getDesignator().obj.getType().getElemType().getKind())){
			report_error("GRESKA: Na liniji : " + statementFindAndReplace.getLine() + 
					" Oba Expr u zagradi moraju imati rezultat istog tipa kao sto su elemnti nizova", null);
			return;
		}
		
		// mozda neko dodeljivanje vrednosti nizu sa leve strane jednakosti
	}
	
	
	
	///----------ACT_PARS----------///

	public void visit(CallingFuncName callingFuncName){
		//pravimo novu listu za nove act parametre
		callingFuncName.obj = callingFuncName.getDesignator().obj;
		actParsLists.add(new ArrayList<Struct>());
	}
	
	//----------------------OVDE PROVERI DA NISU SLUCAJNO U SUPROTNOM REDOSLEDU ARGUMENTI-------------------//
	public void visit (ActPars actPars){
		//dodaj prvi parametar
		actParsLists.get(actParsLists.size() - 1).add(actPars.getExpr().struct);
	}
	
	public void visit(MultipleActParsList multipleActParsList){
		//dodajemo redom parametre
		actParsLists.get(actParsLists.size() - 1).add(multipleActParsList.getExpr().struct);
	}
	
	
	
	///----------CONDITION----------///
	
	public void visit(OneExpr oneExpr){
		if (oneExpr.getExpr().struct != boolType){
			report_error("GRESKA: Na liniji : " + oneExpr.getLine() + 
					" Uslov u izrazu kontrola toka mora biti tipa bool", null);
		}
	}
	
	public void visit(TwoExpr twoExpr){
		if (!twoExpr.getExpr().struct.compatibleWith(twoExpr.getExpr1().struct)){
			report_error("GRESKA: Na liniji : " + twoExpr.getLine() + 
					" Izrazi u uslovu kontrole toka moraju biti uporedivi", null);
		}
		if (twoExpr.getExpr().struct.getKind() == Struct.Array
				|| twoExpr.getExpr1().struct.getKind() == Struct.Array){
			if (!"==".equals(relOp) && !"!=".equals(relOp)){
				report_error("GRESKA: Na liniji : " + twoExpr.getLine() + 
						" Jedine operacije poredjenja za nizove su != i ==", null);
			}
		}
	}
	
	
	
	///----------EXPR----------///

	public void visit(ExprMinus exprMinus){
		if (exprMinus.getTerm().struct.getKind() != Struct.Int){
			report_error("GRESKA: Na liniji : " + exprMinus.getLine() + 
					" Nakon mirusa mora biti izraz tipa int", null);
			exprMinus.struct = Tab.noType;
			return;
		}
		exprMinus.struct = Tab.intType;
	}
	
	public void visit(NoExprMinus noExprMinus){
		/*if(!noExprMinus.getExpr().struct.compatibleWith(noExprMinus.getTerm().struct)){
			report_error("GRESKA: Na liniji : " + noExprMinus.getLine() + 
					"Sabirci moraju biti kompatibilni", null);
			noExprMinus.struct = Tab.noType;
			return
		}*/
		if(noExprMinus.getExpr().struct != Tab.intType || noExprMinus.getTerm().struct != Tab.intType){
			report_error("GRESKA: Na liniji : " + noExprMinus.getLine() + 
					" Svi sabirci moraju biti tipa int", null);
			noExprMinus.struct = Tab.noType;
			return;
		}
		noExprMinus.struct = Tab.intType;
	}
	
	public void visit(NoAddTerm noAddTerm){
		noAddTerm.struct = noAddTerm.getTerm().struct;
	}
	
	
	
	///----------TERM----------///

	public void visit(TermFactor termFactor){
		termFactor.struct = termFactor.getFactor().struct;
	}
	
	public void visit(MultipleTerm multipleTerm){
		if (multipleTerm.getFactor().struct != Tab.intType || multipleTerm.getTerm().struct != Tab.intType){
			report_error("GRESKA: Na liniji : " + multipleTerm.getLine() + 
					" Svaki cinilac mora biti tipa int", null);
			multipleTerm.struct = Tab.noType;
			return;
		}
		multipleTerm.struct = Tab.intType;
	}
	
	
	
	///----------FACTOR----------///

	public void visit(FactorFuncCall factorFuncCall) {
		if (factorFuncCall.getCallingFuncName().obj.getKind() != Obj.Meth){
			report_error("GRESKA: Na liniji " + factorFuncCall.getLine() + 
					": Ne postoji funkcija sa imenom " + 
					factorFuncCall.getCallingFuncName().getDesignator().getDesName().getName(), null);
			factorFuncCall.struct = Tab.noType;
			return;
		}
		
		List<Struct> lastActPars = actParsLists.remove(actParsLists.size() - 1);
		int actParsNumber = lastActPars.size();
		int formParsNumber = factorFuncCall.getCallingFuncName().obj.getLevel();
		if (actParsNumber != formParsNumber) {
			report_error("GRESKA: Na liniji : " + factorFuncCall.getLine() + 
					" Nije prosledjen odgovarajuci broj parametara ", null);
			factorFuncCall.struct = Tab.noType;
			return;
		}
		
		// popunicemo listu stvarnim parametrima funkcije
		List<Obj> formalPars = new ArrayList<Obj>();
		int i = 0;
		for (Iterator<Obj> formalParsIter = 
				factorFuncCall.getCallingFuncName().obj.getLocalSymbols().iterator();
				i < factorFuncCall.getCallingFuncName().obj.getLevel() && formalParsIter.hasNext();){
			formalPars.add(formalParsIter.next());
			i++;
		}
		
		//provera da li su parametri na istim mestima istog tipa
		for (int j = 0; j < formParsNumber; j++){
			if (!lastActPars.get(j).assignableTo(formalPars.get(j).getType())){
				report_error("GRESKA: Na liniji : " + factorFuncCall.getLine() + 
						"Parametri na poziciji " + j + " nisu istog tipa", null);
				factorFuncCall.struct = Tab.noType;
				return;
			}
		}
		
		factorFuncCall.struct = factorFuncCall.getCallingFuncName().obj.getType();
	}
	
	public void visit(FactorDesignator factorDesignator){
		factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
	}
	
	public void visit(FactorNumber factorNumber){
		factorNumber.struct = Tab.intType;
	}
	
	public void visit(FactorChar factorChar){
		factorChar.struct = Tab.charType;
	}
	
	public void visit(FactorBool factorBool){
		factorBool.struct = boolType;
	}
	
	public void visit(FactorArray factorArray){
		if (factorArray.getExpr().struct != Tab.intType){
			report_error("GRESKA: Na liniji : " + factorArray.getLine() + 
					" U uglastim zagradama mora biti izraz koji predstavlja broj elemenata "
					+ "i mora biti tipa int", null);
			factorArray.struct = Tab.noType;
			return;
		}
		
		factorArray.struct = new Struct(Struct.Array, factorArray.getType().struct);
	}
	
	public void visit(FactorExpr factorExpr){
		factorExpr.struct = factorExpr.getExpr().struct;
	}
	
	
	
	///----------OPERATIONS----------///

	public void visit(Assignop assignop){
		relOp = "=";
	}
	
	public void visit(RelopEqual relopEqual){
		relOp = "==";
	}
	
	public void visit(RelopNotEqual relopNotEqual){
		relOp = "!=";
	}
	
	public void visit(RelopGreater relopGreater){
		relOp = ">";
	}
	
	public void visit(RelopGreaterEqual relopGreaterEqual){
		relOp = ">=";
	}
	
	public void visit(RelopLess relopLess){
		relOp = "<";
	}
	
	public void visit(RelopLessEqual relopLessEqual){
		relOp = "<=";
	}
	
	public void visit(AddopPlus addopPlus){
		relOp = "+";
	}
	
	public void visit(AddopMinus addopMinus){
		relOp = "-";
	}
	
	public void visit(MulopMul MulopMul){
		relOp = "*";
	}
	
	public void visit(MulopDiv MulopDiv){
		relOp = "/";
	}
	
	public void visit(MulopMod MulopMod){
		relOp = "%";
	}
	
	public boolean passed() {
		return !errorDetected;
	}
		
}
