package edu.uprm.cse.datastructures.cardealer.util;

public class DLLPositionalList<E> implements PositionalList<E> {
	
	private static class Node<E> implements Position<E> {
		
		public Node(E element, Node<E> next, Node<E> prev) {
			super();
			this.element = element;
			this.next = next;
			this.prev = prev;
		}

		public Node() {
			super();
			this.element = null;
			this.next = null;
			this.prev = null;
		}

		private E element;
		private Node<E> next;
		private Node<E> prev;
		

		
		@Override
		public E getElement() {
			
			if (this.next == null) {
				return null;
			}
			return  this.element;
		}


		public Node<E> getNext() {
			return next;
		}


		public void setNext(Node<E> next) {
			this.next = next;
		}


		public Node<E> getPrev() {
			return prev;
		}


		public void setPrev(Node<E> prev) {
			this.prev = prev;
		}


		public void setElement(E element) {
			this.element = element;
		}
		
	}

	private Node<E> header;
	private Node<E> tail;
	private int currentSize;
	
	public DLLPositionalList() {
		this.currentSize = 0;
		this.header = new Node<E>();
		this.tail = new Node<E>();
		
		this.header.setNext(this.tail);
		this.tail.setPrev(this.header);
	}
	
	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public Position<E> first() {
		if (this.isEmpty()) {
			return null;
		}
		else {
			return this.header.getNext();
		}
	}

	@Override
	public Position<E> last() {
		if (this.isEmpty()) {
			return null;
		}
		else {
			return this.tail.getPrev();
		}
	}

	@Override
	public Position<E> before(Position<E> p) {
		if (!this.isValid(p)) {
			return null;	
		}
		else {
			Node<E> N = (Node<E>) p;
			return position(N.getPrev());
		}	
	}

	@Override
	public Position<E> after(Position<E> p) {
		if (!this.isValid(p)) {
			return null;	
		}
		else {
			Node<E> N = (Node<E>) p;
			return position(N.getNext());
		}
	}

	@Override
	public Position<E> addFirst(E e) {
		return this.addBetween(e, this.header, this.header.getNext());
	}

	@Override
	public Position<E> addLast(E e) {
		return this.addBetween(e, this.tail.getPrev(), this.tail);

	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) {
		if (!this.isValid(p)){
			return null;
		}
		else {
			Node<E> N = (Node<E>) p;
			return this.addBetween(e, N.getPrev(), N);
		}
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) {
		if (!this.isValid(p)){
			return null;
		}
		else {
			Node<E> N = (Node<E>) p;
			return this.addBetween(e, N, N.getNext());
		}
	}

	@Override
	public E set(Position<E> p, E e) {
		if (!this.isValid(p)) {
			return null;
		}
		else {
			E result  = p.getElement();
			((Node<E>) p).setElement(e); // Foooo!!!!!!;
			return result;
		}
	}

	@Override
	public E remove(Position<E> p) {
		if (!this.isValid(p)) {
			return null;
		}
		else {
			Node<E> temp = (Node<E>) p;
			E result = p.getElement();
			temp.getNext().setPrev(temp.getPrev());
			temp.getPrev().setNext(temp.getNext());
			temp.setNext(null);
			temp.setPrev(null);
			temp.setElement(null);
			this.currentSize--;
			return result;
		}
	}

	private boolean isValid(Position<E> p) {
		Node<E> t = (Node<E>) p;
		return (t.getNext()!= null && t.getPrev()!= null);
	}
	
	private Position<E> position(Node<E> N){
		if ((N == this.header) || (N == this.tail)) {
			return null; // do not return the dummy headers
		}
		else {
			return N;
		}
	}
	private Position<E> addBetween(E e, Node<E> prev, Node<E> next){
		Node<E> newNode = new Node<E>();
		newNode.setElement(e);
		newNode.setNext(next);
		newNode.setPrev(prev);
		prev.setNext(newNode);
		next.setPrev(newNode);
		this.currentSize++;
		return newNode;
	}
}
