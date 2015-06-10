package fields;

import objects.Body;

public interface Field
{
	double strengthAtPosition(double x, double y);
	
	void applyToBody(Body b);
	
	
	
	
}
