package hospital;

/** Represents one completed treatment, pushed onto the TreatmentStack. */
public class TreatmentRecord {
    private int patientId;
    private String patientName;
    private String treatmentDetails;
    private String completedDate;

    public TreatmentRecord(int patientId, String patientName, String treatmentDetails, String completedDate) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.treatmentDetails = treatmentDetails;
        this.completedDate = completedDate;
    }

    public int getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getTreatmentDetails() { return treatmentDetails; }
    public String getCompletedDate() { return completedDate; }

    @Override
    public String toString() {
        return "PatientID: " + patientId +
                " | Name: " + patientName +
                " | Treatment: " + treatmentDetails +
                " | Completed: " + completedDate;
    }
}
