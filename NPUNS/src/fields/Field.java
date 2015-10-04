package fields;

import java.util.List;

import objects.Body;
import utils.Vector;
import vector.Integration;

public interface Field
{
	
	
	void applyToBody(Body b, double dt);
	
	Vector force(Body b);

	public static Field combine(List<Field> fs)
	{
		return new Field()
		{
			private double[] comp = {0, 0, 0, 0};
			

			@Override
			public void applyToBody(Body b, double dt)
			{
				
				
			}

			@Override
			public Vector force(Body b)
			{
				double x = 0, y = 0;
				for(Field f : fs)
				{
					Vector force = f.force(b);
					x += force.x;
					y += force.y;
				}
				return new Vector(x, y);
			}
			
		};
	}
	
	
	
	
	
	
}
