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
package net.ddp.chief;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import net.ddp.chief.lang.java.JavaProcessor;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    	JavaProcessor proc = new JavaProcessor(model);
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    	StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
    	List<String> fileNames = new ArrayList<>();
    	for (String name: args)
    	{
    		fileNames.add(name);
    	}
    	
    	Iterable<? extends JavaFileObject> compilationUnits =
    			fileManager.getJavaFileObjectsFromStrings(fileNames);
    	
    	CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
    	task.setProcessors(Collections.singleton(proc));
    	
    	task.call();
    	
    	model.write(System.out);	
    }
}
