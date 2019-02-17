import java.util.Random;

public class Actor{

	public String name;
	
	public Random rand;

	public boolean hasDarkVision = false; //12
	public boolean hasLight = false; //4
	public boolean isAlive = true;
	public boolean isFallen = false; //true if the character has fallen down

	private int level;
	public int initiative;
	private int sightRadius = 10000; //Essentially infinite
	
	private double[] position = new double[2];
	
	private ObjectStats stats;
	


	public enum actorType {
		PLAYER,
		ENEMY,
	};

	public actorType type;
	

	public Actor() {

	}

	public Actor(int lev, double xPos, double yPos, actorType t) {
		level = lev;
		type = t;
		position[0] = xPos;
		position[1] = yPos;
		stats = new ObjectStats(0, 0, 0, 0, 0, 0, 0, 0, 6);
		stats.setProficiencyBonus(calcProficiencyBonus(level));
	}
	
	public Actor(int lev, double xPos, double yPos, ObjectStats stats) {
		level = lev;
		position[0] = xPos;
		position[1] = yPos;
		this.stats = stats;
		this.stats.setProficiencyBonus(calcProficiencyBonus(level));
	}
	
	public void setLevel(int lev) {
		level = lev;
	}

	public int getLevel() {
		return level;
	}

	public void setInitiative(int ini) {
		initiative = ini;
	}

	public int getInitiative() {
		return initiative;
	}
	
	public void setPosition(double x, double y) {
		position[0] = x;
		position[1] = y;
	}
	
	public double[] getPosition() {
		return position;
	}
	
	public int[] getCoordinates() {
		return new int[] {(int)position[0], (int)position[1]};
	}

	public void setStats(int stren, int dex, int con, 
			int intel, int wis, int cha) {
		stats.setStrength(stren);
		stats.setDexterity(dex);
		stats.setConstitution(con);
		stats.setIntelligence(intel);
		stats.setWisdom(wis);
		stats.setCharisma(cha);
	}

	public ObjectStats getStats() {
		return stats;
	}
	
	

	public int rollD(int numSides) {

		int randRoll = rand.nextInt(numSides);

		if (numSides == 100) {
			return randRoll;
		} else {
			return randRoll + 1;
		}
	}

	public int[] rollD(int numSides, int rollCount) {

		int randRoll;
		int[] rollList = new int[rollCount];

		for (int i = 0; i < rollList.length; i++) {
			randRoll = rand.nextInt(numSides);
			if(numSides != 100) {
				randRoll++;
			}
			rollList[i] = randRoll;
		}
		return rollList;
	}

	public int calcSightRadius (boolean isDark) {
		if (isDark && hasDarkVision) {
			sightRadius = 12;
		} else if (isDark && hasLight) {
			sightRadius = 4;
		} else if (isDark){
			sightRadius = 1;
		} else {
			sightRadius = 10000; //essentially infinity
		}
		return sightRadius;
	}
	
	//increases HP
	public int gainHP(int hitPointGain) {
		stats.setHitPoints(stats.getHitPoints() + hitPointGain);
		return stats.getHitPoints();
	}
	//decreases HP
	public int loseHP(int hitPointReduction) {
		stats.setHitPoints(stats.getHitPoints() - hitPointReduction);
		return stats.getHitPoints();
	}
	
	public int longRest() {
		stats.setHitPoints(stats.getMaxHitPoints());
		return stats.getHitPoints();
	}
	
	public int calcProficiencyBonus(int rating) {//rating is level for player and challenge rating for enemy
		return ((rating - 1) / 4) + 2;
	}
}