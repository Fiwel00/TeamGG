package errorhandling.customexception;

import errorhandling.TeamGGException;

public class FilePathIsEmptyException extends TeamGGException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982096224655463880L;

	public FilePathIsEmptyException() {
		super(1);
	}

}
