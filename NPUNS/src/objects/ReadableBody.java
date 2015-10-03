package objects;

import utils.Cloner;
import fields.Field;
import fxcore.Renderable;

public interface ReadableBody extends Cloner, Locatable, Renderable, Velocity
{
	static Field[] empty = new Field[0];

	public boolean isDestroyed();
	
	public boolean supportsOperation(int code);
	
	public default Field[] getFields()
	{
		return empty;
	}
}
