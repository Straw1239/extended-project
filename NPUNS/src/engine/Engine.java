package engine;

import java.util.Iterator;
import java.util.LinkedList;

import objects.Body;
import objects.Body.Impact;
import utils.Utils;
import utils.Vector;

/**
 * Main game engine. Performs all non graphical internal calculation.
 * Keeps track of Player, all bullets, enemies, and events.
 * Provides an immutable State object representing 
 * one snapshot of engine state.
 * 
 * @author Rajan Troll
 *
 */
public final class Engine 
{
	
	
	/**
	 * Number of ticks this Engine has executed
	 */
	private long updates = 0;
	
	private LinkedList<Body> bodies = new LinkedList<>();
	//private ListMultimap<Faction, ListMultimap<Set<Faction> ,GameObject>> objects = Multimaps.newListMultimap(new EnumMap<>(Faction.class), LinkedList::new);
	
	
	
	
	
	/**
	 * Representation of engine state. Replaced each update, 
	 * is immutable and can be passed around safely.
	 * Passed to the Renderer each frame. 
	 */
	private State state;
	
	
	/**
	 * Creates a new engine with the specified dimensions.
	 * These dimensions cannot be changed after creation.
	 * Initializes the player to the center of the game.
	 * @param width
	 * @param height
	 */
	public Engine()
	{
		
		generateState();
	}
	
	private void generateState()
	{
		state = new State(bodies, updates);
	}
	
	
	
	/**
	 * Returns a State object representing the internal state of all game elements.
	 * State is immutable, engine state cannot be changed. This method does not generate 
	 * a new State, but returns the one calculated after each engine update to all callers 
	 * until the next update.
	 * @return
	 */
	public State getState() 
	{
		return state;
	}
	
	
	
	
	/**
	 * Advances the game represented by this engine one tick.
	 * Updates all game entities, processes all events, and currently can add new enemies.
	 * Also updates the Engine's State object to the latest state.
	 */
	public void update()
	{
		updateObjects();
		Vector center = Vector.ZERO;
		for(Body b : bodies)
		{
			Vector accel = new Vector(b).sub(center);
			accel = accel.scale(-5 / accel.lengthSquared());
			b.hitBy(new Impact(null, new Body.Change(Body.ACCELERATE, accel)));
		}
		updates++;
		generateState();
		
	}
	
	private void updateObjects()
	{
		//Update all objects
		Iterator<Body> all = bodies.iterator();
		while(all.hasNext())
		{
			Body next = all.next();
			next.update(state, 1.0);
			if(next.isDestroyed())
			{
				all.remove();
				//handler.addAllEvents(next.onDeath(state));
			}
		}
	
		
		
	}

	/**
	 * Returns number of engine updates that have occured
	 * @return Time in ticks
	 */
	public long getTime()
	{
		return updates;
	}
	
	
	
	
	
	

	/**
	 * Pauses the engine for the specified time, first finishing execution of the current tick
	 * @param milliseconds to sleep for
	 */
	private volatile long sleepTime = 0;
	
	public synchronized void sleep(long time)
	{
		sleepTime += time;	
	}

	public synchronized void reset()
	{
		synchronized(bodies) 
		{
			
				updates = 0;
				sleepTime = 0;
				bodies.clear();
				generateState();
			
		}
	}

	public void addBody(Body b)
	{
		bodies.add(b);
	}	
}
