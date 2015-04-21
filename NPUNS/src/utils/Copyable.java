package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public interface Copyable<T> extends Cloneable
{
	@SuppressWarnings("unchecked")
	public default T copy()
	{
		try
		{
			Class<Object> object = Object.class;
			Method m = object.getDeclaredMethod("clone");
			m.setAccessible(true);
			return (T)m.invoke(this);
		}
		catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException | NoSuchMethodException| SecurityException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public default T deepCopy()
	{
		Class<?> searcher = getClass();
		ArrayList<Field> fields = new ArrayList<>();
		while(!searcher.equals(Object.class))
		{
			fields.addAll(Arrays.asList(searcher.getDeclaredFields()));
			searcher = searcher.getSuperclass();
		}
		T copy = copy();
		for(Field f : fields)
		{
			boolean access = f.isAccessible();
			try
			{
				
				f.setAccessible(true);
				try
				{
					f.set(copy, ((Copyable<?>)f.get(copy)).deepCopy());
				}
				catch(ClassCastException e)
				{
	
				}	
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				f.setAccessible(access);
			}
		}
		return copy;
		
	}
	
}
