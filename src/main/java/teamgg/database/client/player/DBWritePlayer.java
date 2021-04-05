package teamgg.database.client.player;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import errorhandling.TeamGGException;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.PlayersFieldsEnum;

public class DBWritePlayer extends MongoTeamGGClient {


	/**
	 * 
	 * @param summonerInfo
	 * @throws TeamGGException
	 */
	public static void addNewPlayer(SummonerInfo summonerInfo) throws TeamGGException {

		try (MongoClient client = createNewMongoInstance()) {

			MongoCollection<Document> playerCollection = getPlayersColletion();

			Document playerDocument = new Document(PlayersFieldsEnum.PUU_ID.toString(), summonerInfo.getPuuid());
			playerDocument.append(PlayersFieldsEnum.SUMMONER_NAME.toString(), summonerInfo.getName())
					.append(PlayersFieldsEnum.SUMMONER_NAME_LW.toString(), summonerInfo.getName().toLowerCase())
					.append(PlayersFieldsEnum.ACCOUNT_ID.toString(), summonerInfo.getAccountId())
					.append(PlayersFieldsEnum.PROFILE_ICON_ID.toString(), summonerInfo.getProfileIconId())
					.append(PlayersFieldsEnum.SUMMONER_ID.toString(), summonerInfo.getSummonerId())
					.append(PlayersFieldsEnum.REVISION_DATE.toString(), summonerInfo.getRevisionDate())
					.append(PlayersFieldsEnum.SUMMONER_LEVEL.toString(), summonerInfo.getSummonerLevel());

			playerCollection.insertOne(playerDocument);

		}
	}


}
