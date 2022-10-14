package bammouOualidAnalyse;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class PackageDeclarationVisitor extends ASTVisitor {
	
	List<PackageDeclaration> packages = new ArrayList<PackageDeclaration>();
	
	public boolean visit(PackageDeclaration node) {
		packages.add(node);
		return super.visit(node);
	}
	
	public List<PackageDeclaration> getClasses() {
		return packages;
	}
	
	public int nombrePackages() {
		return packages.size();
	}
	
}
