package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntologyEnum;

public enum Individuals implements OntologyEnum {
	ARRAY("#array"),
	BOOLEAN("#boolean"),
	BYTE("#byte"),
	CHAR("#char"),
	DEFAULT("#default"),
	DOUBLE("#double"),
	FLOAT("#float"),
	INT("#int"),
	STRING("#java.lang.String"),
	LONG("#long"),
	NUMERIC_COMPARISON("#numericComparison"),
	PRIVATE("#private"),
	PROTECTED("#protected"),
	PUBLIC("#public"),
	REF_COMPARISON("#refComparison"),
	SHORT("#short");

	/**
	 * The Namespace of the property.
	 */
	private static final String NAMESPACE = "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#";
	
	/**
	 * The URI of the Object Property.
	 */
	private final String theURI;
	
	private Individuals(String aURI)
	{
		theURI = aURI;
	}
	
	public String getURI()
	{
		return NAMESPACE + theURI;
	}
}
