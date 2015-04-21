package fxcore;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.ReadableBody;
import engine.State;

public class Renderer 
{
	public final Canvas canvas;
	private final GraphicsContext g;
	public final double width, height;
	
	public Renderer(int width, int height)
	{
		canvas = new Canvas(width, height);
		g = canvas.getGraphicsContext2D();
		this.width  = width;
		this.height = height;
	}
	
	public void render(State d)
	{
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.save();
		g.translate(width / 2, height / 2);
		drawObjects(d);
		g.restore();
		displayHUD(d);
	}
	
	private void drawObjects(State d)
	{
		for(ReadableBody obj : d.objects)
		{
			obj.draw(g);
		}
	}

	private void displayHUD(State d)
	{
		
		
	}
}
