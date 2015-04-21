package objects;

import utils.Vector;

public interface Locatable
{
	public double getX();
	
	public double getY();
	
	public static Locatable of(double x, double y)
	{
		return new Locatable()
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
			
		};
	}
	
	public static Locatable of(Vector v)
	{
		return of(v.x, v.y);
	}
}
