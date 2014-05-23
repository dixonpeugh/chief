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
package testfiles;

import javax.management.*; // This is a package import.
import java.math.BigInteger; // This is a class import.
import java.awt.dnd.*;
import java.awt.AlphaComposite;
import static java.awt.color.ColorSpace.*; // Import all statics. 
import static java.awt.event.ContainerEvent.COMPONENT_ADDED; // Single import.

/**
 * This file is designed to test the many imports that can be handled.
 * 
 * @author David Dixon-Peugh
 */
public class Imports
{
	/*
	 * What we should see tree wise is:
	 * 
	 * CompilationUnit: testfiles/Imports.java
	 *     imports Package: javax.management
	 *     imports Package: java.awt.dnd
	 *     imports Class java.math.BigInteger
	 *     imports Class java.awt.AlphaComposite
	 *     
	 * Not sure how the import statics work.
	 */
	public static void main(String args[])
	{
		// This space intentionally left blank.
	}
}
