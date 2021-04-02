package teamgg.data.relationship.dto;

public class RelationshipEnriched extends Relationship {

	private String summoner1;
	private String summoner2;

	public RelationshipEnriched() {
		super();
	}
	
	public RelationshipEnriched(Relationship relationship) {
		super(relationship);
	}

	public String getSummoner1() {
		return summoner1;
	}

	public void setSummoner1(String summoner1) {
		this.summoner1 = summoner1;
	}

	public String getSummoner2() {
		return summoner2;
	}

	public void setSummoner2(String summoner2) {
		this.summoner2 = summoner2;
	}
}
