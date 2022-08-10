package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.TankMopperGameStatus;
import model.TankMopperGameType;
import model.TankMopperGameTypeFactory;
import model.TankMopperModel;
import view.TankMopperFrame;

public class GameMouseListener extends MouseAdapter {
	
	private final TankMopperFrame frame;
	
	private final TankMopperModel model; 

	public GameMouseListener(TankMopperFrame frame, TankMopperModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		TankMopperGameType type = model.getGameType();
		TankMopperGameStatus status = model.getGameStatus();
		frame.removeGridButtons(type);
		
		if (mouseEvent.getSource() == frame.getEasyButton()) {
			type = TankMopperGameTypeFactory.getGameType(1);
		} else if (mouseEvent.getSource() == frame.getMediumButton()) {
			type = TankMopperGameTypeFactory.getGameType(2);
		} else if (mouseEvent.getSource() == frame.getHardButton()) {
			type = TankMopperGameTypeFactory.getGameType(3);
		} 
		
			
		
		model.setGameType(type);
		status.setStartTime();
		status.setElapsedTime(0L);
		status.resetGameStatus(type);
		
		frame.addGridButtons(type);
		frame.resetTimer();
		frame.setGameStatus(model.setGameStatus());
		

	}
	

}
