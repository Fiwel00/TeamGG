package teamgg.data.staticmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import errorhandling.TeamGGException;
import teamgg.data.FieldIdValues;
import teamgg.data.FieldIdsEnum;
import teamgg.data.updateUI;

public class RealTimeMatchInformation {

    private static List<String> allyTeam;

    private static List<FieldIdValues> updateValues = new ArrayList<>();

    public static List<String> getAllyTeam() {
        return allyTeam;
    }

    public static void setAllyTeam(List<String> allyTeam, boolean update) throws TeamGGException {
        RealTimeMatchInformation.allyTeam = allyTeam;
        updateValues.add(new FieldIdValues(FieldIdsEnum.ALLY_TEAM, getAllyTeam()));
        
        checkUpdate(update);
    }

    public static void setAllyTeam(String allyTeamRaw, boolean update) throws TeamGGException {
        setAllyTeam(Arrays.asList(allyTeamRaw.split(",")), update);
    }


    private static void checkUpdate(boolean update) throws TeamGGException {
		if (update && updateValues.isEmpty() == false) {
			updateUI.udpateRealTimeMatches(updateValues);
			updateValues = new ArrayList<>();
		}
    }
}
