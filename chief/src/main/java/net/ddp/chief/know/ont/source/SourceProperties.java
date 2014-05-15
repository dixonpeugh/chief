package net.ddp.chief.know.ont.source;

import net.ddp.chief.know.ont.OntologyProperty;

public enum SourceProperties implements OntologyProperty {
	REFERENCE("http://webprotege.stanford.edu/RDMRkHp8h0ct3i1zwQtAY8X"),
	FILENAME("http://webprotege.stanford.edu/Rc76twhp50zscFov6uhySi"),
	START_LINE("http://webprotege.stanford.edu/Rm1pkOduaTTOIa10eCWOK8"),
	START_COLUMN("http://webprotege.stanford.edu/R9yhebty6XxXyA26bisFVo4"),
	END_LINE("http://webprotege.stanford.edu/RBbVIzjLTbNPN3aWGKgfpvv"),
	END_COLUMN("http://webprotege.stanford.edu/R83FPwADPSfsKXoIMINEcIl");
	
	private final String theURI;
	
	private SourceProperties(String aURI)
	{
		theURI = aURI;
	}
	
	public String getURI()
	{
		return theURI;
	}
}
