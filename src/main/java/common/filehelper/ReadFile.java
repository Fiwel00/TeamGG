package common.filehelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;
import errorhandling.customexception.FilePathIsEmptyException;

public class ReadFile {

	public static String read(String filePath) throws FileNotFoundException {
		try {
			ConsoleHelper.info(ReadFile.class, "reading file from pah: %s", filePath);
			InputStream inputStream = ReadFile.class.getClassLoader().getResourceAsStream(filePath);
			checkInputStreamIsLoaded(inputStream);
			
			InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			
			return new BufferedReader(streamReader).lines().collect(Collectors.joining("\n"));
		
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	
	}

	/**
	 * 
	 * @param relativPath
	 * @return
	 * @throws IOException
	 * @throws FilePathIsEmptyException
	 * @throws FileNotFoundException
	 */
	public static InputStream getFileInputStream(String relativPath) throws TeamGGException {
		
		ConsoleHelper.info(ReadFile.class, "current working directory path: %s", getWorkspacePath());
		checkFilePathIsDefined(relativPath);

		String absolutPath = getAbsolutPath(relativPath);
		ConsoleHelper.info(ReadFile.class, "searching file path %s", absolutPath);

        File configFile = new File(absolutPath);
		
		return createInputStream(configFile);
	}

	private static FileInputStream createInputStream(File configFile) throws TeamGGException{
		try {
			return new FileInputStream(configFile);
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}
	
	private static String getAbsolutPath(String filePath) {
		return getWorkspacePath() + filePath;
	}

	private static String getWorkspacePath() {
		return System.getProperty("user.dir");
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
