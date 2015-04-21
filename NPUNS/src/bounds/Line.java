package bounds;

import javafx.scene.canvas.GraphicsContext;
import objects.Locatable;
import utils.Utils;
import utils.Vector;

public class Line implements Bounds
{
	public final double x1;
	public final double y1;
	public final double x2;
	public final double y2;
	
	public Line(double x1, double y1, double x2, double y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Line(Locatable start, Locatable end)
	{
		this(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	public Line(Locatable start, double angle, double distance)
	{
		x1 = start.getX();
		y1 = start.getY();
		Vector v = Vector.fromPolar(distance, angle);
		x2 = v.x + x1;
		y2 = v.y + y1;
	}
	
	

	@Override
	public boolean isContainedBy(Bounds b)
	{
		return b.contains(this);
	}

	@Override
	public boolean isContainedByCircle(Circle c)
	{
		return c.contains(x1, y1) && c.contains(x2, y2);
	}

	@Override
	public boolean isContainedByRectangle(Rectangle r)
	{
		return r.contains(x1, y1) && r.contains(x2, y2);
	}

	@Override
	public boolean contains(double x, double y)
	{
		return false;
	}

	@Override
	public boolean contains(Bounds b)
	{
		return b.isContainedByLine(this);
	}

	@Override
	public boolean containsCircle(Circle c)
	{
		if(c.radius() > 0) return false;
		return contains(c.centerX(), c.centerY());
	}

	@Override
	public boolean containsRectangle(Rectangle r)
	{
		return false;
	}

	@Override
	public double centerX()
	{
		return (x1 + x2) / 2;
	}

	@Override
	public double centerY()
	{
		return (y1 + y2) / 2;
	}

	@Override
	public double distanceAtAngle(double angle)
	{
		return 0; // TODO Make it return correct distances in the direction of the line
	}

	@Override
	public boolean intersects(Bounds b)
	{
		return b.intersectsLine(this);
	}

	@Override
	public boolean intersectsCircle(Circle circle)
	{
		return distanceSquared(circle.centerX(), circle.centerY()) <= circle.radius() * circle.radius();
	}

	@Override
	public boolean intersectsRectangle(Rectangle r)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void fill(GraphicsContext g)
	{
		g.strokeLine(x1, y1, x2, y2);
	}

	@Override
	public void stroke(GraphicsContext g)
	{
		g.strokeLine(x1, y1, x2, y2);
	}
	
	public double length()
	{
		return Math.sqrt(lengthSquared());
	}
	
	private double lengthSquared()
	{
		return Utils.distanceSquared(x1, y1, x2, y2);
	}

	public double distanceSquared(double x, double y)
	{
		double lSqrd = lengthSquared();
		if(lSqrd == 0.0) return Utils.distanceSquared(x1, y1, x, y);
		double t = ((x - x1) * (x2 - x1) + (y - y1) * (y2 - y1)) / lSqrd;
		if(t < 0.0) return Utils.distanceSquared(x, y, x1, y1);
		if(t > 1.0) return Utils.distanceSquared(x, y, x2, y2);
		return Utils.distanceSquared(x, y, x1 + t * (x2 - x1), y1 + t * (y2 - y1));
	}
	
	public double distance(double x, double y)
	{
		return Math.sqrt(distanceSquared(x, y));
	}


	@Override
	public boolean isContainedByLine(Line line)
	{
		return false; //Check if line overlaps this one?
	}

	@Override
	public boolean intersectsLine(Line line)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
