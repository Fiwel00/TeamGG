package errorhandling;

import java.io.Reader;

import common.stringhelper.StringBuilderExtended;

public class ConsoleHelper {
	
	private ConsoleHelper() {};
	
	/**
	 * print the message in the system console as error
	 * @param errorText
	 */
	public static void error(String errorText) {
		System.err.println(errorText);
	}

	/**
	 * print the exception in the system console as error
	 * @param exception
	 */
	public static void error(TeamGGException exception) {
		
		String stackStrace = formatStackTrace(exception.getStackTrace());
		
		error("("+ exception.getErrorCode() + ") " + exception.getLocalizedMessage() + "\n" + stackStrace);
	}

	/**
	 * print the mesage in the system console as info
	 * @param info
	 */
	public static void info(String info) {
		System.out.println(info);
	}
	
	/**
	 *  print the mesage in the system console as info
	 * @param format
	 * @param args
	 */
	public static void info(String format, Object... args) {
		info(String.format(format, args));
	}
	
	/**
	 * print the mesage in the system console as info
	 * @param info
	 */
	public static void info(StringBuffer info) {
		System.out.println(info);
		
	}
	/**
	 * print the mesage in the system console as info
	 * @param info
	 */
	public static void info(Reader info) {
		System.out.println(info);
		
	}
	
	/**
	 * format the stack trace to be readable
	 * @param stackTrace
	 * @return
	 */
	private static String formatStackTrace(StackTraceElement[] stackTrace) {
		
		StringBuilderExtended formattedStackTraceBuilder = new StringBuilderExtended();
		
		for(StackTraceElement stackTraceElement: stackTrace) {
			formattedStackTraceBuilder.appendNewLine(stackTraceElement.toString());
		}
		
		return formattedStackTraceBuilder.toString();
	}

	
	


}
