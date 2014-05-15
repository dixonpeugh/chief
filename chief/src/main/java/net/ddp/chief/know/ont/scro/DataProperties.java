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
/**
 * 
 */
package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntologyProperty;

/**
 * 
 * @author DDP
 *
 */
public enum DataProperties implements OntologyProperty {
	ENCLOSED_WITHIN_METHOD("enclosedWithinMethod"),
	HAS_COMMENT("hasComment"),
	HAS_METHOD_NAME("hasMethodName"),
	HAS_ORIGINAL_NAME("hasOriginalName"),
	HAS_SIGNATURE("hasSignature");

	/**
	 * The Namespace of the property.
	 */
	private static final String NAMESPACE = "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#";
	
	/**
	 * The URI of the Object Property.
	 */
	private final String theURI;
	
	private DataProperties(String aURI)
	{
		theURI = aURI;
	}
	
	public String getURI()
	{
		return NAMESPACE + theURI;
	}
}
