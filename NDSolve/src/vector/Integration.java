package vector;

import java.util.function.DoubleBinaryOperator;

public class Integration
{
	double[] orbitalRK4(DoubleBinaryOperator xa, DoubleBinaryOperator ya, double x, double y, double xv, double yv, double h, double[] data)
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
		data[0] = x;
		data[1] = y;
		data[2] = xv;
		data[3] = yv;
		
		return data;
	}
	
	public static void main(String[] args)
	{
		for(int i = 0; i < 15; i++)
		{
			System.out.println(distance(1 << i, 0.1, -10, 1.0 / (1 << 16)));
		}
	}

	private static double distance(double v, double k, double G, double h)
	{
		DoubleBinaryOperator dxdt, dydt;
		dxdt = (x, y) -> -k * x * Math.hypot(x, y);
		dydt = (x, y) -> -k * y * Math.hypot(x, y) + G;
		double[] data = {v, v};
		double y = 0;
		double x = 0;
		while(y >= 0)
		{
			x += h * data[0];
			y += h * data[1];
			data = vectorRK4(dxdt, dydt, data[0], data[1], h, data);
		}
		return x;
	}
	
	static double[] vectorRK4(DoubleBinaryOperator dxdt, DoubleBinaryOperator dydt, double x, double y, double h, double[] mem)
	{
		double hs = h / 2;
		double xk1 = dxdt.applyAsDouble(x, y);
		double yk1 = dydt.applyAsDouble(x, y);
		
		double xk2 = dxdt.applyAsDouble(x + hs * xk1, y + hs * yk1);
		double yk2 = dydt.applyAsDouble(x + hs * xk1, y + hs * yk1);
		
		double xk3 = dxdt.applyAsDouble(x + hs * xk2, y + hs * yk2);
		double yk3 = dydt.applyAsDouble(x + hs * xk2, y + hs * yk2);
		
		double xk4 = dxdt.applyAsDouble(x + h * xk3, y + h * yk3);
		double yk4 = dydt.applyAsDouble(x + h * xk3, y + h * yk3);
		
		mem[0] = x + h * (xk1 + 2*(xk2 + xk3) + xk4) / 6;
		mem[1] = y + h * (yk1 + 2*(yk2 + yk3) + yk4) / 6;
		
		return mem;
		
		
	}
	
	
}
