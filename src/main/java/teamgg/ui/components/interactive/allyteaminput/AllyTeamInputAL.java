package teamgg.ui.components.interactive.allyteaminput;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import errorhandling.TeamGGException;
import teamgg.data.staticmodel.RealTimeMatchInformation;
import teamgg.ui.views.RealTimeMatchView;

public class AllyTeamInputAL implements DocumentListener{

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            updatedAllyTeamInput();
        } catch (TeamGGException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            updatedAllyTeamInput();
        } catch (TeamGGException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            updatedAllyTeamInput();
        } catch (TeamGGException e1) {
            e1.printStackTrace();
        }
    }

    private void updatedAllyTeamInput() throws TeamGGException {
		String allyTeam = RealTimeMatchView.getAllyTeamInput().getText();
		//TODO Finish UI integration and resolving ally team relationships
		if (allyTeam == null) {
			allyTeam = "";
		}
		
		RealTimeMatchInformation.setAllyTeam(allyTeam, true);
	}
}
