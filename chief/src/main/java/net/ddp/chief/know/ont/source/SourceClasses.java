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
