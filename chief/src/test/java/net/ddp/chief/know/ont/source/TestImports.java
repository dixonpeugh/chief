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

import java.util.Collections;
import java.util.List;

import net.ddp.chief.know.ont.scro.SCROClasses;

import org.junit.Test;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;

/**
 * @author Basement
 *
 */
public class TestImports extends JavaParserTest {
	public static final String TEST_IMPORT_FILE = "testfiles/Imports.java";
	
	public static final String TEST_PACKAGE_IMPORTS[] = {
		"javax.management", "java.awt.dnd"
	};
	
	public static final String TEST_PACKAGE_IMPORTS_BAD[] = {
		"java.awt.color.ColorSpace",
		"java.awt.event"
	};
	
	public static final String TEST_CLASS_IMPORTS[] = {
		"java.math.BigInteger", "java.awt.AlphaComposite"
	};
	
	public static final String TEST_CLASS_IMPORTS_BAD[] = {
		"java.awt.event.ContainerEvent.COMPONENT_ADDED",
		"java.awt.event.ContainerEvent",
		"java.awt.event.ColorSpace"
	};
	
	/* (non-Javadoc)
	 * @see net.ddp.chief.know.ont.source.JavaParserTest#getResources()
	 */
	@Override
	public List<String> getResources() {
		return Collections.singletonList(TEST_IMPORT_FILE);
	}

	/**
	 * We should see javax.management & java.awt.dnd in our imports.
	 */
	@Test
	public void testPackageImports()
	{
		parseJavaFile();
		getModel().write(System.err);

		assertSPARQL("Did not find required imports.", makeAsk(SCROClasses.PACKAGE, TEST_PACKAGE_IMPORTS));
		for (String badPackage: TEST_PACKAGE_IMPORTS_BAD)
		{
			assertNotSPARQL("Found wrong import: " + badPackage, makeAsk(SCROClasses.PACKAGE, new String[] {badPackage}) );	
		}
	}
	
	/**
	 * Check to see if our class imports were registered.
	 */
	@Test
	public void testClassImports()
	{
		parseJavaFile();
		
		assertSPARQL("Did not find required Class imports.", makeAsk(SCROClasses.CLASS_TYPE, TEST_CLASS_IMPORTS));
		for (String badClass: TEST_CLASS_IMPORTS_BAD)
		{
			assertNotSPARQL("Found wrong import: " + badClass, makeAsk(SCROClasses.PACKAGE, new String[] {badClass}) );	
		}
	}
	

	public Query makeAsk(SCROClasses aType, String anImportList[]) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");
		queryStr.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		queryStr.setNsPrefix("scro", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		queryStr.append("ASK {");
		for (int i = 0; i < anImportList.length; i++)
		{
			String symbol = "?symbol_" + Integer.toHexString(i);
			queryStr.append(" " + symbol + " ");
			queryStr.append(" dc:title ");
			queryStr.appendLiteral(anImportList[i]);
			queryStr.append(".");
			
			queryStr.append(" " + symbol + " rdf:type ");
			queryStr.append("<" + aType.getURI() + "> . ");
			queryStr.append(" ?compilation_unit scro:imports " + symbol + " . " );
		}
		queryStr.append("}");
		
		return queryStr.asQuery();
	}
}
