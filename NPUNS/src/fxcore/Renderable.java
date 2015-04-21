package fxcore;

import java.util.Collection;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;

public interface Renderable extends Drawable
{

	Collection<Effect> specialEffects();
	
	void renderHUD(GraphicsContext g);

}
