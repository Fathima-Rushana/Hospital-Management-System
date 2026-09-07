package hospital;

/**
 * Requirement 4: Patient Visit History - Singly Linked List
 * Each Patient object holds one of these. Nodes are added at the tail
 * so visits display in chronological (oldest -> newest) order.
 */
public class VisitHistory {

    // Internal node class - this is what makes it a "singly linked list"
    private static class Node {
        Visit visit;
        Node next;
        Node(Visit visit) { this.visit = visit; }
    }

    private Node head;
    private Node tail;
    private int size;

    /** Add a new visit to the end of the history. */
    public void addVisit(Visit visit) {
        Node newNode = new Node(visit);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /** Remove a visit by its visitId. Returns true if removed. */
    public boolean removeVisit(int visitId) {
        Node current = head;
        Node previous = null;

        while (current != null) {
            if (current.visit.getVisitId() == visitId) {
                if (previous == null) {
                    // removing the head node
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                if (current == tail) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false; // not found
    }

    /** Search for a visit by its visitId. Returns the Visit or null. */
    public Visit searchVisit(int visitId) {
        Node current = head;
        while (current != null) {
            if (current.visit.getVisitId() == visitId) {
                return current.visit;
            }
            current = current.next;
        }
        return null;
    }

    private static final String ROW_FORMAT = "%-8s %-12s %-18s %-16s %-20s%n";

    /** Display the full visit history for a patient. */
    public void displayHistory() {
        if (head == null) {
            System.out.println("   (No visit history found)");
            return;
        }
        System.out.printf(ROW_FORMAT, "VisitID", "Date", "Doctor", "Diagnosis", "Treatment");
        System.out.println("-".repeat(74));
        Node current = head;
        while (current != null) {
            Visit v = current.visit;
            System.out.printf(ROW_FORMAT, v.getVisitId(), v.getVisitDate(), v.getDoctorName(),
                    v.getDiagnosis(), v.getTreatment());
            current = current.next;
        }
    }

    public int size() { return size; }
}
