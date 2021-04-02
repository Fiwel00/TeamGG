package teamgg.data.matchhistory.dto;

import java.util.List;

public class MatchHistory {

	private List<MatchSimpleRaw> matches;
	private int startIndex;
	private int endIndex;
	private int totalGames;

	public List<MatchSimpleRaw> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchSimpleRaw> matches) {
		this.matches = matches;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}
}
