
public class Player extends Actor{
	
	public boolean isUnconscious = false;
	public boolean isStable = true;
	
	int[] deathSavingThrow = {0, 0}; //savingThrow[0] = success, savingThrow[1] = failure
	
	public int deathSavingThrowSuccess() {
		return deathSavingThrow[0]++;
	}
	public int deathSavingThrowFailure() {
		return deathSavingThrow[1]++;
	}
	public void resetDeathSavingThrow() {
		deathSavingThrow[0] = 0;
		deathSavingThrow[1] = 0;
	}
	//public boolean isStable = true;
	
	private int experiencePoints = 0;
	
	
	//levelXP[0] corresponds to level 1
	public int[] levelXP = {0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000,
					 100000, 120000, 140000, 165000, 195000, 225000, 265000, 305000, 355000}; 
	
	Player() {
		
	}

	Player(int lev, double xPos, double yPos) {
		super(lev, xPos, yPos);
	}
	
	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}
	
	public int getExperiencePoints() {
		return experiencePoints;
	}
	
	public int gainXP(int experiencePointGain) {
		return experiencePoints += experiencePointGain;
	}
}
