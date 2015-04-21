package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import utils.Utils;
import utils.Vector;
import bounds.Bounds;
import engine.State;
/**
 * Base object of the object hierarchy.
 * Each game object has an x, y, and Faction.
 * 
 * @author Rajan Troll
 *
 */
public abstract class Body implements ReadableBody
{
	protected double x, y;
	protected double dx, dy;
	protected double mass;

	
	protected Body(double x, double y, double mass)
	{
		this.x = x;
		this.y = y;
		this.mass = mass;
	}
	
	public void update(State d)
	{
		 x += dx;
		 y += dy;
	}
	
	public boolean collidesWith(Body entity)
	{
		return bounds().intersects(entity.bounds());
	}
	
	public abstract Bounds bounds();
	
	
	@Override
	public double getX()
	{
		return x;
	}
	
	@Override
	public double getY()
	{
		return y;
	}
	
	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError(e);
		}
	}
	
	
	
	public double angleTo(ReadableBody o)
	{
		return Utils.angle(this, o);
	}
	
	public Collection<Effect> specialEffects()
	{
		return Collections.emptyList();
	}
	
	public void renderHUD(GraphicsContext g)
	{
		
	}
	
	public static Body dataOf(double x, double y)
	{
		return new Body(x, y, 0)
		{
			@Override
			public void draw(GraphicsContext g){}

			@Override
			public void update(State d){}

			@Override
			public boolean collidesWith(Body entity)
			{
				return false;
			}

			@Override
			public Bounds bounds()
			{
				return Bounds.NONE;
			}

			@Override
			public boolean isDestroyed()
			{
				return false;
			}
			
		};
	}
	
	public static long collisions = 0; // TESTING ONLY
	
	public String toString()
	{
		return String.format("Body at:(%f, %f)", x, y);
	}
	
	
	
	public void hitBy(Impact impact)
	{
		for(Change c : impact.changes)
		{
			handleChange(c, impact.source);
		}
	}
	
	public static final int IMPULSE = 1;
	public static final int MOVE = 0;
	public static final int ACCELERATE = 2;
	
	
	public boolean supportsOperation(int code)
	{
		switch(code)
		{
		
		case MOVE:
		case IMPULSE:
		case ACCELERATE:
			return true;
		default:
			return false;
		}
	}
	
	protected void handleChange(Change change, Body source)
	{
		switch(change.code)
		{
		
		case IMPULSE:
			Vector vi = (Vector) change.data;
			dx += vi.x / mass;
			dy += vi.y / mass;
			break;
		case ACCELERATE:
			Vector va = (Vector) change.data;
			dx += va.x;
			dy += va.y;
		case MOVE:
			Vector v = (Vector) change.data;
			x += v.x;
			y += v.y;
		default: //throw new IllegalArgumentException();
				
		}
	}
	
	public Impact collideWith(Body other)
	{
		return Impact.NONE;
	}
	
	public static final class Change
	{
		public final int code;
		public final Object data;
		
		public Change(int code, Object obj)
		{
			this.code = code;
			data = obj;
		}
	}
	
	public static final class Impact
	{
		/**
		 * Represents an Impact which has no effect on the target object.
		 */
		public static final Impact NONE = new Impact(null, Collections.emptyList());
		public Body source;
		public Collection<Change> changes;
		
		
		
		public Impact(Body origin, Collection<Change> effects)
		{
			source = origin;
			changes = effects;
		}
		
		public Impact(Body origin)
		{
			this(origin, new ArrayList<>());
		}
		
		public Impact(Body origin, Change... changes)
		{
			this(origin, new ArrayList<>(Arrays.asList(changes)));
		}
		
		
	}
	
	
	/**
	 * Collides the two specified game objects. 
	 * @param obj
	 * @param other
	 */
	public static void collide(Body obj, Body other)
	{
		Impact forOther = obj.collideWith(other);
		Impact forObj = other.collideWith(obj);
		other.hitBy(forOther);
		obj.hitBy(forObj);
	}
	
	
	
	
	
	
	
	
	
}
