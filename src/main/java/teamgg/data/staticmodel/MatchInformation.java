package teamgg.data.staticmodel;

import java.util.ArrayList;
import java.util.List;

import errorhandling.ConsoleHelper;
import teamgg.data.FieldIdValues;
import teamgg.data.FieldIdsEnum;
import teamgg.data.InvalidUpdatePipelineException;
import teamgg.data.updateUI;
import teamgg.data.relationship.dto.RelationshipEnriched;

/**
 * purpose of this class is to store the information regarding the profil of the
 * user when the values changes {@link updateUI} can be triggered to make the
 * necessary changes throughout the application
 * 
 * @author Mehdi Ayadi
 *
 */
public class MatchInformation {

	private static List<RelationshipEnriched> relationships;
	private static List<FieldIdValues> updateValues = new ArrayList<>();

	private static void checkUpdate(boolean update) throws InvalidUpdatePipelineException {
		if (update) {
			updateUI.updateMatches(updateValues);
			updateValues = new ArrayList<>();
		}
	}

	public static List<RelationshipEnriched> getRelationships() {
		return relationships;
	}

	public static void setRelationships(List<RelationshipEnriched> relationships, boolean update) throws InvalidUpdatePipelineException {
		ConsoleHelper.info("update relationships in ui  %s", update);
		MatchInformation.relationships = relationships;
		updateValues.add(new FieldIdValues(FieldIdsEnum.MATCH_GRID, getRelationships()));

		checkUpdate(update);
	}
}
