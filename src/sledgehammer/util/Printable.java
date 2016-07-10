package sledgehammer.util;

import sledgehammer.SledgeHammer;
import sledgehammer.manager.EventManager;

public abstract class Printable {

	static String newLine = System.getProperty("line.separator");

	/**
	 * Prints lines with "getName(): [message...]".
	 * 
	 * @param messages
	 */
	public synchronized void println(Object... messages) {

		if(messages.length == 0) {
			System.out.println();
		} else {			
			// Grab the name of the instance.
			String name = getName();
			
			// Create the header, based on if the name String is null of empty.
			String header = name == null || name.isEmpty() ? "" : name + ": ";
			
			// Go through each Object, and print them as a separate line.
			String compiledString = "";
			for (Object message : messages) {
				compiledString += header + message + newLine;
			}
			
			// Print the result.
			System.out.print(compiledString);
		}
	}

	/**
	 * Prints a message with a header, without a new-line.
	 * @param message
	 */
	public synchronized void printH(Object message) {
		
		// Grab the name of the instance.
		String name = getName();
		
		// Create the header, based on if the name String is null of empty.
		String header = name == null || name.isEmpty() ? "" : name + ": ";
		
		// Print the result.
		System.out.print(header + message);
	}

	/**
	 * Prints a message, without a new-line.
	 * @param message
	 */
	public synchronized void print(Object message) {
		// Print the result.
		System.out.print(message);
	}
	
	public synchronized void stackTrace(Throwable throwable) {
		stackTrace(throwable.getClass().getName(), throwable);
	}
	
	
	public synchronized void stackTrace(String errorText, Throwable throwable) {
		if(errorText != null && !errorText.isEmpty()) {
			errorText = errorText.trim() + ": " + throwable.getCause();
		}
		
		println("Error: " + (errorText != null ? errorText : "") + ": " + throwable.getMessage());
		for(StackTraceElement element : throwable.getStackTrace()) {
			println(element);
		}
		
		// Send to the EventManager for ExceptionListeners to handle.
		if(SledgeHammer.instance != null) {			
			EventManager managerEvent = SledgeHammer.instance.getEventManager();
			if(managerEvent != null) {
				SledgeHammer.instance.getEventManager().handleException(errorText, throwable);			
			}
		}
	}
	
	public synchronized void stackTrace() {
		for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
			println(element);
		}
	}

	/**
	 * Grabs the name of the instance.
	 * 
	 * @return
	 */
	public abstract String getName();

}
