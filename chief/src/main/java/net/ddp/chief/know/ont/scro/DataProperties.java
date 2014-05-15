/**
 * 
 */
package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntologyProperty;

/**
 * 
 * @author DDP
 *
 */
public enum DataProperties implements OntologyProperty {
	ENCLOSED_WITHIN_METHOD("enclosedWithinMethod"),
	HAS_COMMENT("hasComment"),
	HAS_METHOD_NAME("hasMethodName"),
	HAS_ORIGINAL_NAME("hasOriginalName"),
	HAS_SIGNATURE("hasSignature");

	/**
	 * The Namespace of the property.
	 */
	private static final String NAMESPACE = "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#";
	
	/**
	 * The URI of the Object Property.
	 */
	private final String theURI;
	
	private DataProperties(String aURI)
	{
		theURI = aURI;
	}
	
	public String getURI()
	{
		return NAMESPACE + theURI;
	}
}
