import java.util.*;

public class Track implements ListADT<Pod> {
    protected LinkedNode head;
    protected LinkedNode tail;
    private int size;

    public Track() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(Pod newElement) {
        if (newElement == null) {
            throw new NullPointerException("Cannot add null element");
        }
        LinkedNode newNode = new LinkedNode(newElement);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    @Override
    public Pod get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        LinkedNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getPod();
    }

    @Override
    public boolean contains(Pod toFind) {
        LinkedNode current = head;
        while (current != null) {
            if (current.getPod().equals(toFind)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public Pod remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        LinkedNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        Pod removedPod = current.getPod();
        LinkedNode prev = current.getPrev();
        LinkedNode next = current.getNext();
        if (prev != null) {
            prev.setNext(next);
        } else {
            head = next;
        }
        if (next != null) {
            next.setPrev(prev);
        } else {
            tail = prev;
        }
        size--;
        return removedPod;
    }
    
    public boolean addPassenger(String name, boolean isFirstClass) {
        LinkedNode current = head;
        while (current != null) {
            Pod pod = current.getPod();
            try {
                // Compare the pod class with the desired class
                if (pod.getPodClass() == (isFirstClass ? Pod.FIRST : Pod.ECONOMY) && !pod.isFull()) {
                    pod.addPassenger(name);
                    return true;
                }
            } catch (MalfunctioningPodException e) {
                // Handle or log the exception as needed, continue to next pod
            }
            current = current.getNext();
        }
        return false;
    }

    public int findFirstNonFunctional() {
        LinkedNode current = head;
        int index = 0;
        while (current != null) {
            Pod pod = current.getPod();
            if (!pod.isFunctional()) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1; // Return -1 if no non-functional pod is found
    }

    public int findPassenger(String name) {
        LinkedNode current = head;
        int index = 0;
        while (current != null) {
            Pod pod = current.getPod();
            try {
                    if (pod.containsPassenger(name)) {
                        return index;
                    }
               
            } catch (MalfunctioningPodException e) {
                // Handle or log the exception as needed, continue to next pod
            }
            current = current.getNext();
            index++;
        }
        return -1; // Return -1 if the passenger is not found
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LinkedNode current = head;
        while (current != null) {
            try {
                sb.append(current.getPod().toString()).append("\n");
            } catch (Exception e) {
                sb.append("Malfunctioning Pod\n");
            }
            current = current.getNext();
        }
        return sb.toString();
    }

}