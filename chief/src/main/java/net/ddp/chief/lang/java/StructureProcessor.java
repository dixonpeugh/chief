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

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import net.ddp.chief.lang.LanguageProcessor;
import net.ddp.chief.lang.ParseException;

/**
 * This class will parse a Java file, and generate the structural knowledge.  This gets placed into the
 * model passed in.
 * 
 * @author David Dixon-Peugh
 *
 */
public class StructureProcessor implements LanguageProcessor {

	/* (non-Javadoc)
	 * @see net.ddp.chief.lang.LanguageProcessor#process(com.hp.hpl.jena.rdf.model.Model, com.hp.hpl.jena.rdf.model.Resource, java.io.InputStream)
	 */
	public void process(Model aModel, Resource aSourceResoure,
			InputStream anInputStream) throws ParseException {
		// TODO Auto-generated method stub

	}

}
