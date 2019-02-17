import java.util.ArrayList;
import java.util.Comparator;

public class CombatSystem {

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



	public void UpdateCombat(ActorInteraction interaction) {
		switch (interaction.interact)
		{
		case DEATH_SAVE: //Handles 20, 1, >10 and <10
			int roll = interaction.getInfluence();
			
			Player target = (Player)interaction.reactor;
			if (target.getStats().getHitPoints() > 0) {
				target.resetDeathSavingThrow();
			} else {
				if (roll == 20) {
					interaction.reactor.getStats().setHitPoints(1);
					target.resetDeathSavingThrow();
				} else if (roll == 1) {
					for (int i = 0; i < 2; i++) {
						if (target.deathSavingThrowFailure() >= 3) {
							target.isAlive = false;
							target.resetDeathSavingThrow();
							break;
						}
					}
				} else if (roll >= 10) {
					if (target.deathSavingThrowSuccess() >= 3) {
						target.isStable = true;
						target.resetDeathSavingThrow();
					}
				} else {
					if (target.deathSavingThrowFailure() >= 3) {
						target.isAlive = false;
						target.resetDeathSavingThrow();
					}
				}
			}
			break;
		case PHYSICAL_ATTACK:
			int damage = interaction.getInfluence();
			interaction.reactor.loseHP(damage);
			if (interaction.reactor instanceof Player && interaction.reactor.getStats().getHitPoints() <= 0) {
				target = (Player)interaction.reactor;
				target.isUnconscious = true;
				target.isStable = false;
			}
			break;
		case MAGICAL_ATTACK:
			System.out.println("Magical-dagical uwuwu");
			break;
		case HEAL:
			int hitPoints = interaction.getInfluence();
			interaction.reactor.gainHP(hitPoints);
			break;
		default:
			break;
		}
	}
}