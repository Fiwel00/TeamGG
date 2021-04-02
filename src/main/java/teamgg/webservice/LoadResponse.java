package teamgg.webservice;

import errorhandling.TeamGGException;

public class LoadResponse implements AutoCloseable{

	private int status;
	private Object serializedResponse;

	public LoadResponse() {
		
	}
	
	public LoadResponse(int responseCode, Object content) {
		setStatus(responseCode);
		setSerializedResponse(content);
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Object getSerializedResponse() {
		return serializedResponse;
	}

	public void setSerializedResponse(Object serializedResponse) {
		this.serializedResponse = serializedResponse;
	}

	@Override
	public void close() throws TeamGGException {
		
	}

}
