package teamgg.ui.components.interactive.loadprofile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.SwingWorker;

import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.Participant;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import teamgg.api.ApiLeagueOfLegends;
import teamgg.data.InvalidUpdatePipelineException;
import teamgg.data.match.MatchFactory;
import teamgg.data.relationship.dto.Relationship;
import teamgg.data.staticmodel.ProfileInformation;
import teamgg.data.summonerinfo.SummonerInfoFactory;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.client.match.DBReadMatch;
import teamgg.database.client.match.DBWriteMatch;
import teamgg.database.client.player.DBReadPlayer;
import teamgg.database.client.player.DBWritePlayer;
import teamgg.database.client.relationship.DBReadRelationship;
import teamgg.database.client.relationship.DBWriteRelationship;
import teamgg.database.fields.PlayersDBFields;

public class LoadProfileSW extends SwingWorker<Integer, Void>{


	@Override
	protected Integer doInBackground() throws TeamGGException {

		long currentTimeMinus10Days = System.currentTimeMillis() - ((24*60*60*1000) * 10);
		
		String summonerName = ProfileInformation.getSummonerNameInput();
		ConsoleHelper.info(this.getClass(), "Summoner name in input is %s", summonerName);
		
		SummonerInfo summoner = checkDataBase(summonerName);
		
		if (summoner == null) {
			ConsoleHelper.info(this.getClass(), "Database yielded no result, loading from api");
			
			summoner = ApiLeagueOfLegends.loadPlayerOri(summonerName);
			addPlayerToDB(summoner);
		} else if (summoner.getRevisionDate() < currentTimeMinus10Days) {
			ConsoleHelper.info(this.getClass(), "Database entry is too old, updating ");
			
			summoner = ApiLeagueOfLegends.loadPlayerOri(summonerName);
			updatePlayerToDB(summoner);
		}
		
		updateUI(summoner);

		
		
		loadMatchHistory(summoner);
		return 1;
	}

	private void loadMatchHistory(SummonerInfo summonerInfo) throws TeamGGException {

		Integer playersAdded = 0;
		Integer relationShipsAdded = 0;
		Integer loadedMatchesTotal = 0;
		Integer loadedNewMatches = 0;
		
		//get account id
		String accountId = summonerInfo.getAccountId();
		
		//load from api match history of person
		int beginIndex = 0;
		int endIndex = 100;
		
		playerMatchHistorySearch: while (true) {
			com.merakianalytics.orianna.types.core.match.MatchHistory matchHistory = ApiLeagueOfLegends.loadPlayermatchHistoryOri(accountId, beginIndex, endIndex);
			
			List<Match> matches = getMatches(matchHistory);
			
			for (Match match : matches) {
				int winningTeam = getWinningteam(match);

				List<Relationship> relationships = new ArrayList<>();
				List<Participant> allPlayers = match.getParticipants();
				
				//stack list that gets shorter and shorter for each player
				List<Participant> allPlayersCloned = new ArrayList<>(allPlayers);
				
				for (int i = 0; i < allPlayers.size() - 1; i++) {
					Participant player = allPlayers.get(i);
					allPlayersCloned.remove(player);
					
					for (Participant nextPlayerToCompareTo : allPlayersCloned) {
						Relationship relationship = getRelationship(player, nextPlayerToCompareTo, winningTeam, match.getCreationTime().getMillis());
						
						relationships.add(relationship);
					}
					
				}
				playersAdded += addPlayersToDb(allPlayers);
				addToDb(relationships, match);
				relationShipsAdded += relationships.size();
			}
			
			int loadedMatches = matchHistory.size();
			loadedMatchesTotal += loadedMatches; 
			ConsoleHelper.info(this.getClass(), "Loaded %s matches", loadedMatches);
			if(loadedMatches != 100) {
				break playerMatchHistorySearch;
			}
			beginIndex += 100;
			endIndex += 100;
			loadedNewMatches += matches.size();
		}
		
		ConsoleHelper.info(this.getClass(), "Stats \nnew players added: %s\nnew relationships added: %s \nnew matches added: %s\n skimmed through %s total matches", playersAdded, relationShipsAdded, loadedNewMatches, loadedMatchesTotal);
		
	}

	/**
	 * if the player is not already defined in the db add him
	 * @param allPlayers
	 * @throws TeamGGException
	 */
	private Integer addPlayersToDb(List<Participant> allPlayers) throws TeamGGException {
		
		int updatedSummoners = 0;
		
		for (Participant player : allPlayers) {
			
			Summoner summoner = player.getSummoner();
			
			ConsoleHelper.info(this.getClass(), "Checking if player is in DB Account: %s\n SummonerId: %s\nSummonerName: %s", summoner.getAccountId(), summoner.getId(), summoner.getName());
			if(isPlayerInDb(summoner) == false) {
				ConsoleHelper.info(this.getClass(), "Player doesn't exist adding him to Database");
				addToDb(summoner);
				updatedSummoners++;
			}
		}
		return updatedSummoners;
	}

	/**
	 * 
	 * @param player
	 * @throws TeamGGException 
	 */
	private void addToDb(Summoner summoner) throws TeamGGException {
		SummonerInfo summonerInfo = SummonerInfoFactory.createSummmonerInfo(summoner);		
		DBWritePlayer.addNewPlayer(summonerInfo);
	}

	/**
	 * 
	 * @param player
	 * @return
	 * @throws TeamGGException 
	 */
	private boolean isPlayerInDb(Summoner summoner) throws TeamGGException {
		return DBReadPlayer.readPlayer(PlayersDBFields.ACCOUNT_ID, summoner.getAccountId()) == null ? false : true;
	}


	/**
	 * 
	 * @param relationships
	 * @param match 
	 * @throws TeamGGException 
	 */
	private void addToDb(List<Relationship> relationships, Match match) throws TeamGGException {

		DBWriteRelationship.addRelationShips(relationships);
		DBWriteMatch.addNewMatch(MatchFactory.createMatch(match));
		
	}

	/**
	 * 
	 * @param player
	 * @param nextPlayerToCompareTo
	 * @param winningTeam
	 * @param timestampMatch
	 * @return
	 * @throws TeamGGException 
	 */
	private Relationship getRelationship(Participant player, Participant nextPlayerToCompareTo, int winningTeam, long timestampMatch) throws TeamGGException {
		
		//read db exist already ?
		Relationship relationship = readRelationShipDB(player, nextPlayerToCompareTo);
		//
		
		int playerTeam = player.getTeam().getSide().getId();
		int nextPlayerToCompareToTeam = nextPlayerToCompareTo.getTeam().getSide().getId();
		
		if (playerTeam == nextPlayerToCompareToTeam) {
			//same team	
			
			if (winningTeam == playerTeam) {
				// won together
				relationship.setWonWith(relationship.getWonWith() + 1);
			} else {
				// lost together
				relationship.setLostWith(relationship.getLostWith() + 1);
			}
			
			//determine encountered second time before or after ?
			if (relationship.getFirstEncounterWith() > timestampMatch) {
				relationship.setFirstEncounterWith(timestampMatch);
			}
			if (relationship.getLastEncounterWith() < timestampMatch) {
				relationship.setLastEncounterWith(timestampMatch);
			}
			
		} else {
			//opposing team
			if ((winningTeam == playerTeam) == false) {
				// player won against next player	
				
				relationship.setWonAgainst(relationship.getWonAgainst() + 1);
			} else {
				//next player won against player
				relationship.setLostAgainst(relationship.getLostAgainst() + 1);
			}
			
			//determine encountered second time before or after ?
			if (relationship.getFirstEncounterAgainst() > timestampMatch) {
				relationship.setFirstEncounterAgainst(timestampMatch);
			}
			if (relationship.getLastEncounterAgainst() < timestampMatch) {
				relationship.setLastEncounterAgainst(timestampMatch);
			}
			
		} 
		return relationship;
	}

	/**
	 * read database if relationship already exist
	 * return it if it does, else create a new relationship
	 * @param player
	 * @param nextPlayerToCompareTo
	 * @return
	 * @throws TeamGGException 
	 */
	private Relationship readRelationShipDB(Participant player, Participant nextPlayerToCompareTo) throws TeamGGException {
		
		String accountId1 = player.getSummoner().getAccountId();
		String accountId2 = nextPlayerToCompareTo.getSummoner().getAccountId();
		
		//check db
		Relationship relationship = DBReadRelationship.readRelationShip(accountId1, accountId2);
		
		if (relationship == null) {
			//create relationship
			relationship = new Relationship(false);
			relationship.setAccountId1(accountId1);
			relationship.setAccountId2(accountId2);
			
			long dateNow = new GregorianCalendar(2999, Calendar.DECEMBER, 31).getTimeInMillis();
			//initial values that will get overwritten 
			relationship.setFirstEncounterAgainst(dateNow);
			relationship.setFirstEncounterWith(dateNow);
			
			relationship.setLastEncounterAgainst(0);
			relationship.setLastEncounterWith(0);
			
		}
		return relationship;
	}

	/**
	 * 
	 * @param match
	 * @return
	 */
	private int getWinningteam(Match match) {
		return  match.getBlueTeam().isWinner() ? match.getBlueTeam().getSide().getId() : match.getRedTeam().getSide().getId();
		
	}

	/**
	 * 
	 * @param matchHistory
	 * @return
	 * @throws TeamGGException
	 */
	private List<Match> getMatches(com.merakianalytics.orianna.types.core.match.MatchHistory matchHistory) throws TeamGGException {
		List<Match> matches = new ArrayList<>();
		for (Match match : matchHistory) {
			
			//look up in db if it exist, if it doesn't add it
			if (DBReadMatch.readMatch(match.getId()) == null){
				
				matches.add(match);
			}
		}
		return matches;
	}

	

	/**
	 * 
	 * @param summonerInfo
	 * @throws TeamGGException
	 */
	private void addPlayerToDB(SummonerInfo summonerInfo) throws TeamGGException {
		DBWritePlayer.addNewPlayer(summonerInfo);
	}

	
	/**
	 * 
	 * @param summonerInfo
	 * @throws TeamGGException
	 */
	private void updatePlayerToDB(SummonerInfo summoner) throws TeamGGException {
		DBWritePlayer.updatePlayer(summoner);
	}

	
	
	/**
	 * 
	 * @param summonerInfo
	 * @throws TeamGGException
	 */
	private void updateUI(SummonerInfo summonerInfo) throws TeamGGException {
		ProfileInformation.setSummonerInfo(summonerInfo, true);
	}


	/**
	 * 
	 * @param summonerName
	 * @return
	 * @throws TeamGGException
	 */
	private SummonerInfo checkDataBase(String summonerName) throws TeamGGException {
		ConsoleHelper.info(this.getClass(), "Checking database");
		return DBReadPlayer.readPlayer(PlayersDBFields.SUMMONER_NAME_LW, summonerName);
		
	}
	
	
}
