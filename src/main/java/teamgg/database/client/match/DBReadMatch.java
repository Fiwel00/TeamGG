package teamgg.database.client.match;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import teamgg.data.match.MatchFactory;
import teamgg.data.match.dto.Match;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.MatchesFieldEnum;

public class DBReadMatch extends MongoTeamGGClient{


	/**
	 * 
	 * @param gameId
	 * @return
	 * @throws TeamGGException
	 */
	public static Match readMatch(long gameId) throws TeamGGException {
		Match match = null;
		try (MongoClient client = createNewMongoInstance()) {
			
			Document matchDocument = getMatchesColletion().find(eq(MatchesFieldEnum.GAME_ID.toString(), gameId)).first();
			
			if ((matchDocument == null ) == false) {
				ConsoleHelper.info(matchDocument.toJson());
				match = MatchFactory.createMatch(matchDocument);
			}
		}
		return match;
	}
}
