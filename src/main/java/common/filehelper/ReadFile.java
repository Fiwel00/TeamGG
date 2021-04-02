package common.filehelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;
import errorhandling.customexception.FilePathIsEmptyException;

public class ReadFile {

	public static String read(String filePath) throws FilePathIsEmptyException, FileNotFoundException {
		InputStream inputStream = ReadFile.class.getClassLoader().getResourceAsStream(filePath);
		checkInputStreamIsLoaded(inputStream);
		
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		
		return new BufferedReader(streamReader).lines().collect(Collectors.joining("\n"));
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws FilePathIsEmptyException
	 * @throws FileNotFoundException
	 */
	public static InputStream getFileInputStream(String filePath) throws TeamGGException {
		
		checkFilePathIsDefined(filePath);
		InputStream inputStream = ReadFile.class.getClassLoader().getResourceAsStream(filePath);
		checkInputStreamIsLoaded(inputStream);
		
		return inputStream;
	}
	
	/**
	 * 
	 * @param inputStream
	 * @throws FileNotFoundException 
	 */
	private static void checkInputStreamIsLoaded(InputStream inputStream) throws FileNotFoundException {
		if(inputStream == null) {
			throw new FileNotFoundException();
		}
		
	}

	/**
	 * 
	 * @param filePath
	 * @throws FilePathIsEmptyException
	 */
	private static void checkFilePathIsDefined(String filePath) throws FilePathIsEmptyException {
		if(StringUtils.isEmpty(filePath)) {
			throw new FilePathIsEmptyException();
		}
	}
}
