package teamgg.data.summonerinfo;

import static javax.xml.bind.JAXBContext.newInstance;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.transform.stream.StreamSource;

import org.bson.Document;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;

import com.merakianalytics.orianna.types.core.summoner.Summoner;

import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.fields.PlayersDBFields;

public class SummonerInfoFactory {

	
	private static JAXBContext jc;

	/**
	 * 
	 * @param summonerDocument
	 * @return
	 */
	public static SummonerInfo createSummmonerInfo(Document summonerDocument) {
		SummonerInfo summonerInfo = new SummonerInfo();
		
		summonerInfo.setAccountId(summonerDocument.getString(PlayersDBFields.ACCOUNT_ID.toString()));
		summonerInfo.setSummonerId(summonerDocument.getString(PlayersDBFields.SUMMONER_ID.toString()));
		summonerInfo.setName(summonerDocument.getString(PlayersDBFields.SUMMONER_NAME.toString()));
		summonerInfo.setProfileIconId(summonerDocument.getInteger(PlayersDBFields.PROFILE_ICON_ID.toString()));
		summonerInfo.setPuuid(summonerDocument.getString(PlayersDBFields.PUU_ID.toString()));
		summonerInfo.setRevisionDate(summonerDocument.getLong(PlayersDBFields.REVISION_DATE.toString()));
		summonerInfo.setSummonerLevel(summonerDocument.getLong(PlayersDBFields.SUMMONER_LEVEL.toString()));
		
		marshall(summonerInfo);
		
		return summonerInfo;
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 */
	public static SummonerInfo createSummmonerInfo(String jsonInput){
		
		SummonerInfo summonerInfo = null;
		try {

			JAXBUnmarshaller unmarshaller = getJc().createUnmarshaller();
			StreamSource jsonStreamSource = new StreamSource(new StringReader(jsonInput));
			summonerInfo = (SummonerInfo) unmarshaller.unmarshal(jsonStreamSource, SummonerInfo.class).getValue();

			marshall(summonerInfo);

		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		
		return summonerInfo;
		
	}
	
	public static SummonerInfo createSummmonerInfo(Summoner summoner) {
		
		SummonerInfo summonerInfo = new SummonerInfo();
		summonerInfo.setAccountId(summoner.getAccountId());
		summonerInfo.setId(summoner.getId());
		summonerInfo.setName(summoner.getName());
		
		int iconId = -1;
		if ((summoner.getProfileIcon() == null) == false) {
			iconId = summoner.getProfileIcon().getId();
		}
		
		summonerInfo.setProfileIconId(iconId);
		summonerInfo.setPuuid(summoner.getPuuid());
		summonerInfo.setRevisionDate(summoner.getUpdated().getMillis());
		summonerInfo.setSummonerLevel(summoner.getLevel());
	
		return summonerInfo;
	}

	/**
	 * 
	 * @param summonerInfo
	 */
	private static void marshall(SummonerInfo summonerInfo){
		try {
			Marshaller marshaller = getJc().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(summonerInfo, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @return
	 * @throws JAXBException
	 */
	private static JAXBContext getJc() throws JAXBException {
		if (jc == null) { 
			initJc();
		}

		return jc;
	}

	/**
	 * create jax b context
	 * @throws JAXBException
	 */
	private static void initJc () throws JAXBException {
		
		Map<String, Object> properties = new HashMap<>(2);
		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		properties.put(JAXBContextProperties.JSON_ATTRIBUTE_PREFIX, "@");
		
		jc = (JAXBContext) newInstance(new Class[] { SummonerInfo.class }, properties);
	}

	



	
}
