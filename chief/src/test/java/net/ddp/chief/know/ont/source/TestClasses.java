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

import net.ddp.chief.know.ont.scro.DataProperties;
import net.ddp.chief.know.ont.scro.ObjectProperties;
import net.ddp.chief.know.ont.scro.SCROClasses;

import org.junit.Test;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;

/**
 * @author Basement
 *
 */
public class TestClasses extends JavaParserTest {
	public static final String TEST_IMPORT_FILE = "testfiles/Classes.java";
	
	/* (non-Javadoc)
	 * @see net.ddp.chief.know.ont.source.JavaParserTest#getResources()
	 */
	@Override
	public List<String> getResources() {
		return Collections.singletonList(TEST_IMPORT_FILE);
	}
	
	/**
	 * The class "Classes" should implement "Runnable" and extend "Imports"
	 */
	@Test
	public void testClassesClass()
	{
		parseJavaFile();
		getModel().write(System.err);
		assertSPARQL("Could not find defined class.", makeAsk(SCROClasses.CLASS_TYPE,
				new String[] {"testfiles.Classes", "testfiles.Imports", "java.lang.Runnable" }));
		
		ParameterizedSparqlString implementsQuery = createSPARQLString();
		implementsQuery.append("ASK {");
		implementsQuery.append(" ?classes dc:title \"testfiles.Classes\" . ");
		implementsQuery.append(" ?classes rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		implementsQuery.append(" ?runnable dc:title \"java.lang.Runnable\" . ");
		implementsQuery.append(" ?runnable rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		implementsQuery.append(" ?classes <" + ObjectProperties.IMPLEMENTS.getURI() + "> ?runnable .");
		implementsQuery.append(" ?runnable <" + ObjectProperties.IMPLEMENTED_BY.getURI() + "> ?classes .");
		implementsQuery.append(" } ");
		
		assertSPARQL("Classes does not implement Runnable.", implementsQuery.asQuery());

		
		ParameterizedSparqlString extendsQuery = createSPARQLString();
		extendsQuery.append("ASK {");
		extendsQuery.append(" ?classes dc:title \"testfiles.Classes\" . ");
		extendsQuery.append(" ?classes rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		extendsQuery.append(" ?imports dc:title \"testfiles.Imports\" . ");
		extendsQuery.append(" ?imports rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		extendsQuery.append(" ?classes <" + ObjectProperties.INHERITS.getURI() + "> ?imports .");
		extendsQuery.append(" ?imports <" + ObjectProperties.INHERITED_BY.getURI() + "> ?classes .");
		extendsQuery.append(" } ");
		
		assertSPARQL("Classes does not extend Imports.", extendsQuery.asQuery());
	}
	
	public void testInnerClasses()
	{
		ParameterizedSparqlString innerQuery = createSPARQLString();
		innerQuery.append("ASK {");
		innerQuery.append(" ?inner dc:title \"testfiles.Classes.InnerClass\" . ");
		innerQuery.append(" ?inner rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		innerQuery.append(" ?classes dc:title \"testfiles.Classes\" . ");
		innerQuery.append(" ?classes rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		innerQuery.append(" ?inner <" + ObjectProperties.HAS_OUTER_CLASS.getURI() + "> ?classes .");
		innerQuery.append(" } ");
		
		assertSPARQL("InnerClass does not have outer class: Classes.", innerQuery.asQuery());
		
	}

	public ParameterizedSparqlString createSPARQLString()
	{
		ParameterizedSparqlString rc = new ParameterizedSparqlString();
		rc.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");
		rc.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		rc.setNsPrefix("scro", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		
		return rc;
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
		}
		queryStr.append("}");
		
		return queryStr.asQuery();
	}
}
