import java.util.*;

public class AllOne {
    private class Node {
        int count;
        Set<String> keys = new HashSet<>();
        Node prev, next;
        Node(int count) { this.count = count; }
    }

    private Map<String, Node> keyNodeMap = new HashMap<>();
    private Node head = new Node(Integer.MIN_VALUE);
    private Node tail = new Node(Integer.MAX_VALUE);

    public AllOne() {
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        Node curr = keyNodeMap.get(key);
        if (curr != null) {
            Node next = curr.next.count == curr.count + 1 ? curr.next : insertAfter(curr, new Node(curr.count + 1));
            next.keys.add(key);
            keyNodeMap.put(key, next);
            curr.keys.remove(key);
            if (curr.keys.isEmpty()) remove(curr);
        } else {
            Node next = head.next.count == 1 ? head.next : insertAfter(head, new Node(1));
            next.keys.add(key);
            keyNodeMap.put(key, next);
        }
    }

    public void dec(String key) {
        Node curr = keyNodeMap.get(key);
        if (curr == null) return;
        if (curr.count == 1) {
            keyNodeMap.remove(key);
        } else {
            Node prev = curr.prev.count == curr.count - 1 ? curr.prev : insertAfter(curr.prev, new Node(curr.count - 1));
            prev.keys.add(key);
            keyNodeMap.put(key, prev);
        }
        curr.keys.remove(key);
        if (curr.keys.isEmpty()) remove(curr);
    }

    public String getMaxKey() {
        return tail.prev != head ? tail.prev.keys.iterator().next() : "";
    }

    public String getMinKey() {
        return head.next != tail ? head.next.keys.iterator().next() : "";
    }

    private Node insertAfter(Node node, Node newNode) {
        newNode.prev = node;
        newNode.next = node.next;
        node.next.prev = newNode;
        node.next = newNode;
        return newNode;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}