package org.programming.mitra.exercises.practice.logics;

public class LinkedListRotation
{
    public static void main(String[] args)
    {
        Node<Integer> n1 = new Node<>(1);
        Node<Integer> n2 = new Node<>(2);
        Node<Integer> n3 = new Node<>(3);
        Node<Integer> n4 = new Node<>(4);
        Node<Integer> n5 = new Node<>(5);
        Node<Integer> n6 = new Node<>(6);
        Node<Integer> n7 = new Node<>(7);
        Node<Integer> n8 = new Node<>(8);

        Node<Integer> head = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;

        System.out.println(transform(head, 6));
    }

    private static <T> Node<T> transform(Node<T> start, int k)
    {
        Node<T> next = start;
        for (int i = 1; i < k && next != null; i++)
        {
            next = next.next;
        }

        if (next == null)
        {
            return start;
        }

        int count = 1;
        Node<T> beg;
        Node<T> end = start;
        while (count < k && end.next != null)
        {
            beg = start;
            start = end.next;
            end.next = start.next;
            start.next = beg;
            count++;
            System.out.println(start);
        }

        end.next = transform(end.next, k);

        return start;
    }
}

class Node<T>
{
    T value;
    Node<T> next;

    public Node(T value)
    {
        this.value = value;
    }

    public Node(T value, Node<T> next)
    {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString()
    {
        return value + ((next == null) ? "" : "->" + next);
    }
}
