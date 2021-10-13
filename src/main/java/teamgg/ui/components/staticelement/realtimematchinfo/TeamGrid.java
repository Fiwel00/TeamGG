package teamgg.ui.components.staticelement.realtimematchinfo;

import java.awt.Color;
import java.util.List;

import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;
import teamgg.data.relationship.dto.RelationshipEnriched;
import teamgg.ui.components.staticelement.StaticComponent;

public class TeamGrid extends StaticComponent{

    private List<RelationshipEnriched> players;

	
	public TeamGrid(String title) throws FileNotFoundException{
		super("resources/ui/teamGrid");
        setBackground(Color.GRAY);
        setTitle(title);

	}

    
    private void setTitle(String title) {
        setComponenthtml(String.format(getComponenthtml(), title));
    }



    public List<RelationshipEnriched> getPlayers() {
        return players;
    }

    public void setPlayers(List<RelationshipEnriched> players) {
        this.players = players;
    }





	@Override
	public void update() throws TeamGGException {
		// TODO Auto-generated method stub
		
	}



    public void setPlayers(Object value, boolean b) {
    }




}
