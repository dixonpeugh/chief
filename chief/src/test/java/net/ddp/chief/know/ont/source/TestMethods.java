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

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

/**
 * @author Basement
 *
 */
public class TestMethods extends JavaParserTest {
	public static final String TEST_METHODS_FILE = "testfiles/Methods.java";
	
	public static final String[][] EXPECTED_METHODS =
		{
		{ "<init>", "()" },
		{ "<init>", "(I)" },
		{ "<init>", "([I)" },
		{ "<init>", "(Ljava/lang/String;)" },
		{ "main", "([Ljava/lang/String;)V" },
		{ "getInstance", "()Ltestfiles/Methods;" },
		{ "getValue", "()I" },
		{ "getName", "()Ljava/lang/String;" },
		{ "add", "(II)I" },
		{ "doNothing", "()V" },
		{ "doAbstract", "()V" }
		};
	
	/**
	 * These are the expected static methods and their signatures.
	 */
	public static final String[][] STATIC_METHODS =
		{
		{ "main", "([Ljava/lang/String;)V" },
		{ "getInstance", "()Ltestfiles/Methods;" },
		{ "getValue", "()I" }
		};
	
	public static final String[][] ABSTRACT_METHODS =
		{
		{ "doAbstract", "()V" }
		};

	
	public static final String[][] FINAL_METHODS =
		{
		{ "doNothing", "()V" }
		};
	
	/* (non-Javadoc)
	 * @see net.ddp.chief.know.ont.source.JavaParserTest#getResources()
	 */
	@Override
	public List<String> getResources() {
		return Collections.singletonList(TEST_METHODS_FILE);
	}

	
	@Before
	public void setup()
	{
		parseJavaFile();		
		getModel().write(System.out);
	}
	
	/**
	 * This test will ensure we have all of our methods.
	 */
	@Test
	public void testMethods()
	{
		ParameterizedSparqlString sparql = createSPARQLString();
		
		sparql.append(" ASK { ");
		int sym = 0;
		
		sparql.append(" ?clazz dc:title \"testfiles.Methods\" . ");
		sparql.append(" ?clazz rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . ");
		
		for ( String pair[]: EXPECTED_METHODS)
		{
			String symName = "?method_" + sym;
			sym++;
			
			sparql.append(" ?clazz <" + ObjectProperties.HAS_METHOD.getURI() + "> " + symName + " . ");
			sparql.append(" " + symName);
			sparql.append(" <" + DataProperties.HAS_METHOD_NAME.getURI() + "> ");
			sparql.appendLiteral(pair[0]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " ");
			sparql.append(" <" + DataProperties.HAS_SIGNATURE.getURI() + "> ");
			sparql.appendLiteral(pair[1]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " rdf:type <" + SCROClasses.METHOD.getURI() + "> .\n ");
		}
		sparql.append("}");
		System.err.println(sparql);
		assertSPARQL("Cannot find expected methods.", sparql.asQuery());
	}
	
	@Test
	public void testStaticMethods()
	{
		ParameterizedSparqlString sparql = createSPARQLString();
		sparql.append(" ASK { ");
		int sym = 0;
		
		sparql.append(" ?clazz dc:title \"testfiles.Methods\" . ");
		sparql.append(" ?clazz rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . \n");

		for (String pair[] : STATIC_METHODS)
		{
			String symName = "?method_" + sym;
			sym++;
			
			sparql.append(" ?clazz <" + ObjectProperties.HAS_STATIC_METHOD.getURI() + "> " + symName + " . ");
			sparql.append(" " + symName);
			sparql.append(" <" + DataProperties.HAS_METHOD_NAME.getURI() + "> ");
			sparql.appendLiteral(pair[0]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " ");
			sparql.append(" <" + DataProperties.HAS_SIGNATURE.getURI() + "> ");
			sparql.appendLiteral(pair[1]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " rdf:type <" + SCROClasses.STATIC_METHOD.getURI() + "> .\n ");
			
		}
		sparql.append(" } ");
		
		assertSPARQL("Cannot find static methods.", sparql.asQuery());
	}

	@Test
	public void testAbstractMethods()
	{
		ParameterizedSparqlString sparql = createSPARQLString();
		sparql.append(" ASK { ");
		int sym = 0;
		
		sparql.append(" ?clazz dc:title \"testfiles.Methods\" . ");
		sparql.append(" ?clazz rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . \n");

		for (String pair[] : ABSTRACT_METHODS)
		{
			String symName = "?method_" + sym;
			sym++;
			
			sparql.append(" ?clazz <" + ObjectProperties.HAS_ABSTRACT_METHOD.getURI() + "> " + symName + " . ");
			sparql.append(" " + symName);
			sparql.append(" <" + DataProperties.HAS_METHOD_NAME.getURI() + "> ");
			sparql.appendLiteral(pair[0]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " ");
			sparql.append(" <" + DataProperties.HAS_SIGNATURE.getURI() + "> ");
			sparql.appendLiteral(pair[1]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " rdf:type <" + SCROClasses.ABSTRACT_METHOD.getURI() + "> .\n ");
			
		}
		sparql.append(" } ");
		
		assertSPARQL("Cannot find abstract methods.", sparql.asQuery());
	}
	
	@Test
	public void testFinalMethods()
	{
		ParameterizedSparqlString sparql = createSPARQLString();
		sparql.append(" ASK { ");
		int sym = 0;
		
		sparql.append(" ?clazz dc:title \"testfiles.Methods\" . ");
		sparql.append(" ?clazz rdf:type <" + SCROClasses.CLASS_TYPE.getURI() + "> . \n");

		for (String pair[] : FINAL_METHODS)
		{
			String symName = "?method_" + sym;
			sym++;
			
			sparql.append(" ?clazz <" + ObjectProperties.HAS_FINAL_METHOD.getURI() + "> " + symName + " . ");
			sparql.append(" " + symName);
			sparql.append(" <" + DataProperties.HAS_METHOD_NAME.getURI() + "> ");
			sparql.appendLiteral(pair[0]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " ");
			sparql.append(" <" + DataProperties.HAS_SIGNATURE.getURI() + "> ");
			sparql.appendLiteral(pair[1]);
			sparql.append(" . ");
			
			sparql.append(" " + symName + " rdf:type <" + SCROClasses.FINAL_METHOD.getURI() + "> .\n ");
			
		}
		sparql.append(" } ");
		
		assertSPARQL("Cannot find final methods.", sparql.asQuery());
	}
	
	
	public ParameterizedSparqlString createSPARQLString()
	{
		ParameterizedSparqlString rc = new ParameterizedSparqlString();
		rc.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");
		rc.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		rc.setNsPrefix("scro", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		
		return rc;
	}
}
