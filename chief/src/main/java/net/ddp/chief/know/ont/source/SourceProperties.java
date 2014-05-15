/*
   Copyright 2014 David Dixon-Peugh

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
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
