package model;

import view.TankMopperHighScores;

public class TankMopperModel {
	
	private final TankMopperGameStatus gameStatus;
	
	private TankMopperGameType gameType;
	
	private final TankMopperHighScores highScores;
	
	public TankMopperModel() {
		this.highScores = new TankMopperHighScores();
		this.highScores.readScores();
		
		this.gameType = TankMopperGameTypeFactory.getGameType(
				highScores.getGameType());
		this.gameStatus = new TankMopperGameStatus(gameType);
		this.gameStatus.setStartTime();
	}
	
	public String setGameStatus() {
		StringBuilder sb = new StringBuilder();
		sb.append(getGameType().getLevelName());
		sb.append(" level: ");
		sb.append("Tanks left - ");
		sb.append(getGameStatus().getTanksRemaining());
		sb.append(", time - ");
		sb.append(getGameStatus().getFormattedElapsedTime());

		return sb.toString(); 
	}

	public TankMopperGameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameType(TankMopperGameType gameType) {
		this.gameType = gameType;
	}

	public TankMopperGameType getGameType() {
		return gameType;
	}

	public TankMopperHighScores getHighScores() {
		return highScores;
	}

}

