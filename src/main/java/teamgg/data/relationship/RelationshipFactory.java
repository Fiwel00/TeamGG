package teamgg.data.relationship;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import teamgg.data.relationship.dto.Relationship;
import teamgg.database.fields.RelationshipDBFields;

public class RelationshipFactory {

	public static Relationship createRelationship(Document relationshipDocument) {
		Relationship relationship = new Relationship();
		
		relationship.setAccountId1(relationshipDocument.getString(RelationshipDBFields.ACCOUNT_ID_1.toString()));
		relationship.setAccountId2(relationshipDocument.getString(RelationshipDBFields.ACCOUNT_ID_2.toString()));
		relationship.setExistInDb(true);
		relationship.setFirstEncounterAgainst(relationshipDocument.getLong(RelationshipDBFields.FIRST_ENCOUNTER_AGAINST.toString()));
		relationship.setFirstEncounterWith(relationshipDocument.getLong(RelationshipDBFields.FIRST_ENCOUNTER_WITH.toString()));
		relationship.setLastEncounterAgainst(relationshipDocument.getLong(RelationshipDBFields.LAST_ENCOUNTER_AGAINST.toString()));
		relationship.setLastEncounterWith(relationshipDocument.getLong(RelationshipDBFields.LAST_ENCOUNTER_WITH.toString()));
		relationship.setLostAgainst(relationshipDocument.getInteger(RelationshipDBFields.LOST_AGAINST.toString()));
		relationship.setLostWith(relationshipDocument.getInteger(RelationshipDBFields.LOST_WITH.toString()));
		relationship.setWonAgainst(relationshipDocument.getInteger(RelationshipDBFields.WON_AGAINST.toString()));
		relationship.setWonWith(relationshipDocument.getInteger(RelationshipDBFields.WON_WITH.toString()));
		
		return relationship;
	}

	public static List<Relationship> createRelationships(List<Document> relationshipsDocument) {
		
		List<Relationship> relationships = new ArrayList<>();
		
		for (Document relationshipDocument : relationshipsDocument) {
			Relationship relationship = createRelationship(relationshipDocument);
			relationships.add(relationship);
		}
		
		
		return relationships;
	}

}
