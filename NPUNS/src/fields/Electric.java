package fields;

import objects.Body;
import vector.Integration;

public class Electric implements Field
{
	
	Body source;
	
	public Electric(Body source)
	{
		this.source = source;
	}

	@Override
	public double xComponent(double x, double y)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double yComponent(double x, double y)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	private double[] comp = new double[4];

	@Override
	public void applyToBody(Body b, double dt)
	{
		double[] newData = Integration.orbitalRK4((x, y) -> xComponent(x, y) * b.charge() / b.mass(), 
												(x, y) -> yComponent(x, y) * b.charge() / b.mass(), b.getX(),  b.getY(), 
				  b.getXV(), b.getYV(), dt, comp);
		b.setPosition(newData[0], newData[1]);
		b.setVelocity(newData[2], newData[3]);

	}

}
