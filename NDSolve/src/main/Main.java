package main;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.function.DoubleBinaryOperator;

import com.google.common.util.concurrent.ListenableFuture;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static java.lang.Math.*;
import static main.Utils.compute;
import static main.Utils.exec;

public class Main extends Application
{

	final double width = 1920, height = 1080;

	
	Canvas canvas = new Canvas(width, height);
	GraphicsContext g = canvas.getGraphicsContext2D();
	{
		g.save();
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	
	double zoomX = 25, zoomY = 25;
	double[][] ycoords, xcoords;
	@Override
	public void start(Stage stage) throws Exception
	{
		Group gr = new Group(canvas);
		Scene scene = new Scene(gr);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> 
		{
			if(e.getCode() == KeyCode.ESCAPE) System.exit(0);
		});
		stage.addEventFilter(ScrollEvent.SCROLL, e -> 
		{
			double scale = exp(e.getDeltaY() / 1000);
			zoomX *= scale;
			zoomY *= scale;
			clear();
			drawAxis();
			g.setLineWidth(1);
			zoom();
			drawCurves();
		});
		clear();
		drawAxis();
		g.setLineWidth(1);
		zoom();
		g.setStroke(Color.GREEN);


		DoubleBinaryOperator f = (x, y) ->  x*x*x + 6*x*y -24*y ;
		int steps = 1 << 8;
		double dist = 80;

		double stepSize = 1.0 / steps;
		double minDist = 0.1;
		
		stage.show();
		
		double min = -20, max = 20, ysep = .5;
		int curves = (int) ((Math.round((max - min) / ysep)) + 1) * 2;
		
		xcoords = new double[curves][];
		ycoords = new double[curves][];
		ListenableFuture<?> fut = compute.submit(() ->
		{
			int index = 0;
			for(double i = min; i <= max; i += ysep)
			{
				double[][] right = genPoints(f, 0, i, stepSize, minDist, (x, y) -> x <= dist + 1);
				xcoords[index] = right[0];
				ycoords[index] = right[1];
				index++;
				double[][] left = genPoints(f, 0, i, -stepSize, minDist, (x, y) -> x >= -(dist + 1));
		
				xcoords[index] = left[0];
				ycoords[index] = left[1];
				index++;
			}
		});
		fut.addListener(this::drawCurves, Platform::runLater);
		
		
	}
	
	private void drawCurves()
	{
		g.setStroke(Color.GREEN);
		for(int i = 0; i < xcoords.length; i++)
		{
			g.strokePolyline(xcoords[i], ycoords[i], xcoords[i].length);
		}
	}
	
	
	
	private void zoom()
	{
		g.scale(zoomX, zoomY);
	}



	private void drawAxis()
	{
		g.setStroke(Color.BLACK);
		g.strokeLine(0, -10000, 0, 10000);
		g.strokeLine(-10000, 0, 10000, 0);
		
	}



	double[][] genPoints(DoubleBinaryOperator dydx, double x, double y, double step, double minDist, DoubleBiPredicate go)
	{
		double[] xcoords = new double[128];
		double[] ycoords = new double[128];
		int index = 0;
		xcoords[index] = x;
		ycoords[index] = y;
		index++;
		double mind2 = minDist * minDist;
		PointIterator results = Integration.RK4Path(dydx, x, y, step);
		while(go.test(results.getX(), results.getY()))
		{
			results.advance();
			double d2 = Utils.hypotSquared(results.getX() - x, results.getY() - y);
			if(d2 >= mind2)
			{
				x = results.getX();
				y = results.getY();
				xcoords[index] = x;
				ycoords[index] = y;
				index++;
				if(index == xcoords.length)
				{
					xcoords = Arrays.copyOf(xcoords, xcoords.length * 2);
					ycoords = Arrays.copyOf(ycoords, ycoords.length * 2);
				}
			}
		}
		xcoords = Arrays.copyOf(xcoords, index );
		ycoords = Arrays.copyOf(ycoords, index );
		return new double[][]{xcoords, ycoords};
	}


	private void clear()
	{
		g.restore();
		g.clearRect(0, 0, width, height);
		g.save();
		g.translate(width / 2, height / 2);
		g.scale(1, -1);
	}
	
	
	
	
	
	

}
