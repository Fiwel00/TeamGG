package teamgg.database.client.player;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import teamgg.data.summonerinfo.SummonerInfoFactory;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.PlayersDBFields;

public class DBReadPlayer extends MongoTeamGGClient{

	
	public DBReadPlayer() throws TeamGGException {
		super();
	}

	/**
	 * 
	 * @param summonerIdentifier
	 * @return
	 * @throws TeamGGException
	 */
	public static SummonerInfo readPlayer(PlayersDBFields field, String summonerIdentifier) throws TeamGGException {
		
		SummonerInfo summonerInfo = null;
		try (MongoClient client = createNewMongoInstance()) {
			ConsoleHelper.info("Searching DB Player Collection %s" , field.toString(), summonerIdentifier);
			
			if (field.equals(PlayersDBFields.SUMMONER_NAME_LW)) {
				summonerIdentifier = summonerIdentifier.toLowerCase();
			}
			
			Document summonerDocument = getPlayersColletion().find(eq(field.toString(), summonerIdentifier)).first();
			
			if ((summonerDocument == null ) == false) {
				ConsoleHelper.info(summonerDocument.toJson());
				summonerInfo = SummonerInfoFactory.createSummmonerInfo(summonerDocument);
				
			}
		}
		return summonerInfo;
	}
	
}
