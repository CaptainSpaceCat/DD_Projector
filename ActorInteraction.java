
public class ActorInteraction {
	Actor actor;
	Actor reactor;
	private int influence;
	InteractionCode interact; 
	boolean isCritical;

	ActorInteraction() {

	}

	ActorInteraction(Actor actor, Actor reactor, int influence, InteractionCode interact, boolean isCritical) {
		this.actor = actor;
		this.reactor = reactor;
		setInfluence(influence);
		this.interact = interact;
		this.isCritical = isCritical;
	}

	public void setInfluence(int influence) {
		this.influence = influence;
	}
	
	public int getInfluence() {
		return influence;
	}

	
}
