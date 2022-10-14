package bammouOualidAnalyse;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class StatementVisitor extends ASTVisitor {
	
	List<Statement> lignes = new ArrayList<Statement>();
	int nombreLignes;
	int countNombreLignes;
	
	public boolean visit(MethodDeclaration node) {
		countNombreLignes = 0;
		return true;
	}
	
	public List<Statement> getLignes() {
		return lignes;
	}
	
	public int getNombreLignes() {
		return countNombreLignes;
	}


	public boolean visit (AssertStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	public boolean visit (Block node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	public boolean visit (BreakStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	public boolean visit (ConstructorInvocation node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	public boolean visit (ContinueStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	public boolean visit (DoStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (EmptyStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (EnhancedForStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (ExpressionStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (ForStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (IfStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (LabeledStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (ReturnStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (SwitchCase node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (SwitchStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (SynchronizedStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (ThrowStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (TryStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (TypeDeclarationStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (VariableDeclarationStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
	public boolean visit (WhileStatement node) {
		countNombreLignes++;
		lignes.add(node);
		return true;
	}
	
}
