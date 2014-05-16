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
package net.ddp.chief;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.ddp.chief.know.ont.OntModelFactory;
import net.ddp.chief.know.ont.OntologyHelper;
import net.ddp.chief.know.ont.scro.SCROClasses;
import net.ddp.chief.know.ont.source.SourceProperties;
import net.ddp.chief.lang.ParseException;
import net.ddp.chief.lang.java.StructureProcessor;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
			InputStream input = App.class.getClassLoader().getResourceAsStream("App.java");
			
	       	Model model = ModelFactory.createDefaultModel();
			OntModelFactory factory = new OntModelFactory("http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
			
			OntModel theSCROModel = factory.getOntologyModel();
			OntologyHelper helper = new OntologyHelper(theSCROModel);

	       	Individual root = helper.makeIndividual(SCROClasses.COMPILATION_UNIT, "App.java");
	       	Property prop = model.getProperty(SourceProperties.FILENAME.getURI());
	       	model.add(model.createStatement(root, prop, model.createLiteral("App.java")));
	       	
	       	StructureProcessor proc = new StructureProcessor();
	       	proc.process(model, root, input);
	       	
	       	model.write(System.out);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
