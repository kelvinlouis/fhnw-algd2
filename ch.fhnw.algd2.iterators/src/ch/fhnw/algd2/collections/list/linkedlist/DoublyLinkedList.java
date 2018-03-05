package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class DoublyLinkedList<E> extends MyAbstractList<E> {
	// variable int modCount already declared by AbstractList<E>
	private int size = 0;
	private Node<E> first, last;

	@Override
	public boolean add(E e) {
		Node<E> newNode = new Node(last, e, null);

		if (first == null) {
			first = newNode;
			last = first;
		} else {
			last = newNode;
		}

		++size;
		increaseModCount();
		return true;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		Objects.requireNonNull(o);

		// Check from the start and end (faster search)
		Node<E> startNode = first;
		Node<E> endNode = last;

		if (startNode == endNode && startNode != null) {
			if (startNode.elem.equals(o)) {
				removeHead();
				return true;
			}
		}

		while (startNode != null && endNode != startNode) {
			if (startNode.elem.equals(o)) {
				return removeNode(startNode);
			}
			if (endNode.elem.equals(o)) {
				return removeNode(startNode);
			}

			startNode = startNode.next;
			endNode = endNode.prev;
		}
		return false;
	}

	private boolean removeNode(Node<E> node) {
		Node<E> prev = node.prev;
		Node<E> next = node.next;

		prev.next = next;
		next.prev = prev;

		node.next = null;
		node.prev = null;

		--size;
		increaseModCount();

		return true;
	}

	@Override
	public E remove(int index) {
		Objects.requireNonNull(index);
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

		int half = size / 2;

		if (index == 0) {
			return removeHead();
		} else if (index == size-1) {
			return removeTail();
		} else {
			if (index <= half) {
				return removeAtUsingNext(index);
			} else {
				return removeAtUsingPrev(index);
			}
		}
	}

	private E removeHead() {
		Node<E> removedNode = first;

		first = first.next;

		if (first != null) {
			first.prev = null;
		}

		--size;

		if (size == 0 || size == 1) {
			last = first;
		}

		increaseModCount();
		removedNode.next = null;
		return removedNode.elem;
	}

	private E removeTail() {
		Node<E> removedNode = last;

		last = removedNode.prev;
		last.next = null;
		--size;

		if (size == 1) {
			first = last;
		}

		increaseModCount();
		return removedNode.elem;
	}

	private E removeAtUsingNext(int index) {
		Node<E> node = first;
		int i = 0;

		while(i < index) {
			node = node.next;
			i++;
		}

		removeNode(node);
		return node.elem;
	}

	private E removeAtUsingPrev(int index) {
		Node<E> node = first;
		int i = size - 1;

		while(i > index) {
			node = node.prev;
			i--;
		}

		removeNode(node);
		return node.elem;
	}

	@Override
	public boolean contains(Object o) {
		Objects.requireNonNull(o);

		// Check from the start and end (faster search)
		Node<E> startNode = first;
		Node<E> endNode = last;

		if (startNode == endNode && startNode != null) {
			// First and last are same (just one element)
			return startNode.elem.equals(o);
		}

		while (startNode != null && endNode != startNode) {
			if (startNode.elem.equals(o)) {
				return true;
			}
			if (endNode.elem.equals(o)) {
				return true;
			}

			startNode = startNode.next;
			endNode = endNode.prev;
		}
		return false;
	}

	@Override
	public E get(int index) {
		Objects.requireNonNull(index);
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

		int half = size / 2;

		if (index <= half) {
			return getByNext(index);
		} else {
			return getByPrev(index);
		}
	}

	private E getByNext(int index) {
		Node<E> node = first;
		for(int i = 0; i < index; i++) node = node.next;
		return node.elem;
	}

	private E getByPrev(int index) {
		Node<E> node = last;
		for(int i = size - 1; i > index; i--) node = node.prev;
		return node.elem;
	}

	private void increaseModCount() {
		modCount++;
	}

	@Override
	public Object[] toArray() {
		return arrayForDoublyLinkedList();
		// return arrayForCyclicDoublyLinkedList();
	}

	private Object[] arrayForDoublyLinkedList() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	private Object[] arrayForCyclicDoublyLinkedList() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first.next;
		while (current != first) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException("Implement later");
	}

	private static class Node<E> {
		private E elem;
		private Node<E> prev, next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(Node<E> prev, E elem, Node<E> next) {
			this.prev = prev;
			this.elem = elem;
			this.next = next;
		}
	}

	private class MyListIterator implements Iterator<E> {
		@Override
		public boolean hasNext() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E next() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
