package model;

public class TankMopperGameStatus {

	private boolean tank[][];

	private boolean flag[][];
	
	private boolean exposed[][];
	
	private boolean checkwinbool[][];

	private int tanksFound;
	private int tanksRemaining;

	private long startTime;
	private long elapsedTime;

	public TankMopperGameStatus(TankMopperGameType type) {
		resetGameStatus(type);
	}

	public void resetGameStatus(TankMopperGameType type) {
		this.tanksRemaining = type.getNumberOfTanks();
		this.tanksFound = 0;

		this.tank = new boolean[type.getRow()][type.getColumn()];
		this.flag = new boolean[type.getRow()][type.getColumn()];
		this.exposed = new boolean[type.getRow()][type.getColumn()];
		this.checkwinbool = new boolean[type.getRow()][type.getColumn()];

		resetBooleans(type);
		setTanks(type);
	}

	private void resetBooleans(TankMopperGameType type) {
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				tank[x][y] = false;
				flag[x][y] = false;
				exposed[x][y] = false;
				checkwinbool[x][y] = false;
			}
		}
	}

	private void setTanks(TankMopperGameType type) {
		int count = 0;
		while (count < tanksRemaining) {
			int randx = (int) (Math.random() * (type.getRow() - 2) + 1);
			int randy = (int) (Math.random() * (type.getColumn() - 2) + 1);
			if (tank[randx][randy] == false) {
				tank[randx][randy] = true;
				checkwinbool[randx][randy] = true;
				count++;
			}
		}
	}

	public boolean isTank(int x, int y) {
		return tank[x][y];
	}

	public boolean isFlag(int x, int y) {
		return flag[x][y];
	}

	public void setFlag(int x, int y, boolean flag) {
		this.flag[x][y] = flag;
	}

	public boolean isExposed(int x, int y) {
		return exposed[x][y];
	}

	public void setExposed(int x, int y, boolean exposed) {
		this.exposed[x][y] = exposed;
	}

	public boolean isBooleanWin(int x, int y) {
		return checkwinbool[x][y];
	}

	public void setBooleanWin(int x, int y, boolean win) {
		this.checkwinbool[x][y] = win;
	}

	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}

	public long getStartTime() {
		return startTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		this.elapsedTime = System.currentTimeMillis() - getStartTime();
		return elapsedTime;
	}

	public long getElapsedTimeInSeconds() {
		return elapsedTime / 1000L;
	}

	public String getFormattedElapsedTime() {
		StringBuilder sb = new StringBuilder();

		long currTime = getElapsedTimeInSeconds();
		int hours = (int) (currTime / 3600L);
		currTime -= (long) hours * 3600L;
		int minutes = (int) (currTime / 60L);
		int seconds = (int) currTime - minutes * 60;

		if (hours > 0) {
			sb.append(hours);
			sb.append(":");
			if (minutes < 10) {
				sb.append("0");
			}
		}
		if (minutes > 0) {
			sb.append(minutes);
			sb.append(":");
			if (seconds < 10) {
				sb.append("0");
			}
		}
		sb.append(seconds);

		return sb.toString();
	}

	public int tankFound() {
		++this.tanksFound;
		return --this.tanksRemaining;
	}

	public int tankNotFound() {
		--this.tanksFound;
		return ++this.tanksRemaining;
	}

	public int getTanksRemaining() {
		return tanksRemaining;
	}

	public int getTanksFound() {
		return tanksFound;
	}

	
	public int surroundingTanks(TankMopperGameType type, int x, int y) {
		int surTanks = 0;
		for (int q = x - 1; q <= x + 1; q++) {
			for (int w = y - 1; w <= y + 1; w++) {
				while (true) {
					
					if (q < 0 || w < 0 || q >= type.getRow()
							|| w >= type.getColumn())
						break;
					if (tank[q][w] == true)
						surTanks++;
					break;
				}
			}
		}
		return surTanks;
	}

}
