package objects;

import fields.Field;
import fields.Gravity;
import utils.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import bounds.Bounds;
import bounds.Point;


public abstract class Particle extends Body
{
	protected Particle(double x, double y, double mass)
	{
		super(x, y, mass);
	}

	public Bounds bounds()
	{
		return new Point(x, y);
	}
	private static Color color = Color.WHITE;
	private static double radius = 5;
	
	
	public static Particle newIndestructible(double x, double y, double mass)
	{
		return new Particle(x, y, mass)
		{
			Field g = new Gravity(this);
			Field[] fs = {g};
			@Override
			public boolean isDestroyed()
			{
				return false;
			}

			@Override
			public void draw(GraphicsContext g)
			{
				g.setFill(color);
				g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
			}

			@Override
			public Field[] getFields()
			{
				return fs;
			}
		};
	}
	
	
	
	public static Particle newIndestructible(double x, double y, double mass, Vector velocity)
	{
		Particle result = newIndestructible(x, y, mass);
		result.dx = velocity.x;
		result.dy = velocity.y;
		return result;
	}
}
