package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.TankMopperHighScores;
import model.TankMopperGameStatus;
import model.TankMopperGameType;
import model.TankMopperModel;
import view.HighScoresDialog;
import view.TankMopperFrame;

public class GridMouseListener extends MouseAdapter {
	
	private final TankMopperFrame frame;
	
	private final TankMopperModel model;

	
	public GridMouseListener(TankMopperFrame frame, TankMopperModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		TankMopperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				if (mouseEvent.getSource() == buttonTable[x][y]) {
					if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
						leftButtonPressed(mouseEvent, x, y);
					} else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
						rightButtonPressed(mouseEvent, x, y);
					}
					checkWin();
					break;
				}
			}
		}
	}
	
	

	private void leftButtonPressed(MouseEvent mouseEvent, int x, int y) {
		TankMopperGameStatus status = model.getGameStatus();
		TankMopperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		if (!status.isFlag(x, y)) {
			if (status.isTank(x, y)) {
				gameOver("You Lose!");
				frame.pauseTimer();
				frame.setGameStatus(model.setGameStatus());
				model.getHighScores().setGameType(type.getSetup());
			} else {
				status.setExposed(x, y, true);
				status.setBooleanWin(x, y, true);

				buttonTable[x][y].setBackground(Color.BLACK);
				int surroundingTanks = status.surroundingTanks(type, x, y);
				if (surroundingTanks > 0) {
					String text = Integer.toString(surroundingTanks);
					buttonTable[x][y].setText(text);
					setColor(surroundingTanks, x, y);
				} else {
					expose(x, y);
				}
			}
		}
	}

	private void rightButtonPressed(MouseEvent mouseEvent, int x, int y) {
		TankMopperGameStatus status = model.getGameStatus();
		JButton[][] buttonTable = frame.getButtonTable();
		ImageIcon ic2 = new ImageIcon(getClass().getResource("bd.png"));
		
		if (status.isFlag(x, y)) {
			buttonTable[x][y].setText("");
			buttonTable[x][y].setForeground(Color.white);
			status.setFlag(x, y, false);
			status.setBooleanWin(x, y, false);
			status.tankNotFound();
			frame.setGameStatus(model.setGameStatus());
		}
		
		else if (!status.isBooleanWin(x, y) || status.isTank(x, y)) {
			buttonTable[x][y].setIcon(ic2);
			buttonTable[x][y].setBackground(Color.white);
			status.setFlag(x, y, true);
			status.setBooleanWin(x, y, true);
			status.tankFound();
			frame.setGameStatus(model.setGameStatus());
		}
	}

	
	private void checkWin() {
		TankMopperGameStatus status = model.getGameStatus();
	    TankMopperGameType type = model.getGameType();
		
		boolean allexposed = true;
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				if (status.isFlag(x, y) && !status.isTank(x, y)) {
					allexposed = false;
					break;
				}
				if (!status.isBooleanWin(x, y)) {
					allexposed = false;
					break;
				}
			}
		}
		if (allexposed) {
			TankMopperHighScores highScores = model.getHighScores();
			gameOver("You Win!!");
			
			new HighScoresDialog(frame.getFrame(), "TankMopper High Scores",
					type.getLevelName(), highScores);
		}
	}
	
	public void st() 
	{
		TankMopperHighScores highScores = model.getHighScores();
		TankMopperGameType type = model.getGameType();

		new HighScoresDialog(frame.getFrame(), "TankMopper High Scores",
				type.getLevelName(), highScores);
	}

	
	private void expose(int x, int y) {
		TankMopperGameStatus status = model.getGameStatus();
		TankMopperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		status.setExposed(x, y, true);
		for (int q = x - 1; q <= x + 1; q++) {
			for (int w = y - 1; w <= y + 1; w++) {
				while (true) {
					
					if (q < 0 || w < 0 || q >= type.getRow()
							|| w >= type.getColumn())
						break;
					if (status.isFlag(q, w))
						break;

					status.setBooleanWin(q, w, true);
					buttonTable[q][w].setBackground(Color.BLACK);
					int surroundingTanks = status.surroundingTanks(type, q,
							w);
					if (surroundingTanks > 0) {
						String surrtanks = Integer
								.toString(surroundingTanks);
						buttonTable[q][w].setText(surrtanks);
						setColor(surroundingTanks, q, w);
					} else if (!status.isExposed(q, w)) {
						expose(q, w);
					}
					break;
				}
			}
		}
	}

	private void setColor(int surroundingTanks, int x, int y) {
		JButton[][] buttonTable = frame.getButtonTable();
		
		if (surroundingTanks < 3)
			buttonTable[x][y].setForeground(Color.white);
		else if (surroundingTanks < 5)
			buttonTable[x][y].setForeground(Color.yellow);
		else
			buttonTable[x][y].setForeground(Color.red);
	}
      ImageIcon ic3 = new ImageIcon(getClass().getResource("tank.png"));
	private void gameOver(String message) {
		TankMopperGameStatus status = model.getGameStatus();
		TankMopperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				if (status.isTank(x, y)) {
					buttonTable[x][y].setBackground(Color.black);
					buttonTable[x][y].setIcon(ic3); // exposes all bombs
				}
				buttonTable[x][y].setEnabled(false); // disable all buttons
			}
		}

		if(message=="You Lose!")
		{
			frame.pauseTimer();
			ImageIcon l = new ImageIcon(getClass().getResource("loser.png"));
			JOptionPane.showMessageDialog(null,"You Lose!","",JOptionPane.INFORMATION_MESSAGE,l);
		}
		if(message=="You Win!!")
		{
			TankMopperHighScores highScores = model.getHighScores();

			frame.pauseTimer();
			frame.setGameStatus(model.setGameStatus());
			highScores.setGameType(type.getSetup());
			highScores.addScore(type.getSetup(), status.getElapsedTime(),
					status.getFormattedElapsedTime());
			ImageIcon w= new ImageIcon(getClass().getResource("trophy.png"));
			JOptionPane.showMessageDialog(null,"You Win!!","",JOptionPane.INFORMATION_MESSAGE,w);
		}
	}
}