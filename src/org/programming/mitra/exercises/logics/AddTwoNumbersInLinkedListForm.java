package org.programming.mitra.exercises.logics;

public class AddTwoNumbersInLinkedListForm {

    public static void main(String[] args) {
        // Test
        ListNode l1 = new ListNode(2).next(new ListNode(4).next(new ListNode(6)));
        ListNode l2 = new ListNode(5).next(new ListNode(6).next(new ListNode(4)));

        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers(l1, l2));

        // Test
        l1 = new ListNode(5);
        l2 = new ListNode(5);

        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers(l1, l2));

        // Test
        l1 = new ListNode(1).next(new ListNode(8));
        l2 = new ListNode(0);

        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers(l1, l2));

        // Test
        l1 = new ListNode(0);
        l2 = new ListNode(1).next(new ListNode(8));

        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers(l1, l2));
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode head = null;
        ListNode previous = null;
        while (l1 != null || l2 != null) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            carry = sum / 10;

            ListNode node = new ListNode(sum % 10);
            if (head == null) {
                head = node;
            } else {
                previous.next = node;
            }

            previous = node;
        }

        if (carry != 0) {
            previous.next = new ListNode(carry);
        }

        return head;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public ListNode next(ListNode next) {
            this.next = next;
            return this;
        }

        @Override
        public String toString() {
            return val + "->" + next;
        }
    }
}


