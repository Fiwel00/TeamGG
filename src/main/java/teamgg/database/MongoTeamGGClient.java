package teamgg.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import errorhandling.TeamGGException;
import teamgg.ConfigFile;

public abstract class MongoTeamGGClient {

	private static final String COLLECTION_PLAYERS = "Players";
	private static final String COLLECTION_MATCHES = "Matches";
	private static final String COLLECTION_RELATIONSHIP = "Relationship";
	private static MongoClient mongoClient;

	protected MongoTeamGGClient() {
	}

	/**
	 * @return
	 * @throws TeamGGException
	 */
	protected static MongoCollection<Document> getPlayersColletion() throws TeamGGException {
		return getDatabase().getCollection(COLLECTION_PLAYERS);
	}

	/**
	 * @return
	 * @throws TeamGGException
	 */
	protected static MongoCollection<Document> getMatchesColletion() throws TeamGGException {
		return getDatabase().getCollection(COLLECTION_MATCHES);
	}

	/**
	 * @return
	 * @throws TeamGGException
	 */
	protected static MongoCollection<Document> getRelationshipColletion() throws TeamGGException {
		return getDatabase().getCollection(COLLECTION_RELATIONSHIP);
	}

	/**
	 * 
	 * @return
	 * @throws TeamGGException
	 */
	private static MongoDatabase getDatabase() throws TeamGGException {
		return getMongoClient().getDatabase(ConfigFile.getDataBase());
	}

	/**
	 * 
	 * @return
	 */
	private static MongoClient getMongoClient() {
		return mongoClient;
	}

	protected static MongoClient createNewMongoInstance() throws TeamGGException {
		mongoClient = MongoClients.create(ConfigFile.getHostDB());
		return mongoClient;
	}

}
