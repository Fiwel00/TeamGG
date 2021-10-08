package errorhandling;

import java.io.Reader;

import common.stringhelper.StringBuilderExtended;

public class ConsoleHelper {
	
	private static final String INFO_MESSAGE_FORMAT = "Info: %s> %s";

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
	 * @param <T>
	 * @param Class
	 * @param info
	 */
	public static <T> void info(Class<T> classname, String info) {
		System.out.println(String.format(INFO_MESSAGE_FORMAT, classname.toString(), info));
	}
	
	/**
	 *  print the mesage in the system console as info
	 * @param <T>
	 * @param format
	 * @param args
	 */
	public static <T> void info(Class<T> classname, String format, Object... args) {
		info(classname, String.format(format, args));
	}
	
	/**
	 * print the mesage in the system console as info
	 * @param <T>
	 * @param info
	 */
	public static <T> void info(Class<T> classname, StringBuffer info) {
		System.out.println(String.format(INFO_MESSAGE_FORMAT, classname.toString(), info));
		
	}
	/**
	 * print the mesage in the system console as info
	 * @param <T>
	 * @param info
	 */
	public static <T> void info(Class<T> classname, Reader info) {
		System.out.println(String.format(INFO_MESSAGE_FORMAT, classname.toString(), info));
		
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
