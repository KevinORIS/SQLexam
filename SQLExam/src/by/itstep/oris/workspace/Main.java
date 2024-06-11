package by.itstep.oris.workspace;

import by.itstep.oris.workspace.dal.PatientDAO;
import by.itstep.oris.workspace.model.entity.Hospital;
import by.itstep.oris.workspace.model.entity.Patient;

public class Main {
	public static void main(String[] args) {
		
		PatientDAO patientDAO = new PatientDAO();
		
		patientDAO.connect();
		
		Patient patient = patientDAO.findMostPayPatient();
		
		System.out.print(patient.getFirstName());
		
	}
}
