package net.ddp.chief.know.ont.source;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SourceFactory {
	/**
	 * Public location of the Source ontology
	 */
	public static final String EXTERNAL_URI = "http://webprotege.stanford.edu/project/9vNakt3jfbWKGR91LyzX2e#";

	/**
	 * Classpath location of the SCRO (Source Code Representation Ontology)
	 */
	public static final String INTERNAL_URI = "ontologies/source.owl";
	
	/**
	 * Returns a Jena Ontology model for Source.
	 * @return A Jena Ontology model for Source.
	 */
	public OntModel getOntologyModel()
	{
		OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(INTERNAL_URI);
		model.read(stream, EXTERNAL_URI);

		return model;
	}	
}
