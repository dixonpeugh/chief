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
package net.ddp.chief.know.ont.source;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import net.ddp.chief.know.ont.scro.SCROClasses;
import net.ddp.chief.lang.java.JavaProcessor;

import org.junit.Assert;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * @author David Dixon-Peugh
 * 
 * This serves as the base class for all tests which parse java files.
 * 
 * During the startup, this will parse the Java file returned by "getResourceName".
 * During the test, several SPARQL queries will be passed to assertSPARQL which
 * will ensure that bits of the model are present.
 * 
 */

public abstract class JavaParserTest {
	private final OntModel theModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

	/**
	 * Returns a list of resources to run through the compiler.
	 * @return A list of resources to run through the compiler.
	 */
	public abstract List<String> getResources();
	
	/**
	 * Returns the model that was created.
	 * @return The model that was created.
	 */
	public final OntModel getModel()
	{
		return theModel;
	}
	
	/**
	 * This will trigger the compiler on the resources returned by getResources().  The internal
	 * model will be loaded.
	 */
	public void parseJavaFile()
	{
		List<String> resourceURLs = new ArrayList<>();
		for (String resource: getResources())
		{
			URL classResource = this.getClass().getClassLoader().getResource(resource);
			resourceURLs.add(classResource.getPath());
		}
    	JavaProcessor proc = new JavaProcessor(theModel);
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    	StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

    	Iterable<? extends JavaFileObject> compilationUnits =
    			fileManager.getJavaFileObjectsFromStrings(resourceURLs);
    	
    	CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
    	task.setProcessors(Collections.singleton(proc));
    	
    	task.call();
	}
	
	/**
	 * Given an ask-type SPARQL query, evaluate it on the model.  Assert that it returns TRUE.
	 * 
	 * @param aMessage Message to fail with if the statement returns false.
	 * @param aStatement The SPARQL statement.
	 */
	public void assertSPARQL(String aMessage, Query aQuery)
	{
		QueryExecution exec = QueryExecutionFactory.create(aQuery, theModel);
		
		Assert.assertTrue(aMessage, exec.execAsk());
	}
	
	/**
	 * Given an ask-type SPARQL query, evaluate it on the model.  Assert that it returns false.
	 * @param aMessage Message to fail with if the statement returns false.
	 * @param aStatement The SPARQL statement.
	 */
	public void assertNotSPARQL(String aMessage, Query aQuery)
	{
		QueryExecution exec = QueryExecutionFactory.create(aQuery, theModel);
		
		Assert.assertFalse(aMessage, exec.execAsk());
	}
}
