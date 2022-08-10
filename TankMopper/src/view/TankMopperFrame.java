package view;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import controller.TankMopperTimer;
import model.TankMopperGameType;
import model.TankMopperModel;

public class TankMopperFrame {
	
	private JFrame frame;
	
	private TankMopperModel model;
	
	private TankMopperPanel tankmopperPanel;
	
	private	TankMopperTimer timer;
	
	private ImageIcon icon;
	
	
	

	public TankMopperFrame(TankMopperModel model) {
		this.model = model;
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		frame = new JFrame("TankMopper");
		
		icon = new ImageIcon(getClass().getResource("tanki.png"));
		frame.setIconImage(icon.getImage());
		

		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProcedure();
            }
        });
		
		tankmopperPanel = new TankMopperPanel(this, model);
		frame.add(tankmopperPanel.getMainPanel(), BorderLayout.CENTER);
		
		frame.setBackground(Color.orange);
		frame.pack();
		frame.setLocationByPlatform(true);
		
		frame.setVisible(true);
		
		
		System.out.println(frame.getSize());
		
        	timer = new TankMopperTimer(this, model);
    		new Thread(timer).start();
        
	}
	
	
	
     void exitProcedure() {
    	model.getHighScores().writeScores();
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }
	
	public JFrame getFrame() {
		return frame;
	} 

	public TankMopperPanel getTankMopperPanel() {
		return tankmopperPanel;
	}
	
	public JButton[][] getButtonTable() {
		return tankmopperPanel.getButtonTable();
	}
	
	public void setGameStatus(String text) {
		tankmopperPanel.getGameStatus().setText(text);
	}
	
	public void removeGridButtons(TankMopperGameType type) {
		tankmopperPanel.removeGridButtons(type);
	}
	
	public void addGridButtons(TankMopperGameType type) {
		tankmopperPanel.addGridButtons(type);
	}
	
	public JButton getEasyButton() {
		return tankmopperPanel.getEasyButton();
	}

	public JButton getMediumButton() {
		return tankmopperPanel.getMediumButton();
	}
	
	public JButton getHardButton() {
		return tankmopperPanel.getHardButton();
	}
	public JButton getStartButton() {
		return tankmopperPanel.getStartButton();
	}




	
	public void resetTimer() {
		timer.resetTimer();
	}
	
	public void pauseTimer() {
		timer.pauseTimer();
	}

}