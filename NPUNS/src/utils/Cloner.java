package utils;

import static utils.Utils.*;

/**
 * Interface for objects with public, non throwing clone.
 * Provides a convenience non throwing cloning method which automatically casts to the base class.
 * @author Rajan
 *
 */
public interface Cloner extends Cloneable
{
	public Object clone();
	
	/**
	 * Convenient way to clone: returns the correct type of object, automatically casting. Does not throw
	 * checked exceptions.
	 * Any thrown exceptions are wrapped in errors and propagated.
	 * @param Cloner to be cloned
	 * @return
	 */
	public static <T extends Cloner> T clone(T object)
	{
		try
		{
			return cast(object.clone());
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError(e);
		}
	}
}
