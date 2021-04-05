package teamgg.ui.components.matchInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import common.filehelper.ReadFile;
import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;
import errorhandling.customexception.FilePathIsEmptyException;
import errorhandling.customexception.MatchInfoUpdateFailedException;
import teamgg.data.relationship.dto.RelationshipEnriched;

public class MatchGrid extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RelationshipEnriched> relationships;

	private String matchHistoryHtml;
	private String matchHistoryFilePath = "resources/ui/matchGrid.html";
	private static final String NEW_ROW_PLAYER_HTML = ""+
			"<tr>   \r\n" + 
			"	<th>%s</th>\r\n" + 
			"	<th>%s</th>  \r\n" + 
			"	<th>%s</th>  \r\n" + 
			"	<th>%s</th>  \r\n" + 
			"	<th>%s</th>  \r\n" + 
			"</tr> ";
	
	
	public MatchGrid() throws TeamGGException{
		setVisible(true);
	
		initMatchHistory();
		
		updateMatchInfo();
	}

	private void initMatchHistory() throws FileNotFoundException {
		try {
			matchHistoryHtml = ReadFile.read(matchHistoryFilePath);
		} catch (FilePathIsEmptyException | FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<RelationshipEnriched> getRelationships() {
		if(relationships == null)
			relationships = new ArrayList<>();
		return relationships;
	}

	/**
	 * 
	 * @param value
	 * @param update
	 * @throws MatchInfoUpdateFailedException 
	 */
	@SuppressWarnings("unchecked")
	public void setRelationships(Object value, boolean update) throws MatchInfoUpdateFailedException {
		setRelationships((List<RelationshipEnriched>) value, update);
		
	}
	/**
	 * 
	 * @param relationships
	 * @throws MatchInfoUpdateFailedException 
	 */
	public void setRelationships(List<RelationshipEnriched> relationships, boolean update) throws MatchInfoUpdateFailedException {
		this.relationships = relationships;
		checkUpdate(update);
	}

	/**
	 * 
	 * @return
	 */
	public String getMatchHistoryHtml() {
		return matchHistoryHtml;
	}

	/**
	 * 
	 * @param relationships
	 * @throws MatchInfoUpdateFailedException 
	 */
	public void updateMatchInfo() throws MatchInfoUpdateFailedException {
		try {
			initMatchHistory();
		} catch (FileNotFoundException e) {
			throw new MatchInfoUpdateFailedException();
		}
		List<RelationshipEnriched> relationships = getRelationships();
		
		for (int i = 0; i < relationships.size(); i++) {
			RelationshipEnriched relationship = relationships.get(i);
			String newRowPlayer = getNewRowPlayer(relationship);
			
			//while not the last entry, add a string format placeholder for the next relationship
			if((i == relationships.size() - 1) == false) {
				newRowPlayer += "%%s";
			}
			
			matchHistoryHtml = String.format(matchHistoryHtml, newRowPlayer);
		}
		ConsoleHelper.info("Updating view match history HTML %s", matchHistoryHtml);
		
		setText(matchHistoryHtml);
	}


	/**
	 * 
	 * @param relationship
	 * @return
	 */
	public String getNewRowPlayer(RelationshipEnriched relationship) {
		
		return String.format(NEW_ROW_PLAYER_HTML, relationship.getSummoner2(), 
				relationship.getWonWith(), relationship.getLostWith(),
				relationship.getWonAgainst(), relationship.getLostAgainst());
	}

	
	
	private void checkUpdate(boolean update) throws MatchInfoUpdateFailedException {
		if (update) {
			updateMatchInfo();
		}
	}
}
