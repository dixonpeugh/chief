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
package net.ddp.chief.lang.java;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Ontology;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

/**
 * @author David Dixon-Peugh
 *
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("*")
public class JavaProcessor extends AbstractProcessor
{
	public static final String SCRO_URI = "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#";
	
	/**
	 * Tool for handling trees.
	 */
	private Trees theTrees;
	
	/**
	 * Processor for gathering structural information.
	 */
	private StructureProcessor theStructureProc;
	
	public JavaProcessor(OntModel aModel)
	{
		Ontology project = aModel.createOntology("http://www.dixon-peugh.net/ontology/project.owl");
		project.addImport(aModel.createResource(SCRO_URI));
		aModel.loadImports();
		
		theStructureProc = new StructureProcessor(aModel);
	}
	@Override
	public void init(ProcessingEnvironment anEnvironment)
	{
		super.init(anEnvironment);
		theTrees = Trees.instance(anEnvironment);
	}
	
	/* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		for (Element e: roundEnv.getRootElements())
		{
			TreePath path = theTrees.getPath(e);
			theStructureProc.scan(path, theTrees);
		}
		return true;
	}

}
