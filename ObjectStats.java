
public class ObjectStats {
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	
	public int s_mod = calcStatMod(strength);
	public int d_mod = calcStatMod(dexterity);
	public int c_mod = calcStatMod(constitution);
	public int i_mod = calcStatMod(intelligence);
	public int w_mod = calcStatMod(wisdom);
	public int ch_mod = calcStatMod(charisma);
	
	private int maxHitPoints; //may be exceeded 
	private int hitPoints; //may be negative
	
	private int proficiencyBonus;
	
	private int speed;
	
	

	ObjectStats(int stren, int dex, int con, 
			int intel, int wis, int cha, int maxHP, int HP, int spd) {
		strength = stren;
		dexterity = dex;
		constitution = con;
		intelligence = intel;
		wisdom = wis;
		charisma = cha;
		
		maxHitPoints = maxHP;
		hitPoints = HP;
		
		speed = spd;
	}

	public void setStrength(int stren) {
		strength = stren;
	}

	public int getStrength() {
		return strength;
	}

	public void setDexterity(int dex) {
		dexterity = dex;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setConstitution(int con) {
		constitution = con;
	}

	public int getConstitution() {
		return constitution;
	}

	public void setIntelligence(int intel) {
		intelligence = intel;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setWisdom(int wis) {
		wisdom = wis;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setCharisma(int cha) {
		charisma = cha;
	}

	public int getCharisma() {
		return charisma;
	}
	
	public void setMaxHitPoints(int maxHP) {
		maxHitPoints = maxHP;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}
	
	public void setHitPoints(int HP) {
		hitPoints = HP;
	}

	public int getHitPoints() {
		return hitPoints;
	}
	
	public void setSpeed(int spd) {
		speed = spd;
	}
	
	public int getSpeed() {
		return speed;
	}
	//Calculates the stat modifier given an ability stat
	private int calcStatMod (int stat) {
		return (stat - 10) / 2;
	}

	public void setProficiencyBonus(int pBonus) {
		proficiencyBonus = pBonus;
	}
	
	public int getProficiencyBonus() {
		return proficiencyBonus;
	}

	
	
	
}

