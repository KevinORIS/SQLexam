package by.itstep.oris.workspace.model.logic;

import by.itstep.oris.workspace.model.entity.Patient;

public interface PatientManager {
	int calcTotalPatientCount();
	
	Patient findLeastPayPatient();
	
	Patient findMostPayPatient();
	
	String findMostPopularTreatment();
}
