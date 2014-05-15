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
