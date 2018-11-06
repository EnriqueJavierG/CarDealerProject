/**
 * @author Enrique J. Gonzalez Mendez
 * This class is a generic list that is circular, it is sorted and is doubly linked.
 * Has a nested class for Nodes
 */


package edu.uprm.cse.datastructures.cardealer.util;


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uprm.cse.datastructures.cardealer.util.*;

public class CircularSortedDoublyLinkedList<E> implements SortedList<E>{
	//Node nested class 
	private static class Node<E> {  
		private E element; // reference to the element stored at this node
		private Node<E> prev; // reference to the previous node in the list
		private Node<E> next; // reference to the subsequent node in the list

		/**
		 * 
		 * @param e element of the node
		 * @param p the previous node
		 * @param n the next node
		 */
		public Node(E e, Node<E> p, Node<E> n) {  
			element = e;
			prev = p;
			next = n;
		}  
		public E getElement( ) {
			return element; 
		}  
		public void setElement(E e){
			this.element = e;
		}
		public Node<E> getPrev( ) {
			return prev; 
		}  
		public Node<E> getNext( ) {
			return next; 
		}  
		public void setPrev(Node<E> p) { 
			prev = p; 
		}  
		public void setNext(Node<E> n) { 
			next = n; 
		}  
		public void clearNode(){
			element = null;
			next = prev = null;

		}
	}

	// Instance variables
	private Node<E> header;
	private int size;
	private Comparator<E> cmp;

	//Iterator nested class(For future use)
	class CSDLLIterator implements Iterator<E>{
		Node<E> current = null;
		@Override
		public boolean hasNext() {

			if(header != null && current == null) {
				return true;
			} 
			else if(current!= null) {
				return current.getNext() != null;
			} else
				return false;
		}

		@Override
		public E next() throws NoSuchElementException {
			if(current == null && header != null) {
				current = header;
			}
			else if(current!= null) {
				current = current.getNext();
				return current.getElement();
			}
			throw new NoSuchElementException("Element does not exist");
		}

	}
	//Class Contructor
	public CircularSortedDoublyLinkedList(Comparator<E> cmp) {
		this.cmp = cmp;
		header = new Node<E>(null, header, header);
		size = 0;
	}

	/**
	 * iterator for future use
	 */
	public Iterator<E> iterator() {
		return new CSDLLIterator();
	}
	/**
	 * adds the given object to the list
	 * @returns true
	 */
	
	@Override
	public boolean add(E e) {
		Node<E> current = header.getNext();
		if(this.isEmpty()) {
			Node<E> nta = new Node<>(e,header,header);
			header.setNext(nta);
			header.setPrev(nta);
		}
		else if(cmp.compare(e, header.getPrev().getElement()) >= 0){
			Node<E> nta = new Node<>(e,header.getPrev(),header);
			header.getPrev().setNext(nta);
			header.setPrev(nta);
		}
		else{
			while(current != header){
				if(cmp.compare(e, current.getElement())<= 0){
					Node<E> nta = new Node<>(e,current.getPrev(),current);
					current.getPrev().setNext(nta);
					current.setPrev(nta);
					break;
				}
				current = current.getNext();
			}
		}
		size++;
		return true;
	}
	
	//size getter
	@Override
	public int size() {
		return size;			

	}
	/**
	 * Removes the object the object given in the list  
	 * @return true if successfully removes the object
	 */
	@Override
	public boolean remove(E e) {
		if(!contains(e)){return false;}
		if(size == 0 || header.getNext() == header) {
			return false;
		}
		Node<E> current = header.getNext();
		while(!current.getElement().equals(e)) {
			current = current.getNext();
		}

		Node<E> prev = current.getPrev();
		Node<E> next = current.getNext();
		current.clearNode();
		next.setPrev(prev);
		prev.setNext(next);
		size--;
		return true;

	}

	/**
	 * Removes the object at given index
	 * @return true if object could be remove
	 */
	@Override
	public boolean remove(int index) throws IndexOutOfBoundsException {
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		if(isEmpty()) return false;

		int counter = 0;
		Node<E> current = header.getNext();
		if(size == 1){
			header.clearNode();
			header.setNext(header);
			header.setPrev(header);
			size--;
			return true;
		}
		while(counter != index) {
			current = current.getNext();
			counter++;
		}
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		current.clearNode();
		size--;
		return true;
	}
	/**
	 * Removes all the objects in the list that are equal to given object
	 * @return number of removed objects
	 */
	@Override
	public int removeAll(E e) {
		int counter = 0;
		Node<E> current = header.getNext();    
		while(current != header){
			if(current.getElement().equals(e)){
				current = current.getNext();
				remove(e);
				counter++;
			}
			else
				current = current.getNext();

		}
		return counter;
		//cool way to do the same
		//		while(this.contains(e)){
		//			this.remove(e);
		//			counter++;
		//		}
		//
		//		return counter;
	}

	/**
	 * @return first object in list
	 */
	@Override
	public E first() {
		return header.getNext().getElement();
	}
	/**
	 * @return last object of list
	 */
	@Override
	public E last() {
		return header.getPrev().getElement();
	}
	/**
	 * @return object at given index
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		Node<E> current = header.getNext();
		int counter = 0;
		while(counter != index) {
			current = current.getNext();
			counter++;
		}
		return current.getElement();
	}
	/**
	 * Empties the list
	 */
	@Override
	public void clear() {
		while(!isEmpty()){
			remove(last());
		}
	}

	/**
	 * @return true if the list contains the given object
	 */
	@Override
	public boolean contains(E e) {
		Node<E> current = header.getNext();

		while(current != header) {
			if(current.getElement().equals(e)){
				return true;
			}
			current = current.getNext();
		}
		return false;

	}
	/**
	 * @return true if the list is Empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0 || header.getNext() == header;
	}
	/**
	 * @returns the index of the first encounter of the given object  
	 */
	@Override
	public int firstIndex(E e) {

		Node<E> c = header.getNext();
		int counter = 0;
		while(c != header) {
			if(c.getElement().equals(e)) {
				return counter;
			}
			else {
				c = c.getNext();
				counter++;
			}
		}
		return -1; // object not found in the list 
	}

	/**
	 * @return the last index where the given object was 
	 */
	@Override
	public int lastIndex(E e) {
		Node<E> c = header.getPrev();
		int counter = this.size();
		while(c != header) {
			if(c.getElement().equals(e)) {
				return counter;
			}
			else {
				c = c.getPrev();
				counter--;
			}
		}
		return -1;// object is not in the list 
	}



}

