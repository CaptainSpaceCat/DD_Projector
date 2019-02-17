
public class Player extends Actor{
	
	private int experiencePoints = 0;
	
	//levelXP[0] corresponds to level 1
	public int[] levelXP = {0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000,
					 100000, 120000, 140000, 165000, 195000, 225000, 265000, 305000, }; 
	
	Player() {

	}

	Player(int lev, int ini, double xPos, double yPos) {
		super(lev, ini, xPos, yPos);
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
