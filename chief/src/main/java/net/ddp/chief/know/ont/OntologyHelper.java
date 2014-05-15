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

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

public class OntologyHelper {
	private final OntModel theModel;
	
	/**
	 * Create an OntologyHelper with the provided ontology.
	 * @param aModel The ontology model.
	 */
	public OntologyHelper(OntModel aModel)
	{
		theModel = aModel;
	}
	
	/**
	 * Returns the ontology class for the given enumerated value.
	 * @param aClass The enumerated value we are looking for.
	 * @return The Jena Ontology class.
	 */
	public OntClass getOntClass(OntologyClass aClass)
	{
		return theModel.getOntClass(aClass.getURI());
	}
	
	/**
	 * Create an anonymous individual of the given class.
	 * @param aClass The type of individual we want.
	 * @return The anonymous individual.
	 */
	public Individual makeIndividual(OntologyClass aClass)
	{
		return theModel.getOntClass(aClass.getURI()).createIndividual();
	}
	
	/**
	 * Creates a named individual of the class we requested.
	 * @param aClass The type of individual we want.
	 * @param anIndividualName The unique name of the individual.
	 * @return The named individual.
	 */
	public Individual makeIndividual(OntologyClass aClass, String anIndividualName)
	{
		return theModel.getOntClass(aClass.getURI()).createIndividual(aClass.getURI() + ":" + anIndividualName);
	}
}
