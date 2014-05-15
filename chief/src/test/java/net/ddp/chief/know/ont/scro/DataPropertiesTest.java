package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntModelFactory;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;

import junit.framework.TestCase;

public class DataPropertiesTest extends TestCase {
	public void testEnumeration()
	{
		OntModelFactory factory = new OntModelFactory("/ontologies/scro.owl", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		OntModel model = factory.getOntologyModel();
		
		for (DataProperties prop: DataProperties.values())
		{
			DatatypeProperty dataProp = model.getDatatypeProperty(prop.getURI());
			assertNotNull("Cannot find datatype property: " + prop, dataProp);
		}
	}

}
