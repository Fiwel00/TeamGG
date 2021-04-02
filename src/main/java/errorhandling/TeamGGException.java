package errorhandling;

import java.io.InputStream;
import java.util.Properties;

public class TeamGGException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4309643294917310564L;
	


	private final int errorCode;
	private final String localizedMessage;
	
	private static final String ERROR_MESSAGE_PATH = "resources/errormessages/error.properties";
	
	
	public TeamGGException(int errorCode) {
	
		this.errorCode = errorCode;
		this.localizedMessage = findLocalizedMessage(errorCode);
	}

	/**
	 * find the corresponding error message to the error code
	 * TODO localise it
	 * @param errorCode
	 * @return
	 */
	private String findLocalizedMessage(int errorCode) {
		
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ERROR_MESSAGE_PATH);
		
		String message;
		try {
			Properties errorProperties = new Properties();
			errorProperties.load(inputStream);
			message = errorProperties.getProperty(Integer.toString(errorCode));

		} catch (Exception e) {
			message = "Error property file is not configured correctly";
		}

		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	
}
