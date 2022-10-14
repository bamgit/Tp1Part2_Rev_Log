package bammouOualidAnalyse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Parser {
	
	public static final String projectPath = "C:\\Users\\hp 2015\\waliiid\\Work\\ico\\JavaLam\\Lamyae";
	public static final String projectSourcePath = projectPath + "\\src";
	public static final String jrePath = "C:\\Program Files (x86)\\Java\\jre1.8.0_321\\lib\\rt.jar";
	
	public static int nombreDeClasses = 0;
	public static int nombreDeLignesApp = 0;
	public static int nombreDeMethodes = 0;
	public static int nombreDePackages = 0;
	public static int nombreDAttributs = 0;
	// pour stocker le nombre de methodes pour chaque classe :
	public static Map<TypeDeclaration, Integer> mapClasseNombreMethodes = new HashMap<TypeDeclaration, Integer>();
	// Les top 10% de la map precedante :
	public static List<TypeDeclaration> listeTop10ClasseNombreMethodes;
	// pour stocker le nombre d'attributs pour chaque classe :
	public static Map<TypeDeclaration, Integer> mapClasseNombreAttributs = new HashMap<TypeDeclaration, Integer>();
	// Les top 10% de la map precedante :
	public static List<TypeDeclaration> listeTop10ClasseNombreAttributs;
	// 
	public static List<TypeDeclaration> listeClassesDansLesDeux;
	// 
	public static List<TypeDeclaration> listeClassesPlusXMethodes = new ArrayList<TypeDeclaration>();
	public static int xMet = 6; // 6 est juste un ex, il serait mieux qu'on recoive la valeur de la part de l'utilisateur
	// pour stocker le nombre de parametres pour chaque methodes :
	public static Map<MethodDeclaration, Integer> mapMethodeNombreParams = new HashMap<MethodDeclaration, Integer>();
	public static List<MethodDeclaration> listeMethodesTop1MaxParams = new ArrayList<MethodDeclaration>();
	// pour stocker le nombre de lignes de code pour chaque méthode :
	public static Map<MethodDeclaration, Integer> mapMethodeNombreLignesCode = new HashMap<MethodDeclaration, Integer>();
	// Les top 10% de la map precedante :
	public static List<MethodDeclaration> listeTop10MethodeNombreLignesCode;
		
		
	public static void main(String[] args) throws IOException {

		// Lire les fichier JAVA :
		final File dossier = new File(projectSourcePath);
		ArrayList<File> fichiersJava = listJavaFilesForFolder(dossier);

		// Pour chaque fichier trouvé :
		for (File fichier : fichiersJava) {
			String contenu = FileUtils.readFileToString(fichier);

			CompilationUnit analyse = parse(contenu.toCharArray());
			
			// Informations ==> classes ..
			traiterClassesInfos(analyse);
			
			// Informations ==> methodes ..
			traiterMethodesInfos(analyse);
			
			// Informations ==> packages ..
			traiterPackagesInfos(analyse);
			
			// Informations ==> variables ..
			traiterVariablesInfos(analyse);
			
			// Informations ==> lignes de code dans les méthodes ..
			traiterLignesCodeDansMethodesInfos(analyse);
			
			// Informations ==> lignes de code dans toute l'app ..
			traiterLignesCodeAppInfos(analyse);
						
			//print method invocations
			//printMethodInvocationInfo(parse);
			

		}
		
		// 1
		System.out.println("Question 1 : \n"
				+ "Nombre total de classes : " + nombreDeClasses
				+ "\n---------------");
		
		// 2
		System.out.println("Question 2 : \n"
				+ "Nombre total de lignes de code : " + nombreDeLignesApp
				+ "\n---------------");
		
		// 3
		System.out.println("Question 3 : \n"
				+ "Nombre total de methodes : " + nombreDeMethodes
				+ "\n---------------");
		
		// 4
		System.out.println("Question 4 : \n"
				+ "Nombre total de packages : " + nombreDePackages
				+ "\n---------------");
		
		// 5
		System.out.println("Question 5 : \n"
				+ "Nombre moyen de méthodes par classe : " + (float)nombreDeMethodes / (float)nombreDeClasses
				+ "\n---------------");
		
		// 6
		System.out.println("Question 6 : \n"
				+ "Nombre moyen de de lignes de code par méthode : " + (float)nombreDeLignesApp / (float)nombreDeMethodes
				+ "\n---------------");
		
		// 7
		System.out.println("Question 7 : \n"
				+ "a- Nombre total d'attributs : " + nombreDAttributs + "\n"
				+ "b- Nombre moyen d'attributs par classe : " + (float)nombreDAttributs / (float)nombreDeClasses
				+ "\n---------------");
		
		// 8
		System.out.println("Question 8 : \n"
				+ "Les 10% des classes avec le plus grand nombre de méthodes :");
		for (TypeDeclaration classe : listeTop10ClasseNombreMethodes) {
		    System.out.println("=> " + classe.getName());
		}
		System.out.println("---------------");
		
		// 9
		System.out.println("Question 9 : \n"
				+ "Les 10% des classes avec le plus grand nombre d'attributs :");
		for (TypeDeclaration classe : listeTop10ClasseNombreAttributs) {
		    System.out.println("=> " + classe.getName());
		}
		System.out.println("---------------");
		
		// 10
		System.out.println("Question 10 : \n"
				+ "Les classes qui font partie en mm temps des deux cat prec :");
		for (TypeDeclaration classe : listeClassesDansLesDeux) {
		    System.out.println("=> " + classe.getName());
		}
		if (listeClassesDansLesDeux.size() == 0) {
			System.out.println("=> Aucune classe trouvée!");
		}
		System.out.println("---------------");
		
		// 11
		System.out.println("Question 11 : \n"
				+ "Les classes qui ont plus de X (x = " + xMet + ") Methodes :");
		for (TypeDeclaration classe : listeClassesPlusXMethodes) {
		    System.out.println("=> " + classe.getName());
		}
		if (listeClassesPlusXMethodes.size() == 0) {
			System.out.println("=> Aucune classe trouvée!");
		}
		System.out.println("---------------");
		
		// 12
		System.out.println("Question 9 : \n"
				+ "Les 10% des méthodes avec le plus grand nombre de lignes de code :");
		for (MethodDeclaration classe : listeTop10MethodeNombreLignesCode) {
		    System.out.println("=> " + classe.getName() + "()");
		}
		System.out.println("---------------");
		
		// 13
		System.out.println("Question 13 : \n"
				+ "Le nombre maximal de parametres par rapport à toutes les methodes :");
		for (MethodDeclaration methode : listeMethodesTop1MaxParams) {
		    System.out.println("=> " + methode.getName() + "() - Nombre de params : " + methode.parameters().size());
		}
		
		
		
	}

	// Lire tous les fichier JAVA dans un dossier
	public static ArrayList<File> listJavaFilesForFolder(final File dossier) {
		ArrayList<File> fichiersJava = new ArrayList<File>();
		if (dossier.listFiles() != null) {
			for (File fileEntry : dossier.listFiles()) {
				if (fileEntry.isDirectory()) {
					fichiersJava.addAll(listJavaFilesForFolder(fileEntry));
				} else if (fileEntry.getName().contains(".java")) {
					// System.out.println(fileEntry.getName());
					fichiersJava.add(fileEntry);
				}
			}
		}

		return fichiersJava;
	}

	// creer l'AST
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}
	
	// Informations ==> classes ..
	public static void traiterClassesInfos(CompilationUnit parse) {
		ClassDeclarationVisitor visiteur = new ClassDeclarationVisitor();
		parse.accept(visiteur);
		
		int nombreMethodesDansClasse = 0;
		for (TypeDeclaration classs : visiteur.getClasses()) {
			
			MethodDeclarationVisitor visiteurMeth = new MethodDeclarationVisitor();
			classs.accept(visiteurMeth);
			nombreMethodesDansClasse = visiteurMeth.nombreMethodes();
			// mettre la classe et son nombre de methodes dans mapClasseNombreMethodes :
			mapClasseNombreMethodes.put(classs, nombreMethodesDansClasse);
			
			// Ordonner la liste "DESC" :
			Map<TypeDeclaration, Integer> listSorted = mapClasseNombreMethodes
					.entrySet()
			        .stream()
			        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
					.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
							(entry1, entry2) -> entry2, LinkedHashMap::new));
			
			// Prendre les top 10%
			int prcntg10 = nombreDeClasses / 10;
			if (prcntg10 < 1) {
				prcntg10 = 1;
			}
			listeTop10ClasseNombreMethodes = new ArrayList<TypeDeclaration>(listSorted.keySet())
					.subList(0, prcntg10);
			
			// Les classes avec plus de X methodes :
			
			Iterator<Map.Entry<TypeDeclaration, Integer>> iterateur = mapClasseNombreMethodes.entrySet().iterator();
			while (iterateur.hasNext()) {
			    Map.Entry<TypeDeclaration, Integer> entry = iterateur.next();
			    if(entry.getValue() > xMet) { // Si le nombre de methodes de la class > x, on rajoute le meth..
			    	listeClassesPlusXMethodes.add(entry.getKey());
			    	listeClassesPlusXMethodes = new ArrayList<TypeDeclaration>(new LinkedHashSet<TypeDeclaration>(listeClassesPlusXMethodes));
			    }
			}
		}
		
		// Nombre total des classes = l'ancienne valeur + nombre de classes trouvé dans le fichier!
		nombreDeClasses += visiteur.nombreClasses();

	}

	// Informations ==> methodes ..
	public static void traiterMethodesInfos(CompilationUnit parse) {
		MethodDeclarationVisitor visiteur = new MethodDeclarationVisitor();
		parse.accept(visiteur);
		
		for (MethodDeclaration method : visiteur.getMethods()) {
			/*System.out.println("Nom de la methode : " + method.getName()
					+ " Type de retour : " + method.getReturnType2());*/
			
			mapMethodeNombreParams.put(method, method.parameters().size());
		}
		// Ordonner la liste "DESC" :
		Map<MethodDeclaration, Integer> listSorted = mapMethodeNombreParams
				.entrySet()
		        .stream()
		        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
						(entry1, entry2) -> entry2, LinkedHashMap::new));
		
		// Prendre les top 10%
		int prcntg10 = nombreDeClasses / 10;
		if (prcntg10 < 1) {
			prcntg10 = 1;
		}
		listeMethodesTop1MaxParams = new ArrayList<MethodDeclaration>(listSorted.keySet())
				.subList(0, 1);
					
		// // Nombre total des methodes = l'ancienne valeur + nombre de methodes trouvé dans le fichier!
		nombreDeMethodes += visiteur.nombreMethodes();

	}
	
	// Informations ==> packages ..
	public static void traiterPackagesInfos(CompilationUnit parse) {
		PackageDeclarationVisitor visiteur = new PackageDeclarationVisitor();
		parse.accept(visiteur);
		
		/*for (MethodDeclaration method : visiteur.getMethods()) {
			System.out.println("Nom de la methode : " + method.getName()
					+ " Type de retour : " + method.getReturnType2());
		}*/
		
		// Nombre total des packages = l'ancienne valeur + nombre de packages trouvé!
		nombreDePackages += visiteur.nombrePackages();

	}

	// Informations ==> variables/attributs ..
	public static void traiterVariablesInfos(CompilationUnit parse) {

		ClassDeclarationVisitor visiteur1 = new ClassDeclarationVisitor();
		parse.accept(visiteur1);
		for (TypeDeclaration classs : visiteur1.getClasses()) {

			FieldDeclarationVisitor visiteur2 = new FieldDeclarationVisitor();
			classs.accept(visiteur2);
			
			int nombreAttributsDansClasse = 0;
			for (FieldDeclaration field : visiteur2.getFields()) {
				
				VariableDeclarationFragmentVisitor visiteur3 = new VariableDeclarationFragmentVisitor();
				field.accept(visiteur3);
				
				for (VariableDeclarationFragment variableDeclarationFragment : visiteur3.getVariables()) {
					
					/*for (VariableDeclarationFragment variableDeclarationFragment : visiteur2
					.getVariables()) {
						System.out.println("variable name: "
								+ variableDeclarationFragment.getName()
								+ " variable Initializer: "
								+ variableDeclarationFragment.getInitializer());
					}*/
				}
				
				nombreAttributsDansClasse = visiteur3.nombreVariables();
				// mettre la classe et son nombre d'attributs dans mapClasseNombreAttributs :
				mapClasseNombreAttributs.put(classs, nombreAttributsDansClasse);
				
				// Ordonner la liste "DESC" :
				Map<TypeDeclaration, Integer> listSorted = mapClasseNombreAttributs
						.entrySet()
				        .stream()
				        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
						.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
								(entry1, entry2) -> entry2, LinkedHashMap::new));
				
				// Prendre les top 10%
				int prcntg10 = nombreDeClasses / 10;
				if (prcntg10 < 1) {
					prcntg10 = 1;
				}
				listeTop10ClasseNombreAttributs = new ArrayList<TypeDeclaration>(listSorted.keySet())
						.subList(0, prcntg10);
				
				// Nombre total d'attributs = l'ancienne valeur + nombre d'attributs/variabless trouvé!
				nombreDAttributs += visiteur3.nombreVariables();
				
				//
				listeClassesDansLesDeux = new ArrayList<TypeDeclaration>(listeTop10ClasseNombreAttributs);
				listeClassesDansLesDeux.retainAll(listeTop10ClasseNombreMethodes);
				
			}

		}
	}
	
	// Informations ==> Lignes de code dans les methodes ..
	public static void traiterLignesCodeDansMethodesInfos(CompilationUnit parse) {
		MethodDeclarationVisitor visiteur1 = new MethodDeclarationVisitor();
		parse.accept(visiteur1);
		
		int nombreLignesDansMethode = 0;
		for (MethodDeclaration methode : visiteur1.getMethods()) {
			StatementVisitor visiteur2 = new StatementVisitor();
			methode.accept(visiteur2);
			
			nombreLignesDansMethode = visiteur2.getNombreLignes();
			// mettre la methode et son nombre de lignes de code dans mapMethodeNombreLignesCode :
			mapMethodeNombreLignesCode.put(methode, nombreLignesDansMethode);
			
			// Ordonner la liste "DESC" :
			Map<MethodDeclaration, Integer> listSorted = mapMethodeNombreLignesCode
					.entrySet()
			        .stream()
			        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
					.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
							(entry1, entry2) -> entry2, LinkedHashMap::new));
			
			// Prendre les top 10%
			int prcntg10 = nombreDeMethodes / 10;
			if (prcntg10 < 1) {
				prcntg10 = 1;
			}
			listeTop10MethodeNombreLignesCode = new ArrayList<MethodDeclaration>(listSorted.keySet())
					.subList(0, prcntg10);
		}

	}
	
	// Informations ==> Lignes de code dans toute l'app ..
	public static void traiterLignesCodeAppInfos(CompilationUnit parse) {

		StatementAppVisitor visiteur = new StatementAppVisitor();
		parse.accept(visiteur);
		
		nombreDeLignesApp += visiteur.getNombreLignes();

	}
	
	// navigate method invocations inside method
		public static void printMethodInvocationInfo(CompilationUnit parse) {

			MethodDeclarationVisitor visiteur1 = new MethodDeclarationVisitor();
			parse.accept(visiteur1);
			for (MethodDeclaration method : visiteur1.getMethods()) {

				MethodInvocationVisitor visiteur2 = new MethodInvocationVisitor();
				method.accept(visiteur2);

				for (MethodInvocation methodInvocation : visiteur2.getMethods()) {
					System.out.println("method " + method.getName() + " invoc method "
							+ methodInvocation.getName());
				}

			}
		}

}
