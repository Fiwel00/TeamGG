package common.stringhelper;

/**
 * 
 * @author Mehdi Ayadi
 *
 */
public class StringBuilderExtended {

	private static final String COLON = ": ";
	private static final String NEW_LINE = "\n";
	private StringBuilder stringbuilder;
	
	/**
	 * constructor
	 * 
	 */
	public StringBuilderExtended() {
		stringbuilder = new StringBuilder();
	}
	
	/**
	 * constructor
	 * inits a stringBuilder with the passed String
	 * 
	 * @param baseString
	 */
	public StringBuilderExtended(String baseString) {
		stringbuilder = new StringBuilder();
		stringbuilder.append(baseString);
		appendNewLine();
	}

	/**
	 * appends a new line character
	 */
	private void appendNewLine() {
		stringbuilder.append(NEW_LINE);
	}
	
	/**
	 *  appends a new line to the stringbuilder with designated name and value separated by a colon
	 * @param name
	 * @param value
	 */
	public void appendNewLine(String name, String value) {
		stringbuilder.append(name);
		stringbuilder.append(COLON);
		stringbuilder.append(value);
		appendNewLine();
	}
	
	/**
	 *  appends a new line to the stringbuilder
	 * @param value
	 */
	public void appendNewLine(String value) {
		stringbuilder.append(value);
		appendNewLine();
	}
	
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return stringbuilder.toString();
	}

}
