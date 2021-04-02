package teamgg.webservice;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;

import errorhandling.ConsoleHelper;
import teamgg.webservice.common.ParameterStringBuilder;

/**
 * 
 * @author Mehdi Ayadi
 * @deprecated use Orianna abstraction layer
 * @Ignore
 *	class that will be marshalled / unmarshalled for external API
 * @param <T>
 */
public class UrlConnectionWrapper<T> {

	private String url;
	private Map<String, String> params;
	private JAXBContext jc;
	private Class<?> responseClass;

	public UrlConnectionWrapper(String uri, Class<?> responseClass) {
		setUrl(uri);
		setParams(new HashMap<String, String>());
		setResponseClass(responseClass);

		Map<String, Object> properties = new HashMap<String, Object>(2);
		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		properties.put(JAXBContextProperties.JSON_ATTRIBUTE_PREFIX, "@");

		try {
			setJc((JAXBContext) JAXBContext.newInstance(new Class[] { getResponseClass() }, properties));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @deprecated LOL why did i build this function here
	 * @param responseObject
	 * @return
	 * @throws JAXBException
	 */
	public String marshall (Object responseObject) throws JAXBException {
		Marshaller marshaller = getJc().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter stringwrite = new StringWriter();
		marshaller.marshal(responseObject, stringwrite);
		
		return stringwrite.toString();
	}

	/**
	 * get call that can be enriched with url params through addParam
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public LoadResponse load() throws MalformedURLException, UnsupportedEncodingException {
		LoadResponse response = null;

		String params = ParameterStringBuilder.getParamsString(this.getParams());
		URL urlConnector = new URL(this.getUrl() + "?" + params);

		try {

			ConsoleHelper.info("URL call : " + this.getUrl() + "?" + params);
			HttpURLConnection connection = (HttpURLConnection) urlConnector.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();
			ConsoleHelper.info("\nStatus: " + responseCode);

			Object content = null;
			switch (responseCode) {
			
				case 200:
					BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					content = retrieveContent(bufferedInputStream);
					bufferedInputStream.close();
					break;
				case 429:
					ConsoleHelper.error("API rate limit reached");
					break;
				case 404:
					ConsoleHelper.error("Info not found ...");
					break;
				case 500:
					ConsoleHelper.error("Server internal error ...");
					break;
				case 502:
					ConsoleHelper.error("Bad gateway ...");
					break;
				case 503:
					ConsoleHelper.error("Gateway timeout ...");
					break;
				case 504:
					ConsoleHelper.error("Gateway timeout ...");
					break;
				default:
					//todo exception
					ConsoleHelper.error("\n Response Code couldn't be handled");
			}
			
			response = new LoadResponse(responseCode, content);

			connection.disconnect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return response;
	}

	/**
	 * async load with a retry functionality when being blocked by the api rate limit
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public CompletableFuture<LoadResponse> loadRetry() throws MalformedURLException, UnsupportedEncodingException {
		LoadResponse response = load();
		
		//if too many requests then try again 120 secs later
		while (response.getStatus() == 429 || response.getStatus() == 504 || response.getStatus() == 503 || response.getStatus() == 502 || response.getStatus() == 500 || response.getStatus() == 404) {
			try {
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response = load();
		}
		
		return completedFuture(response);
		
	}
	
	/**
	 * retrieve json content and marshall it to the specified class given in the constructor
	 * @param bufferedInputStream
	 * @return
	 * @throws IOException
	 */
	private Object retrieveContent(BufferedReader bufferedInputStream) throws IOException {
		String inputLine = bufferedInputStream.readLine();

		StringBuffer content = new StringBuffer();
		while (inputLine != null) {
			content.append(inputLine);
			inputLine = bufferedInputStream.readLine();
		}

		Object responseObject = null;
		try {

			JAXBUnmarshaller unmarshaller = getJc().createUnmarshaller();
			StreamSource json = new StreamSource(new StringReader(content.toString()));
			responseObject = ((javax.xml.bind.Unmarshaller) unmarshaller).unmarshal(json, getResponseClass())
					.getValue();

			Marshaller marshaller = getJc().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(responseObject, System.out);

		} catch (PropertyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (javax.xml.bind.JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return responseObject;
	}

	/**
	 * add Get paramater that will be used when the
	 * 
	 * @param param
	 * @param value
	 */
	public void addParam(String param, String value) {
		this.getParams().put(param, value);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> hashMap) {
		this.params = hashMap;
	}

	public JAXBContext getJc() {
		return jc;
	}

	public void setJc(JAXBContext jc) {
		this.jc = jc;
	}

	public Class<?> getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(Class<?> responseClass) {
		this.responseClass = responseClass;
	}

}
