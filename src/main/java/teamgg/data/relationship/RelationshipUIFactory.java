package teamgg.data.relationship;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import errorhandling.ConsoleHelper;
import teamgg.data.relationship.dto.Relationship;
import teamgg.data.relationship.dto.RelationshipEnriched;
import teamgg.data.summonerinfo.SummonerInfoFactory;
import teamgg.data.summonerinfo.dto.SummonerInfo;
import teamgg.database.fields.RelationshipFieldsUIEnum;

public class RelationshipUIFactory {

	public static List<RelationshipEnriched> createRelationshipsUI(List<Document> relationshipsUIDocument) {

		List<RelationshipEnriched> relationshipsUI = new ArrayList<>();
		
		for (Document relationshipUIDocument : relationshipsUIDocument) {
			
			RelationshipEnriched relationshipUI = createRelationship(relationshipUIDocument);
			relationshipsUI.add(relationshipUI);
		}
		
		
		return relationshipsUI;
	}
	
	public static RelationshipEnriched createRelationship(Document relationshipDocument) {
		Relationship relationship = RelationshipFactory.createRelationship(relationshipDocument);

		RelationshipEnriched relationshipUI = new RelationshipEnriched(relationship);
		
		ConsoleHelper.info(RelationshipUIFactory.class, "document %s", relationshipDocument.toJson());
		
		List<Document> object = (List<Document>) relationshipDocument.get(RelationshipFieldsUIEnum.SUMMONER_1.toString());
		SummonerInfo summmonerInfo = SummonerInfoFactory.createSummmonerInfo(object.get(0));
		relationshipUI.setSummoner1(summmonerInfo.getName());
		
		object = (List<Document>) relationshipDocument.get(RelationshipFieldsUIEnum.SUMMONER_2.toString());
		summmonerInfo = SummonerInfoFactory.createSummmonerInfo(object.get(0));
		relationshipUI.setSummoner2(summmonerInfo.getName());
		
		return relationshipUI;
	}

}
