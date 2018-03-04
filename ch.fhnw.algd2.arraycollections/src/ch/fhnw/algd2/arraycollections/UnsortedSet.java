package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Set;

public class UnsortedSet<E> extends AbstractArrayCollection<E> implements Set<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private Object[] data;
	private int index = 0;

	public UnsortedSet() {
		this(DEFAULT_CAPACITY);
	}

	public UnsortedSet(int capacity) {
		data = new Object[capacity];
	}

	@Override
	public boolean add(E e) {
		if (e == null) throw new IllegalArgumentException("No null allowed");
		if (contains(e)) return false;
		if (size() == data.length) increaseArray();

		data[index] = e;
		index++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) throw new IllegalArgumentException("No null allowed");
		int i = indexOf(o);

		if (i == -1) return false;

		while (i+1 < size()) {
			data[i] = data[i+1];
			data[i+1] = null;
			i++;
		}

		index--;
		return true;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) > -1;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size());
	}

	@Override
	public int size() {
		return index;
	}

	private int indexOf(Object o) {
		if (o == null) throw new IllegalArgumentException("No null allowed");
		if (size() == 0) return -1;
		int i = 0;

		while (i < index) {
			if (data[i].equals(o)) return i;
			i++;
		}

		return -1;
	}

	private void increaseArray() {
		Object[] copy = new Object[data.length + DEFAULT_CAPACITY];
		int i = 0;

		while (i < size()) {
			copy[i] = data[i];
			i++;
		}

		data = copy;
	}

	public static void main(String[] args) {
		UnsortedSet<Integer> bag = new UnsortedSet<Integer>();
		bag.add(2);
		bag.add(2);
		bag.add(1);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
