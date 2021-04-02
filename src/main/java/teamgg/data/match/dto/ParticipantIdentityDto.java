package teamgg.data.match.dto;

public class ParticipantIdentityDto {
	
	private int participantId;
	private PlayerDto player;

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public PlayerDto getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDto player) {
		this.player = player;
	}
}
