package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntModelFactory;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

import junit.framework.TestCase;

public class ClassesTest extends TestCase {
	public void testEnumeration()
	{
		OntModelFactory factory = new OntModelFactory("/ontologies/scro.owl", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		OntModel model = factory.getOntologyModel();
		
		for (SCROClasses clazz: SCROClasses.values())
		{
			OntClass ontClass = model.getOntClass(clazz.getURI());
			assertNotNull("Could not find Ontology Class for: " + clazz, ontClass );
		}
	}
}
