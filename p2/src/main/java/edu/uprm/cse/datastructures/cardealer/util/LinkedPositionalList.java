package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedPositionalList<E> implements PositionalList<E> {

	private static class DNode<E> implements Position<E> { 
		private E element; 
		private DNode<E> prev, next;
		public E getElement() {
			return element;
		}
		public DNode(E element, DNode<E> prev, DNode<E> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}
		public DNode(E element) {
			this(element, null, null);
		}
		public DNode() {
			this(null, null, null);
		}
		public void setElement(E element) {
			this.element = element;
		}
		public DNode<E> getPrev() {
			return prev;
		}
		public void setPrev(DNode<E> prev) {
			this.prev = prev;
		}
		public DNode<E> getNext() {
			return next;
		}
		public void setNext(DNode<E> next) {
			this.next = next;
		} 
		public void clean() { 
			element = null; 
			prev = next = null; 
		}
	}
	
	private DNode<E> header, trailer; 
	private int size; 
	private Iterator<Position<E>> posIterator; 
	
	
	public LinkedPositionalList() {
		header = new DNode<>(); 
		trailer = new DNode<>(); 
		header.setNext(trailer);
		trailer.setPrev(header); 
		size = 0; 
	}
	
	public boolean contains(Position<E> p) {
		if(this.isEmpty()) return false;
		Position<E> current = this.first();
		while(current != last()) {
			if(current == p) return true;
			current = this.after(current);
		}
		if(last() == p) return true;
		return false;
	}
	//Checks if position is valid
	private DNode<E> validate(Position<E> p) throws IllegalArgumentException { 
		try { 
			DNode<E> dp = (DNode<E>) p; 
			if (dp.getPrev() == null || dp.getNext() == null) 
				throw new IllegalArgumentException("Invalid internal node."); 
			if(!this.contains(p)) 		
				throw new IllegalArgumentException("Invalid internal node."); 

			return dp; 
		} catch (ClassCastException e) { 
			throw new IllegalArgumentException("Invalid position type."); 
		}
	}
	
	private Position<E> position(DNode<E> dn) { 
		if (dn == header || dn == trailer) 
			return null; 
		return dn; 
	}
	
	private DNode<E> addBetween(DNode<E> b, DNode<E> a, E e) { 
		DNode<E> n = new DNode<>(e, b, a); 
		b.setNext(n); 
		a.setPrev(n); 
		size++; 
		return n; 
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> first() {
		return position(header.getNext());
	}

	@Override
	public Position<E> last() {
		return position(trailer.getPrev());
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		return position(validate(p).getPrev());
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		return position(validate(p).getNext());
	}

	@Override
	public Position<E> addFirst(E e) {
		return addBetween(header, header.getNext(), e);
	}

	@Override
	public Position<E> addLast(E e) {
		return addBetween(trailer.getPrev(), trailer, e);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e)
			throws IllegalArgumentException {
		DNode<E> dp = validate(p); 
		return addBetween(dp.getPrev(), dp, e);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e)
			throws IllegalArgumentException {
		DNode<E> dp = validate(p); 
		return addBetween(dp, dp.getNext(), e);
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		DNode<E> dp = validate(p);
		E etr = dp.getElement(); 
		dp.setElement(e);
		return etr;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		DNode<E> dp = validate(p); 
		E etr = dp.getElement(); 
		DNode<E> b = dp.getPrev(); 
		DNode<E> a = dp.getNext(); 
		b.setNext(a);
		a.setPrev(b);
		dp.clean(); 
		size--; 
		return etr;
	}

	@Override
	public Iterable<Position<E>> positions() { 
		return new PositionIterable(); 
	}

	@Override
	public Iterator<Position<E>> iterator() {
		return new ElementIterator();
	}
	public Iterator<E> iteratorBackward() {
		return new BackwardElementIterator();
	}

	
	// Implementation of Iterator and Iterable...
	private class PositionIterator implements Iterator<Position<E>> {
		private DNode<E> cursor = header.getNext(), 
			    recent = null; 
		@Override
		public boolean hasNext() {
			return cursor != trailer;
		}

		@Override
		public Position<E> next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No more elements."); 
			recent = cursor; 
			cursor = cursor.getNext(); 
			return recent;
		} 
		
		public void remove() throws IllegalStateException { 
			if (recent == null) 
				throw new IllegalStateException("remove() not valid at this state of the iterator."); 
			DNode<E> b = recent.getPrev(); 
			DNode<E> a = recent.getNext(); 
			b.setNext(a);
			a.setPrev(b);
			recent.clean(); 
			recent = null; 
			size--;          // important because we are removing recent directly....
		}
		
	}
	// new iterator for iterator lab
	private class PositionBackwardIterator implements Iterator<Position<E>> {
		private DNode<E> cursor = trailer.getPrev(), 
			    recent = null; 
		@Override
		public boolean hasNext() {
			return cursor != header;
		}

		@Override
		public Position<E> next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No more elements."); 
			recent = cursor; 
			cursor = cursor.getPrev(); 
			return recent;
		} 
		
		public void remove() throws IllegalStateException { 
			if (recent == null) 
				throw new IllegalStateException("remove() not valid at this state of the iterator."); 
			DNode<E> b = recent.getPrev(); 
			DNode<E> a = recent.getNext(); 
			b.setNext(a);
			a.setPrev(b);
			recent.clean(); 
			recent = null; 
			size--;          // important because we are removing recent directly....
		}
		
	}
	
	private class ElementIterator implements Iterator<Position<E>> { 
		Iterator<Position<E>> posIterator = 
				new PositionIterator(); 
		@Override
		public boolean hasNext() {
			return posIterator.hasNext();
		}

		@Override
		public Position<E> next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No more iterators."); 
			return posIterator.next();
		} 
		
		public void remove() throws IllegalStateException { 
			posIterator.remove();
		}
	}
	//added iterator
	private class BackwardElementIterator implements Iterator<E> { 
		Iterator<Position<E>> posIterator = 
				new PositionBackwardIterator(); 
		@Override
		public boolean hasNext() {
			return posIterator.hasNext();
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No more elements."); 
			return posIterator.next().getElement();
		} 
		
		public void remove() throws IllegalStateException { 
			posIterator.remove();
		}
	}
	
	private class PositionIterable implements Iterable<Position<E>> {

		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		} 
		
	}
	
}
