package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GameMouseListener;
import controller.GridMouseListener;
import model.TankMopperGameType;
import model.TankMopperModel;

public class TankMopperPanel {

	private Font largeFont;
	private Font smallFont;

	private GridLayout gridLayout;
	
	private final GridMouseListener gridMouseListener;

	private JButton easyButton;
	private JButton mediumButton;
	private JButton hardButton;
	private JButton startButton;
	private JButton exit;
	private JButton stats;
	

	private JButton[][] buttonTable;

	private JLabel gameStatus;

	private JPanel flowPanel;
	private JPanel gridPanel;
	private JPanel mainPanel;
	
	private final TankMopperFrame frame;
	
	private final TankMopperModel model;

	public TankMopperPanel(TankMopperFrame frame, TankMopperModel model) {
		this.frame = frame;
		this.model = model;
		this.gridMouseListener = new GridMouseListener(frame, model);
		createGUIControl();
	}

	private void createGUIControl() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBackground(new Color(35,43,42));

		largeFont = TankMopperFont.getBoldFont(18);
		smallFont = TankMopperFont.getBoldFont(12);

		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.setBackground(new Color(35,43,42));



		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(new Color(35,43,42));

		ImageIcon ic1= new ImageIcon(getClass().getResource("tanki.png"));
		JLabel newGame = new JLabel("TANKMOPPER",ic1, JLabel.CENTER);
		newGame.setFont(largeFont); 
		newGame.setForeground(Color.blue);

		labelPanel.add(newGame);

		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(new Color(35,43,42));
		

		gameStatus = new JLabel(model.setGameStatus(), JLabel.RIGHT);
		gameStatus.setFont(largeFont);
		gameStatus.setForeground(Color.blue);

		statusPanel.add(gameStatus);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(35,43,42));
		
		GameMouseListener gameMouseListener = 
				new GameMouseListener(frame, model);

		easyButton = new JButton("Easy");
		mediumButton = new JButton("Medium");
		hardButton = new JButton("Hard");
		startButton = new JButton("Restart");
		exit = new JButton("Exit");
		stats = new JButton("Stats");
		

		easyButton.addMouseListener(gameMouseListener);
		mediumButton.addMouseListener(gameMouseListener);
		hardButton.addMouseListener(gameMouseListener);
		startButton.addMouseListener(gameMouseListener);
        exit.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent ae)
        	{
        		int choice=JOptionPane.showConfirmDialog(null,"Do you want to quit?","",JOptionPane.YES_NO_OPTION);
                if(choice==JOptionPane.YES_OPTION)
                {
                	frame.exitProcedure();
                }
        	}
        });
        
        stats.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent ae)
        	{
        		gridMouseListener.st();
        	}
        });
        
		easyButton.setFont(smallFont);
		easyButton.setBackground(new Color(63,81,181));
		easyButton.setForeground(Color.white);
		mediumButton.setFont(smallFont);
		mediumButton.setBackground(new Color(63,81,181));
		mediumButton.setForeground(Color.white);
		hardButton.setFont(smallFont);
		hardButton.setBackground(new Color(63,81,181));
		hardButton.setForeground(Color.white);
		startButton.setFont(smallFont);
	    startButton.setBackground(new Color(63,81,181));
		startButton.setForeground(Color.white);
		exit.setFont(smallFont);
		exit.setBackground(new Color(63,81,181));
		exit.setForeground(Color.white);
	    stats.setFont(smallFont);
		stats.setBackground(new Color(63,81,181));
		stats.setForeground(Color.white);
		
		Dimension d = mediumButton.getPreferredSize();
		easyButton.setPreferredSize(d);
		hardButton.setPreferredSize(d);
		startButton.setPreferredSize(d);
		stats.setPreferredSize(d);
		exit.setPreferredSize(d);
		

		buttonPanel.add(easyButton);
		buttonPanel.add(mediumButton);
		buttonPanel.add(hardButton);
		buttonPanel.add(startButton);
		buttonPanel.add(stats);
		buttonPanel.add(exit);
	

		controlPanel.add(labelPanel, BorderLayout.NORTH);
		controlPanel.add(buttonPanel, BorderLayout.CENTER);
		controlPanel.add(statusPanel, BorderLayout.SOUTH);
		
		
		flowPanel = new JPanel(new FlowLayout());
		flowPanel.setLayout(new BoxLayout(flowPanel, BoxLayout.PAGE_AXIS));
		flowPanel.setPreferredSize(model.getGameType().getMaximumSize());
		flowPanel.setBackground(new Color(63,81,181));
		
		gridLayout = new GridLayout(0, model.getGameType().getColumn());
		gridPanel = new JPanel(gridLayout);
		addGridButtons(model.getGameType());
		
		flowPanel.add(gridPanel);

		mainPanel.add(controlPanel);
		mainPanel.add(flowPanel);
	}

	public void addGridButtons(TankMopperGameType type) {
		calculateBorder(type);
		gridLayout.setColumns(type.getColumn());
		gridPanel.setPreferredSize(type.getPanelSize());
		buttonTable = new JButton[type.getRow()][type.getColumn()];
		Insets insets = new Insets(0, 0, 0, 0);

		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				buttonTable[x][y] = new JButton();
				buttonTable[x][y].addMouseListener(gridMouseListener);
				buttonTable[x][y].setFont(largeFont);
				buttonTable[x][y].setMargin(insets);
				//buttonTable[x][y].setForeground(Color.GRAY);
				//buttonTable[x][y].setBackground(Color.GRAY);
				gridPanel.add(buttonTable[x][y]);
			}
		}

	}

	public void removeGridButtons(TankMopperGameType type) {
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				buttonTable[x][y].removeMouseListener(gridMouseListener);
				gridPanel.remove(buttonTable[x][y]);
			}
		}
	}
	
	private void calculateBorder(TankMopperGameType type) {
		Dimension o = type.getMaximumSize();
		Dimension i = type.getPanelSize();
		
		int heightGap = o.height - i.height;
		int widthGap = o.width - i.width;
		
		int top = heightGap / 2;
		int bottom = heightGap - top;
		int left = widthGap / 2;
		int right = widthGap - left;
		
		flowPanel.setBorder(BorderFactory.createEmptyBorder(
				top, left, bottom, right));
	}

	public JButton[][] getButtonTable() {
		return buttonTable;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JLabel getGameStatus() {
		return gameStatus;
	}

	public JButton getEasyButton() {
		return easyButton;
	}

	public JButton getMediumButton() {
		return mediumButton;
	}

	public JButton getHardButton() {
		return hardButton;
	}
	public JButton getStartButton() {
		return startButton;
	}


}