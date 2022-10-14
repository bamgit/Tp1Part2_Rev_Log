package bammouOualidAnalyse;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class StatementAppVisitor extends ASTVisitor {
	
	int nombreLignes;
	int countNombreLignes;
	
	public boolean visit(PackageDeclaration node) {
		countNombreLignes = 1;
		return true;
	}
	
	public int getNombreLignes() {
		return countNombreLignes;
	}
	
	
	public boolean visit (TypeDeclaration node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (FieldDeclaration node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (Block node) {
		countNombreLignes++;
		return true;
	}
	public boolean visit (BreakStatement node) {
		countNombreLignes++;
		return true;
	}
	public boolean visit (ConstructorInvocation node) {
		countNombreLignes++;
		return true;
	}
	public boolean visit (ContinueStatement node) {
		countNombreLignes++;
		return true;
	}
	public boolean visit (DoStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (EmptyStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (EnhancedForStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (ExpressionStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (ForStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (IfStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (LabeledStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (ReturnStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (SwitchCase node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (SwitchStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (SynchronizedStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (ThrowStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (TryStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (TypeDeclarationStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (VariableDeclarationStatement node) {
		countNombreLignes++;
		return true;
	}
	
	public boolean visit (WhileStatement node) {
		countNombreLignes++;
		return true;
	}
	
}
