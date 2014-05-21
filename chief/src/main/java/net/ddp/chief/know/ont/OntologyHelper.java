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
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class OntologyHelper {
	public static String SPARQL_RDF_PREFIX = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
	public static String SPARQL_OWL_PREFIX = "prefix owl: <http://www.w3.org/2002/07/owl#>\n";
	public static String SPARQL_SCRO_PREFIX = "prefix scro: <http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#>\n";
	public static String SPARQL_DDP_PREFIX = "prefix ddp: <http://www.dixon-peugh.net/ontology/project.owl#>\n";
	
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
		Individual ind = theModel.getOntClass(aClass.getURI()).createIndividual();
		return ind;
	}
	
	/**
	 * Creates a named individual of the class we requested.
	 * @param aClass The type of individual we want.
	 * @param anIndividualName The unique name of the individual.
	 * @return The named individual.
	 */
	public Individual makeIndividual(OntologyClass aClass, String anIndividualName)
	{
		Individual ind = theModel.getOntClass(aClass.getURI()).createIndividual(aClass.getURI() + "/" + anIndividualName);
		Property dcTitle = theModel.createProperty("http://purl.org/dc/elements/1.1/", "title");
		ind.addProperty(dcTitle, anIndividualName);
		return ind;
	}

	/**
	 * Finds or creates a named individual within our ontology.
	 * @param aClass The type of individual we want.
	 * @param anIndividualName The unique name of the individual.
	 * @return The named individual (either created fresh - or found in our ontology.)
	 */
	public Individual findOrMakeIndividual(OntologyClass aClass, String anIndividualName)
	{
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		queryStr.append("select ?ind {");
		queryStr.append("?ind rdf:type ");
		queryStr.appendLiteral(aClass.getURI());
		queryStr.append(". ?ind rdf:resource ");
		queryStr.appendLiteral(aClass.getURI() + "/" + anIndividualName);
		queryStr.append(". }");

		Query findIndividual = queryStr.asQuery();
		
		ResultSet results = QueryExecutionFactory.create(findIndividual, theModel).execSelect();
		while (results.hasNext())
		{
			QuerySolution result = results.next();
			Resource ind = result.getResource("?ind");
			if (ind.canAs(Individual.class))
			{
				return ind.as(Individual.class);
			}
		} 

		return makeIndividual(aClass, anIndividualName);
	}

}
