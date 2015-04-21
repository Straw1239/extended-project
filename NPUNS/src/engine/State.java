package engine;

import objects.ReadableBody;
import utils.Utils;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * Class representing an internal engine view.
 * Immutable.
 * Passed from the engine to the renderer for displaying to the screen
 * also passed to all engine objects in update.
 * @author Rajan Troll
 *
 */
public class State 
{
	public final ImmutableCollection<? extends ReadableBody> objects;
	
	
	public final long time;
	
	
	public State(Iterable<? extends ReadableBody> objects, long time)
	{
		this.objects = ImmutableList.copyOf(Utils.stream(objects).map(e -> (ReadableBody) e.clone()).iterator());
		this.time = time;
	}
}
