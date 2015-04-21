package bounds;

import javafx.scene.canvas.GraphicsContext;
import utils.Utils;
import utils.Vector;

public interface Rectangle extends Bounds
{
	
	public double width();
	
	public double height();
	
	public default boolean contains(Bounds b)
	{
		return b.isContainedByRectangle(this);
	}
	
	public default boolean containsCircle(Circle c)
	{
		return
		!((c.centerX() + c.radius() > getX() + width()) ||
		(c.centerX() - c.radius() < getX()) || 
		(c.centerY() + c.radius() > getY() + height()) ||
		(c.centerY() - c.radius() < getY()));
		
	}
	
	public default boolean containsRectangle(Rectangle r)
	{
		throw new UnsupportedOperationException();
	}
	
	public default boolean isContainedBy(Bounds b)
	{
		return b.containsRectangle(this);
	}
	
	public default boolean isContainedByRectangle(Rectangle r)
	{
		return r.containsRectangle(this);
	}
	
	public default boolean isContainedByCircle(Circle c)
	{
		return c.containsRectangle(this);
	}
	
	

	@Override
	public default boolean contains(double x, double y)
	{
		return Utils.isInRange(x, getX(), getX() + width()) && Utils.isInRange(y, getY(), getY() + height());
	}

	@Override
	public default Vector center()
	{
		return new Vector(centerX(), centerY());
	}

	@Override
	public default double distanceAtAngle(double angle)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public default boolean intersects(Bounds b)
	{
		return b.intersectsRectangle(this);
	}

	@Override
	public default boolean intersectsCircle(Circle circle)
	{
		double x = Math.abs(centerX() - circle.centerX());
		double y = Math.abs(centerY() - circle.centerY());
		double xapo = width() / 2;
		double yapo = height() / 2;
		
		if(x > (xapo + circle.radius())) return false;
		if(y > (yapo + circle.radius())) return false;
		
		if(x <= xapo || y <= yapo) return true;
		
		double cornerDistanceSquared = Math.pow(x - xapo, 2) + Math.pow(y - yapo, 2);
		return cornerDistanceSquared <= Math.pow(circle.radius(), 2);
	}

	@Override
	public default boolean intersectsRectangle(Rectangle r)
	{
		return contains(r.getX(), r.getY()) || r.contains(getX(), getY());
	}
	
	public double getX(); 
	public double getY();
	

	@Override
	public default double centerX()
	{
		return getX() + width() / 2;
	}

	@Override
	public default double centerY()
	{
		return getY() + height() / 2;
	}
	
	public static Rectangle of(double x, double y, double width, double height)
	{
		return new Rectangle()
		{
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
			
			public double width()
			{
				return width;
			}
			
			public double height()
			{
				return height;
			}
			
		};
	}
	
	public default boolean isContainedByLine(Line l)
	{
		return l.containsRectangle(this);
	}
	
	public default boolean intersectsLine(Line l)
	{
		return l.intersectsRectangle(this);
	}
	
	public default void stroke(GraphicsContext g)
	{
		g.strokeRect(getX(), getY(), width(), height());
	}
	
	public default void fill(GraphicsContext g)
	{
		g.fillRect(getX(), getX(), width(), height());
	}

}
