package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method

        this.size = 0;
        this.head = new LLNode<E>(null);
        this.tail = new LLNode<E>(null);

        this.head.prev = null;
        this.head.next = this.tail;

        this.tail.prev = this.head;
        this.tail.next = null;

	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
        if (element == null){
            throw new NullPointerException("THIS IS A NULL");
        }

        LLNode<E> new_llnode = new LLNode<E>(element);
        LLNode<E> old_llnode = tail.prev;

        old_llnode.next = new_llnode;

        new_llnode.prev = old_llnode;
        new_llnode.next = tail;

        tail.prev = new_llnode;
        size +=1;

		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.

        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        LLNode<E> current_llnode = head;
        int i = 0;

        while(i <= index){
            current_llnode = current_llnode.next;
            i++;
        }

        E value = current_llnode.data;
        return value;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
        if (index > size || index < 0 ){
            throw new IndexOutOfBoundsException(" index out of bound");
        }
        if (element == null){
            throw new NullPointerException("the element is null");
        }

        LLNode<E> need_add_llnode  = new LLNode<E>(element);
        LLNode<E> add_after_llnode = head;
        int i = 0;

        while(i<=index){
            add_after_llnode = add_after_llnode.next;
            i++;
        }

        add_after_llnode.prev.next = need_add_llnode;

        need_add_llnode.prev = add_after_llnode.prev;
        need_add_llnode.next = add_after_llnode;

        add_after_llnode.prev = need_add_llnode;

        size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method

        if (index > size || index < 0 ){
            throw new IndexOutOfBoundsException("index out of bound");
        }

        LLNode<E> need_remove = head;
        int i = 0;

        while(i<=index){
            need_remove = need_remove.next;
            i++;
        }

        need_remove.prev.next = need_remove.next;
        need_remove.next.prev = need_remove.prev;

        size--;

        E value = need_remove.data;
		return value;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method

        if (index > size || index < 0 ){
            throw new IndexOutOfBoundsException(" index out of bound");
        }
        if (element == null){
            throw new NullPointerException("the element is null");
        }

        LLNode<E> remove_llnode = head;
        int i = 0;

        while(i<=index){
            remove_llnode = remove_llnode.next;
            i++;
        }

        E value = remove_llnode.data;
        remove_llnode.data = element;
        return value;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
