package by.itstep.oris.workspace.model.logic;

public interface PatientManager {
	void calcTotalPatientCount();
	
	void findLeastPayPatient();
	
	void findMostPayPatient();
	
	void findMostPopularTreatment();
}
