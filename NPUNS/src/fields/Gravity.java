package fields;

import objects.Body;
import objects.Body.Impact;
import utils.Utils;

public class Gravity implements Field
{
	public static double G = 1.0;
	
	private Body source;
	public Gravity(Body source)
	{
		this.source = source;
	}
	@Override
	public double strengthAtPosition(double x, double y)
	{
		return G * source.mass() * (Utils.distanceSquared(source.getX(), source.getY(), x, y));
	}

	@Override
	public void applyToBody(Body b)
	{
		b.hitBy(new Impact(source, new Body.Change(Body.ACCELERATE, strengthAtPosition(b.getX(), b.getY()))));
	}

}
