import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * This class implements a link-based queue.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 * @param <T>	Any generic type
 */
public class LinkedQueue<T> implements Queue<T>
{
	private LinkedList<T> list;
	
	/**
	 * This constructor creates an empty queue.
	 */
	public LinkedQueue()
	{
		list = new LinkedList<T>();
	}
	
	/**
	 * This method adds a new element into the queue, even if it is null.
	 */
	public boolean add(T element)
	{
		if (element == null)
		{
			throw new NullPointerException();
		}

		return offer(element);
	}
	
	/**
	 * This method returns the first element in the queue.
	 * 
	 * @return	Returns the first element in the queue
	 * @throws  NoSuchElementException
	 */
	public T element()
	{
		if (list.isEmpty())
		{
			throw new NoSuchElementException();
		}
		
		return list.getFirst();
	}
	
	/**
	 * This method adds a new element into the queue.
	 */
	public boolean offer(T element)
	{
		list.addLast(element);
		
		return true;
	}
	
	/**
	 * This method returns the first element in the queue.
	 * 
	 * @return	Returns the first element in the queue and null if the queue is
	 * 			empty
	 */
	public T peek()
	{
		if (list.isEmpty())
		{
			return null;
		}
		
		return list.getFirst();
	}
	
	/**
	 * This method removes the first element from the queue and returns it.
	 * 
	 * @return	Returns the element that was removed from the queue
	 * @throws	NoSuchElementException
	 */
	public T remove()
	{
		if (list.isEmpty())
		{
			throw new NoSuchElementException();
		}
		
		return poll();
	}
	
	/**
	 * This method returns the first element of the queue.
	 * 
	 * @return	Returns the first element in the queue
	 */
	public T poll()
	{
		return list.removeFirst();
	}

	/**
	 * This method is not implemented.
	 * 
	 * @throws	UnsupportedOperationException
	 */
	public boolean addAll(Collection<? extends T> arg0)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * This method empties the queue.
	 */
	public void clear()
	{
		list.clear();
	}

	/**
	 * This method determines whether or not the given element is in the queue.
	 * 
	 * @return	Returns true if the given element is in the queue and false if
	 * 			it is not
	 */
	public boolean contains(Object element)
	{
		return list.contains(element);
	}

	/**
	 * This method returns true if the queue contains all of the elements in the
	 * specified collection.
	 * 
	 * @return Returns true if the queue contains all of the elements in the
	 * 			specified collection.
	 */
	public boolean containsAll(Collection<?> collection)
	{
		Iterator<?> i = collection.iterator();
		boolean hasAll = true;
		
		while (i.hasNext())
		{
			if (!list.contains(i.next()))
			{
				hasAll = false;
			}
		}
		
		return hasAll;
	}

	/**
	 * This method determines whether or not the queue is empty.
	 * 
	 * @return	Returns true if the queue is empty and false if it is not
	 */
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * This method gets a new iterator for the current queue.
	 * 
	 * @return	Returns an iterator to the queue
	 */
	public Iterator<T> iterator()
	{
		return list.iterator();
	}

	/**
	 * This method removes an object from the queue.
	 * 
	 * @return	Returns true if the object was successfully removed, false if
	 * 			it was not
	 */
	public boolean remove(Object element)
	{
		return list.remove(element);
	}

	/**
	 * This method is not implemented.
	 * 
	 * @return	Not used
	 * @throws	UnsupportedOperationException
	 */
	public boolean removeAll(Collection<?> collection)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is not implemented.
	 * 
	 * @return	Not used
	 * @throws	UnsupportedOperationException
	 */
	public boolean retainAll(Collection<?> collection)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * This method gets the size of the queue.
	 * 
	 * @return	The number of elements in the queue
	 */
	public int size()
	{
		return list.size();
	}

	/**
	 * This method places all of the queue's elements in an array.
	 * 
	 * @return	Returns an array with the queue's elements
	 */
	public Object[] toArray()
	{
		return list.toArray();
	}

	/**
	 * This method places all the queue's elements into a generic array.
	 * 
	 * @return	A generic array with the queue's elements
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] returnType)
	{
		return list.toArray(returnType);
	}
}