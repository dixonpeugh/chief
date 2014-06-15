/*
   Copyright 2014 David Dixon-Peugh

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package net.ddp.chief.lang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import net.ddp.chief.know.ont.OntModelFactory;
import net.ddp.chief.know.ont.OntologyHelper;
import net.ddp.chief.know.ont.OntologyProperty;
import net.ddp.chief.know.ont.scro.DataProperties;
import net.ddp.chief.know.ont.scro.Individuals;
import net.ddp.chief.know.ont.scro.ObjectProperties;
import net.ddp.chief.know.ont.scro.SCROClasses;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

/**
 * This class will parse a Java file, and generate the structural knowledge.  This gets placed into the
 * model passed in.
 * 
 * @author David Dixon-Peugh
 *
 */
public class StructureProcessor extends TreePathScanner<Object, Trees> {
	/**
	 * This integer will increment every time we define an anonymous class.  Our anonymous classes
	 * are of the format $$3$$, instead of the Java format - just because.
	 */
	private static int theAnonymousClassCounter = 0;
	
	/**
	 * This map will contain a map of short names to fully qualified names.  These
	 * have come in through the import statement.  (If the import is a * it doesn't work just yet.)
	 */
	private Map<String, String> theImportedTypes = new HashMap<>();
	
	/**
	 * This set details the primitive types in Java.
	 */
	private static Set<String> thePrimitiveTypes = new HashSet<>();
	
	{
		thePrimitiveTypes.add("void");
		thePrimitiveTypes.add("short");
		thePrimitiveTypes.add("int");
		thePrimitiveTypes.add("long");
		thePrimitiveTypes.add("byte");
		thePrimitiveTypes.add("char");
		thePrimitiveTypes.add("boolean");
		thePrimitiveTypes.add("float");
		thePrimitiveTypes.add("double");
	}
	/**
	 * This is the name of the current package.  Default package is "".
	 */
	private String thePackageName = "";
	
	/**
	 * This is the current compilation unit we are processing.
	 */
	private Individual theCurrentCUnit = null;
	
	/**
	 * This is the current class we are processing.
	 */
	private Individual theCurrentClass = null;
	
	/**
	 * The actual ontology model for SCRO.
	 */
	private OntModel theSCROModel = null;
	
	/**
	 * The SCRO Ontology Helper
	 */
	private OntologyHelper theSCROHelper = null;
	
	/**
	 * The model we are building.
	 */
	private final OntModel theModel;

	
	public StructureProcessor(OntModel aModel)
	{
		OntModelFactory factory = new OntModelFactory("http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		theModel = aModel;
		theSCROModel = factory.getOntologyModel();
		theSCROHelper = new OntologyHelper(theModel);
	}
	
	/**
	 * Given a classname, lookup the individual - or create it if we haven't already.  This strips out
	 * any genericness for now.  (Although, eventually, we'll have to put it in.)
	 * @param aClassName
	 * @return
	 */
	public Individual findClass(String aClassName)
	{
		return theSCROHelper.findOrMakeIndividual(SCROClasses.CLASS_TYPE, aClassName);
	}
	
	/**
	 * Given a package name, lookup the individual, or create it if we haven't already.
	 */
	public Individual findPackage(String aClassName)
	{
		return theSCROHelper.findOrMakeIndividual(SCROClasses.PACKAGE, aClassName);
	}
	
	/**
	 * Given a fully qualified class name, get the package it belongs to.
	 * @param aClassName A fully qualified class name.
	 * @return The package the class belongs to.
	 */
	public String makePackageName(String aClassName)
	{
		return aClassName.substring(0, aClassName.lastIndexOf("."));
	}
	
	/**
	 * Creates a property in the model.
	 * @param aProperty
	 * @return
	 */
	public Property makeProperty(OntologyProperty aProperty)
	{
		return theSCROModel.createProperty(aProperty.getURI());
	}

	/**
	 * Tries its best to come up with a fully qualified type name.
	 * @param aTypeName
	 * @return
	 */
	public String resolve(String aTypeName)
	{
		String noArray = aTypeName;
		String arrayPart = "";
		if (noArray.contains("["))
		{
			noArray = aTypeName.substring(0, aTypeName.indexOf('['));
			arrayPart = aTypeName.substring(aTypeName.indexOf('['));
		}
		
		if (thePrimitiveTypes.contains(noArray))
		{
			return aTypeName;
		}
		else if (noArray.contains("."))
		{
			return aTypeName;
		} 
		else if (theImportedTypes.containsKey(noArray))
		{
			return (theImportedTypes.get(noArray) + arrayPart);
		}
		else 
		try
		{

			Class.forName("java.lang." + noArray);
			return "java.lang." + aTypeName;				
		}
		catch (ClassNotFoundException ex)
		{
			// Will happen most of the time.
			return thePackageName + "." + aTypeName;
		}
	}
	
	/**
	 * Given a typename, create a typespec for it.  (i.e. java.lang.String --> Ljava/lang/String; and int -> int)
	 * @param aTypeName
	 * @return
	 */
	public String toTypeSpec(String aTypeName)
	{
		StringBuffer rc = new StringBuffer();
		if (aTypeName.contains("["))
		{
			int numDimensions = aTypeName.split("\\[").length - 1;
			for (int i = 0; i < numDimensions; i++)
			{
				rc.append("[");				
			}
			
			aTypeName = aTypeName.substring(0, aTypeName.indexOf("["));
		}
		switch (aTypeName)
		{
		case "void":
			rc.append("V");
			break;
		case "int":
			rc.append("I");
			break;
		case "long":
			rc.append("J");
			break;
		case "short":
			rc.append("S");
			break;
		case "float":
			rc.append("F");
			break;
		case "double":
			rc.append("D");
			break;
		case "boolean":
			rc.append("Z");
			break;
		case "char":
			rc.append("C");
			break;
		default:
			rc.append("L");
			rc.append(aTypeName.replaceAll("\\.", "/"));
			rc.append(";");		
		}
		
		return rc.toString();
	}
	
	public void applyAccessControl(Individual anItem, Set<Modifier> aModifierSet)
	{
		Property hasAccessControl = makeProperty(ObjectProperties.HAS_ACCESS_CONTROL);
		for (Modifier mod: aModifierSet)
		{
			switch (mod)
			{
			case PRIVATE:
				anItem.addProperty(hasAccessControl, theSCROHelper.getIndividual(Individuals.PRIVATE));
				break;
			case PUBLIC:
				anItem.addProperty(hasAccessControl, theSCROHelper.getIndividual(Individuals.PUBLIC));
				break;
			case PROTECTED:
				anItem.addProperty(hasAccessControl, theSCROHelper.getIndividual(Individuals.PROTECTED));
				break;
			default:
				break;
			}
		}
	}
	
	// BEGIN VISITOR
	/**
	 * Visits a compilation unit.  Sets the compilation unit so that the
	 * classes defined within it can reference it.
	 * 
	 * CompilationUnit IMPORTS Class
	 * CompilationUnit IMPORTS Package
	 * 
	 */
	@Override
	public Object visitCompilationUnit(CompilationUnitTree aNode, Trees aTrees)
	{
		String uri = aNode.getSourceFile().toUri().toString();
		System.err.println(uri);
		theCurrentCUnit = theSCROHelper.makeIndividual(SCROClasses.COMPILATION_UNIT);
		thePackageName = aNode.getPackageName().toString();
		
		Property imports = makeProperty(ObjectProperties.IMPORTS);
		for (ImportTree importTree: aNode.getImports())
		{
			if (!importTree.isStatic())
			{
				String importName = importTree.getQualifiedIdentifier().toString();
				if (importName.endsWith("*"))
				{
					theCurrentCUnit.addProperty(imports, findPackage(makePackageName(importName)));				
				}
				else
				{
					theCurrentCUnit.addProperty(imports, findClass(importName));
					String shortName = importName.substring(importName.lastIndexOf('.'));
					theImportedTypes.put(shortName, importName);
				}
			}
		}
		
		return super.visitCompilationUnit(aNode, aTrees);
	}
	

	/**
	 * Visits a class definition. Here we determine:
	 * Class BELONGS_TO_PKG Package
	 * Class IMPLEMENTS Interface / Interface EXTENDED_BY Class
	 * Class EXTENDS Class / Class EXTENDED_BY Class
	 * 
	 * Individual passed in is a package.
	 */
	@Override
	public Object visitClass(ClassTree aNode, Trees aTrees)
	{
		TreePath path = getCurrentPath();
		TypeElement element = (TypeElement) aTrees.getElement(path);

		String className = element.getSimpleName().toString();
		if (className.equals(""))
		{
			// This is an Anonymous class
			className = "$$" + theAnonymousClassCounter + "$$";
			theAnonymousClassCounter++;
		}
		
		String qualName = element.getQualifiedName().toString();
		if (qualName.equals(""))
		{
			qualName = className;
		}
		
		Individual clazz = findClass(qualName);
		theImportedTypes.put(className, qualName);
		
		Property definedIn = makeProperty(ObjectProperties.DEFINED_IN_C_UNIT);
		Property belongsTo = makeProperty(ObjectProperties.BELONGS_TO_PKG);
		
		Element parent = element.getEnclosingElement();
		while (parent != null)
		{
			boolean handled = false;
			switch (parent.getKind())
			{
			case PACKAGE:
				thePackageName = ((PackageElement) parent).getSimpleName().toString();
			
				Individual pkg = findPackage(thePackageName);				
			
				clazz.addProperty(belongsTo, pkg);
				handled = true;
				break;
			case CLASS:
				String enclosingClassName = parent.getSimpleName().toString();
				Individual enclosingClass = findClass(resolve(enclosingClassName));
			
				Property outerClass = makeProperty(ObjectProperties.HAS_OUTER_CLASS);
				clazz.addProperty(outerClass, enclosingClass);
				handled = true;
				break;
			default:
				parent = parent.getEnclosingElement();
			}			
			
			if (handled)
				break;
		}
		
		if (theCurrentCUnit != null)
		{
			clazz.addProperty(definedIn, theCurrentCUnit);
		}
		
		Property extendsProp = makeProperty(ObjectProperties.INHERITS);
		Property extendedByProp = makeProperty(ObjectProperties.INHERITED_BY);
		
		Individual superClazz = findClass(element.getSuperclass().toString());
		clazz.addProperty(extendsProp, superClazz);
		superClazz.addProperty(extendedByProp, clazz);
		
		Individual superPkg = theSCROHelper.findOrMakeIndividual(SCROClasses.PACKAGE, makePackageName(element.getSuperclass().toString()));
		superClazz.addProperty(belongsTo, superPkg);
		
		for (TypeMirror interfaceMirror: element.getInterfaces())
		{
			Property implementsProp = makeProperty(ObjectProperties.IMPLEMENTS);
			Property implementedByProp = makeProperty(ObjectProperties.IMPLEMENTED_BY);
			
			Individual implementz = findClass(interfaceMirror.toString());
			
			clazz.addProperty(implementsProp, implementz);
			implementz.addProperty(implementedByProp, clazz);

			Individual implPkg = findPackage(makePackageName(interfaceMirror.toString()));
			implementz.addProperty(belongsTo, implPkg);
		}

		applyAccessControl(clazz, aNode.getModifiers().getFlags());
		theCurrentClass = clazz;
		return super.visitClass(aNode, aTrees);
	}
	
	/**
	 * This visits a method definition.
	 * 
	 * Defines:
	 * Class HAS_METHOD Method
	 */
	@Override
	public Object visitMethod(MethodTree aMethodTree, Trees aTrees)
	{
		
		Property hasMethod = makeProperty(ObjectProperties.HAS_METHOD);
		Property hasAbstractMethod = makeProperty(ObjectProperties.HAS_ABSTRACT_METHOD);
		Property hasStaticMethod = makeProperty(ObjectProperties.HAS_STATIC_METHOD);
		Property hasFinalMethod = makeProperty(ObjectProperties.HAS_FINAL_METHOD);
		Property methodName = makeProperty(DataProperties.HAS_METHOD_NAME);
		Property signature = makeProperty(DataProperties.HAS_SIGNATURE);

		StringBuffer methodSignature = new StringBuffer();
		
		methodSignature.append("(");
		
		for (VariableTree param: aMethodTree.getParameters())
		{
			methodSignature.append(toTypeSpec(resolve(param.getType().toString())));
		}
		
		methodSignature.append(")");
		if (aMethodTree.getReturnType() != null)
		{
			methodSignature.append(toTypeSpec(resolve(aMethodTree.getReturnType().toString())));
		}		
		
		Individual method = theSCROHelper.makeIndividual(SCROClasses.METHOD);
		
		theCurrentClass.addProperty(hasMethod, method);
		method.addProperty(methodName, aMethodTree.getName().toString());
		method.addProperty(signature, methodSignature.toString());

		Set<Modifier> modifiers = aMethodTree.getModifiers().getFlags();
		for (Modifier mod: modifiers)
		{
			switch (mod) {
			case STATIC:
				method.addOntClass(theSCROHelper.getOntClass(SCROClasses.STATIC_METHOD));
				theCurrentClass.addProperty(hasStaticMethod, method);
				break;
			case ABSTRACT:
				method.addOntClass(theSCROHelper.getOntClass(SCROClasses.ABSTRACT_METHOD));
				theCurrentClass.addProperty(hasAbstractMethod, method);
				break;
			case FINAL:
				method.addOntClass(theSCROHelper.getOntClass(SCROClasses.FINAL_METHOD));
				theCurrentClass.addProperty(hasFinalMethod, method);
				break;
			default:
				// TODO: Handle all cases.
				break;
			}
		}
		
		applyAccessControl(method, modifiers);
		//String methodName = aMethodTree.getName().toString();
		//String returnType = aMethodTree.getReturnType().toString();
		//List<? extends VariableTree> parameters = aMethodTree.getParameters();

//		Individual method = theSCROHelper.makeIndividual(SCROClasses.METHOD, aMethodTree.getName().toString());
//		theModel.add(theModel.createStatement(aClass, hasMethod, method));
		
		return super.visitMethod(aMethodTree, aTrees);
	}
}
