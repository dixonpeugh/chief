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

public abstract class Methods
{
	// No arg constructor
	public Methods()
	{
		
	}
	
	// Primitive constructor
	public Methods(int x)
	{
		
	}
	
	// Array constructor
	public Methods(int x[])
	{
		
	}
	
	// Object constructor
	public Methods(String x)
	{
		
	}

	// Public Static
	public static void main(String args[])
	{
		
	}
	
	// Package Static
	static Methods getInstance()
	{
		int x=getValue();
		return null;
	}
	
	// Private Static
	private static int getValue()
	{
		return 4;
	}
	
	// Public Virtual
	public String getName()
	{
		return "Methods";
	}
	
	// Protected Virtual
	protected int add(int x, int y)
	{
		doNothing();
		return x + y;
	}
	
	// Private
	final private void doNothing()
	{
		
	}
	
	abstract void doAbstract();
}