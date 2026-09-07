package hospital;

import java.util.Scanner;

/**
 * Ties together all four required data structures:
 *  1. PatientBST      - Binary Search Tree for patient records
 *  2. EmergencyQueue   - Queue for patients waiting for treatment
 *  3. TreatmentStack   - Stack for completed treatment history
 *  4. VisitHistory     - Singly Linked List (one per patient) for past visits
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static PatientBST patientBST = new PatientBST();
    private static EmergencyQueue emergencyQueue = new EmergencyQueue();
    private static TreatmentStack treatmentStack = new TreatmentStack();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> searchPatient();
                case 3 -> deletePatient();
                case 4 -> patientBST.inOrderDisplay();
                case 5 -> addToEmergencyQueue();
                case 6 -> treatNextPatient();
                case 7 -> emergencyQueue.displayQueue();
                case 8 -> viewTreatmentHistory();
                case 9 -> addVisitToPatient();
                case 10 -> viewPatientVisitHistory();
                case 11 -> removeVisitFromPatient();
                case 12 -> searchVisitInPatientHistory();
                case 0 -> System.out.println("Exiting system. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM =====");
        System.out.println(" 1. Register new patient (BST insert)");
        System.out.println(" 2. Search patient by ID (BST search)");
        System.out.println(" 3. Delete patient (BST delete)");
        System.out.println(" 4. Display all patients - ascending ID (BST in-order)");
        System.out.println(" 5. Add patient to emergency queue (Enqueue)");
        System.out.println(" 6. Treat next patient (Dequeue)");
        System.out.println(" 7. Display emergency queue");
        System.out.println(" 8. View treatment history (Stack)");
        System.out.println(" 9. Add visit to patient history (Linked List)");
        System.out.println("10. View a patient's visit history");
        System.out.println("11. Remove a visit from patient history");
        System.out.println("12. Search a visit in patient's history");
        System.out.println(" 0. Exit");
    }

    // ---------- BST operations ----------
    private static void registerPatient() {
        int id = readInt("Enter Patient ID: ");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        int age = readInt("Enter Age: ");
        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine();

        Patient patient = new Patient(id, name, age, contact, condition);
        patientBST.insert(patient);
        System.out.println("Patient registered successfully.");
    }

    private static void searchPatient() {
        int id = readInt("Enter Patient ID to search: ");
        Patient patient = patientBST.search(id);
        if (patient != null) {
            System.out.println("Found: " + patient);
        } else {
            System.out.println("No patient found with ID " + id);
        }
    }

    private static void deletePatient() {
        int id = readInt("Enter Patient ID to delete: ");
        boolean removed = patientBST.delete(id);
        System.out.println(removed ? "Patient deleted." : "No patient found with ID " + id);
    }

    // ---------- Queue operations ----------
    private static void addToEmergencyQueue() {
        int id = readInt("Enter Patient ID to add to queue: ");
        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("Patient not found. Please register the patient first.");
            return;
        }
        emergencyQueue.enqueue(patient);
    }

    private static void treatNextPatient() {
        Patient patient = emergencyQueue.dequeue();
        if (patient == null) return;

        System.out.println("Now treating: " + patient);
        System.out.print("Enter treatment details: ");
        String treatment = scanner.nextLine();
        System.out.print("Enter completion date (e.g. 2026-09-06): ");
        String date = scanner.nextLine();

        TreatmentRecord record = new TreatmentRecord(patient.getPatientId(), patient.getName(), treatment, date);
        treatmentStack.push(record);
    }

    // ---------- Stack operations ----------
    private static void viewTreatmentHistory() {
        System.out.println("Treatment history (most recent first):");
        treatmentStack.displayStack();
    }

    // ---------- Linked List operations ----------
    private static void addVisitToPatient() {
        int patientId = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        int visitId = readInt("Enter Visit ID: ");
        System.out.print("Enter Visit Date: ");
        String date = scanner.nextLine();
        System.out.print("Enter Doctor Name: ");
        String doctor = scanner.nextLine();
        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Enter Treatment: ");
        String treatment = scanner.nextLine();

        Visit visit = new Visit(visitId, date, doctor, diagnosis, treatment);
        patient.getVisitHistory().addVisit(visit);
        System.out.println("Visit added to patient history.");
    }

    private static void viewPatientVisitHistory() {
        int patientId = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.println("Visit history for " + patient.getName() + ":");
        patient.getVisitHistory().displayHistory();
    }

    private static void removeVisitFromPatient() {
        int patientId = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        int visitId = readInt("Enter Visit ID to remove: ");
        boolean removed = patient.getVisitHistory().removeVisit(visitId);
        System.out.println(removed ? "Visit removed." : "Visit ID not found.");
    }

    private static void searchVisitInPatientHistory() {
        int patientId = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        int visitId = readInt("Enter Visit ID to search: ");
        Visit visit = patient.getVisitHistory().searchVisit(visitId);
        if (visit != null) {
            System.out.println("Found: " + visit);
        } else {
            System.out.println("Visit ID " + visitId + " not found for this patient.");
        }
    }

    // ---------- Utility ----------
    private static int readInt(String prompt) {
        System.out.print(prompt);
   // Edge case: If the removed node was the tail, update the tail pointer to previous
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
    }
}
