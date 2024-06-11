package by.itstep.oris.workspace.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import by.itstep.oris.workspace.model.entity.Hospital;
import by.itstep.oris.workspace.model.entity.Patient;
import by.itstep.oris.workspace.model.logic.PatientManager;

public class PatientDAO extends AbstractDAO implements PatientManager {
	public static final String GET_ALL_PATIENTS = "SELECT * FROM patients";
	public static final String GET_PATIENT = "SELECT * FROM patients WHERE id_patient = ?";
	public static final String INSERT_PATIENT = "INSERT INTO patients"
			+ "(first_name, second_name, age, gender) VALUES(?, ?, ?, ?)";
	public static final String DELETE_PATIENT = "DELETE FROM patients WHERE id_patient = ?";
	public static final String GET_PATIENTS_AND_DISEASES = "SELECT * FROM patients "
			+ "JOIN patients_has_diseases ON patients.id_patient = patients_has_diseases.id_patient "
			+ "JOIN diseases ON patients_has_diseases.id_disease = diseases.id_disease;";

	@Override
	public void insert(Patient patient) {
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_PATIENT);
			
			statement.setString(1, patient.getFirstName());
			statement.setString(2, patient.getSecondName());
			statement.setInt(3, patient.getAge());
			statement.setByte(4, patient.getGender());
			
			statement.executeUpdate();
			
			} catch (SQLException e) {
				System.out.println(e);
			}
	}

	@Override
	public void delete(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_PATIENT);
			
			statement.setInt(1, id);
			
			statement.executeUpdate();
			
			} catch (SQLException e) {
				System.out.println(e);
			}
	}

	@Override
	public void update(int id, Patient patient) {
		delete(id);
		insert(patient);
	}

	@Override
	public Patient get(int id) {
		Patient patient = null;
		try {
		PreparedStatement statement = connection.prepareStatement(GET_PATIENT);
		
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		resultSet.next();
		
		patient = new Patient(
				resultSet.getString("first_name"),
				resultSet.getString("second_name"),
				resultSet.getInt("age"),
				resultSet.getByte("gender"));
		
		} catch (SQLException e) {
			System.out.println(e);
		}
		return patient;
	}

	@Override
	public Hospital getAll() {
		Hospital hospital = new Hospital();

		try {
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(GET_ALL_PATIENTS);

			while (resultSet.next()) {
				Patient patient = new Patient(
						resultSet.getString("first_name"),
						resultSet.getString("second_name"),
						resultSet.getInt("age"),
						resultSet.getByte("gender"));
				
				hospital.addPatient(patient);
			}

		} catch (SQLException e) {
			System.out.print(e);
		}
		return hospital;
	}

	@Override
	public int calcTotalPatientCount() {
		Hospital hospital = getAll();
		
		return hospital.getPatientsCount();
	}

	@Override
	public Patient findLeastPayPatient() {

		int leastPayPatientId = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(GET_PATIENTS_AND_DISEASES);
		
			ResultSet resultSet = statement.executeQuery();
			
			int leastPay = Integer.MAX_VALUE;
			int patientId = 0;
			int patientPay = 0;
			
			while(resultSet.next()) {
				if(patientId == resultSet.getInt("id_patient")) {
					patientPay += resultSet.getInt("treatment_price");
				} else {
					patientId = resultSet.getInt("id_patient");
					patientPay = resultSet.getInt("treatment_price");
					if(leastPay > patientPay) {
						leastPay = patientPay;
						leastPayPatientId = patientId;
					}
				}
				
			}
			
		} catch (SQLException e) {
			System.out.print(e);
		}
		return get(leastPayPatientId);
	}

	@Override
	public Patient findMostPayPatient() {
		
		int mostPayPatientId = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(GET_PATIENTS_AND_DISEASES);
		
			ResultSet resultSet = statement.executeQuery();
			
			int mostPay = 0;
			int patientId = 0;
			int patientPay = 0;

			while(resultSet.next()) {
			    int currentPatientId = resultSet.getInt("id_patient");
			    int treatmentPrice = resultSet.getInt("treatment_price");
			    
			    if(patientId != currentPatientId) {
			        if(patientPay > mostPay) {
			            mostPay = patientPay;
			            mostPayPatientId = patientId;
			        }
			        
			        patientId = currentPatientId;
			        patientPay = treatmentPrice;
			    } else {
			        patientPay += treatmentPrice;
			    }
			}

			if (patientPay > mostPay) {
			    mostPay = patientPay;
			    mostPayPatientId = patientId;
			}
		
		} catch (SQLException e) {
			System.out.print(e);
		}
		return get(mostPayPatientId);	
	}

	@Override
	public String findMostPopularTreatment() {
		Map<String, Integer> nameCountMap = new HashMap<>();
	    
	    try {
	        PreparedStatement statement = connection.prepareStatement(GET_PATIENTS_AND_DISEASES);
	        ResultSet resultSet = statement.executeQuery();
	        
	        while (resultSet.next()) {
	            String currentDisease = resultSet.getString("name");
	            nameCountMap.put(currentDisease, nameCountMap.getOrDefault(currentDisease, 0) + 1);
	        }
	        
	    } catch (SQLException e) {
	        System.out.print(e);
	    }
	    
	    
	    String mostCommonDisease = "";
	    int maxCount = 0;
	    
	    for (Map.Entry<String, Integer> entry : nameCountMap.entrySet()) {
	        if (entry.getValue() > maxCount) {
	            maxCount = entry.getValue();
	            mostCommonDisease = entry.getKey();
	        }
	    }
	    
	    return mostCommonDisease;
	}

}
