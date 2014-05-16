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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import net.ddp.chief.know.ont.OntModelFactory;
import net.ddp.chief.know.ont.OntologyHelper;
import net.ddp.chief.know.ont.scro.DataProperties;
import net.ddp.chief.know.ont.scro.ObjectProperties;
import net.ddp.chief.know.ont.scro.SCROClasses;
import net.ddp.chief.lang.LanguageProcessor;
import net.ddp.chief.lang.ParseException;

import org.apache.commons.io.IOUtils;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;

/**
 * This class will parse a Java file, and generate the structural knowledge.  This gets placed into the
 * model passed in.
 * 
 * @author David Dixon-Peugh
 *
 */
public class StructureProcessor extends TreeScanner<Void, Individual> implements LanguageProcessor {
	/**
	 * The compiler which will do the heavy lifting.
	 */
	private final JavaCompiler theCompiler;
	
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
	private Model theModel = null;
	
	/**
	 * This is a wrapper class around an InputStream.  It allows us to compile from resources
	 * instead of having to use files.
	 */
	private class StreamJavaSource extends SimpleJavaFileObject
	{
		private final InputStream theText;
		
		public StreamJavaSource(URI aURI, InputStream anInputStream)
		{
			super(aURI, JavaFileObject.Kind.SOURCE );
			theText = anInputStream;
		}

		@Override
		public CharSequence getCharContent(boolean anIgnoreEncodingErrorsFlag) throws IOException
		{
			return IOUtils.toString(theText);
		}
	}
	
	public StructureProcessor()
	{
		theCompiler = ToolProvider.getSystemJavaCompiler();

		OntModelFactory factory = new OntModelFactory("http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		
		theSCROModel = factory.getOntologyModel();
		theSCROHelper = new OntologyHelper(theSCROModel);
	}
	
	/* (non-Javadoc)
	 * @see net.ddp.chief.lang.LanguageProcessor#process(com.hp.hpl.jena.rdf.model.Model, com.hp.hpl.jena.rdf.model.Resource, java.io.InputStream)
	 */
	public void process(Model aModel, 
			Individual aSourceResource,
			InputStream anInputStream) 
					throws ParseException
	{
		theModel = aModel;
		
		final StandardJavaFileManager fileManager = 
				theCompiler.getStandardFileManager(null, null, null);
		try {
			JavaFileObject file = new StreamJavaSource(new URI(aSourceResource.getURI()), anInputStream);

		
			final JavacTask task = (JavacTask)
				theCompiler.getTask(null, fileManager, null, null, null, Collections.singletonList(file));
			//final Trees trees = Trees.instance(task);
			for(CompilationUnitTree cUnitTree: task.parse())
			{
				cUnitTree.accept(this, aSourceResource);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public Void visitCompilationUnit(CompilationUnitTree aNode, Individual aRoot)
	{
		Property belongsTo = theSCROModel.createProperty(ObjectProperties.BELONGS_TO_PKG.getURI());
		Individual pkg = theSCROHelper.makeIndividual(SCROClasses.PACKAGE, aNode.getPackageName().toString());
		
		theModel.add(theModel.createStatement(aRoot, belongsTo, pkg));

		Property imports = theSCROModel.createProperty(ObjectProperties.IMPORTS.getURI());
		for (ImportTree tree: aNode.getImports())
		{
			String importName = tree.getQualifiedIdentifier().toString();
			String pkgName = importName.substring(0, importName.lastIndexOf("."));
			Individual importedPkg = theSCROHelper.makeIndividual(SCROClasses.PACKAGE, pkgName);
			theModel.add(theModel.createStatement(aRoot, imports, importedPkg));
		}
		
		for (Tree tree: aNode.getTypeDecls())
		{
			tree.accept(this, pkg);
		}
		return null;
	}	
	
	@Override
	public Void visitClass(ClassTree aNode, Individual aPackage)
	{
		Property belongsTo = theSCROModel.createProperty(ObjectProperties.BELONGS_TO_PKG.getURI());
		Individual clazz = theSCROHelper.makeIndividual(SCROClasses.CLASS_TYPE, aNode.getSimpleName().toString());

		theModel.add(theModel.createStatement(clazz, belongsTo, aPackage));
	
		if (aNode.getExtendsClause() != null)
		{
			Property extendsProp = theSCROModel.createProperty(ObjectProperties.INHERITS.getURI());
			Property extendedByProp = theSCROModel.createProperty(ObjectProperties.INHERITED_BY.getURI());
			Individual extendz = theSCROHelper.makeIndividual(SCROClasses.CLASS_TYPE, aNode.getExtendsClause().toString());
		
			theModel.add(theModel.createStatement(clazz, extendsProp, extendz));
			theModel.add(theModel.createStatement(extendz, extendedByProp, clazz));
		}
		
		if (aNode.getImplementsClause().size() > 0)
		{
			Property implementsProp = theSCROModel.createProperty(ObjectProperties.IMPLEMENTS.getURI());
			Property implementedByProp = theSCROModel.createProperty(ObjectProperties.IMPLEMENTED_BY.getURI());
		
			for (Tree tree: aNode.getImplementsClause())
			{
				Individual implementz = theSCROHelper.makeIndividual(SCROClasses.CLASS_TYPE, tree.toString());
			
				theModel.add(theModel.createStatement(clazz, implementsProp, implementz));
				theModel.add(theModel.createStatement(implementz, implementedByProp, clazz));
			}
		}
		return null;
	}
}
