package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Iterator;

public interface PositionalList<E> extends Iterable<Position<E>> {
	
	public int size();
	
	public boolean isEmpty();
	
	public Position<E> first();
	
	public Position<E> last();
	
	public Position<E> before(Position<E> p);
	
	public Position<E> after(Position<E> p);
	
	public Position<E> addFirst(E e);
	
	public Position<E> addLast(E e);
	
	public Position<E> addBefore(Position<E> p, E e);

	public Position<E> addAfter(Position<E> p, E e);
	
	public E set(Position<E> p, E e);

	public E remove(Position<E> p);

	Iterable<Position<E>> positions();

	Iterator<Position<E>> iterator();


}
