package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntModelFactory;
import junit.framework.TestCase;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;

public class IndividualsTest extends TestCase {

	public void testEnumeration()
	{
		OntModelFactory factory = new OntModelFactory("/ontologies/scro.owl", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		OntModel model = factory.getOntologyModel();
		
		for (Individuals ind: Individuals.values())
		{	
			Property prop = model.getProperty(ind.getURI());
			assertNotNull("Cannot find datatype property: " + ind, prop);
		}
	}
}
