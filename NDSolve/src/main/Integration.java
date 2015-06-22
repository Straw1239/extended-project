package main;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoublePredicate;

public class Integration
{
	public static double deltaRK4(DoubleBinaryOperator f, double x, double y, double step)
	{
		double halfStep = step / 2;
		double k1 = f.applyAsDouble(x, y);
		double k2 = f.applyAsDouble(x + halfStep, y + k1 * halfStep);
		double k3 = f.applyAsDouble(x + halfStep, y + k2 * halfStep);
		double k4 = f.applyAsDouble(x + step, y + k3 * step);
		return (step / 6) * (k1 + 2 * (k2 + k3) + k4);
		
	}
	
	public static double RK4(DoubleBinaryOperator f, double x, double y, double step)
	{
		return y + deltaRK4(f, x, y, step);
	}
	
	public static PointIterator RK4Path(DoubleBinaryOperator f, double sx, double sy, double step)
	{
		return new PointIterator()
		{
			double xC = 0, yC = 0;
			double x = sx, y = sy;
			double h = step;
			@Override
			public boolean advance()
			{
				double dy = deltaRK4(f, x, y, h) - yC;
				double yt = y + dy;
				yC = (yt - y) - dy;
				y = yt;
				double dx = h - xC;
				double xt = x + dx;
				xC = (xt - x) - dx;
				x = xt;
				return true;
			}

			@Override
			public double getY()
			{
				return y;
			}

			@Override
			public double getX()
			{
				return x;
			}
			
		};
	}
}
