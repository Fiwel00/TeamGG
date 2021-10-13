package teamgg.data;

import java.util.List;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import errorhandling.customexception.MatchInfoUpdateFailedException;
import teamgg.ui.components.staticelement.matchInfo.MatchGrid;
import teamgg.ui.components.staticelement.profileinfo.ProfileInfo;
import teamgg.ui.components.staticelement.realtimematchinfo.TeamGrid;
import teamgg.ui.views.MatchInfoView;
import teamgg.ui.views.ProfileView;
import teamgg.ui.views.RealTimeMatchView;

/**
 * 
 * @author Mehdi Ayadi
 *
 *	purpose is to update the right ui components with the incoming information
 *
 */
public class updateUI {

	/**
	 * 
	 * @param updateValues
	 * @throws TeamGGException
	 */
	public static void updateSummoner(List<FieldIdValues> updateValues) throws TeamGGException {
		
		for (FieldIdValues fieldIdValues : updateValues) {
			
			
			ProfileInfo profilInfolabel = ProfileView.getProfilInfo();
			
			Object value = fieldIdValues.getValue();
			
			switch (fieldIdValues.getFieldId()) {
				case ACCOUNT_ID: 
					profilInfolabel.setAccountId(value, false);
					break;
				case SUMMONER_NAME:
					profilInfolabel.setSummonerName(value, false);
					break;
				case SUMMONER_LEVEL:
					profilInfolabel.setSummonerLevel(value, false);
					break;
				case PROFILE_ICON_ID: 
					profilInfolabel.setProfileIconId(value, false);
					break;
				case SUMMONER_NAME_INPUT:
					break;
				default:
					throw new InvalidUpdatePipelineException(fieldIdValues.getFieldId().toString(), value);
			}
			
			profilInfolabel.update();
			
		}
		
	}
	
	/**
	 * 
	 * @param updateValues
	 * @throws InvalidUpdatePipelineException
	 * @throws MatchInfoUpdateFailedException 
	 */
	public static void updateMatches(List<FieldIdValues> updateValues) throws InvalidUpdatePipelineException, MatchInfoUpdateFailedException {
		
		MatchGrid matchInfoGrid = MatchInfoView.getMatchGrid();
		
		for (FieldIdValues fieldIdValues : updateValues) {
			
			Object value = fieldIdValues.getValue();
			
			switch (fieldIdValues.getFieldId()) {
				case MATCH_GRID:
					ConsoleHelper.info(updateUI.class, "updating MATCH_GRID");
					matchInfoGrid.setRelationships(value, true);
					break;
				default:
					throw new InvalidUpdatePipelineException(fieldIdValues.getFieldId().toString(), value);
			}

			matchInfoGrid.update();
			
		}
		
	}


    public static void udpateRealTimeMatches(List<FieldIdValues> updateValues) throws TeamGGException {
		TeamGrid allyTeamGrid = RealTimeMatchView.getAllyTeam();
		
		for (FieldIdValues fieldIdValues : updateValues) {
			
			Object value = fieldIdValues.getValue();
			
			switch (fieldIdValues.getFieldId()) {
				case ALLY_TEAM:
					ConsoleHelper.info(updateUI.class, "updating ALLY_TEAM");
					allyTeamGrid.setPlayers(value, true);
					break;
				default:
					throw new InvalidUpdatePipelineException(fieldIdValues.getFieldId().toString(), value);
			}

			allyTeamGrid.update();
			
		}

    }

}
