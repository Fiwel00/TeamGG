package teamgg.data;

import errorhandling.TeamGGException;

public class InvalidUpdatePipelineException extends TeamGGException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String field;
	private final Object value;

	public InvalidUpdatePipelineException(String field, Object value) {
		super(7);
		this.field = field;
		this.value = value;
	}


	public String getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}

}
