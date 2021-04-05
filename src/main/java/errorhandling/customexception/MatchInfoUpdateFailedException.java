package errorhandling.customexception;

import errorhandling.TeamGGException;

public class MatchInfoUpdateFailedException extends TeamGGException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5884546610385655051L;

	public MatchInfoUpdateFailedException() {
		super(8);
	}

}
