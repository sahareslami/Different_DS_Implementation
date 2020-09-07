/* Copyright (C) Kamaledin Ghiasi-Shirazi, Ferdowsi Univerity of Mashhad
 * 24 Aban 1396(Hijri shamsi)
 * 15 November 2017
 * Authors: Pooya Hosseinzadeh, Fatemeh Tabadkani, Kamaledin Ghiasi-Shirazi
*/

package ac.um.ds;
import java.util.Iterator;


public class LinkedLinearList<T> {
    private class Node<T> {
        public T data;
        public Node prev;
        public Node next;
    }

    public class LinkedListForwardIterator<T> implements Iterator<T> {
        private Node<T> currNode;

        //constructors:
        public LinkedListForwardIterator() {
            this.currNode = null;
        }

        public LinkedListForwardIterator(Node<T> node) {
            this.currNode = node;
        }
        public T retrieve() {//it's get equivalent
        	return this.currNode.data;
        }

        //overloading operators:
        @Override
        public T next() { // preincrement
            currNode = currNode.next;
            return currNode.data;
        }

        @Override
        public boolean hasNext() {
            return currNode.next != headerNode;
        }
    }

    public class LinkedListBackwardIterator<T> implements Iterator<T> {
        Node<T> currNode;
        
        public LinkedListBackwardIterator() {
            this.currNode = null;
        }

        public LinkedListBackwardIterator(Node<T> node) {
            this.currNode = node;
        }
        
        public T retrieve() {//it's get equivalent
        	return this.currNode.data;
        }

        //overloading operators:
        @Override
        public T next() { // preincrement
            currNode = currNode.prev;
            return currNode.data;
        }

        @Override
        public boolean hasNext() {
            return currNode.prev != headerNode;
        }
    }

    public LinkedLinearList() {
        mSize = 0;
        headerNode = new Node<T>();
        headerNode.next = headerNode;
        headerNode.prev = headerNode;
    }

    public Iterator<T> ForwardIterator() {
        return new LinkedListForwardIterator(headerNode);
    }

    public Iterator<T> BackwardIterator() {
        return new LinkedListBackwardIterator(headerNode);
    }

    // The returned iterator would point to the inserted element.
    public void insert(int position, T data) throws IndexOutOfBoundsException {
        if(position > mSize)
            throw new IndexOutOfBoundsException();
        else{
            Node<T> prevNode  = headerNode;
            for(int i = 0 ; i <= (position - 1) ; i++){
                prevNode = prevNode.next;
            }

            Node<T> newNode = new Node<T>();
            Node<T> nextNode = prevNode.next;

            newNode.data = data;
            newNode.next = nextNode;
            newNode.prev = prevNode;

            prevNode.next = newNode;
            nextNode.prev =  newNode;

            mSize++;

        }
    }

    // The returned iterator would point to the element after the removed one.
    public void remove(int position) throws IndexOutOfBoundsException {
        if(position > mSize)
            throw new IndexOutOfBoundsException();
        else{
            Node<T> currNode = headerNode;
            for(int i = 0 ; i <= position ; i++){
                currNode = currNode.next;
            }
            Node<T> nextNode = currNode.next;
            Node<T> prvNode = currNode.prev;

            nextNode.prev = prvNode;
            prvNode.next = nextNode;

            mSize--;
        }


    }

    public int size() {
        return mSize;
    }

    @Override
    public String toString() {
        String str = "";
        Node currNode = headerNode;
        for (int i = 0; i < mSize; i++) {
            currNode = currNode.next;
            str += (" " + currNode.data);
        }
        return str;
    }

    private Node<T> headerNode;
    private int mSize;
}