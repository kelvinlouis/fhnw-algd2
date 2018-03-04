package ch.fhnw.algd2.collections.list.stack;

import ch.fhnw.algd2.collections.list.linkedlist.MyLinkedList;

import java.util.EmptyStackException;
import java.util.List;

public class MyStack<E> implements IStack<E> {
	List<E> list = new MyLinkedList();

	@Override
	public E pop() {
		if (list.isEmpty()) throw new EmptyStackException();

		return list.remove(list.size() - 1);
	}

	@Override
	public E push(E elem) {
		list.add(list.size(), elem);
		return elem;
	}

	@Override
	public E peek() {
		if (list.isEmpty()) throw new EmptyStackException();
		return list.get(list.size() - 1);
	}

	@Override
	public boolean empty() {
		return list.isEmpty();
	}

	public static void main(String[] args) {
		MyStack<Integer> stack = new MyStack<Integer>();
		System.out.println("Pushing numbers to stack (0 to 9)");
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		System.out.println("Pop numbers from stack");
		while (!stack.empty()) {
			System.out.println(stack.pop());
		}
	}
}
