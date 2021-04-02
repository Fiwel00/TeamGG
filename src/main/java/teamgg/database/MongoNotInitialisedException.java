package teamgg.database;

import errorhandling.TeamGGException;

public class MongoNotInitialisedException extends TeamGGException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MongoNotInitialisedException() {
		super(5);
	}

}
