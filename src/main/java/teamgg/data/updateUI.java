package teamgg.data;

import java.util.List;

import errorhandling.ConsoleHelper;
import teamgg.ui.components.matchInfo.MatchGrid;
import teamgg.ui.components.profileinfo.ProfileInfo;
import teamgg.ui.views.MatchInfoView;
import teamgg.ui.views.ProfileView;

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
	 * @throws InvalidUpdatePipelineException
	 */
	public static void updateSummoner(List<FieldIdValues> updateValues) throws InvalidUpdatePipelineException {
		
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
			
			profilInfolabel.updateProfilInfo();
			
		}
		
	}
	
	/**
	 * 
	 * @param updateValues
	 * @throws InvalidUpdatePipelineException
	 */
	public static void updateMatches(List<FieldIdValues> updateValues) throws InvalidUpdatePipelineException {
		
		MatchGrid matchInfoGrid = MatchInfoView.getMatchGrid();
		
		for (FieldIdValues fieldIdValues : updateValues) {
			
			Object value = fieldIdValues.getValue();
			
			switch (fieldIdValues.getFieldId()) {
				case MATCH_GRID:
					ConsoleHelper.info("updating MATCH_GRID");
					matchInfoGrid.setRelationships(value, true);
					break;
				default:
					throw new InvalidUpdatePipelineException(fieldIdValues.getFieldId().toString(), value);
			}

			matchInfoGrid.updateMatchInfo();
			
		}
		
	}

}
