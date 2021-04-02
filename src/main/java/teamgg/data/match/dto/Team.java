package teamgg.data.match.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Team {
	private List<Player> players;
	private boolean won;
	private int teamId;

	public Team(int teamId, String win, List<Player> players) {
		setTeamId(teamId);
		setWon(StringUtils.equals(win, "Win") ? true: false);
		setPlayers(players);
	}

	public Team() {
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

}
