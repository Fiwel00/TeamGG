package teamgg.database.client;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.combine;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import errorhandling.TeamGGException;
import teamgg.data.match.dto.Match;
import teamgg.data.match.dto.Player;
import teamgg.data.match.dto.Team;
import teamgg.data.relationship.dto.Relationship;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.MatchesFieldEnum;
import teamgg.database.fields.PlayersFieldsEnum;
import teamgg.database.fields.RelationshipFieldsEnum;

public class DBWriteClient extends MongoTeamGGClient {

	public DBWriteClient() throws TeamGGException {
		super();
	}

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

	/**
	 * 
	 * @param relationships
	 * @throws TeamGGException
	 */
	public static void addRelationShips(List<Relationship> relationships) throws TeamGGException {
		try (MongoClient client = createNewMongoInstance()) {

			MongoCollection<Document> relationshipCollection = getRelationshipColletion();

			List<Document> newRelationshipDocuments = new ArrayList<>();

			for (Relationship relationship : relationships) {
				if (relationship.isExistInDb() == false) {
					Document newRelationshipDocument = new Document(RelationshipFieldsEnum.ACCOUNT_ID_1.toString(),
							relationship.getAccountId1())
									.append(RelationshipFieldsEnum.ACCOUNT_ID_2.toString(),
											relationship.getAccountId2())
									.append(RelationshipFieldsEnum.FIRST_ENCOUNTER_AGAINST.toString(),
											relationship.getFirstEncounterAgainst())
									.append(RelationshipFieldsEnum.FIRST_ENCOUNTER_WITH.toString(),
											relationship.getFirstEncounterWith())
									.append(RelationshipFieldsEnum.LAST_ENCOUNTER_AGAINST.toString(),
											relationship.getLastEncounterAgainst())
									.append(RelationshipFieldsEnum.LAST_ENCOUNTER_WITH.toString(),
											relationship.getLastEncounterWith())
									.append(RelationshipFieldsEnum.LOST_AGAINST.toString(),
											relationship.getLostAgainst())
									.append(RelationshipFieldsEnum.LOST_WITH.toString(), relationship.getLostWith())
									.append(RelationshipFieldsEnum.WON_AGAINST.toString(), relationship.getWonAgainst())
									.append(RelationshipFieldsEnum.WON_WITH.toString(), relationship.getWonWith());

					newRelationshipDocuments.add(newRelationshipDocument);
				} else {

					Bson filter = and(
							asList(eq(RelationshipFieldsEnum.ACCOUNT_ID_1.toString(), relationship.getAccountId1()),
									eq(RelationshipFieldsEnum.ACCOUNT_ID_2.toString(), relationship.getAccountId2())));
					Bson updateFirstEncounterAgainst = set(RelationshipFieldsEnum.FIRST_ENCOUNTER_AGAINST.toString(),
							relationship.getFirstEncounterAgainst());
					Bson updateFirstEncounterWith = set(RelationshipFieldsEnum.FIRST_ENCOUNTER_WITH.toString(),
							relationship.getFirstEncounterWith());
					Bson updateLastEncounterAgainst = set(RelationshipFieldsEnum.LAST_ENCOUNTER_AGAINST.toString(),
							relationship.getLastEncounterAgainst());
					Bson updateLastEncounterWith = set(RelationshipFieldsEnum.LAST_ENCOUNTER_WITH.toString(),
							relationship.getLastEncounterWith());
					Bson updateLostAgainst = set(RelationshipFieldsEnum.LOST_AGAINST.toString(),
							relationship.getLostAgainst());
					Bson updateLostWith = set(RelationshipFieldsEnum.LOST_WITH.toString(), relationship.getLostWith());
					Bson updateWonAgainst = set(RelationshipFieldsEnum.WON_AGAINST.toString(),
							relationship.getWonAgainst());
					Bson updateWonWith = set(RelationshipFieldsEnum.WON_WITH.toString(), relationship.getWonWith());

					Bson updates = combine(updateWonWith, updateFirstEncounterAgainst, updateFirstEncounterWith,
							updateLastEncounterAgainst, updateLastEncounterWith, updateLostAgainst, updateLostWith,
							updateWonAgainst);
					
					//update one at a time
					relationshipCollection.updateOne(filter, updates);
				}

			}
			//write in bulk the new one
			if(newRelationshipDocuments.isEmpty() == false) {
				relationshipCollection.insertMany(newRelationshipDocuments);
			}
		}
	}

	public static void addNewMatch(Match match) throws TeamGGException {
		try (MongoClient client = createNewMongoInstance()) {

			MongoCollection<Document> matchesCollection = getMatchesColletion();
			Document matchDocument = new Document(MatchesFieldEnum.GAME_ID.toString(), match.getGameId())
					.append(MatchesFieldEnum.PLATFORM_ID.toString(), match.getPlatformId())
					.append(MatchesFieldEnum.TIME_STAMP.toString(), match.getTimestamp());

			List<Document> teamsDocument = new ArrayList<>();

			for (Team team : match.getTeams()) {

				List<Document> playersDocument = new ArrayList<>();

				for (Player player : team.getPlayers()) {
					Document playerDocument = new Document(MatchesFieldEnum.ACCOUNT_ID.toString(),
							player.getAccountId())
									.append(MatchesFieldEnum.CHAMPION_ID.toString(), player.getChampionId())
									.append(MatchesFieldEnum.SUMMONER_ID.toString(), player.getSummonerId())
									.append(MatchesFieldEnum.SUMMONER_NAME.toString(), player.getSummonerName())
									.append(MatchesFieldEnum.TEAM_ID.toString(), player.getTeamId());
					playersDocument.add(playerDocument);
				}

				Document teamDocument = new Document(MatchesFieldEnum.PLAYERS.toString(), playersDocument)
						.append(MatchesFieldEnum.TEAM_ID.toString(), team.getTeamId())
						.append(MatchesFieldEnum.WON.toString(), team.isWon());
				teamsDocument.add(teamDocument);
			}

			matchDocument.append(MatchesFieldEnum.TEAMS.toString(), teamsDocument);

			matchesCollection.insertOne(matchDocument);
		}
	}

}
