package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntModelFactory;
import junit.framework.TestCase;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;

public class ObjectPropertiesTest extends TestCase {

	public void testEnumeration()
	{
		OntModelFactory factory = new OntModelFactory("/ontologies/scro.owl", "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		OntModel model = factory.getOntologyModel();
		
		for (ObjectProperties prop: ObjectProperties.values())
		{	
			ObjectProperty objProp = model.getObjectProperty(prop.getURI());
			assertNotNull("Cannot find datatype property: " + prop, objProp);
		}
	}
}
