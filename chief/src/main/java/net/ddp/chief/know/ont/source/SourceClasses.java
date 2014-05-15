package net.ddp.chief.know.ont.source;

import net.ddp.chief.know.ont.OntologyClass;

public enum SourceClasses implements OntologyClass {
	REFERENCE("http://webprotege.stanford.edu/RaQ870mdKc2Ls4nnTPcIa1"),
	STACK_TRACE("http://webprotege.stanford.edu/RBMgk4UYp7cZut3cZWGpLlw");

	private final String theURI;
	
	public String getURI()
	{
		return theURI;
	}	
	private SourceClasses(String aURI)
	{
		theURI = aURI;
	}
	
}
