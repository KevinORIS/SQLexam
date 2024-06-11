package by.itstep.oris.workspace.model.entity;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class Hospital implements Iterable<Patient> {
	private List<Patient> patients;
	
	public Hospital() {
        patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }
    
    public int getPatientsCount() {
        return patients.size();
    }
    
    @Override
    public Iterator<Patient> iterator() {
        return patients.iterator();
    }

}
