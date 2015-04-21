package bounds;

import javafx.scene.canvas.GraphicsContext;
import objects.Locatable;
import utils.Vector;

public interface Bounds extends Locatable
{
	public boolean isContainedBy(Bounds b);
	
	public boolean isContainedByCircle(Circle c);
	
	public boolean isContainedByRectangle(Rectangle r);
	
	public boolean contains(double x, double y);
	
	public boolean contains(Bounds b);
	
	public boolean containsCircle(Circle c);
	
	public boolean containsRectangle(Rectangle r);
	
	public default boolean containsLine(Line l)
	{
		return l.isContainedBy(this);
	}
	
	public default boolean contains(Vector v)
	{
		return contains(v.x, v.y);
	}
	
	
	
	public default Vector center()
	{
		return new Vector(centerX(), centerY());
	}
	
	public double centerX();
	
	public double centerY();
	
	public default double getX()
	{
		return centerX();
	}
	
	public default double getY()
	{
		return centerY();
	}
	
	public double distanceAtAngle(double angle);
	
	public boolean intersects(Bounds b);
	
	public boolean intersectsCircle(Circle circle);
	
	public boolean intersectsRectangle(Rectangle r);
	
	public static final Bounds NONE = new Bounds()
	{
		@Override
		public boolean contains(double x, double y)
		{
			return false;
		}

		@Override
		public double centerX()
		{
			return Double.NaN;
		}

		@Override
		public double centerY()
		{
			return Double.NaN;
		}

		@Override
		public double distanceAtAngle(double angle)
		{
			return 0;
		}

		@Override
		public boolean intersects(Bounds b)
		{
			return false;
		}

		@Override
		public boolean intersectsCircle(Circle circle)
		{
			return false;
		}

		@Override
		public boolean intersectsRectangle(Rectangle r)
		{
			return false;
		}

		@Override
		public boolean contains(Bounds b)
		{
			return false;
		}

		@Override
		public boolean containsCircle(Circle c)
		{
			return false;
		}

		@Override
		public boolean containsRectangle(Rectangle r)
		{
			return false;
		}

		@Override
		public boolean isContainedBy(Bounds b)
		{
			return false;
		}

		@Override
		public boolean isContainedByCircle(Circle c)
		{
			return false;
		}

		@Override
		public boolean isContainedByRectangle(Rectangle r)
		{
			return false;
		}

		@Override
		public void fill(GraphicsContext g)
		{
			// Nothing
			
		}

		@Override
		public void stroke(GraphicsContext g)
		{
			// Nothing
			
		}

		@Override
		public double getX()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getY()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isContainedByLine(Line line)
		{
			return false;
		}

		@Override
		public boolean intersectsLine(Line line)
		{
			return false;
		}
	};
	
	public void fill(GraphicsContext g);
	
	public void stroke(GraphicsContext g);

	public boolean isContainedByLine(Line line);

	public boolean intersectsLine(Line line);
	
	
	
	
}
