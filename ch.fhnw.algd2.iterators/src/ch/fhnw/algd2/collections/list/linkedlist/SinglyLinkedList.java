package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.*;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class SinglyLinkedList<E> extends MyAbstractList<E> {
	private int size = 0;
	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E e) {
		Node<E> newNode = new Node(e, null);

		if (first == null) {
			first = newNode;
			last = first;
		} else {
			last.next = newNode;
			last = newNode;
		}

		++size;
		increaseModCount();
		return true;
	}

	@Override
	public boolean contains(Object o) {
		Objects.requireNonNull(o);

		Node<E> node = first;

		while (node != null) {
			if (node.elem.equals(o)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		Objects.requireNonNull(o);

		Node<E> node = first;
		Node<E> prev = null;

		while(node != null && !node.elem.equals(o)) {
			prev = node;
			node = node.next;
		}

		if (node == null) return false;

		if (node == last) {
			last = prev;
		}

		if (prev == null) {
			first = node.next;
		} else {
			prev.next = node.next;
		}

		node.next = null;

		size--;
		increaseModCount();
		return true;
	}

	@Override
	public E get(int index) {
		Objects.requireNonNull(index);
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

		Node<E> node = first;

		for(int i = 0; i < index; i++) node = node.next;

		return node.elem;
	}

	@Override
	public void add(int index, E element) {
		Objects.requireNonNull(index);
		Objects.requireNonNull(element);
		if (index < 0 || index > size) throw new IndexOutOfBoundsException();

		if (index == 0) {
			addHead(element);
		} else if (index == size) {
			addTail(element);
		} else {
			addElementAt(index, element);
		}
	}

	private void addHead(E element) {
		Node<E> newNode = new Node(element, first);

		if (last == first) {
			last = newNode;
		}

		first = newNode;
		increaseModCount();
		++size;
	}

	private void addTail(E element) {
		Node<E> newNode = new Node(element, null);

		last.next = newNode;
		last = newNode;
		increaseModCount();
		++size;
	}

	private void addElementAt(int index, E element) {
		Node<E> node = first;
		int i = 1;

		while(i < index) {
			node = node.next;
			i++;
		}

		Node<E> newNode = new Node(element);
		newNode.next = node.next;
		node.next = newNode;

		increaseModCount();
		++size;
	}

	@Override
	public E remove(int index) {
		Objects.requireNonNull(index);
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

		if (index == 0) {
			return removeHead();
		} else if (index == size-1) {
			return removeTail();
		} else {
			return removeAt(index);
		}
	}

	private E removeHead() {
		Node<E> removedNode = first;

		first = first.next;
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
		Node<E> prev = first;

		while(prev.next != last) {
			prev = prev.next;
		}

		last = prev;
		prev.next = null;
		--size;

		if (size == 1) {
			first = last;
		}

		increaseModCount();
		return removedNode.elem;
	}

	private E removeAt(int index) {
		Node<E> node = first;
		Node<E> prev = null;
		int i = 0;

		while(i < index) {
			prev = node;
			node = node.next;
			i++;
		}

		prev.next = node.next;
		node.next = null;

		--size;
		increaseModCount();
		return node.elem;
	}

	private void increaseModCount() {
		modCount++;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public int size() {
		return size;
	}

	private static class Node<E> {
		private final E elem;
		private Node<E> next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(E elem, Node<E> next) {
			this.elem = elem;
			this.next = next;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<E> {
		private int prevCursor = -1;
		private int cursor = 0;
		private int expectedModCount = modCount;

		@Override
		public boolean hasNext() {
			return cursor < size();
		}

		@Override
		public E next() {
			checkModCount();

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			E elem = get(cursor);
			prevCursor = cursor;
			cursor++;

			return elem;
		}

		@Override
		public void remove() {
			checkModCount();

			try {
				SinglyLinkedList.this.remove(prevCursor);
				if (prevCursor < cursor) cursor--; // has to be decremented, because the list is reduced by one
				expectedModCount++;
				prevCursor = -1;
			} catch(Exception e) {
				throw new IllegalStateException();
			}
		}

		private void checkModCount() {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
		}
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
