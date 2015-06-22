package main;

import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class Utils
{

	public static double hypotSquared(double a, double b)
	{
		return a * a + b * b;
	}
	
	public static final ListeningScheduledExecutorService compute = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));
	public static final ListeningExecutorService exec = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
}
