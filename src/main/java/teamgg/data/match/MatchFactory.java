package teamgg.data.match;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.merakianalytics.orianna.types.core.match.Participant;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import errorhandling.TeamGGException;
import teamgg.data.match.dto.Match;
import teamgg.data.match.dto.MatchRaw;
import teamgg.data.match.dto.ParticipantDto;
import teamgg.data.match.dto.ParticipantIdentityDto;
import teamgg.data.match.dto.Player;
import teamgg.data.match.dto.Team;
import teamgg.data.match.dto.TeamStatsDto;
import teamgg.database.fields.MatchesFieldEnum;

public class MatchFactory {

	/**
	 * 
	 * @param matchRaw
	 * @return
	 * @throws TeamGGException 
	 */
	public static Match createMatch(MatchRaw matchRaw) throws TeamGGException {

		Match match = new Match();
		
		List<Team> teams = new ArrayList<>();
		List<TeamStatsDto> teamsRaw = matchRaw.getTeams();

		for (TeamStatsDto teamRaw : teamsRaw) {
			
			List<Player> players = getPlayers(matchRaw, teamRaw);
			
			Team team = new Team(teamRaw.getTeamId(), teamRaw.getWin(), players);
			teams.add(team);
		}
		
		match.setTeams(teams);
		
		match.setPlatformId(matchRaw.getPlatformId());
		match.setGameId(matchRaw.getGameId());
		match.setTimestamp(matchRaw.getGameCreation());
		
		return match;
	}

	/**
	 * 
	 * @param matchRaw
	 * @param teamRaw
	 * @return
	 * @throws TeamGGException 
	 */
	private static List<Player> getPlayers(MatchRaw matchRaw, TeamStatsDto teamRaw) throws TeamGGException {
		List<Player> players = new ArrayList<>();
		
		for (ParticipantDto participantDto : matchRaw.getParticipants()) {
			
			if (teamRaw.getTeamId() == participantDto.getTeamId()) {
				
				ParticipantIdentityDto participantIdentityDto = retrieveParticipant(participantDto, matchRaw.getParticipantIdentities());
				
				Player player = new Player();
				
				player.setAccountId(participantIdentityDto.getPlayer().getCurrentAccountId());
				player.setSummonerId(participantIdentityDto.getPlayer().getSummonerId());
				player.setSummonerName(participantIdentityDto.getPlayer().getSummonerName());
				player.setChampionId(participantDto.getChampionId());
				player.setTeamId(participantDto.getTeamId());
				
				players.add(player);
			}
		}
		return players;
	}

	/**
	 * 
	 * @param participantDto
	 * @param participantIdentities
	 * @return
	 * @throws TeamGGException 
	 */
	private static ParticipantIdentityDto retrieveParticipant(ParticipantDto participantDto, List<ParticipantIdentityDto> participantIdentities) throws TeamGGException {
		
		for (ParticipantIdentityDto participantIdentityDto : participantIdentities) {
			if(participantDto.getParticipantId() == participantIdentityDto.getParticipantId()){
				return participantIdentityDto;
			}
		}
		
		throw new NoCorrespondingParticipantFoundException();
	}

	/**
	 * 
	 * @param matchDocument
	 * @return
	 */
	public static Match createMatch(Document matchDocument) {
		
		Match match = new Match();
		
		match.setGameId(matchDocument.getLong(MatchesFieldEnum.GAME_ID.toString()));
		match.setPlatformId(matchDocument.getString(MatchesFieldEnum.PLATFORM_ID.toString()));
		match.setTimestamp(matchDocument.getLong(MatchesFieldEnum.TIME_STAMP.toString()));

		List<Document> teamsDocument = (List<Document>) matchDocument.get(MatchesFieldEnum.TEAMS.toString());
		List<Team> teams = new ArrayList<>();
		for (Document teamDocument : teamsDocument) {
			
			Team team = new Team();
			team.setWon(teamDocument.getBoolean(MatchesFieldEnum.WON.toString()));
			team.setTeamId(teamDocument.getInteger(MatchesFieldEnum.TEAM_ID.toString()));
			
			List<Document> playersDocument = (List<Document>) teamDocument.get(MatchesFieldEnum.PLAYERS.toString());
			List<Player> players = new ArrayList<>();
			for (Document playerDocument : playersDocument) {
				
				Player player = new Player();
				player.setAccountId(playerDocument.getString(MatchesFieldEnum.ACCOUNT_ID.toString()));
				player.setChampionId(playerDocument.getInteger(MatchesFieldEnum.CHAMPION_ID.toString()));
				player.setSummonerId(playerDocument.getString(MatchesFieldEnum.SUMMONER_ID.toString()));
				player.setSummonerName(playerDocument.getString(MatchesFieldEnum.SUMMONER_NAME.toString()));
				player.setTeamId(playerDocument.getInteger(MatchesFieldEnum.TEAM_ID.toString()));
				
				players.add(player);
				
			}
			team.setPlayers(players);
			teams.add(team);
		}
		
		return match;
	}

	public static Match createMatch(com.merakianalytics.orianna.types.core.match.Match matchOri) {

		Match match = new Match();

		match.setGameId(matchOri.getId());
		match.setPlatformId(matchOri.getPlatform().getTag());
		match.setTimestamp(matchOri.getCreationTime().getMillis());
		
		List<Team> teams = new ArrayList<>();

		com.merakianalytics.orianna.types.core.match.Team teamOriBlue = matchOri.getBlueTeam();
		Team teamBlue = getTeam(teamOriBlue);
		teams.add(teamBlue);
		
		com.merakianalytics.orianna.types.core.match.Team teamOriRed = matchOri.getRedTeam();
		Team teamRed = getTeam(teamOriRed);
		
		teams.add(teamRed);
		match.setTeams(teams);
		return match;
	}

	private static Team getTeam(com.merakianalytics.orianna.types.core.match.Team teamOri) {
		Team team = new Team();
		team.setWon(teamOri.isWinner());
		team.setTeamId(teamOri.getSide().getId());
		team.setPlayers(getPlayers(teamOri.getParticipants()));
		
		return team;
	}

	private static List<Player> getPlayers(SearchableList<Participant> participants) {

		List<Player> players = new ArrayList<>();
		for (Participant participant : participants) {
			Summoner summoner = participant.getSummoner();
			
			Player player = new Player();
			player.setAccountId(summoner.getAccountId());
			player.setChampionId(participant.getChampion().getId());
			player.setSummonerId(summoner.getId());
			player.setSummonerName(summoner.getName());
			player.setTeamId(participant.getTeam().getSide().getId());
			
			players.add(player);
		}
		
		return players;
	}

}
