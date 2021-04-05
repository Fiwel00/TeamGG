package teamgg.database.client.relationship;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import errorhandling.TeamGGException;
import teamgg.data.relationship.dto.Relationship;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.RelationshipDBFields;

public class DBWriteRelationship extends MongoTeamGGClient {


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
					Document newRelationshipDocument = new Document(RelationshipDBFields.ACCOUNT_ID_1.toString(),
							relationship.getAccountId1())
									.append(RelationshipDBFields.ACCOUNT_ID_2.toString(),
											relationship.getAccountId2())
									.append(RelationshipDBFields.FIRST_ENCOUNTER_AGAINST.toString(),
											relationship.getFirstEncounterAgainst())
									.append(RelationshipDBFields.FIRST_ENCOUNTER_WITH.toString(),
											relationship.getFirstEncounterWith())
									.append(RelationshipDBFields.LAST_ENCOUNTER_AGAINST.toString(),
											relationship.getLastEncounterAgainst())
									.append(RelationshipDBFields.LAST_ENCOUNTER_WITH.toString(),
											relationship.getLastEncounterWith())
									.append(RelationshipDBFields.LOST_AGAINST.toString(),
											relationship.getLostAgainst())
									.append(RelationshipDBFields.LOST_WITH.toString(), relationship.getLostWith())
									.append(RelationshipDBFields.WON_AGAINST.toString(), relationship.getWonAgainst())
									.append(RelationshipDBFields.WON_WITH.toString(), relationship.getWonWith());

					newRelationshipDocuments.add(newRelationshipDocument);
				} else {

					Bson filter = and(
							asList(eq(RelationshipDBFields.ACCOUNT_ID_1.toString(), relationship.getAccountId1()),
									eq(RelationshipDBFields.ACCOUNT_ID_2.toString(), relationship.getAccountId2())));
					Bson updateFirstEncounterAgainst = set(RelationshipDBFields.FIRST_ENCOUNTER_AGAINST.toString(),
							relationship.getFirstEncounterAgainst());
					Bson updateFirstEncounterWith = set(RelationshipDBFields.FIRST_ENCOUNTER_WITH.toString(),
							relationship.getFirstEncounterWith());
					Bson updateLastEncounterAgainst = set(RelationshipDBFields.LAST_ENCOUNTER_AGAINST.toString(),
							relationship.getLastEncounterAgainst());
					Bson updateLastEncounterWith = set(RelationshipDBFields.LAST_ENCOUNTER_WITH.toString(),
							relationship.getLastEncounterWith());
					Bson updateLostAgainst = set(RelationshipDBFields.LOST_AGAINST.toString(),
							relationship.getLostAgainst());
					Bson updateLostWith = set(RelationshipDBFields.LOST_WITH.toString(), relationship.getLostWith());
					Bson updateWonAgainst = set(RelationshipDBFields.WON_AGAINST.toString(),
							relationship.getWonAgainst());
					Bson updateWonWith = set(RelationshipDBFields.WON_WITH.toString(), relationship.getWonWith());

					Bson updates = combine(updateWonWith, updateFirstEncounterAgainst, updateFirstEncounterWith,
							updateLastEncounterAgainst, updateLastEncounterWith, updateLostAgainst, updateLostWith,
							updateWonAgainst);
					
					//update one at a time
					relationshipCollection.updateOne(filter, updates);
				}

			}
			//write in bulk the new relationships that were found 
			if(newRelationshipDocuments.isEmpty() == false) {
				relationshipCollection.insertMany(newRelationshipDocuments);
			}
		}
	}

}
