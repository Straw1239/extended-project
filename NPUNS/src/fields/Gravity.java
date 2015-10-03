package fields;

import objects.Body;
import objects.Body.Impact;
import utils.Utils;
import vector.Integration;

public class Gravity implements Field
{
	public static double G = 1.0;
	
	private Body source;
	public Gravity(Body source)
	{
		this.source = source;
	}
	
	public double strengthAtPosition(double x, double y)
	{
		return G * source.mass() * (Utils.distanceSquared(source.getX(), source.getY(), x, y));
	}
	
	private double[] comp = new double[4];

	@Override
	public void applyToBody(Body b, double dt)
	{
		double[] newData = Integration.orbitalRK4(this::xComponent, this::yComponent, b.getX(),  b.getY(), 
																					  b.getXV(), b.getYV(), dt, comp);
		b.setPosition(newData[0], newData[1]);
		b.setVelocity(newData[2], newData[3]);
		
	}
	@Override
	public double xComponent(double x, double y)
	{
		double distance = Math.hypot(x - source.getX(), y - source.getY());
		return G * source.mass() * (source.getX() - x) / (distance * distance * distance); 
	}
	@Override
	public double yComponent(double x, double y)
	{
		double distance = Math.hypot(x - source.getX(), y - source.getY());
		return G * source.mass() * (source.getY() - y) / (distance * distance * distance); 
	}

}
