package utils;

import java.util.Random;

/**
 * Random number generator replacing built in algorithm.
 * Faster, longer period, more random.
 * 
 *
 */

public class XRandom extends Random
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private long seed;

	 public XRandom(long seed) 
	 {
		 this.seed = seed;
	 }

	 @Override
	 protected int next(int nbits) 
	 {
		 long x = seed;
		 x ^= (x << 21);
		 x ^= (x >>> 35);
		 x ^= (x << 4);
		 seed = x;
		 x &= ((1L << nbits) - 1);
		 return (int) x;
	 }
	
	
}
