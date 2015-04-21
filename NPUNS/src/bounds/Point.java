package bounds;

import javafx.scene.canvas.GraphicsContext;

public class Point implements Bounds
{
	public final double x, y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean isContainedBy(Bounds b)
	{
		return b.contains(x, y);
	}

	@Override
	public boolean isContainedByCircle(Circle c)
	{
		return c.contains(x, y);
	}

	@Override
	public boolean isContainedByRectangle(Rectangle r)
	{
		return r.contains(x, y);
	}

	@Override
	public boolean contains(double x, double y)
	{
		return this.x == x && this.y == y;
	}

	@Override
	public boolean contains(Bounds b)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsCircle(Circle c)
	{
		return c.centerX() == x && c.centerY() == y && c.radius() == 0;
	}

	@Override
	public boolean containsRectangle(Rectangle r)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public double centerX()
	{
		return x;
	}

	@Override
	public double centerY()
	{
		return y;
	}

	@Override
	public double distanceAtAngle(double angle)
	{
		return 0;
	}

	@Override
	public boolean intersects(Bounds b)
	{
		return b.contains(x, y);
	}

	@Override
	public boolean intersectsCircle(Circle circle)
	{
		return intersects(circle);
	}

	@Override
	public boolean intersectsRectangle(Rectangle r)
	{
		return intersects(r);
	}

	@Override
	public void fill(GraphicsContext g)
	{
		g.fillOval(x, y, 0, 0);;
	}

	@Override
	public void stroke(GraphicsContext g)
	{
		g.strokeOval(x, y, 0, 0);
	}

	@Override
	public boolean isContainedByLine(Line line)
	{
		return line.contains(x, y);
	}

	@Override
	public boolean intersectsLine(Line line)
	{
		return line.intersects(this);
	}

}
