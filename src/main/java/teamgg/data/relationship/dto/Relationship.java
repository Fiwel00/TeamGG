package teamgg.data.relationship.dto;

public class Relationship {

	private String accountId1;
	private String accountId2;
	private int wonWith;
	private int wonAgainst;
	private int lostWith;
	private int lostAgainst;
	private long firstEncounterWith;
	private long lastEncounterWith;
	private long firstEncounterAgainst;
	private long lastEncounterAgainst;
	
	private boolean existInDb;

	public Relationship () {
	}
	
	public Relationship (boolean existInDb) {
		setExistInDb(existInDb);
		if (existInDb == false) {
			setWonAgainst(0);
			setWonWith(0);
			setLostAgainst(0);
			setLostWith(0);
		}
		
	}
	
	public Relationship(Relationship relationship) {
		setAccountId1(relationship.getAccountId1());
		setAccountId2(relationship.getAccountId2());
		setExistInDb(relationship.isExistInDb());
		
		setFirstEncounterAgainst(relationship.getFirstEncounterAgainst());
		setFirstEncounterWith(relationship.getFirstEncounterWith());
		setLastEncounterAgainst(relationship.getLastEncounterAgainst());
		setLastEncounterWith(relationship.getLastEncounterWith());
		
		setLostAgainst(relationship.getLostAgainst());
		setWonAgainst(relationship.getWonAgainst());
		setLostWith(relationship.getLostWith());
		setWonWith(relationship.getWonWith());
		
	}


	public String getAccountId1() {
		return accountId1;
	}

	public void setAccountId1(String accountId1) {
		this.accountId1 = accountId1;
	}

	public String getAccountId2() {
		return accountId2;
	}

	public void setAccountId2(String accountId2) {
		this.accountId2 = accountId2;
	}

	public int getWonWith() {
		return wonWith;
	}

	public void setWonWith(int wonWith) {
		this.wonWith = wonWith;
	}

	public int getWonAgainst() {
		return wonAgainst;
	}

	public void setWonAgainst(int wonAgainst) {
		this.wonAgainst = wonAgainst;
	}

	public int getLostWith() {
		return lostWith;
	}

	public void setLostWith(int lostWith) {
		this.lostWith = lostWith;
	}

	public int getLostAgainst() {
		return lostAgainst;
	}

	public void setLostAgainst(int lostAgainst) {
		this.lostAgainst = lostAgainst;
	}

	public long getFirstEncounterWith() {
		return firstEncounterWith;
	}

	public void setFirstEncounterWith(long firstEncounterWith) {
		this.firstEncounterWith = firstEncounterWith;
	}

	public long getLastEncounterWith() {
		return lastEncounterWith;
	}

	public void setLastEncounterWith(long lastEncounterWith) {
		this.lastEncounterWith = lastEncounterWith;
	}

	public long getFirstEncounterAgainst() {
		return firstEncounterAgainst;
	}

	public void setFirstEncounterAgainst(long firstEncounterAgainst) {
		this.firstEncounterAgainst = firstEncounterAgainst;
	}

	public long getLastEncounterAgainst() {
		return lastEncounterAgainst;
	}

	public void setLastEncounterAgainst(long lastEncounterAgainst) {
		this.lastEncounterAgainst = lastEncounterAgainst;
	}

	public boolean isExistInDb() {
		return existInDb;
	}

	public void setExistInDb(boolean existInDb) {
		this.existInDb = existInDb;
	}
}
