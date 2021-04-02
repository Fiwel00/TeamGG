package teamgg.database.client;

import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Aggregates;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import teamgg.data.match.MatchFactory;
import teamgg.data.match.dto.Match;
import teamgg.data.relationship.RelationshipFactory;
import teamgg.data.relationship.RelationshipUIFactory;
import teamgg.data.relationship.dto.Relationship;
import teamgg.data.relationship.dto.RelationshipEnriched;
import teamgg.data.summonerinfo.SummonerInfoFactory;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.MongoTeamGGClient;
import teamgg.database.fields.MatchesFieldEnum;
import teamgg.database.fields.PlayersFieldsEnum;
import teamgg.database.fields.RelationshipFieldsEnum;
import teamgg.database.fields.RelationshipFieldsUIEnum;

public class DBReadClient extends MongoTeamGGClient{

	
	public DBReadClient() throws TeamGGException {
		super();
	}

	/**
	 * 
	 * @param summonerIdentifier
	 * @return
	 * @throws TeamGGException
	 */
	public static SummonerInfo readPlayer(PlayersFieldsEnum field, String summonerIdentifier) throws TeamGGException {
		
		SummonerInfo summonerInfo = null;
		try (MongoClient client = createNewMongoInstance()) {
			ConsoleHelper.info("Searching DB Player Collection %s" , field.toString(), summonerIdentifier);
			
			if (field.equals(PlayersFieldsEnum.SUMMONER_NAME_LW)) {
				summonerIdentifier = summonerIdentifier.toLowerCase();
			}
			
			Document summonerDocument = getPlayersColletion().find(eq(field.toString(), summonerIdentifier)).first();
			
			if ((summonerDocument == null ) == false) {
				ConsoleHelper.info(summonerDocument.toJson());
				summonerInfo = SummonerInfoFactory.createSummmonerInfo(summonerDocument);
				
			}
		}
		return summonerInfo;
	}

	/**
	 * 
	 * @param gameId
	 * @return
	 * @throws TeamGGException
	 */
	public static Match readMatch(long gameId) throws TeamGGException {
		Match match = null;
		try (MongoClient client = createNewMongoInstance()) {
			
			Document matchDocument = getMatchesColletion().find(eq(MatchesFieldEnum.GAME_ID.toString(), gameId)).first();
			
			if ((matchDocument == null ) == false) {
				ConsoleHelper.info(matchDocument.toJson());
				match = MatchFactory.createMatch(matchDocument);
			}
		}
		return match;
	}

	/**
	 * 
	 * @param accountId1
	 * @param accountId2
	 * @return
	 * @throws TeamGGException
	 */
	public static Relationship readRelationShip(String accountId1, String accountId2) throws TeamGGException {

		Relationship relationShip = null;
		try (MongoClient client = createNewMongoInstance()) {
			
			Bson filter = and(eq(RelationshipFieldsEnum.ACCOUNT_ID_1.toString(), accountId1), eq(RelationshipFieldsEnum.ACCOUNT_ID_2.toString(), accountId2));
			Document relationshipDocument = getRelationshipColletion().find(filter).first();
			
			if (relationshipDocument == null) {
				filter = and(eq(RelationshipFieldsEnum.ACCOUNT_ID_1.toString(), accountId2), eq(RelationshipFieldsEnum.ACCOUNT_ID_2.toString(), accountId1));
				relationshipDocument = getRelationshipColletion().find(filter).first();
			}
			
			if ((relationshipDocument == null ) == false) {
				ConsoleHelper.info(relationshipDocument.toJson());
				relationShip = RelationshipFactory.createRelationship(relationshipDocument);
			}
		}
		return relationShip;
	}

	/**
	 * 
	 * @param accountId
	 * @return
	 * @throws TeamGGException 
	 */
	public static List<RelationshipEnriched> readRelationShips(String accountId) throws TeamGGException {
		List<RelationshipEnriched> relationshipsUI = new ArrayList<>();
		
		try (MongoClient client = createNewMongoInstance()) {

			List<Document> relationshipsDocument = new ArrayList<>();

			String fieldAccount1 = RelationshipFieldsEnum.ACCOUNT_ID_1.toString();
			String fieldAccount2 = RelationshipFieldsEnum.ACCOUNT_ID_2.toString();

			String fieldSummoner1 = RelationshipFieldsUIEnum.SUMMONER_1.toString();
			String fieldSummoner2 = RelationshipFieldsUIEnum.SUMMONER_2.toString();

			String fieldAccount = PlayersFieldsEnum.ACCOUNT_ID.toString();

			Bson filterAccountID1 = eq(fieldAccount1, accountId);
			Bson filterAccountID2 = eq(fieldAccount2, accountId);
			
			String playCollectionName = getPlayersColletion().getNamespace().getCollectionName();
			
			Bson match = Aggregates.match(or(filterAccountID1,filterAccountID2));
			
			ConsoleHelper.info("%s %s %s %s",playCollectionName,  fieldAccount1, fieldAccount, fieldSummoner1);
			
			Bson lookupSum1 = lookup(playCollectionName,  fieldAccount1, fieldAccount, fieldSummoner1);
			Bson lookupSum2 = lookup(playCollectionName, fieldAccount2, fieldAccount, fieldSummoner2);
			
			List<Document> readDocuments = getRelationshipColletion().aggregate(asList(match, lookupSum1, lookupSum2)).into(new ArrayList<Document>());
			relationshipsDocument.addAll(readDocuments);
			
			ConsoleHelper.info(String.valueOf(relationshipsDocument.size()));
			for (Document relationshipDocument : relationshipsDocument) {
				
			ConsoleHelper.info("document %s", relationshipDocument.toJson());
			}
			
			if ((relationshipsDocument.isEmpty() ) == false) {
				relationshipsUI = RelationshipUIFactory.createRelationshipsUI(relationshipsDocument);
			}
		}
		return relationshipsUI;
		
	}
}
