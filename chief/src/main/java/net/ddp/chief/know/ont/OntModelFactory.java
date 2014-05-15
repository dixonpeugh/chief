package net.ddp.chief.know.ont;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class OntModelFactory {
	private final String theNamespace;
	
	private final String theInternalResource;
	
	public OntModelFactory(String aNamespace)
	{
		theNamespace = aNamespace;
		theInternalResource = null;
	}
	
	public OntModelFactory(String aNamespace, String anInternalResource)
	{
		theNamespace = aNamespace;
		theInternalResource = anInternalResource;
	}
	
	public String getNamespace()
	{
		return theNamespace;
	}
	
	/**
	 * Returns a Jena Ontology model for Source.
	 * @return A Jena Ontology model for Source.
	 */
	public OntModel getOntologyModel()
	{
		OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		if (theInternalResource != null)
		{
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(theInternalResource);
			model.read(stream, getNamespace());
		}
		else
		{
			model.read(getNamespace());
		}
		return model;
	}	
}
