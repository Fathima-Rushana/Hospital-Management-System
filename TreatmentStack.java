package hospital;

/**
 * Requirement 3: Treatment History - Stack (LIFO)
 * Implemented manually with an array-based stack.
 */
public class TreatmentStack {

    private static final int DEFAULT_CAPACITY = 100;
    private TreatmentRecord[] records;
    private int top; // index of the top element, -1 means empty

    public TreatmentStack() {
        records = new TreatmentRecord[DEFAULT_CAPACITY];
        top = -1;
    }

    /** Push - add a completed treatment record onto the stack. */
    public void push(TreatmentRecord record) {
        if (top == records.length - 1) {
            growArray();
        }
        records[++top] = record;
        System.out.println("Treatment record pushed to history stack.");
    }

    /** Pop - remove and return the most recently completed treatment record. */
    public TreatmentRecord pop() {
        if (isEmpty()) {
            System.out.println("Treatment history stack is empty. Nothing to pop.");
            return null;
        }
        TreatmentRecord record = records[top];
        records[top--] = null;
        return record;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    private static final String ROW_FORMAT = "%-6s %-20s %-30s %-12s%n";

    /** Display all treatment records, most recent first (top of stack first). */
    public void displayStack() {
        if (isEmpty()) {
            System.out.println("No treatment records available.");
            return;
        }
        System.out.printf(ROW_FORMAT, "ID", "Name", "Treatment", "Completed");
        System.out.println("-".repeat(70));
        for (int i = top; i >= 0; i--) {
            TreatmentRecord r = records[i];
            System.out.printf(ROW_FORMAT, r.getPatientId(), r.getPatientName(),
                    r.getTreatmentDetails(), r.getCompletedDate());
        }
    }

    private void growArray() {
        TreatmentRecord[] newArray = new TreatmentRecord[records.length * 2];
        System.arraycopy(records, 0, newArray, 0, records.length);
        records = newArray;
    }

    public int size() { return top + 1; }
}
