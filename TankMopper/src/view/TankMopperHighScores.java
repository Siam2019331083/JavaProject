package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.TankMopperScore;

public class TankMopperHighScores {

	private static final String fileName = "/scores.txt";
	private static final String pathName = System.getProperty("user.home") + 
			"/TankMopper";

	private int gameType;

	private List<TankMopperScore> scores;

	private String lineSeparator;

	public TankMopperHighScores() {
		this.scores = new ArrayList<TankMopperScore>();
		this.lineSeparator = System.getProperty("line.separator");
		createFile();
	}

	private void createFile() {
		File file = new File(pathName);
		if (!file.exists()) {
			file.mkdir();
			file = new File(pathName + fileName);
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void readScores() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathName + fileName));
			String line = "";
			boolean firstTimeSwitch = true;
			while ((line = reader.readLine()) != null) {
				if (firstTimeSwitch) {
					this.gameType = Integer.valueOf(line.trim());
					firstTimeSwitch = false;
				} else {
					TankMopperScore score = new TankMopperScore();
					score.getFileString(line);
					scores.add(score);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeScores() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(pathName + fileName));
			writer.write(Integer.toString(gameType));
			writer.write(lineSeparator);
			for (int i = 0; i < scores.size(); i++) {
				TankMopperScore score = scores.get(i);
				String s = score.setFileString();
				writer.write(s);
				writer.write(lineSeparator);
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addScore(int gameLevel, long milliseconds, 
			String displayTime) {
		TankMopperScore score = new TankMopperScore(gameLevel, 
				milliseconds, displayTime);
		scores.add(score);
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public List<TankMopperScore> getScoresByDate() {
		List<TankMopperScore> newList = new ArrayList<TankMopperScore>();

		for (int i = 0; i < scores.size(); i++) {
			TankMopperScore score = scores.get(i);
			if (score.getGameLevel() == getGameType()) {
				newList.add(score.clone());
			}
		}

		Collections.sort(newList, new DateComparator());
		return Collections.unmodifiableList(newList);
	}

	public List<TankMopperScore> getScoresByTime() {
		List<TankMopperScore> newList = new ArrayList<TankMopperScore>();

		for (int i = 0; i < scores.size(); i++) {
			TankMopperScore score = scores.get(i);
			if (score.getGameLevel() == getGameType()) {
				newList.add(scores.get(i).clone());
			}
		}

		Collections.sort(newList, new TimeComparator());
		return Collections.unmodifiableList(newList);
	}

	public class DateComparator implements Comparator<TankMopperScore> {

		@Override
		public int compare(TankMopperScore o1, TankMopperScore o2) {
			return o2.getTimestamp().compareTo(o1.getTimestamp());
		}

	}

	public class TimeComparator implements Comparator<TankMopperScore> {

		@Override
		public int compare(TankMopperScore o1, TankMopperScore o2) {
			long x = o1.getMilliseconds() - o2.getMilliseconds();
			if (x == 0L) {
				return o2.getTimestamp().compareTo(o1.getTimestamp());
			} else {
				return (int) x;
			}
		}

	}

}