
public class Enemy extends Actor {

	public int initiativeCount;
	public int[] initiativeList = new int[initiativeCount];
	
	public int[] xpDrop = {0, 200, 450, 700, 1100, 1800, 2300, 2900, 3900, 5000, 5900, 7200, 8400,
					10000, 11500, 13000, 15000, 18000, 20000, 22000, 25000, 33000, 41000,
					50000, 62000, 75000, 90000, 105000, 120000, 135000, 155000};
	
	Enemy() {

	}

	Enemy(int lev, double xPos, double yPos, int iniCount){
		super(lev, xPos, yPos);
		initiativeCount = iniCount;
	}

	public int[] fillInitiativeList() {
		initiativeList[0] = getInitiative();
		for (int i = 1; i < initiativeList.length; i++) {
			int cur = calcEnemyInitiative();
			//for (int j = 0; j < initiativeList.length; j++) {
				if (cur < initiativeList[i]) {
					
				}
			//}
			initiativeList[i] = calcEnemyInitiative();
		}
		return initiativeList;
	}
	
	public int[] getInitiativeList() {
		return initiativeList;
	}
	
	private int calcEnemyInitiative() {
		return rollD(20) + getStats().d_mod;
	}
	
	
}
