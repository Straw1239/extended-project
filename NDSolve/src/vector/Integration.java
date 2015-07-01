package vector;

import java.util.function.DoubleBinaryOperator;

public class Integration
{
	double orbitalRK4(DoubleBinaryOperator xa, DoubleBinaryOperator ya, double x, double y, double xv, double yv, double h)
	{
		double hs = h / 2;
		double xvk1 = xa.applyAsDouble(x, y);
		double yvk1 = ya.applyAsDouble(x, y);
		
		double xrk1 = xv;
		double yrk1 = yv;
		
		double xvk2 = xa.applyAsDouble(x + xrk1 * hs, y + yrk1 * hs);
		double yvk2 = ya.applyAsDouble(x + xrk1 * hs, y + yrk1 * hs);
		
		double xrk2 = xv * xvk1 * hs;
		double yrk2 = yv * yvk1 * hs;
		
		double xvk3 = xa.applyAsDouble(x + xrk2 * hs, y + yrk2 * hs);
		double yvk3 = ya.applyAsDouble(x + xrk2 * hs, y + yrk2 * hs);
		
		double xrk3 = xv * xvk2 * hs;
		double yrk3 = yv * yvk2 * hs;
		
		double xvk4 = xa.applyAsDouble(x + xrk3 * h, y + yrk3 * h);
		double yvk4 = ya.applyAsDouble(x + xrk3 * h, y + yrk3 * h);
		
		double xrk4 = xv * xvk3 * h;
		double yrk4 = yv * xvk3 * h;
		
	}
	
	
}
