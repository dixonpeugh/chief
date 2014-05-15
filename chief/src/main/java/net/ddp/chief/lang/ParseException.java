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

/**
 * This is the application version of a ParseException.
 * 
 * @author David Dixon-Peugh
 *
 */
public class ParseException extends Exception {

	/**
	 * For serialization purposes
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Create a ParseException with no further information.
	 */
	public ParseException()
	{
		super();
	}
	
	/**
	 * Create a ParseException with only a message.
	 * @param aMessage
	 */
	public ParseException(String aMessage)
	{
		super(aMessage);
	}
	
	/**
	 * Wrap an existing exception with a parse exception.
	 * @param aRootCause
	 */
	public ParseException(Throwable aRootCause)
	{
		super(aRootCause);
	}
	
	/**
	 * Create an exception with both a message and a root cause.
	 * @param aMessage
	 * @param aRootCause
	 */
	public ParseException(String aMessage, Throwable aRootCause)
	{
		super(aMessage, aRootCause);
	}
}
