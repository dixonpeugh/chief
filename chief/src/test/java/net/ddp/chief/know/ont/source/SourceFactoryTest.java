package net.ddp.chief.know.ont.source;

import com.hp.hpl.jena.ontology.OntModel;

import junit.framework.TestCase;

public class SourceFactoryTest extends TestCase {

	public void testFactory()
	{
		SourceFactory factory = new SourceFactory();
		OntModel model = factory.getOntologyModel();
		model.write(System.out);
	}
	
	public void testResolve() throws ClassNotFoundException
	{
		Class.forName("java.lang.String");
	}
}
