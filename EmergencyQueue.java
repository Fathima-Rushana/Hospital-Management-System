package hospital;

/**
 * Requirement 2: Emergency Patient Queue - Queue (FIFO)
 * Implemented manually with a singly linked list (no java.util.Queue used,
 * so the underlying data structure is explicit for the assignment).
 */
public class EmergencyQueue {

    private static class QueueNode {
        Patient patient;
        QueueNode next;
        QueueNode(Patient patient) { this.patient = patient; }
    }

    private QueueNode front;
    private QueueNode rear;
    private int size;

    /** Enqueue - add a patient to the back of the waiting line. */
    public void enqueue(Patient patient) {
        QueueNode newNode = new QueueNode(patient);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
        System.out.println("Patient " + patient.getPatientId() + " added to emergency queue.");
    }

    /** Dequeue - remove and return the next patient for treatment. */
    public Patient dequeue() {
        if (isEmpty()) {
            System.out.println("Emergency queue is empty. No patient to treat.");
            return null;
        }
        Patient patient = front.patient;
        front = front.next;
        if (front == null) rear = null; // queue became empty
        size--;
        return patient;
    }

    public boolean isEmpty() {
        return front == null;
    }

    private static final String ROW_FORMAT = "%-4s %-6s %-20s %-5s %-14s %-15s%n";

    /** Display all patients currently waiting, in FIFO order. */
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No patients currently waiting.");
            return;
        }
        System.out.printf(ROW_FORMAT, "Pos", "ID", "Name", "Age", "Contact", "Condition");
        System.out.println("-".repeat(68));
        QueueNode current = front;
        int position = 1;
        while (current != null) {
            Patient p = current.patient;
            System.out.printf(ROW_FORMAT, position, p.getPatientId(), p.getName(),
                    p.getAge(), p.getContactNumber(), p.getMedicalCondition());
            current = current.next;
            position++;
        }
    }

    public int size() { return size; }
}
