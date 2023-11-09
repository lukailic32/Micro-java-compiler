package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.SemanticAnalyzer;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPC;
	private Stack<Character> operator = new Stack<Character>();
	private Stack<Integer> brojLokalnihPromenljivihFunkcije = new Stack<Integer>();

	private Stack<List<Integer>> adreseZaSkaknjeNakonANDConditiona = new Stack<List<Integer>>();
	private Stack<List<Integer>> adreseZaSkaknjeNakonORConditiona = new Stack<List<Integer>>();
	private Stack<Integer> adreseZaSkaknjeNakonTHENBloka = new Stack<Integer>();

	private Stack<Integer> adreseZaSkakanjeNaPocetakWhile = new Stack<Integer>();
	private Stack<List<Integer>> adreseZaPopunjavanjeBREAK = new Stack<List<Integer>>();
	private Stack<Integer> adreseZaPopunjavanjeCONTINUE = new Stack<Integer>();
	
	private Stack<Integer> adresaZaSkakanjeNaPocetakForeach = new Stack<Integer>();

	public int getMainPC() {
		return mainPC;
	}

	// /----------STATEMENT----------///

	public void visit(NonEmptyNumConstOpt nonEmptyNumConstOpt) {
		Code.loadConst(nonEmptyNumConstOpt.getValue());
	}

	public void visit(StatementPrint statementPrint) {
		if (statementPrint.getNumConstOpt().struct == Tab.intType) {
			// ima vec stavljenu sirinu na stek
			if (statementPrint.getExpr().struct == Tab.intType
					|| statementPrint.getExpr().struct == SemanticAnalyzer.boolType) {
				Code.put(Code.print);
			} else
				Code.put(Code.bprint);
		} else {
			if (statementPrint.getExpr().struct == Tab.intType
					|| statementPrint.getExpr().struct == SemanticAnalyzer.boolType) {
				Code.loadConst(5);
				Code.put(Code.print);
			} else {
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
		}
	}

	public void visit(StatementRead statementRead) {
		if (!(statementRead.getDesignator().obj.getType() != Tab.intType && statementRead
				.getDesignator().obj.getType() != SemanticAnalyzer.boolType)) {
			Code.put(Code.read);
			Code.store(statementRead.getDesignator().obj);
		} else {
			Code.put(Code.bread);
			Code.store(statementRead.getDesignator().obj);
		}
	}

	public void visit(StatementReturn statementReturn) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(StatementReturnExpr statementReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(StatementFindAny statementFindAny) {
		Obj cnt = Tab.insert(Obj.Var, "cnt", Tab.intType);
		// brojLokalnihPromenljivihFunkcije.push(brojLokalnihPromenljivihFunkcije.pop()
		// + 1);
		cnt.setAdr(brojLokalnihPromenljivihFunkcije.peek() + 1);
		Code.loadConst(0);
		Code.store(cnt);
		cnt.setLevel(1);
		Code.loadConst(0);
		Code.store(statementFindAny.getDesignator().obj); // u levi des smestimo
															// false
		int adr1 = Code.pc;
		Code.load(statementFindAny.getDesignator1().obj);
		Code.put(Code.arraylength); // Procitamo duzinu tog niza na stek
		Code.load(cnt); // Stavimo counter na stek
		Code.putFalseJump(Code.ne, 0); // napusta se petlja, ispitali smo sve
										// elemente niza
										// treba da skinemo expr sa steka
		int adr2 = Code.pc - 2; // adresa za pecovanje

		Code.put(Code.dup); // Dupliramo Expr na steku
		Code.load(statementFindAny.getDesignator1().obj); // Stavimo adresu niza
															// na stek &array
		Code.load(cnt); // stavljamo indeks cnt
		if (statementFindAny.getDesignator1().obj.getType().getElemType()
				.getKind() == Struct.Char)
			Code.put(Code.baload);
		else
			Code.put(Code.aload); // imamo na steku exp i array[cnt]

		Code.load(cnt); // ovde
		Code.loadConst(1); // smo
		Code.put(Code.add); // inc
		Code.store(cnt); // brojac

		Code.putFalseJump(Code.eq, adr1);
		Code.loadConst(1);
		Code.store(statementFindAny.getDesignator().obj); // u levi des smestimo
															// true;
		Code.fixup(adr2);
		Code.put(Code.pop);

		// NASTAVI KAD SHVATIS
	}

	// /----------FACTOR----------///

	public void visit(NoExprMinus noExprMinus) {
		Character c = operator.pop();
		switch (c) {
		case '+':
			Code.put(Code.add);
			break;
		case '-':
			Code.put(Code.sub);
			break;
		default:
			break;
		}
	}

	public void visit(MultipleTerm multipleTerm) {
		Character c = operator.pop();
		switch (c) {
		case '*':
			Code.put(Code.mul);
			break;
		case '/':
			Code.put(Code.div);
			break;
		case '%':
			Code.put(Code.rem);
			break;
		default:
			break;
		}
	}

	// /----------FACTOR----------///

	public void visit(FactorNumber factorNumber) {
		Obj num = Tab.insert(Obj.Con, "$", factorNumber.struct);
		num.setLevel(0);
		num.setAdr(factorNumber.getN());

		Code.load(num);
	}

	public void visit(FactorChar factorChar) {
		Obj ch = Tab.insert(Obj.Con, "$", factorChar.struct);
		ch.setLevel(0);
		ch.setAdr(factorChar.getC());

		Code.load(ch);
	}

	public void visit(FactorBool factorBool) {
		Obj bl = Tab.insert(Obj.Con, "$", factorBool.struct);
		bl.setLevel(0);
		if (factorBool.getB() == true)
			bl.setAdr(1);
		else
			bl.setAdr(0);

		Code.load(bl);
	}

	public void visit(FactorArray factorArray) {
		// Skine &array address sa ExpStack jer je suvisan za ovu smenu
		Code.put(Code.newarray);
		if (factorArray.getExpr().struct == Tab.intType
				|| factorArray.getExpr().struct == SemanticAnalyzer.boolType) {
			Code.put(1);
		} else
			Code.put(0);
	}

	public void visit(FactorFuncCall factorFuncCall) {
		// MOZDA Obj func =
		// factorFuncCall.getCallingFuncName().getDesignator().obj;
		Obj func = factorFuncCall.getCallingFuncName().obj;

		if (func.getName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		if (func.getName().equals("ord")) {
			return;
		}
		if (func.getName().equals("chr")) {
			return;
		}

		int offset = func.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset); // pc = pc + offset
	}

	public void visit(FactorDesignator factorDesignator) {
		Code.load(factorDesignator.getDesignator().obj);
	}

	// /----------DESIGNATOR_STATEMENT----------///

	public void visit(DesignatorFuncCall designatorFuncCall) {
		Obj func = designatorFuncCall.getCallingFuncName().obj;

		if ("len".equals(designatorFuncCall.getCallingFuncName()
				.getDesignator().getDesName().getName())) {
			Code.put(Code.arraylength);
			Code.put(Code.pop);
			return;
		}

		int offset = func.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (func.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	public void visit(DesignatorStatementInc designatorStatementInc) {
		Obj obj = designatorStatementInc.getDesignator().obj;
		if (obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(obj);
	}

	public void visit(DesignatorStatementDec designatorStatementDec) {
		Obj obj = designatorStatementDec.getDesignator().obj;
		if (obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(obj);
	}

	public void visit(Designator designator) {
		// Ako je u pitanju niz, a nema [ ] onda nema potrebe da na steku stoji
		// &array address
		if (designator.obj.getType().getKind() == Struct.Array
				&& designator.getExprOpt().struct == Tab.noType) {
			Code.put(Code.pop);
		}
		/*
		 * if (designator.getExprOpt().struct != Tab.noType){ // ovo bi trebalo
		 * da stavlja &array Code.load(designator.obj); }
		 */// Ako je u pitanju obicna promenljiva ne treba raditi nista
	}

	public void visit(DesName desName) {
		// UPITNO
		if (desName.obj.getType().getKind() == Struct.Array) {
			// &array address
			Code.load(desName.obj);
		}
	}

	public void visit(
			DesignatorStatementDesignator designatorStatementDesignator) {
		Code.store(designatorStatementDesignator.getDesignator().obj);
	}

	// /----------METH_DECL----------///

	public void visit(VoidMethodType voidMethodType) {
		if ("main".equals(voidMethodType.getMethodName())) {
			mainPC = Code.pc;
		}
		voidMethodType.obj.setAdr(Code.pc);
		int formalArgs = voidMethodType.obj.getLevel();
		int localSyms = voidMethodType.obj.getLocalSymbols().size();
		brojLokalnihPromenljivihFunkcije.push(localSyms - formalArgs);
		// Generate the entry
		Code.put(Code.enter);
		Code.put(formalArgs);
		Code.put(localSyms);
	}

	public void visit(OtherMethodType otherMethodType) {
		otherMethodType.obj.setAdr(Code.pc);
		int formalArgs = otherMethodType.obj.getLevel();
		int localSyms = otherMethodType.obj.getLocalSymbols().size();
		brojLokalnihPromenljivihFunkcije.push(localSyms - formalArgs);
		// Generate the entry
		Code.put(Code.enter);
		Code.put(formalArgs);
		Code.put(localSyms);
	}

	public void visit(MethodDecl MethodDecl) {
		// if(correctMethodDecl.getMethodTypeName().obj.getFpPos() == -1) {
		// fpPos is set to -1 when non-void meth doesnt have return
		// Code.put(Code.trap);
		// Code.put(1);
		brojLokalnihPromenljivihFunkcije.pop();
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// /----------OPERATIONS----------///

	public void visit(AddopPlus addopPlus) {
		operator.push('+');
	}

	public void visit(AddopMinus addopMinus) {
		operator.push('-');
	}

	public void visit(MulopMul mulopMul) {
		operator.push('*');
	}

	public void visit(MulopDiv mulopDiv) {
		operator.push('/');
	}

	public void visit(MulopMod mulopMod) {
		operator.push('%');
	}

	public void visit(ExprMinus exprMinus) {
		Code.put(Code.neg);
	}

	public void visit(RelopEqual relopEqual) {
		operator.push('=');
	}

	public void visit(RelopNotEqual RelopNotEqual) {
		operator.push('!');
	}

	public void visit(RelopGreater RelopGreater) {
		operator.push('>');
	}

	public void visit(RelopGreaterEqual RelopGreaterEqual) {
		operator.push('g');
	}

	public void visit(RelopLess relopLess) {
		operator.push('<');
	}

	public void visit(RelopLessEqual relopLessEqual) {
		operator.push('l');
	}

	// /----------COND----------///

	public void visit(OneExpr oneExpr) {
		// Na steku se vec nalazi bool vrednost Expr (
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		// na ovu adresu cemo upisati adresu sledeceg OR Conditiona (jer se OR
		// sastoji iz vise AND-ova)
		adreseZaSkaknjeNakonANDConditiona.peek().add(Code.pc - 2);
	}

	public void visit(TwoExpr twoExpr) {
		switch (operator.pop()) {
		case '=':
			Code.putFalseJump(Code.eq, 0);
			break;
		case '!':
			Code.putFalseJump(Code.ne, 0);
			break;
		case '>':
			Code.putFalseJump(Code.gt, 0);
			break;
		case 'g':
			Code.putFalseJump(Code.ge, 0);
			break;
		case '<':
			Code.putFalseJump(Code.lt, 0);
			break;
		case 'l':
			Code.putFalseJump(Code.le, 0);
			break;
		default:
			break;
		}
		adreseZaSkaknjeNakonANDConditiona.peek().add(Code.pc - 2);
	}

	public void visit(Ornont ornont) {
		// Ako dodjemo do ove instrukcije znaci da u AND Conditionima imamo true
		// i zato je nismo preskocili
		// Ne znamo jos na koju tacno adresu skacemo (gde pocinje THEN)
		Code.putJump(0);
		adreseZaSkaknjeNakonORConditiona.peek().add(Code.pc - 2);

		// Ako je u AND-u vraceno FALSE skok treba da ide to trenutne
		// instrukcije (da preskoci ovo skakanje na than granu)
		List<Integer> l = adreseZaSkaknjeNakonANDConditiona.peek();
		for (int i = 0; i < l.size(); i++) {
			Code.fixup(l.get(i));
		}
		adreseZaSkaknjeNakonANDConditiona.peek().clear();
	}

	public void visit(IfNonT ifNonT) {
		adreseZaSkaknjeNakonANDConditiona.push(new ArrayList<Integer>());
		adreseZaSkaknjeNakonORConditiona.push(new ArrayList<Integer>());
		// adreseZaSkaknjeNakonTHENBloka.push(new ArrayList<Integer>());
	}

	public void visit(Condition condition) {
		// Ova metoda se izvrsava kad se zavrse svi conditioni
		List<Integer> l = adreseZaSkaknjeNakonORConditiona.peek();
		for (int i = 0; i < l.size(); i++) {
			Code.fixup(l.get(i));
		}
		adreseZaSkaknjeNakonORConditiona.peek().clear();
	}

	public void visit(ElseNonT elseNonT) {
		// Ovo je nakon then bloka kad imamo else
		Code.putJump(0);
		adreseZaSkaknjeNakonTHENBloka.push(Code.pc - 2);

		List<Integer> l = adreseZaSkaknjeNakonANDConditiona.peek();
		for (int i = 0; i < l.size(); i++) {
			Code.fixup(l.get(i));
		}
		adreseZaSkaknjeNakonANDConditiona.peek().clear();
	}

	public void visit(NoStatementElse noStatementElse) {
		// Pecovanje AND Conditiona u poslednjem OR Conditionu
		List<Integer> l = adreseZaSkaknjeNakonANDConditiona.peek();
		for (int i = 0; i < l.size(); i++) {
			Code.fixup(l.get(i));
		}
		adreseZaSkaknjeNakonANDConditiona.peek().clear();
	}

	public void visit(StatementIf statementIf) {
		adreseZaSkaknjeNakonANDConditiona.pop();
		adreseZaSkaknjeNakonORConditiona.pop();
	}

	public void visit(StatementElse statementElse) {
		Code.fixup(adreseZaSkaknjeNakonTHENBloka.peek());
		adreseZaSkaknjeNakonTHENBloka.pop();
	}

	public void visit(WhileNonT whileNonT) {
		adreseZaSkakanjeNaPocetakWhile.push(Code.pc);

		adreseZaSkaknjeNakonANDConditiona.push(new ArrayList<Integer>());
		adreseZaSkaknjeNakonORConditiona.push(new ArrayList<Integer>());

		adreseZaPopunjavanjeBREAK.push(new ArrayList<Integer>());
		adreseZaPopunjavanjeCONTINUE.push(Code.pc);
	}

	public void visit(StatementWhile statementWhile) {
		Code.putJump(adreseZaSkakanjeNaPocetakWhile.pop());
		// Pecovanje AND Conditiona u poslednjem OR Conditionu
		List<Integer> l = adreseZaSkaknjeNakonANDConditiona.peek();
		for (int i = 0; i < l.size(); i++) {
			Code.fixup(l.get(i));
		}
		adreseZaSkaknjeNakonANDConditiona.peek().clear();
		
		l = adreseZaPopunjavanjeBREAK.peek();
		for (int i = 0; i < l.size(); i++) {
			Code.fixup(l.get(i));
		}
		adreseZaPopunjavanjeBREAK.peek().clear();
		//PROVERI
		adreseZaPopunjavanjeCONTINUE.pop();
	}

	public void visit(StatementContinue statementContinue) {
		// Skakanje u nazad
		Code.putJump(adreseZaPopunjavanjeCONTINUE.peek());
	}

	public void visit(StatementBreak statementBreak){
		Code.putJump(0);
		adreseZaPopunjavanjeBREAK.peek().add(Code.pc - 2);
	}
	
	public void visit(ForeachDes foreachDes){
		Code.load(foreachDes.getDesignator().obj); //&array
		Code.load(foreachDes.getDesignator().obj); //&array &array

		//podeseno sve za ucitavanje prvog elem niza
	}
	
	public void visit(ForeachStart foreachStart) {
		Obj cnt = Tab.insert(Obj.Var, "cnt", Tab.intType);
		// brojLokalnihPromenljivihFunkcije.push(brojLokalnihPromenljivihFunkcije.pop()
		// + 1);
		cnt.setAdr(brojLokalnihPromenljivihFunkcije.peek() + 1);
		Code.loadConst(0);
		Code.store(cnt);
		cnt.setLevel(1);
		Obj obj = Tab.find(foreachStart.getVarName());
		adresaZaSkakanjeNaPocetakForeach.push(Code.pc);
		adreseZaPopunjavanjeBREAK.push(new ArrayList<Integer>());
		adreseZaPopunjavanjeCONTINUE.push(Code.pc);
		Code.put(Code.aload); // &array array[i]
		Code.store(obj);	//&array
	}
	
	public void visit(ForeachEnd foreachEnd){
		//&array
		
	}
	
	
	
	
	
	
	
	
	
}
