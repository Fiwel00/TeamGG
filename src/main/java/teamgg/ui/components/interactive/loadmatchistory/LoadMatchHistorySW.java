package teamgg.ui.components.interactive.loadmatchistory;

import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;

import teamgg.data.relationship.dto.RelationshipEnriched;
import teamgg.data.staticmodel.MatchInformation;
import teamgg.data.staticmodel.ProfileInformation;
import teamgg.database.client.relationship.DBReadRelationship;

public class LoadMatchHistorySW extends SwingWorker<Integer, Void> {

	@Override
	protected Integer doInBackground() throws Exception {

		//get user from model
		String accountId = ProfileInformation.getAccountId();
		
		//read data in db all his relationship
		List<RelationshipEnriched> relationships = DBReadRelationship.readRelationShips(accountId);
		
		for (RelationshipEnriched relationship : relationships) {
			//format the data in a user friendly way where the search account is always in first position
			//meaning all the against fields have to be swapped around
			//the data is structured accountId_1 "wonAgainst" accountId_2 which is the same as accountId_2 "lostAgainst" accountId_1
			if (StringUtils.equals(relationship.getAccountId2(), accountId)) {
				
				String accountId1 = relationship.getAccountId1();
				String accountId2 = relationship.getAccountId2();

				relationship.setAccountId1(accountId2);
				relationship.setAccountId2(accountId1);

				String summmoner1 = relationship.getSummoner1();
				String summmoner2 = relationship.getSummoner2();
				
				relationship.setSummoner2(summmoner1);
				relationship.setSummoner1(summmoner2);
				
				int lostAgainst = relationship.getLostAgainst();
				int wonAgainst = relationship.getWonAgainst();
				
				relationship.setLostAgainst(wonAgainst);
				relationship.setWonAgainst(lostAgainst);
				
			}
		}
		
		//put it in model
		
		MatchInformation.setRelationships(relationships, true);
		return 1;
	}

}
