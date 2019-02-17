import java.util.ArrayList;
import java.util.Comparator;

public class CombatSystem {
	public static ArrayList<Actor> makeNewList() {
		ArrayList<Actor> actorList = new ArrayList<Actor>();
		actorList.add(new Player(1, 7, 0, 0));
		actorList.add(new Player(1, 5, 0, 0));
		actorList.add(new Player(1, 8, 0, 0));
		actorList.add(new Enemy(1, 24, 0, 0, 3));
		actorList.add(new Enemy(1, 3, 0, 0, 3));
		return actorList;
	}
	
	private static ArrayList<Actor> getActorReferencesSortedByInitiative(ArrayList<Actor> actors) {
		ArrayList<Actor> sortedActors = new ArrayList<Actor>();
		ArrayList<Tuple<Actor, Integer>> actorInitiatives = new ArrayList<Tuple<Actor, Integer>>();
		for (Actor actor : actors) {
			if (actor instanceof Enemy) {
				Enemy enemy = (Enemy) actor;
				for (int initiative : enemy.getInitiativeList()) {
					actorInitiatives.add(new Tuple<Actor, Integer>(actor, initiative));
				}
			} else { // By process of elimination, actor must be a Player
				actorInitiatives.add(new Tuple<Actor, Integer>(actor, actor.getInitiative()));
			}
		}
		actorInitiatives.sort(new Comparator<Tuple<Actor, Integer>>() {
			public int compare(Tuple<Actor, Integer> t1, Tuple<Actor, Integer> t2) {
				if (t1.y.equals(t2.y)) {
					return (int)(2 * (Math.random() - 0.5));
				}
				return t1.y - t2.y;
			};
		});
		for (Tuple<Actor, Integer> actorInitiative : actorInitiatives) {
			sortedActors.add(actorInitiative.x);
		}
		return sortedActors;
	}
	
	
}