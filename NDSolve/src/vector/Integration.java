package vector;

import java.util.function.DoubleBinaryOperator;

public class Integration
{
	void orbitalRK4(DoubleBinaryOperator xa, DoubleBinaryOperator ya, double x, double y, double xv, double yv, double h)
	{
		double hs = h / 2;
		double xvk1 = xa.applyAsDouble(x, y);
		double yvk1 = ya.applyAsDouble(x, y);
		
		double xrk1 = xv;
		double yrk1 = yv;
		
		double xvk2 = xa.applyAsDouble(x + xrk1 * hs, y + yrk1 * hs);
		double yvk2 = ya.applyAsDouble(x + xrk1 * hs, y + yrk1 * hs);
		
		double xrk2 = xv + xvk1 * hs;
		double yrk2 = yv + yvk1 * hs;
		
		double xvk3 = xa.applyAsDouble(x + xrk2 * hs, y + yrk2 * hs);
		double yvk3 = ya.applyAsDouble(x + xrk2 * hs, y + yrk2 * hs);
		
		double xrk3 = xv + xvk2 * hs;
		double yrk3 = yv + yvk2 * hs;
		
		double xvk4 = xa.applyAsDouble(x + xrk3 * h, y + yrk3 * h);
		double yvk4 = ya.applyAsDouble(x + xrk3 * h, y + yrk3 * h);
		
		double xrk4 = xv + xvk3 * h;
		double yrk4 = yv + xvk3 * h;
		
		xv = h * (xvk1 + 2 * (xvk2 + xvk3) + xvk4) / 6;
		yv = h * (yvk1 + 2 * (yvk2 + yvk3) + yvk4) / 6;
		
		x = h * (xrk1 + 2 * (xrk2 + xrk3) + xrk4) / 6;
		y = h * (yrk1 + 2 * (yrk2 + yrk3) + yrk4) / 6;
		
		
		
	}
	
	
}
