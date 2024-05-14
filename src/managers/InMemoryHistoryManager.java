package managers;

import managers.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historyList = new ArrayList<>();
    Map<Integer, Node> historyMap =  new HashMap<>();
    // CustomLinkedList
    public Node head = null;
    public Node tail = null;
    private int size = 0;

    @Override
    public void add(Task task) {
        Node newNode = new Node(task);
        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
        } else{
            if (size == 10) {
                removeNode(head);
                System.out.println("Удален первый узел");
            }
        }
        linkLast(task);
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        getTasks();
        return historyList;
    }

    public void linkLast(Task task) {
        Node newNode = new Node(task);
        if (head == null)
            head = newNode;
        else
            tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    public void getTasks() {
        historyList.clear();
        Node currentNode = head;
        while (currentNode != null) {
            historyList.add(currentNode.data);
            currentNode = currentNode.next;
        }
    }

    private void removeNode(Node node) {
        Node currentNode = head;
        if (size!=0) {
            for (int i=0;i<size;i++) {
                if (currentNode.data.getId() == node.data.getId()) {
                    Node nextNode = currentNode.next;
                    Node prevNode = currentNode.prev;
                    if (nextNode == null) {
                        tail = prevNode;
                    } else {
                        nextNode.prev = prevNode;
                    }
                    if (prevNode == null) {
                        head = nextNode;
                    }
                    else {
                        prevNode.next = nextNode;
                    }
                    size--;
                    break;
                }
                currentNode = currentNode.next;
            }
        }
    }
}

