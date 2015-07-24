package main;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoublePredicate;
import static java.lang.Math.*;
public class Integration
{
	
	public static void main(String[] args)
	{
		DoubleBinaryOperator f = (x, y) -> sqrt(1 -x*x);
		double startX = 0;
		double startY = 0;
		Cursor c = adaptiveRKC45Path(f, startX, startY, 1.0 / (1 << 20), 1.0 / (1L << 30));
	
		PointIterator p1 = RK4Path(f, startX, startY, 1.0 / (1 << 25));
		double end = 1;
		long a = System.nanoTime();
		c.moveX(end - startX);
		System.out.println("Adaptive:" + (System.nanoTime() - a) / (1_000_000_000.0));
		a = System.nanoTime();
		while(p1.getX() < end) p1.advance();
		System.out.println("Fixed:" + (System.nanoTime() - a) / (1_000_000_000.0));
		double expected = PI / 4;
		//System.out.println(c.getY());
		System.out.println(c.getY() / expected - 1);
		System.out.println(p1.getY() / expected - 1);
	}
	
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
	
	
	private static final double tolerance = 0.8;
	public static PointIterator adaptiveRK4Path(DoubleBinaryOperator f, double sx, double sy, double step, double err)
	{
		
		return new PointIterator()
		{
			double x = sx, y = sy;
			
			double h = step * 2; //Multiply by 2 as we internally use two smaller half steps
			double hs = h / 2;
			@Override
			public boolean advance()
			{
				//TODO add kahan summation to reduce roundoff error
				
				//One big step
				while(true)
				{
					//System.out.println(y);
					//System.out.println(h);
					double dy = deltaRK4(f, x, y, h);
					double yBig = dy;
					
					double dhy = deltaRK4(f, x, y, hs);
					double ySmall =  dhy + deltaRK4(f, x + hs, y + dhy, hs);
					
					double delta = abs(ySmall - yBig) / 15;
					
					//System.out.println(delta);
					if(delta < err / 16 * tolerance)
					{
						
						y += ySmall;
						x += h;
						if(h < err)
						{
							hs = h;
							h *= 2;
						}
						
						break;
					}
					else if(delta > err * tolerance)
					{
						
						h = hs;
						hs /= 2;
						continue;
					}
					else
					{
						y += ySmall;
						x += h;
						break;
					}
				}
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
	public interface Cursor
	{
		double getX();
		
		double getY();
		
		void setPosition(double x, double y);
		
		void moveX(double xd);
		
		void moveY(double yd);
	}
	
	public static Cursor adaptiveRKC45Path(DoubleBinaryOperator f, double sx, double sy, double step, double err)
	{
		return new Integration.Cursor()
		{
			double x = sx, y = sy;
			double h = step;
		
			public double getX(){return x;}

			public double getY(){return y;}

			public void setPosition(double x, double y)
			{
				this.x = x;
				this.y = y;
			}
			private static final double a2 = 1.0 / 5,
										a3 = 3.0 / 10, 
										a4 = 3.0 / 5, 
										a5 = 1, 
										a6 = 7.0 / 8;
			private static final double b21 = 1.0 / 5,
										b31 = 3.0 / 40, 		b32 = 9.0 / 40 ,
										b41 = 3.0 / 10, 		b42 = -9.0 / 10, 	b43 = 6.0 / 5 ,
										b51 = -11.0/54,	 		b52 = 5.0 / 2  , 	b53 = -70.0/27, 		b54 = 35.0 / 27,
										b61 = 1631.0 / 55296, 	b62 = 175.0 / 512,	b63 = 575.0 / 13824, 	b64 = 44275.0 / 110592, b65 = 253.0 / 4096; //Numerical recepies
			private static final double c1 = 37.0 / 378,  	cs1 = 2825.0 / 27648, 
										c2 = 0, 			cs2 = 0, 
										c3 = 250.0 / 621,	cs3 = 18575.0 / 48384, 
										c4 = 125.0 / 594, 	cs4 = 13525.0 / 55296, 
										c5 = 0, 			cs5 = 277.0 / 14336, 
										c6 = 512.0 / 1771,	cs6 = 1.0 / 4; 
			private static final double cd1 = c1 - cs1,
										cd2 = c2 - cs2,
										cd3 = c3 - cs3,
										cd4 = c4 - cs4,
										cd5 = c5 - cs5,
										cd6 = c6 - cs6;
			private static final double S = 0.875;
			
			private static final double minRatio = 0.125 * 0.125 * 0.125 * 0.125;// 1/8^4				
			private static final double maxRatio = 8*8*8*8*8;//8^5							
									
										
			double xC = 0, yC = 0;							
			@Override
			public void moveX(double xd)
			{
				xd += x;
				while(x < xd)
				{
					while(true)
					{
						//System.out.println("step:"+ h);
						double k1 = h*f.applyAsDouble(x, y);
						double k2 = h*f.applyAsDouble(x + a2*h, y + b21*k1);
						double k3 = h*f.applyAsDouble(x + a3*h, y + b31*k1 + b32*k2);
						double k4 = h*f.applyAsDouble(x + a4*h, y + b41*k1 + b42*k2 + b43*k3);
						double k5 = h*f.applyAsDouble(x + a5*h, y + b51*k1 + b52*k2 + b53*k3 + b54*k4);
						double k6 = h*f.applyAsDouble(x + a6*h, y + b61*k1 + b62*k2 + b63*k3 + b64*k4 + b65*k5);
						
						double increment = c1*k1 + c2*k2 + c3*k3 + c4*k4 + c5*k5 + c6*k6;
						double error = abs(cd1*k1 + cd2*k2 + cd3*k3 + cd4*k4 + cd5*k5 + cd6*k6);
						//System.out.println(error);
						double desiredError = abs(increment) * err / xd; // What if predicted slope is 0, yet function isn't actually totally flat? Perhaps floor of 10^-9?
						//System.out.println("Desired:" + desiredError);
						double ratio = desiredError / error;
						if(error > desiredError)
						{
							if(ratio < minRatio) 	h *= S * 0.125;
							else 					h *= S * fastFourthRoot(ratio);
							if(xC + h == xC) throw new RuntimeException("Stepsize Underflow");
							continue;
						}
						//Kahan sum both x and y
						double dx = h - xC;
						double xt = x + dx;
						xC = (xt - x) - dx;
						x = xt;
						
						double dy = increment - yC;
						double yt = y + dy;
						yC = (yt - y) - dy;
						y = yt;
						h *= S * ((ratio >= maxRatio) ? 8 : fastFifthRoot(ratio));
						h = min(h, xd - x);
						break;
					}
					
					
				}
			}
			
			

			@Override
			public void moveY(double yd)
			{
				
			}
		};	
	}
	
	private static double fastFourthRoot(double x)
	{
		return (0.00815176 + x *(0.57294 + x *(4.94898 + x *(8.37038 + 2.16289 *x))))/(0.0310471 + x *(1.15524 + x *(6.56725 + x *(7.31 + x))));
	}
	
	private static double fastFifthRoot(double x)
	{
		return (0.0133812 + x *(0.0413244 + x *(0.0162057 + (0.00124834 + 0.0000144931* x) *x)))/(0.02112 + x *(0.0393412 + x *(0.011045 + (0.000609098 + 4.26539E-6 *x)* x)));
	}
	
}
