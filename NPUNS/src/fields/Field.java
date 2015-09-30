package fields;

import objects.Body;

public interface Field
{
	double xComponent(double x, double y);
	
	double yComponent(double x, double y);
	
	void applyToBody(Body b, double dt);
	
	
	
	
}
