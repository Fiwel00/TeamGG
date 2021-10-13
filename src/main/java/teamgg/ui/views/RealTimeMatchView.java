package teamgg.ui.views;

import java.awt.Color;

import javax.swing.JPanel;

import errorhandling.TeamGGException;
import teamgg.ui.components.interactive.allyteaminput.AllyTeamInput;
import teamgg.ui.components.staticelement.realtimematchinfo.TeamGrid;

public class RealTimeMatchView extends JPanel{
    
    private static TeamGrid allyTeam;
    private static TeamGrid ennemyTeam;
    private static AllyTeamInput allyTeamInput;


    public RealTimeMatchView () throws TeamGGException {
        super();
		setVisible(true);
		setBackground(Color.CYAN);
	
		
		setAllyTeam(new TeamGrid("Ally"));
        setEnnemyTeam(new TeamGrid("Ennemy"));
        setAllyTeamInput(new AllyTeamInput());
        
	}



    public static AllyTeamInput getAllyTeamInput() {
        return allyTeamInput;
    }



    public void setAllyTeamInput(AllyTeamInput allyTeamInput) {
        RealTimeMatchView.allyTeamInput = allyTeamInput;
        add(allyTeamInput);
    }



    public static TeamGrid getAllyTeam() {
        return RealTimeMatchView.allyTeam;
    }



    public void setAllyTeam(TeamGrid allyTeam) {
        RealTimeMatchView.allyTeam = allyTeam;
        add(allyTeam);
    }



    public static TeamGrid getEnnemyTeam() {
        return RealTimeMatchView.ennemyTeam;
    }



    public void setEnnemyTeam(TeamGrid ennemyTeam) {
        RealTimeMatchView.ennemyTeam = ennemyTeam;
        add(ennemyTeam);
    }
}
