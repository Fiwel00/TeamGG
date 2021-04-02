package errorhandling.customexception;

import errorhandling.TeamGGException;

public class FileNotFoundException extends TeamGGException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229137485913817858L;

	
	public FileNotFoundException() {
		super(2);
	}
}
