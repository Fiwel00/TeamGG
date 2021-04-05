package teamgg.database.client.match;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import errorhandling.TeamGGException;
import teamgg.data.match.dto.Match;
import teamgg.data.match.dto.Player;
import teamgg.data.match.dto.Team;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.MatchesFieldEnum;

public class DBWriteMatch extends MongoTeamGGClient {


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
