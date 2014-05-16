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
package net.ddp.chief.lang;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * This is a common interface for any class which will process a language
 * file and create a model.
 * 
 * @author David Dixon-Peugh
 */
public interface LanguageProcessor {
	
	/**
	 * Process an input stream, loading statements into the model.
	 * @param aModel Model to load with values from the input.
	 * @param anInputStream Input stream to load the model from.
	 */
	public void process(Model aModel, Individual aSourceResoure, InputStream anInputStream)
		throws ParseException;
}
