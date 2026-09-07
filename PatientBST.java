package hospital;

/**
 * Requirement 1: Patient Records - Binary Search Tree
 * Keyed on patientId. Supports insert, search, delete, and in-order traversal.
 */
public class PatientBST {

    private static class TreeNode {
        Patient patient;
        TreeNode left, right;
        TreeNode(Patient patient) { this.patient = patient; }
    }

    private TreeNode root;

    // ---------- INSERT ----------
    public void insert(Patient patient) {
        root = insertRec(root, patient);
    }

    private TreeNode insertRec(TreeNode node, Patient patient) {
        if (node == null) {
            return new TreeNode(patient);
        }
        if (patient.getPatientId() < node.patient.getPatientId()) {
            node.left = insertRec(node.left, patient);
        } else if (patient.getPatientId() > node.patient.getPatientId()) {
            node.right = insertRec(node.right, patient);
        } else {
            System.out.println("Patient ID " + patient.getPatientId() + " already exists. Insert ignored.");
        }
        return node;
    }

    // ---------- SEARCH ----------
    public Patient search(int patientId) {
        TreeNode result = searchRec(root, patientId);
        return (result == null) ? null : result.patient;
    }

    private TreeNode searchRec(TreeNode node, int patientId) {
        if (node == null || node.patient.getPatientId() == patientId) {
            return node;
        }
        if (patientId < node.patient.getPatientId()) {
            return searchRec(node.left, patientId);
        }
        return searchRec(node.right, patientId);
    }

    // ---------- DELETE ----------
    public boolean delete(int patientId) {
        if (search(patientId) == null) {
            return false;
        }
        root = deleteRec(root, patientId);
        return true;
    }

    private TreeNode deleteRec(TreeNode node, int patientId) {
        if (node == null) return null;

        if (patientId < node.patient.getPatientId()) {
            node.left = deleteRec(node.left, patientId);
        } else if (patientId > node.patient.getPatientId()) {
            node.right = deleteRec(node.right, patientId);
        } else {
            // Node found - handle the 3 classic BST deletion cases
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Two children: replace with in-order successor (smallest in right subtree)
            TreeNode successor = findMin(node.right);
            node.patient = successor.patient;
            node.right = deleteRec(node.right, successor.patient.getPatientId());
        }
        return node;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // ---------- IN-ORDER TRAVERSAL ----------
    private static final String ROW_FORMAT = "%-6s %-20s %-5s %-14s %-15s%n";

    public void inOrderDisplay() {
        if (root == null) {
            System.out.println("No patient records found.");
            return;
        }
        System.out.printf(ROW_FORMAT, "ID", "Name", "Age", "Contact", "Condition");
        System.out.println("-".repeat(64));
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode node) {
        if (node == null) return;
        inOrderRec(node.left);
        Patient p = node.patient;
        System.out.printf(ROW_FORMAT,
                p.getPatientId(), p.getName(), p.getAge(), p.getContactNumber(), p.getMedicalCondition());
        inOrderRec(node.right);
    }
}
