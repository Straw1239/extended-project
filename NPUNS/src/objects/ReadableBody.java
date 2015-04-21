package objects;

import utils.Cloner;
import fxcore.Renderable;

public interface ReadableBody extends Cloner, Locatable, Renderable
{
	public boolean isDestroyed();
	
	public boolean supportsOperation(int code);
}
