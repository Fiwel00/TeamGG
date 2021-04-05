package teamgg.database.client.player;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import errorhandling.TeamGGException;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.PlayersDBFields;

public class DBWritePlayer extends MongoTeamGGClient {


	/**
	 * 
	 * @param summonerInfo
	 * @throws TeamGGException
	 */
	public static void addNewPlayer(SummonerInfo summonerInfo) throws TeamGGException {

		try (MongoClient client = createNewMongoInstance()) {

			MongoCollection<Document> playerCollection = getPlayersColletion();

			Document playerDocument = new Document(PlayersDBFields.PUU_ID.toString(), summonerInfo.getPuuid());
			playerDocument.append(PlayersDBFields.SUMMONER_NAME.toString(), summonerInfo.getName())
					.append(PlayersDBFields.SUMMONER_NAME_LW.toString(), summonerInfo.getName().toLowerCase())
					.append(PlayersDBFields.ACCOUNT_ID.toString(), summonerInfo.getAccountId())
					.append(PlayersDBFields.PROFILE_ICON_ID.toString(), summonerInfo.getProfileIconId())
					.append(PlayersDBFields.SUMMONER_ID.toString(), summonerInfo.getSummonerId())
					.append(PlayersDBFields.REVISION_DATE.toString(), summonerInfo.getRevisionDate())
					.append(PlayersDBFields.SUMMONER_LEVEL.toString(), summonerInfo.getSummonerLevel());

			playerCollection.insertOne(playerDocument);

		}
	}
	
	public static void updatePlayer(SummonerInfo summonerInfo) throws TeamGGException {
		
		try (MongoClient client = createNewMongoInstance()) {

			MongoCollection<Document> playerCollection = getPlayersColletion();

			Bson filter = eq(PlayersDBFields.PUU_ID.toString(), summonerInfo.getPuuid());

			Bson summonerName = set(PlayersDBFields.SUMMONER_NAME.toString(), summonerInfo.getName());
			Bson summonerNameLW = set(PlayersDBFields.SUMMONER_NAME_LW.toString(), summonerInfo.getName().toLowerCase());
			Bson profileIconID = set(PlayersDBFields.PROFILE_ICON_ID.toString(), summonerInfo.getProfileIconId());
			Bson summonerID = set(PlayersDBFields.SUMMONER_ID.toString(), summonerInfo.getSummonerId());
			Bson revisionDate = set(PlayersDBFields.REVISION_DATE.toString(), summonerInfo.getRevisionDate());
			Bson summonerLevel = set(PlayersDBFields.SUMMONER_LEVEL.toString(), summonerInfo.getSummonerLevel());
			
			Bson update = combine(summonerName, summonerNameLW, profileIconID,	summonerID, revisionDate, summonerLevel);
			
			playerCollection.updateOne(filter, update);

		}
		
	}


}
